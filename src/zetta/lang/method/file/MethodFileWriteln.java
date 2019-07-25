package zetta.lang.method.file;

public class MethodFileWriteln extends MethodFileWrite {

    public MethodFileWriteln(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] {
		"Writes the argument to the end of the file on a new line",
		"Writes each value to the end file on a new line" };
    }

    @Override
    protected String getPrintPrefix() {
	return !hasPrinted ? "" : "\n";
    }

}
