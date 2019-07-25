package zetta.lang.method.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import zetta.Zetta;
import zetta.exception.FileException;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeFile;
import zetta.token.TokenMethodCall;
import zetta.token.TokenString;
import zetta.token.TokenVariable;

public class MethodFileArchive extends MethodFile {

    public MethodFileArchive(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Compresses and archives the file with the name of the argument then returns the archive" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtFile.name };
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtString.name } };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 1)) {
	    if ((args[0] instanceof TokenVariable)
		    || (args[0] instanceof TokenString)
		    || (args[0] instanceof TokenMethodCall)) return true;
	    new UnexpectedTokenException(args[0], Zetta.tokenVariable,
		    Zetta.tokenString);
	}
	return false;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable obj = Zetta.getVariableOfType(object, Zetta.dtFile);
	String archName = "";
	if (args[0] instanceof TokenString) {
	    archName = args[0].value;
	} else if (args[0] instanceof TokenMethodCall) {
	    archName = ((TokenMethodCall) args[0]).method.execute(
		    args[0].subTokens, ((TokenMethodCall) args[0]).object)
		    .toString();
	} else {
	    final Variable v = Zetta.getVariableOfType(args[0]);
	    archName = v.dType.string(v);
	}
	ZipOutputStream out = null;
	try {
	    out = new ZipOutputStream(new FileOutputStream(archName));
	} catch (final FileNotFoundException e) {
	    new FileException(obj.dType.string(obj), e);
	    Zetta.exit();
	}
	final byte[] b = new byte[1024];
	if (((File) obj.getIndex(DataTypeFile.INDEX_FILE)).isDirectory()) {
	    final int parentPathLength = ((File) obj
		    .getIndex(DataTypeFile.INDEX_FILE)).getAbsolutePath()
		    .length();
	    final File[] files = ((File) obj.getIndex(DataTypeFile.INDEX_FILE))
		    .listFiles();
	    final LinkedList<File> allFiles = new LinkedList<File>();
	    for (final File f : files) {
		allFiles.add(f);
	    }
	    for (int c = 0; c < allFiles.size(); c++) {
		final File file = allFiles.get(c);
		if (file.getName().startsWith(".")) {
		    continue;
		}
		if (file.isDirectory()) {
		    final File[] files2 = file.listFiles();
		    for (final File f : files2) {
			allFiles.add(f);
		    }
		    continue;
		}
		FileInputStream fis = null;
		try {
		    fis = new FileInputStream(file);
		} catch (final FileNotFoundException e) {
		    new FileException(obj.dType.string(obj), e);
		}
		try {
		    out.putNextEntry(new ZipEntry(file.getAbsolutePath()
			    .substring(parentPathLength)));
		} catch (final IOException e) {
		    new FileException(obj.dType.string(obj), e);

		}
		int length;
		try {
		    while ((length = fis.read(b)) > 0) {
			out.write(b, 0, length);
		    }
		} catch (final IOException e) {
		    new FileException(obj.dType.string(obj), e);

		}
		try {
		    out.closeEntry();
		} catch (final IOException e) {
		    new FileException(obj.dType.string(obj), e);

		}
		try {
		    fis.close();
		} catch (final IOException e) {
		    new FileException(obj.dType.string(obj), e);

		}

	    }
	} else {
	    FileInputStream in = null;
	    try {
		in = new FileInputStream(obj.dType.string(obj));
	    } catch (final FileNotFoundException e) {
		new FileException(obj.dType.string(obj), e);

	    }
	    try {
		out.putNextEntry(new ZipEntry(obj.dType.string(obj)));
	    } catch (final IOException e) {
		new FileException(obj.dType.string(obj), e);

	    }
	    int count;

	    try {
		while ((count = in.read(b)) > 0) {
		    out.write(b, 0, count);
		}
	    } catch (final IOException e) {
		new FileException(obj.dType.string(obj), e);

	    }
	    try {
		in.close();
	    } catch (final IOException e) {
		new FileException(obj.dType.string(obj), e);

	    }
	}
	try {
	    out.close();
	    return Zetta.dtFile.initVariable("", args);
	} catch (final IOException e) {
	    new FileException(obj.dType.string(obj), e);
	}

	return Zetta.dtFile.initVariable("", new TokenString("d" + archName
		+ "d"));
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtFile;
    }

}
