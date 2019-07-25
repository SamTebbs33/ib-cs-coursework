package zetta.lang.datatype;

import zetta.Zetta;
import zetta.exception.UnexpectedDataTypeException;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.DataType;
import zetta.lang.Method;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.token.TokenLiteral;
import zetta.token.TokenMethodCall;
import zetta.token.TokenVariable;

/**
 * 
 * @author samueltebbs, 23:30:08 - 30 Jul 2014
 */
public class DataTypeGui extends DataType {

    public static final int INDEX_NAME = 0, INDEX_MESSAGE = 1, INDEX_ICON = 2;

    public DataTypeGui() {}

    public DataTypeGui(final String name) {
	super(name);
    }

    @Override
    public String sizeDesc() {
	return "Returns 0";
    }

    @Override
    public String stringDesc() {
	return "Returns the GUI's title";
    }

    @Override
    public String docDescription() {
	return "Used to show dialogue boxes to the user";
    }

    @Override
    public String[][] constructArgs() {
	return new String[][] { { Zetta.dtString.name, Zetta.dtString.name,
		Zetta.dtString.name } };
    }

    @Override
    public String[] constructDesc() {
	return new String[] { "Sets the GUI name to String 1, the message to String 2 and retrieves the icon from String 3" };
    }

    @Override
    public long size(final Variable v) {
	return v.getValue().length - 3;
    }

    /**
     * obj = v
     */
    @Override
    public boolean opEqual(final Variable v, final Object obj) {
	if (obj instanceof Variable) {
	    ((Variable) obj).dType.opEqual((Variable) obj, v);
	}
	new UnexpectedDataTypeException(v, Zetta.dtBoolean);
	return false;
    }

    /**
     * obj < v
     */
    @Override
    public boolean opLessThan(final Variable v, final Object obj) {
	if (obj instanceof Variable) {
	    ((Variable) obj).dType.opEqual((Variable) obj, v);
	}
	new UnexpectedDataTypeException(v, Zetta.dtBoolean);
	return false;
    }

    /**
     * obj > v
     */
    @Override
    public boolean operatorGreaterThan(final Variable v, final Object obj) {
	if (obj instanceof Variable) {
	    ((Variable) obj).dType.opEqual((Variable) obj, v);
	}
	new UnexpectedDataTypeException(v, Zetta.dtBoolean);
	return false;
    }

    @Override
    public boolean canConstruct(final Token[] args, final String raw) {
	if (Method.checkLength(args, true, 3)) {
	    if ((args[2] instanceof TokenVariable)
		    || (args[2] instanceof TokenMethodCall)
		    || (args[2] instanceof TokenLiteral)) {
		if ((args[1] instanceof TokenVariable)
			|| (args[1] instanceof TokenMethodCall)
			|| (args[1] instanceof TokenLiteral)) {
		    if ((args[2] instanceof TokenVariable)
			    || (args[2] instanceof TokenMethodCall)
			    || (args[2] instanceof TokenLiteral)) return true;
		    else {
			new UnexpectedTokenException(args[2],
				Zetta.tokenVariable, Zetta.tokenMethodCall,
				Zetta.tokenString, Zetta.tokenNumber);
		    }
		} else {
		    new UnexpectedTokenException(args[1], Zetta.tokenVariable,
			    Zetta.tokenMethodCall, Zetta.tokenString,
			    Zetta.tokenNumber);
		}
	    } else {
		new UnexpectedTokenException(args[0], Zetta.tokenVariable,
			Zetta.tokenMethodCall, Zetta.tokenString,
			Zetta.tokenNumber);
	    }
	}
	return false;
    }

    @Override
    public Variable initVariable(final String string, final Token[] args) {
	final Variable v = new Variable(Zetta.dtGui, string,
		new Object[INDEX_ICON + 1]);
	v.setIndex(INDEX_NAME, Token.getString(args[0]));
	v.setIndex(INDEX_MESSAGE, Token.getString(args[1]));
	v.setIndex(INDEX_ICON, Token.getString(args[2]));
	return v;
    }

    @Override
    public String toString() {
	return name;
    }

    @Override
    public String string(final Variable v) {
	// TODO Auto-generated method stub
	return v.getIndex(0).toString();
    }

    @Override
    public Object get(final Variable v) {
	return v.getIndex(0);
    }

    @Override
    public void setVariableToVariable(final Variable from, final Variable to) {
	if (from.dType == to.dType) {
	    from.setValue(to.getValue());
	} else {
	    from.setIndex(0, to.getIndex(0));
	}
    }

    @Override
    public void setVariableToObject(final Variable from, final Object to) {
	from.setIndex(0, to);
    }

    @Override
    public Variable operatorAssign(final Variable lhs, final Token token) {
	if (token instanceof TokenMethodCall) {
	    final Object v = Zetta.executeMethod((TokenMethodCall) token);
	    if (v instanceof Variable) {
		if (((Variable) v).dType == Zetta.dtGui) return (Variable) v;
		else {
		    new UnexpectedDataTypeException(((Variable) v).dType,
			    Zetta.dtGui);
		}
	    }
	} else if (token instanceof TokenVariable) return Zetta
		.getVariableOfType(token, Zetta.dtGui);
	else {
	    new UnexpectedTokenException(token, Zetta.tokenMethodCall,
		    Zetta.tokenVariable);
	}
	return null;
    }

    @Override
    public Variable sortArray(final Variable obj) {
	return obj;
    }

}
