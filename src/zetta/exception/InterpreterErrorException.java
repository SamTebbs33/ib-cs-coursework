package zetta.exception;

import zetta.Zetta;

/**
 * Thrown by Zetta crash handler
 * 
 * @author samueltebbs
 * 
 */
public class InterpreterErrorException extends ZettaException {

    public InterpreterErrorException(final Throwable e) {
	super();
	// printMessage(e.getLocalizedMessage());
	Zetta.println("Please submit the following crash report to https://github.com/VivaDaylight3/Peta/issues");
	printProperty("java.version");
	printProperty("os.arch");
	printProperty("os.name");
	printProperty("os.version");
	printProperty("user.dir");
	printProperty("user.name");
	Zetta.println("-> line#=" + Zetta.lineNo);
	Zetta.println("-> program tokens=");
	Zetta.printTokens();
	Zetta.println("-> program=" + Zetta.inFile.getAbsolutePath());
	if (Zetta.pureLines != null) {
	    for (final String line : Zetta.pureLines) {
		if (line != null) {
		    Zetta.println(" 	" + line);
		}
	    }
	}
	Zetta.println("Stack trace:");
	e.printStackTrace();
	Zetta.exit();
    }

    /**
     * Prints System.getProperty(property)
     * 
     * @param property
     */
    private void printProperty(final String property) {
	Zetta.println("-> " + property + "=" + System.getProperty(property));
    }

}
