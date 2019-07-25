package zetta.lang.datatype;

import zetta.lang.Variable;

/**
 * Represents a data type whose values are mapped to Object keys
 */
public interface MappedDataType {

    public Variable getMappedValue(Variable v, String key);

    public boolean hasMappedKey(Variable v, String key);

    public boolean hasMappedValue(Variable v, Variable value);

    public int getMappedSize(Variable v);

    public void setMappedKey(Variable v, String key, Variable value);

    public void setMappedValue(Variable v, Variable value, String key);

    /**
     * Return the keys associated with the variable. Return with the keys' data
     * type in the 0th index and the keys after that
     */
    public Object[] getAllMappedKeys(Variable v);

    /**
     * Return the values associated with the variable. Return with the values'
     * data type in the 0th index and the values after that
     */
    public Object[] getAllMappedValues(Variable v);

}
