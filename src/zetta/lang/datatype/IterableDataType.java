package zetta.lang.datatype;

import zetta.lang.DataType;
import zetta.lang.Variable;

/**
 * Represents a data type whose values can be iterated upon (i.e characters in a
 * String, or indexes in an array), e.g in a loop
 * 
 * @author samueltebbs
 */
public interface IterableDataType {

    public Variable iteratorValue(Variable v, String index);

    public Variable iteratorValue(Variable v, int index);

    public boolean iteratorHasIndex(Variable v, Object index);

    public int iteratorLength(Variable v);

    public Object iteratorKey(Variable v, int index);

    public DataType iteratorDataType(Variable v);

    public DataType iteratorKeyDataType(Variable collection);

    /**
     * Return the keys associated with the variable. Return with the keys' data
     * type in the 0th index and the keys after that
     */
    public Variable[] getAllIteratorIndexes(Variable v);

    /**
     * Return the values associated with the variable. Return with the values'
     * data type in the 0th index and the values after that
     */
    public Variable[] getAllIteratorValues(Variable v);

}