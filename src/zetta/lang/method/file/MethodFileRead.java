package zetta.lang.method.file;

import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

import zetta.Zetta;
import zetta.exception.FileException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeFile;

public class MethodFileRead extends MethodFile {

    public MethodFileRead(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	return checkLength(args, true, 0);
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { "" } };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns a String Array containing the lines in the file" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtArray.name + "<" + Zetta.dtString.name
		+ ">" };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable v = Zetta.getVariableOfType(object, Zetta.dtFile);
	final Variable array = new Variable(Zetta.dtArray, "", new Object[0]);
	final File f = DataTypeFile.getFile(v);
	try {
	    final Scanner s = new Scanner(f);
	    v.setIndex(DataTypeFile.INDEX_IN, s);
	    final LinkedList<String> lines = new LinkedList<String>();
	    while (s.hasNextLine()) {
		lines.add(s.nextLine());
	    }
	    array.setValue(new Object[lines.size() + 1]);
	    array.setIndex(0, Zetta.dtString);
	    int c = 1;
	    for (final String str : lines) {
		array.setIndex(c, asVariable(Zetta.dtString, str));
		c++;
	    }
	    return array;
	} catch (final Exception e) {
	    new FileException(v, e);
	}
	return null;
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtArray;
    }

}
