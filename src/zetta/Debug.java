package zetta;

import java.io.PrintStream;

/**
 * 
 * @author samueltebbs, 18:53:31 - 11 Sep 2014
 */
public class Debug {

    PrintStream out;

    public Debug(final PrintStream out) {
	this.out = out;
    }

    public void logln(final Object obj) {
	out.println(obj);
    }

    public static void log(final Object obj) {
	System.out.println(obj);
    }

    public void close() {
	out.close();
    }

}
