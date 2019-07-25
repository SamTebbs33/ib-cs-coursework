package zetta.lang.datatype;

import zetta.lang.Token;
import zetta.lang.Variable;

/**
 * Represents a data type that stores multiple values mapped to integer or
 * string indexes variables of a data type that implement this interface, are
 * compatible with subscript notation <b>($var[index])</b> like arrays in Java
 * 
 * @author samueltebbs
 * 
 */
public interface CollectionDataType {

    /**
     * Return the length of the collection that can be indexed
     * 
     * @param The
     *            Variable whose length to get
     */
    public int collectionLength(Variable v);

    /**
     * Store the value in the index of the variable
     * 
     * @param The
     *            Variable to use
     * @param the
     *            String index to set
     * @param The
     *            Variable value that should be stored
     */
    public void setCollectionIndex(Variable v, Token index, Variable value);

    /**
     * Return the value at the index
     * 
     * @param the
     *            Variable to use
     * @param the
     *            String index to get
     */
    public Variable getCollectionIndex(Variable v, Token index);

}
