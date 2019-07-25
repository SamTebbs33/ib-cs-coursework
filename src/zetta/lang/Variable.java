package zetta.lang;

/**
 * Represents a variable initiated at runtime that can be used within a program
 * 
 * @author samueltebbs
 * 
 */
public class Variable {

    /**
     * The data type of this variable, defines much of its behaviour
     */
    public DataType dType;
    
    /**
     * The name of the variable
     */
    public String name;
    
    /**
     * The values associated with the variable
     */
    public Object[] value;

    /**
     * Creates a new variable with a data type, name and values
     */
    public Variable(final DataType dType, final String name,
	    final Object... value) {
	this.dType = dType;
	this.name = name;
	this.value = value;
    }

    /**
     * Returns the string representation of the variable.
     * The format of the string is defined by the variable's data type.
     */
    @Override
    public String toString() {
	return dType.string(this);
    }

    /**
     * Sets the value of the variable
    * void
    * @param val - The new values
    *
     */
    public void setValue(final Object[] val) {
	value = val;
    }

    /**
     * Sets a certain index of the value array
    * @param index - Index of the value array
    * @param val - New value
    *
     */
    public void setIndex(final int index, final Object val) {
	// Ensure that the given index is within the array's bounds
	if ((index > -1) && (index < value.length)) {
	    value[index] = val;
	}
    }

    /**
     * Returns the variable's values
     * @return Object[]
     */
    public Object[] getValue() {
	return value;
    }

    /**
     * Returns a certain index of the value array
    * @param index - Index of the value array
    *
     */
    public Object getIndex(final int index) {
	// Ensure that the given index is within the array's bounds
	if ((index > -1) && (index < value.length)) return value[index];
	return null;
    }

}