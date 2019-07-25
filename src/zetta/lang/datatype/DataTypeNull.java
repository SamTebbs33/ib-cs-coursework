package zetta.lang.datatype;

import zetta.exception.CannotDeclareVariableOfTypeException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;

/**
 * 
 * @author samueltebbs, 15:03:52 - 23 Aug 2014
 */
public class DataTypeNull extends DataType {

    public DataTypeNull() {
	name = "null";
    }

    @Override
    public boolean canBeInstantiated() {
	return false;
    }

    @Override
    public boolean opEqual(final Variable v, final Object obj) {
	return false;
    }

    /**
     * obj < v
     */
    @Override
    public boolean opLessThan(final Variable v, final Object obj) {
	return false;
    }

    /**
     * obj > v
     */
    @Override
    public boolean operatorGreaterThan(final Variable v, final Object obj) {
	return false;
    }

    @Override
    public boolean canConstruct(final Token[] args, final String raw) {
	new CannotDeclareVariableOfTypeException(this);
	return false;
    }

    @Override
    public Variable initVariable(final String string, final Token[] args) {
	return null;
    }

    @Override
    public String toString() {
	return name;
    }

    @Override
    public long size(final Variable v) {
	return 0;
    }

    @Override
    public String string(final Variable v) {
	// TODO Auto-generated method stub
	return "null";
    }

    @Override
    public Object get(final Variable v) {
	return v.getIndex(0);
    }

    @Override
    public void setVariableToVariable(final Variable from, final Variable to) {

    }

    @Override
    public void setVariableToObject(final Variable from, final Object to) {
	from.setIndex(0, to);
    }

    @Override
    public String docDescription() {
	return null;
    }

    @Override
    public Variable operatorAssign(final Variable lhs, final Token token) {
	return null;
    }

    @Override
    public Variable initVariable(final String name, final Token t) {
	return initVariable(name, new Token[] { t });
    }

    @Override
    public Variable basicVar() {
	return null;
    }

}
