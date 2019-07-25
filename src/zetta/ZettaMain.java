package zetta;

import java.lang.Thread.UncaughtExceptionHandler;

import zetta.exception.InterpreterErrorException;

/**
 * 
 * @author samueltebbs, 20:12:51 - 4 Sep 2014
 */
public class ZettaMain {

    public static void main(final String[] args) {

	Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
	    @Override
	    public void uncaughtException(final Thread t, final Throwable e) {
		new InterpreterErrorException(e);
	    }
	});

	new Zetta(args);
    }

}
