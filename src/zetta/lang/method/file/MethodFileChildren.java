package zetta.lang.method.file;

import java.io.File;

import zetta.Zetta;
import zetta.exception.EnumFileException;
import zetta.exception.FileException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeFile;
import zetta.token.TokenString;

public class MethodFileChildren extends MethodFileRead {

    public MethodFileChildren(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns a File Array containing the files directly inside this file" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtArray.name + "<" + Zetta.dtFile.name
		+ ">" };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable v = Zetta.getVariableOfType(object, Zetta.dtFile);
	final Variable array = new Variable(Zetta.dtArray, "", new Object[0]);
	final File f = DataTypeFile.getDir(v);
	if (!f.isDirectory()) {
	    new FileException(f, EnumFileException.NOT_A_DIR);
	}
	final File[] files = f.listFiles();
	array.setValue(new Object[files.length + 1]);
	array.setIndex(0, Zetta.dtFile);
	for (int c = 0; c < files.length; c++) {
	    if (files[c].getName() != null) {
		array.setIndex(c + 1, Zetta.dtFile.initVariable("",
			new Token[] { new TokenString("d" + files[c].getName()
				+ "d") }));
	    }
	}
	return array;
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtArray;
    }

}
