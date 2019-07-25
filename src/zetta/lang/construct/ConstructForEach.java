package zetta.lang.construct;

import zetta.Zetta;
import zetta.exception.IteratorException;
import zetta.exception.UnexpectedDataTypeException;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.Construct;
import zetta.lang.DataType;
import zetta.lang.Method;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.IterableDataType;
import zetta.token.TokenConstruct;
import zetta.token.TokenMethodArgument;
import zetta.token.TokenMethodCall;
import zetta.token.TokenVariable;

/**
 * A for-each construct Format: foreach(element(, key), array){ statements... }
 * 
 * @author samueltebbs
 * 
 */
public class ConstructForEach extends Construct {

    public ConstructForEach(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public boolean canConstruct(final Token[] tokens, final TokenConstruct t) {
	if (Method.checkLength(tokens, false, 2)) {
	    if ((tokens[0] instanceof TokenVariable)
		    || (tokens[0] instanceof TokenMethodArgument)) {
		if ((tokens[1] instanceof TokenVariable)
			|| (tokens[1] instanceof TokenMethodCall)) return true;
		else {
		    new UnexpectedTokenException(tokens[1],
			    Zetta.tokenVariable, Zetta.tokenMethodCall);
		}
	    } else {
		new UnexpectedTokenException(tokens[0], Zetta.tokenVariable);
	    }
	} else if (Method.checkLength(tokens, true, 3)) {
	    if ((tokens[0] instanceof TokenVariable)
		    || (tokens[0] instanceof TokenMethodArgument)) {
		if ((tokens[1] instanceof TokenVariable)
			|| (tokens[1] instanceof TokenMethodArgument)) {
		    if ((tokens[2] instanceof TokenVariable)
			    || (tokens[2] instanceof TokenMethodCall)) return true;
		    else {
			new UnexpectedTokenException(tokens[2],
				Zetta.tokenVariable, Zetta.tokenMethodCall);
		    }
		} else {
		    new UnexpectedTokenException(tokens[1], Zetta.tokenVariable);
		}
	    } else {
		new UnexpectedTokenException(tokens[0], Zetta.tokenVariable);
	    }
	}
	return false;
    }

    protected Variable doRepeatedEvaluation(final Variable v, final Token token) {
	return v;
    }

    @Override
    public Variable run(final TokenConstruct token) {
	final int elementInt = 0;
	int keyInt = -1, collectionInt = 1;
	if (token.subTokens.length == 3) {
	    keyInt = 1;
	    collectionInt = 2;
	}

	Variable key = null;
	Variable element = null;
	Variable collection = null;

	// Setting the collection variable
	final Token collectionToken = token.subTokens[collectionInt];
	if (collectionToken instanceof TokenVariable) {
	    collection = Zetta.getVariableOfType(collectionToken);
	} else if (collectionToken instanceof TokenMethodCall) {
	    collection = Zetta.executeMethod((TokenMethodCall) collectionToken);
	}
	if (!(collection.dType instanceof IterableDataType)) {
	    new IteratorException(collection.dType);
	}

	// Setting the element variable
	final Token elementToken = token.subTokens[elementInt];
	final DataType itDType = ((IterableDataType) collection.dType)
		.iteratorDataType(collection);
	if (elementToken instanceof TokenVariable) {
	    element = Zetta.getVariableOfType(collectionToken);
	    if (element.dType != itDType) {
		new UnexpectedDataTypeException(element.dType, itDType);
	    }
	} else if (elementToken instanceof TokenMethodArgument) {
	    element = new Variable(itDType, "", new Object[0]);
	    element.name = elementToken.value;
	}

	// Setting the key variable
	if (keyInt > -1) {
	    final Token keyToken = token.subTokens[keyInt];
	    final DataType itDType2 = ((IterableDataType) collection.dType)
		    .iteratorKeyDataType(collection);
	    if (keyToken instanceof TokenVariable) {
		key = Zetta.getVariableOfType(keyToken);
		if (key.dType != itDType2) {
		    new UnexpectedDataTypeException(key.dType, itDType);
		}
	    } else if (keyToken instanceof TokenMethodArgument) {
		key = new Variable(itDType2, "", new Object[0]);
		key.name = keyToken.value;
	    }
	}

	int c = 0;
	final int iteratorLength = ((IterableDataType) collection.dType)
		.iteratorLength(collection);

	final Variable[] values = ((IterableDataType) collection.dType)
		.getAllIteratorValues(collection);
	final Variable[] keys = keyInt > -1 ? ((IterableDataType) collection.dType)
		.getAllIteratorIndexes(collection) : null;

	while (c < iteratorLength) {
	    element.value = values[c].value;
	    token.localVariables.add(element);
	    if (keyInt > -1) {
		key.value = keys[c].value;
		token.localVariables.add(key);
	    }
	    Zetta.runFromConstruct(token.body, token, true);
	    c++;
	}

	return null;
    }

}
