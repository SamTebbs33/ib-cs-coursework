package zetta.lang.datatype;

import java.util.Arrays;

import zetta.Zetta;
import zetta.lang.DataType;
import zetta.lang.Method;
import zetta.lang.Token;
import zetta.lang.Variable;

/**
 * Used like a stopwatch
 * 
 * @author samueltebbs, 12:02:14 - 25 Jul 2014
 */
public class DataTypeTime extends DataType {

    public DataTypeTime() {}

    public DataTypeTime(final String name) {
	super(name);
    }

    @Override
    public String docDescription() {
	return "Used to track the time used with a program";
    }

    @Override
    public String sizeDesc() {
	return "Returns 0";
    }

    @Override
    public String stringDesc() {
	return "Returns the time in milliseconds held by the variable";
    }

    @Override
    public String[][] constructArgs() {
	return new String[][] { {} };
    }

    @Override
    public String[] constructDesc() {
	return new String[] { "Sets the variable's value to the current system time in milliseconds" };
    }

    @Override
    public boolean canConstruct(final Token[] args, final String raw) {
	return Method.checkLength(args, true, 0);
    }

    @Override
    public Variable initVariable(final String string, final Token[] args) {
	return new Variable(Zetta.dtTime, string,
		new Object[] { System.currentTimeMillis() });
    }

    @Override
    public boolean opEqual(final Variable v, final Object obj) {
	if (obj instanceof Variable) {
	    if (v.dType == ((Variable) obj).dType) return v.getIndex(0).equals(
		    ((Variable) obj).getIndex(0));
	} else if (obj instanceof Double) return (Double) obj == size(v);
	else if (obj instanceof String) return ((String) obj).equals(string(v));
	return false;
    }

    @Override
    public Variable sortArray(final Variable obj) {
	final Variable result = new Variable(Zetta.dtArray, "",
		new Object[obj.getValue().length]);
	result.setValue(Arrays.copyOf(obj.getValue(), obj.getValue().length));
	final long[] longs = new long[result.getValue().length - 1];
	for (int c = 0; c < longs.length; c++) {
	    longs[c] = (long) ((Variable) result.getIndex(c + 1)).getIndex(0);
	}
	boolean done = false;
	while (!done) {
	    done = true;
	    for (int c = 1; c < longs.length; c++) {
		final long l1 = longs[c], l2 = longs[c - 1];
		if (l1 < l2) {
		    done = false;
		    longs[c] = l2;
		    longs[c - 1] = l1;
		    final Variable temp = (Variable) result.getIndex(c);
		    result.setIndex(c, result.getIndex(c + 1));
		    result.setIndex(c + 1, temp);
		}
	    }
	}
	return result;
    }

}
