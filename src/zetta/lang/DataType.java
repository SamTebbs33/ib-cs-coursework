package zetta.lang;

import java.util.Arrays;

import zetta.Zetta;
import zetta.exception.CannotDeclareVariableOfTypeException;
import zetta.exception.UnexpectedDataTypeException;
import zetta.exception.UnexpectedTokenException;
import zetta.token.TokenMethodCall;
import zetta.token.TokenVariable;

/**
 * Data type that variables are declared with
 * 
 * @author samueltebbs
 * 
 */

public class DataType {

    public String name;

    public DataType() {}

    public DataType(final String name) {
	this.name = name;
	Zetta.dataTypes.add(this);
	Zetta.currentLib.dataTypes.add(this);
    }

    public String docDescription() {
	return "";
    }

    public String docOpEqual() {
	return "If the operand is a variable, returns true if the values held by the variable are equal.\n"
		+ "If the operand is a number, returns true if the variable's size is equal to the number.\n"
		+ "If the operand is a string, returns true if the string version of the variable is equal to the operand.";
    }

    /**
     * obj = v
     */
    public boolean opEqual(final Variable v, final Object obj) {
	if (obj instanceof Variable) {
	    if (v.dType == ((Variable) obj).dType) return Arrays.equals(
		    v.getValue(), ((Variable) obj).getValue());
	} else if (obj instanceof Double) return (Double) obj == size(v);
	else if (obj instanceof String) return ((String) obj).equals(string(v));
	return false;
    }

    public String docOpLessThan() {
	return "If the operand is a variable, returns true if the size of the operand is less than the variable.\n"
		+ "If the operand is a number, returns true if the variable's size is less than to the number.\n"
		+ "If the operand is a string, returns true if the string length is less than the variable size.";
    }

    /**
     * obj < v
     */
    public boolean opLessThan(final Variable v, final Object obj) {
	if (obj instanceof Variable) {
	    if (v.dType == ((Variable) obj).dType) return size((Variable) obj) < size(v);
	} else if (obj instanceof Double) return (Double) obj < size(v);
	else if (obj instanceof String) return ((String) obj).length() < size(v);
	return false;
    }

    public String docOpGreaterThan() {
	return "If the operand is a variable, returns true if the size of the operand is greater than the variable.\n"
		+ "If the operand is a number, returns true if the variable's size is greater than to the number.\n"
		+ "If the operand is a string, returns true if the string length is greater than the variable size.";
    }

    /**
     * obj > v
     */
    public boolean operatorGreaterThan(final Variable v, final Object obj) {
	if (obj instanceof Variable) {
	    if (v.dType == ((Variable) obj).dType) return size((Variable) obj) > size(v);
	} else if (obj instanceof Double) return (Double) obj > size(v);
	else if (obj instanceof String) return ((String) obj).length() > size(v);
	return false;
    }

    public boolean canBeInstantiated() {
	return true;
    }

    public String[] constructDesc() {
	return new String[0];
    }

    public String[][] constructArgs() {
	return null;
    }

    public boolean canConstruct(final Token[] args, final String raw) {
	new CannotDeclareVariableOfTypeException(this);
	return false;
    }

    public Variable initVariable(final String string, final Token[] args) {
	return null;
    }

    @Override
    public String toString() {
	return name;
    }

    public String sizeDesc() {
	return "";
    }

    public long size(final Variable v) {
	return 0;
    }

    public String stringDesc() {
	return "";
    }

    public String string(final Variable v) {
	// TODO Auto-generated method stub
	return v.getIndex(0).toString();
    }

    public Object get(final Variable v) {
	return v.getIndex(0);
    }

    public void setVariableToVariable(final Variable from, final Variable to) {
	if (from.dType == to.dType) {
	    from.setValue(to.getValue());
	} else {
	    from.setIndex(0, to.getIndex(0));
	}
    }

    public void setVariableToObject(final Variable from, final Object to) {
	from.setIndex(0, to);
    }

    public String operatorAssignDesc() {
	return "If the operand is a method call, sets the variable to the result of the method call if it is of the same data type.\n"
		+ "If the operand is a variable, sets the variable to the operand if it is of the same data type.";
    }

    public Variable operatorAssign(final Variable lhs, final Token token) {
	if (token instanceof TokenMethodCall) {
	    final Object v = Zetta.executeMethod((TokenMethodCall) token);
	    if (v instanceof Variable) {
		if (((Variable) v).dType == this) return (Variable) v;
		else {
		    new UnexpectedDataTypeException(((Variable) v).dType, this);
		}
	    }
	} else if (token instanceof TokenVariable) return Zetta
		.getVariableOfType(token, this);
	else {
	    new UnexpectedTokenException(token, Zetta.tokenMethodCall,
		    Zetta.tokenVariable);
	}
	return null;
    }

    public Variable initVariable(final String name, final Token t) {
	return initVariable(name, new Token[] { t });
    }

    public Variable basicVar() {
	return null;
    }

    public String docExtraInfo() {
	return "";
    }

    public Variable sortArray(final Variable obj) {
	return obj;
    }

}