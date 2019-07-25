package zetta.lang.datatype;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.util.Scanner;

import zetta.Zetta;
import zetta.exception.EnumFileException;
import zetta.exception.FileException;
import zetta.exception.UnexpectedDataTypeException;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.DataType;
import zetta.lang.Method;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.token.TokenMethodCall;
import zetta.token.TokenString;
import zetta.token.TokenVariable;

/**
 * Represents a file on the local filesystem
 * 
 * @author samueltebbs
 */
public class DataTypeFile extends DataType {

    private long fileSize = 0;

    /**
     * Each holds an index of a File variable's Object[]<br>
     * <b>Name</b> = the index where the file name is held<br>
     * <b>File</b> = the index where the File object is held<br>
     * <b>In</b> = the index where the Scanner object is held (only if the file
     * exists and is not a directory)<br>
     * <b>Mode</b> = the index where the file mode integer is held<br>
     * <b>Raf</b> = the index where the RandomAccessFile object is held<br>
     * <b>Max</b> = the greatest index used
     */
    public static final int INDEX_NAME = 0, INDEX_FILE = 1, INDEX_IN = 2,
	    INDEX_MODE = 3, INDEX_RAF = 4, INDEX_MAX = 4;

    /**
     * Each holds an integer associated with a file mode that can be used during
     * file operations<br>
     * Use <b>INDEX_MODE</b> to retrieve a File variable's index mode<br>
     * <b>Append</b> = the file's contents should be preserved<br>
     * <b>Truncate</b> = the file's contents / the file itself should be deleted
     */
    public static final int MODE_APPEND = 0, MODE_TRUNCATE = 1, MAX_MODE = 1;

    public DataTypeFile(final String name) {
	super(name);
    }

    @Override
    public String docExtraInfo() {
	return "Please note that File methods can be destructive if used without caution.<br><br>"
		+ "File modes determine how files are handled when being manipulated. File modes are set for each file and are not global.<br>"
		+ "<b>0</b> = Append mode. An existing file will not be deleted when creating one with the same name and the file's contents will not be cleared.<br>"
		+ "<b>1</b> = Truncate mode. An existing file will be deleted when creating one with the same name and the file's contents will be cleared.<br>"
		+ "Note that external libraries may not conform to file modes or may deviate from standard behaviour.";
    }

    @Override
    public String sizeDesc() {
	return "Returns the size of the file";
    }

    @Override
    public String stringDesc() {
	return "Returns the string that was used to initialise the variable";
    }

    @Override
    public String docDescription() {
	return "Represents a file that can be written to and read from";
    }

    @Override
    public String[][] constructArgs() {
	return new String[][] { { Zetta.dtString.name } };
    }

    @Override
    public String[] constructDesc() {
	return new String[] { "Sets the file path to the String" };
    }

    @Override
    public boolean canConstruct(final Token[] args, final String raw) {
	if (Method.checkLength(args, true, 1)) {
	    if ((args[0] instanceof TokenString)
		    || (args[0] instanceof TokenVariable)
		    || (args[0] instanceof TokenMethodCall)) return true;
	    else {
		new UnexpectedTokenException(args[0], Zetta.tokenString,
			Zetta.tokenVariable, Zetta.tokenMethodCall);
	    }
	}
	return false;
    }

    @Override
    public Variable initVariable(final String name, final Token[] args) {
	final Variable v = new Variable(Zetta.dtString, name, new Object[2]);
	String fileName = "";
	fileName = Token.getString(args[0]);
	v.dType = Zetta.dtFile;
	final File f = new File(fileName);
	v.setValue(new Object[INDEX_MAX + 1]);
	v.setIndex(INDEX_NAME, fileName);
	v.setIndex(INDEX_FILE, f);
	v.setIndex(INDEX_MODE, 0);
	if (!f.isDirectory() && f.exists()) {
	    try {
		v.setIndex(INDEX_RAF, new RandomAccessFile(f, "rw"));
		v.setIndex(INDEX_IN, new Scanner((File) v.getIndex(INDEX_FILE)));
	    } catch (final Exception e1) {
		new FileException(fileName, e1);
	    }
	}
	return v;
    }

    public Variable set(final Variable v, final Token obj) {
	return initVariable(v.name, new Token[] { obj });
    }

    @Override
    public void setVariableToObject(final Variable from, final Object to) {
	from.setIndex(INDEX_NAME, to);
	from.setIndex(INDEX_FILE, new File(to.toString()));
	try {
	    from.setIndex(INDEX_IN,
		    new Scanner((File) from.getIndex(INDEX_FILE)));
	} catch (final FileNotFoundException e) {
	    new FileException(to.toString(), e);
	}
    }

