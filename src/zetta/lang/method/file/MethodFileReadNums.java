package zetta.lang.method.file;

import java.io.File;
import java.util.LinkedList;

import zetta.Zetta;
import zetta.exception.EnumFileException;
import zetta.exception.FileException;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeFile;

/**
 * 
 * @author samueltebbs, 23:02:34 - 1 Sep 2014
 */
public class MethodFileReadNums extends MethodFileRead {

    public MethodFileReadNums(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns the numbers found in the file's contents" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtArray.name + "<" + Zetta.dtInteger.name
		+ ">" };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable v = Zetta.getVariableOfType(object, Zetta.dtFile);
	final Variable array = new Variable(Zetta.dtArray, "", new Object[0]);
	final File f = ((File) v.getIndex(DataTypeFile.INDEX_FILE));
	if (!f.isDirectory()) {
	    try {
		final String[] contents = Zetta.toString(f, " ").split(" ");
		final LinkedList<String> numbers = new LinkedList<String>();
		for (final String str : contents) {
		    if (Token.isNumber(str)) {
			numbers.add(str);
		    }
		}
		array.setValue(new Object[numbers.size() + 1]);
		array.setIndex(0, Zetta.dtInteger);
		for (int c = 0; c < numbers.size(); c++) {
		    array.setIndex(c + 1, new Variable(Zetta.dtInteger, "",
			    Double.parseDouble(numbers.get(c))));
		}
		return array;
	    } catch (final Exception e) {
		new FileException(v, e);
	    }
	} else {
	    new FileException(f, EnumFileException.IS_A_DIR);
	}
	return null;
    }

}
