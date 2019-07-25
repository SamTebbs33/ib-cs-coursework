package zetta.exception;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import zetta.Debug;
import zetta.InterpreterState;
import zetta.Zetta;
import zetta.documentation.DocGenerator;
import zetta.lang.DataType;

/**
 * Exception thrown by the Zetta interpreter. Those thrown during runtime will
 * exit the interpreter
 * 
 * @author samueltebbs
 * 
 */
public class ZettaException {

    public String message = "", fileMessage = "", exceptionMessage = "";

    public static boolean debug = false, error = false;

    public ZettaException(final String m) {
	this(m, null);
    }

    public ZettaException() {}

    public ZettaException(final String mes, final Exception e) {
	message = "!? " + getClassName() + ": " + mes;
	fileMessage = "	" + Zetta.inFile.getName() + " @ line "
		+ (Zetta.lineNo) + " during " + Zetta.state;
	if (e != null) {
	    exceptionMessage = "        " + e.getMessage();
	}
	Debug.log(message);
	Debug.log(exceptionMessage);
	Debug.log(fileMessage);
	try {
	    final PrintStream pw = new PrintStream(
		    DocGenerator.newFile("errorLog.txt"));
	    final Debug d = new Debug(pw);
	    d.logln(message);
	    d.logln(exceptionMessage);
	    d.logln(fileMessage);
	    if (e != null) {
		e.printStackTrace(pw);
	    }
	    d.close();
	} catch (final FileNotFoundException e1) {}
	if (debug) {
	    Debug.log("Debug Stack Trace");
	    Thread.dumpStack();
	}
	if ((e != null) && debug) {
	    Debug.log("Java Stack Trace");
	    e.printStackTrace();
	}
	if ((Zetta.state == InterpreterState.RUNTIME) && (Zetta.inFile != null)) {
	    Zetta.exit();
	}
	error = true;
    }

    protected String getClassName() {
	return this.getClass().toString()
		.substring(this.getClass().toString().lastIndexOf(".") + 1);
    }

    protected static String format(final DataType[] expected) {
	String res = "[";
	if (expected != null) {
	    for (int c = 0; c < expected.length; c++) {
		res += expected[c].toString();
		if (c < (expected.length - 1)) {
		    res += " | ";
		}
		if (((c % 7) == 0) && (c != 0)) {
		    res += "\n                                                     ";
		}
	    }
	    res += "]";
	}
	return res;
    }

}