    @Override
    public long size(final Variable v) {
	final File file = (File) v.getIndex(INDEX_FILE);
	fileSize = 0;
	if (file.isDirectory()) {
	    fileSize = dirSize(file);
	} else {
	    fileSize = file.length();
	}
	return fileSize;
    }

    private long dirSize(final File dir) {
	long size = 0;
	final File[] files = dir.listFiles();
	for (int c = 0; c < files.length; c++) {
	    final File f = files[c];
	    if (f.isDirectory()) {
		size += dirSize(f);
	    } else {
		size += f.length();
	    }
	}
	return size;
    }

    @Override
    public String string(final Variable v) {
	return (String) v.getIndex(INDEX_NAME);
    }

    @Override
    public Variable operatorAssign(final Variable lhs, final Token token) {
	if (token instanceof TokenMethodCall) {
	    if (Zetta.getMethodReturnType((TokenMethodCall) token) == this) {
		final Object v = Zetta.executeMethod((TokenMethodCall) token);
		if (v instanceof Variable) {
		    if (((Variable) v).dType == Zetta.dtFile) return (Variable) v;
		    else {
			new UnexpectedDataTypeException(((Variable) v).dType,
				Zetta.dtFile);
		    }
		}
	    } else {
		new UnexpectedDataTypeException(
			Zetta.getMethodReturnType((TokenMethodCall) token),
			this);
	    }
	} else if (token instanceof TokenVariable) return Zetta
		.getVariableOfType(token, Zetta.dtFile);
	else {
	    new UnexpectedTokenException(token, Zetta.tokenMethodCall,
		    Zetta.tokenVariable);
	}
	return null;
    }

    /**
     * Returns a file object if it exists and is not a directory, otherwise null
     * 
     * @param the
     *            variable to get the file from
     * @return the File object
     */
    public static File getFile(final Variable v) {
	final File f = (File) v.getIndex(INDEX_FILE);
	if (f == null) return null;
	if (!f.exists()) {
	    new FileException(f.getName(), null, EnumFileException.DOESNT_EXIST);
	}
	if (f.isDirectory()) {
	    new FileException(f.getName(), null, EnumFileException.IS_A_DIR);
	}
	return f;
    }

    /**
     * Returns a file object if it exists and is a directory, otherwise null
     * 
     * @param the
     *            variable to get the file from
     * @return the File object
     */
    public static File getDir(final Variable v) {
	final File f = (File) v.getIndex(INDEX_FILE);
	if (f == null) return null;
	if (!f.exists()) {
	    new FileException(f.getName(), null, EnumFileException.DOESNT_EXIST);
	}
	if (!f.isDirectory()) {
	    new FileException(f.getName(), null, EnumFileException.NOT_A_DIR);
	}
	return f;
    }

    public static File deleteFileIfMode(final Variable v) {
	final File f = ((File) v.getIndex(DataTypeFile.INDEX_FILE));
	if (((int) v.getIndex(DataTypeFile.INDEX_MODE) == DataTypeFile.MODE_TRUNCATE)
		&& f.exists()) {
	    f.delete();
	}
	return f;
    }

    public static File clearFileIfMode(final Variable v) {
	final File f = ((File) v.getIndex(DataTypeFile.INDEX_FILE));
	if (((int) v.getIndex(DataTypeFile.INDEX_MODE) == DataTypeFile.MODE_TRUNCATE)
		&& f.exists()) {
	    try {
		new PrintStream(f);
	    } catch (final FileNotFoundException e) {
		new FileException(f.getName(), e);
	    }
	}
	return f;
    }

    @Override
    public Variable sortArray(final Variable obj) {
	final Variable result = new Variable(Zetta.dtArray, "",
		new Object[obj.getValue().length]);
	final Object[] files = new Object[result.getValue().length];
	files[0] = Zetta.dtFile;
	for (int c = 1; c < files.length; c++) {
	    files[c] = obj.getIndex(c);
	}
	boolean done = false;
	while (!done) {
	    done = true;
	    for (int c = 2; c < files.length; c++) {
		final Variable file1 = (Variable) files[c];
		final Variable file2 = (Variable) files[c - 1];
		if (file1.dType.size(file1) < file2.dType.size(file2)) {
		    done = false;
		    final Variable temp = file2;
		    files[c - 1] = file1;
		    files[c] = temp;
		}
	    }
	}
	result.setValue(files);
	return result;
    }

    public static RandomAccessFile getRAF(final Variable v) {
	if (v.getIndex(INDEX_RAF) == null) {
	    try {
		v.setIndex(INDEX_RAF,
			new RandomAccessFile((File) v.getIndex(INDEX_FILE),
				"rw"));
	    } catch (final FileNotFoundException e) {
		new FileException(((File) v.getIndex(INDEX_FILE)).getName(), e,
			EnumFileException.DOESNT_EXIST);
	    }
	}
	return (RandomAccessFile) v.getIndex(INDEX_RAF);
    }

}
