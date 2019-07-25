package zetta.lang;

import zetta.Zetta;
import zetta.exception.MethodAlreadyDefinedException;
import zetta.exception.UnexpectedArgumentQuantityException;

/**
 * Represents a method written in java that can be used in a program,
 * 
 * @author samueltebbs
 * 
 */
public class Method {

    public String name, pointerDType, pointerMode;
    /**
     * <b>Abstract</b> = Pointer not required, println()<br>
     * <b>Standard</b> = Any pointer, $string.size()<br>
     * <b>Specific</b> = Specific pointer type needed, $string.charAt(2)<br>
     * <b>Package</b> = Package name as pointer, Maths.sin()
     */
    public static final String POINTER_STANDARD = "%", POINTER_ABSTRACT = "",
	    POINTER_SPECIFIC = "&", POINTER_PACKAGE = "#";

    public static final String TYPE_GENERIC = "T";

    public Method(final String name, final String dataType,
	    final String pointerType) {
	pointerDType = dataType;
	this.name = name;
	pointerMode = pointerType;
	for (final Method m : Zetta.methods) {
	    if (m != null) {
		if (m.name.equals(name) && m.pointerDType.equals(dataType)) {
		    new MethodAlreadyDefinedException(name, dataType);
		}
	    }
	}
	Zetta.methods.add(this);
	Zetta.currentLib.methods.add(this);
    }

    public boolean canConstruct(final Token[] args) {
	return false;
    }

    public Variable execute(final Token[] args, final Token object) {
	return null;
    }

    public static boolean checkLength(final Token[] args,
	    final boolean exception, final int length) {
	if (args.length != length) {
	    if (exception) {
		new UnexpectedArgumentQuantityException(args.length, length);
	    }
	    return false;
	}
	return true;
    }

    public String[][] docArguments() {
	return new String[][] {};
    }

    public String[] docDescriptions() {
	return new String[] { "" };
    }

    public String[] docReturnTypes() {
	return new String[] { "" };
    }

    public String[][] docThrows() {
	return new String[][] {};
    }

    public String[] docCodeExamples() {
	return new String[] {};
    }

    public DataType getReturnType() {
	return Zetta.dtNull;
    }

    public DataType getReturnType(final Variable pointer) {
	return getReturnType();
    }

    public static Variable asVariable(final DataType dType,
	    final Object... objects) {
	return asVariable("", dType, objects);
    }

    public static Variable asVariable(final String name, final DataType dType,
	    final Object... objects) {
	return new Variable(dType, name, objects);
    }

}
