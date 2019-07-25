package zetta.lang.method.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import zetta.Zetta;
import zetta.exception.EnumFileException;
import zetta.exception.FileException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeDictionary;
import zetta.lang.datatype.DataTypeFile;
import zetta.token.TokenVariable;

/**
 * 
 * @author samueltebbs, 19:16:04 - 11 Sep 2014
 */
public class MethodFileDiff extends MethodFile {

    public MethodFileDiff(final String name) {
	super(name);
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 1)) return args[0] instanceof TokenVariable;
	return false;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final File fileObj = DataTypeFile.getFile(Zetta.getVariableOfType(
		object, Zetta.dtFile));
	final File fileArg = DataTypeFile.getFile(Zetta.getVariableOfType(
		args[0], Zetta.dtFile));
	final Variable result = new Variable(Zetta.dtDictionary, "",
		new Object[1]);
	result.setIndex(0, Zetta.dtString);
	if (fileArg.getAbsolutePath().equals(fileObj.getAbsolutePath())) return result;
	try {
	    int line = 1;
	    boolean cont = true;
	    final Scanner inObj = new Scanner(fileObj);
	    final Scanner inArg = new Scanner(fileArg);
	    while (cont) {
		String strObj = null, strArg = null;
		if (inObj.hasNextLine()) {
		    strObj = inObj.nextLine();
		}
		if (inArg.hasNextLine()) {
		    strArg = inArg.nextLine();
		}
		if ((strObj == null) && (strArg == null)) {
		    cont = false;
		    break;
		};
		if ((strObj == null) || (strArg == null)) {
		    ((DataTypeDictionary) result.dType).setMappedValue(result,
			    asVariable(Zetta.dtString, strArg), "" + line);
		}
		if (!strObj.equals(strArg)) {
		    ((DataTypeDictionary) result.dType).setMappedValue(result,
			    asVariable(Zetta.dtString, strArg), "" + line);
		}
		line++;
	    }
	    inObj.close();
	    inArg.close();
	} catch (final FileNotFoundException e) {
	    new FileException(fileArg, e, EnumFileException.DOESNT_EXIST);
	}
	return result;
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtFile.name } };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Checks for lines that are not the same in each file and returns a Dictionary with the line numbers as keys and line contents as values" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtDictionary.name };
    }

    @Override
    public String[] docCodeExamples() {
	return new String[] {};
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtDictionary;
    }

}
