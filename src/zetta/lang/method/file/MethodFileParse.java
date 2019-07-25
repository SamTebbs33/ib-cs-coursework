package zetta.lang.method.file;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

import zetta.Zetta;
import zetta.exception.EnumFileException;
import zetta.exception.FileException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeFile;
import zetta.lang.util.FileFormat;

/**
 * 
 * @author samueltebbs, 00:20:38 - 30 Aug 2014
 */
public class MethodFileParse extends MethodFileWrite {

    public MethodFileParse(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Parses the file according to the file format given by the argument and returns a Dictionary that contains the file's contents. "
		+ "<br>See file formats for specific structure and key-value relation" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtDictionary.name + "<"
		+ Zetta.dtString.name + ">" };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable v = Zetta.getVariableOfType(object, Zetta.dtFile);
	final String format = Token.getString(args[0]);
	for (final FileFormat ff : FileFormat.formats) {
	    if (ff.name.equals(format)) {
		final HashMap<String, String> map = ff.parse((File) v
			.getIndex(DataTypeFile.INDEX_FILE));
		final Object[] array = new Object[(map.size() * 2) + 1];
		array[0] = Zetta.dtString;
		final Set<String> set = map.keySet();
		int c = 1;
		for (final String str : set) {
		    array[c] = new Variable(Zetta.dtString, "", map.get(str));
		    array[c + (array.length / 2)] = str;
		    c++;
		}
		final Variable v2 = new Variable(Zetta.dtDictionary, "",
			Zetta.dtString);
		v2.setValue(array);
		return v2;
	    }
	}
	new FileException(format, null, EnumFileException.NO_FORMAT);
	return null;
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtDictionary;
    }

}
