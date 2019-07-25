package zetta.token;

import zetta.Zetta;
import zetta.exception.InvalidSyntaxException;
import zetta.exception.UnexpectedTokenException;
import zetta.exception.VariableAlreadyDefinedException;
import zetta.lang.Token;
import zetta.lang.Variable;

/***
 * A boolean expression with a true/false value that can be evaluated Grammar:
 * method call/variable/number (operator method call/variable/number)...
 * 
 * @author
 * @version 1.00 2014/6/27
 */

public class TokenBooleanExpression extends Token {
    public TokenBooleanExpression() {
	super();
    }

    public TokenBooleanExpression(final String str) {
	super(str);
    }

    @Override
    public Object evaluate() {
	if (value.equals("true")) return true;
	if (value.equals("false")) return false;
	String leftHand = null, rightHand = null;
	Double leftHandD = null, rightHandD = null;
	Variable leftHandV = null, v = null;
	Token lastOp = null;

	for (int c = 1; c <= subTokens.length; c++) {
	    final Token t = subTokens[c - 1];
	    if ((c % 2) == 0) {
		if (t instanceof TokenBooleanOperator) {
		    lastOp = t;
		    continue;
		} else {
		    new UnexpectedTokenException(t, Zetta.tokenBooleanOperator);
		}
	    } else if (t instanceof TokenVariable) {
		v = Zetta.getVariable((TokenVariable) t);
	    } else if (t instanceof TokenString) {
		rightHand = t.value;
	    } else if (t instanceof TokenMethodCall) {
		final String str = Zetta.executeMethod((TokenMethodCall) t)
			.toString();
		if (Token.isNumber(str)) {
		    rightHandD = Double.parseDouble(str);
		} else {
		    rightHand = str;
		}
	    } else if (t instanceof TokenNumber) {
		rightHandD = Double.parseDouble(t.value);
	    } else if (t instanceof TokenBooleanExpression) {
		v = new Variable(Zetta.dtBoolean, "",
			new Object[] { t.evaluate() });
	    } else {

	    }
	    if ((c % 3) == 0) {
		if (lastOp == null) { // TODO: error
		} else {
		    switch (lastOp.value) {
			case "=":
			    if (v != null) {
				if (leftHandV != null) return v.dType.opEqual(
					v, leftHandV);
				if (leftHand != null) return v.dType.opEqual(v,
					leftHand);
				if (leftHandD != null) return v.dType.opEqual(
					v, leftHandD);
			    } else if (leftHandV != null) {
				if (rightHand != null) return leftHandV.dType
					.opEqual(leftHandV, rightHand);
				if (rightHandD != null) return leftHandV.dType
					.opEqual(leftHandV, rightHandD);
			    } else if (leftHand != null) {
				if (rightHand != null) return leftHand
					.equals(rightHand);
				else if (rightHandD != null) return leftHand
					.length() == rightHandD;
			    } else if (rightHand != null) {
				if (leftHandD != null) return leftHandD
					.equals(rightHand.length());
			    } else if (leftHandD != null) {
				if (rightHandD != null) return leftHandD
					.equals(rightHandD);
				else {

				}
			    }
			    break;
			case "<":
			    if (v != null) {
				if (leftHandV != null) return v.dType
					.opLessThan(v, leftHandV);
				if (leftHand != null) return v.dType
					.opLessThan(v, leftHand);
				if (leftHandD != null) return v.dType
					.opLessThan(v, leftHandD);
			    } else if (leftHandV != null) {
				if (rightHand != null) return leftHandV.dType
					.operatorGreaterThan(leftHandV,
						rightHand);
				if (rightHandD != null) return leftHandV.dType
					.operatorGreaterThan(leftHandV,
						rightHandD);
			    } else if (leftHand != null) {
				if (rightHand != null) return leftHand.length() < rightHand
					.length();
				else {
				    // TODO: Error
				}
			    } else if (leftHandD != null) {
				if (rightHandD != null) return leftHandD < rightHandD;
				else {
				    // TODO: Error
				}
			    }
			case "!":
			    if (v != null) {
				if (leftHandV != null) return !(v.dType
					.opEqual(v, leftHandV));
				if (leftHand != null) return !(v.dType.opEqual(
					v, leftHand));
				if (leftHandD != null) return !(v.dType
					.opEqual(v, leftHandD));
			    } else if (leftHandV != null) {
				if (rightHand != null) return !(leftHandV.dType
					.opEqual(leftHandV, rightHand));
				if (rightHandD != null) return !(leftHandV.dType
					.opEqual(leftHandV, rightHandD));
			    } else if (leftHand != null) {
				if (rightHand != null) return !(leftHand
					.equals(rightHand));
				else if (rightHandD != null) return leftHand
					.length() != rightHandD;
			    } else if (rightHand != null) {
				if (leftHandD != null) return !(leftHandD
					.equals(rightHand.length()));
			    } else if (leftHandD != null) {
				if (rightHandD != null) return !(leftHandD
					.equals(rightHandD));
				else {

				}
			    }
			    break;
			case ">":
			    if (v != null) {
				if (leftHandV != null) return v.dType
					.operatorGreaterThan(v, leftHandV);
				if (leftHand != null) return v.dType
					.operatorGreaterThan(v, leftHand);
				if (leftHandD != null) return v.dType
					.operatorGreaterThan(v, leftHandD);
			    } else if (leftHandV != null) {
				if (rightHand != null) return leftHandV.dType
					.opLessThan(leftHandV, rightHand);
				if (rightHandD != null) return leftHandV.dType
					.opLessThan(leftHandV, rightHandD);
			    } else if (leftHand != null) {
				if (rightHand != null) return leftHand.length() > rightHand
					.length();
				else {
				    // TODO: Error
				}
			    } else if (leftHandD != null) {
				if (rightHandD != null) return leftHandD > rightHandD;
				else {
				    // TODO: Error
				}
			    }
		    }
		}
	    } else {
		if (v != null) {
		    leftHandV = v;
		    v = null;
		}
		if (rightHand != null) {
		    leftHand = rightHand;
		    rightHand = null;
		}
		if (rightHandD != null) {
		    leftHandD = rightHandD;
		    rightHandD = null;
		}
	    }
	}
	return false;
    }

    @Override
    public Token getTokenFromString(final String str) {
	if (str.equals("true") || str.equals("false")) return new TokenBooleanExpression(
		str);
	Token token;
	if (containsBooleanOperators(str)) {
	    if ((token = booleanExpression(str)) != null) return token;
	}
	return null;
    }

    @Override
    public boolean runToken() {
	if ((subTokens.length == 3) && subTokens[1].value.equals("=")) {
	    if (subTokens[0] instanceof TokenVariable) {
		/*
		 * TokenVariable t = (TokenVariable) subTokens[0];
		 * if(t.subScript != null){ if(t.dType instanceof
		 * CollectionDataType){ ((CollectionDataType)
		 * t.dType).setCollectionIndex(Zetta.getVariable(t.value),
		 * subTokens[3]); } }else{
		 */
		final Variable lhs = Zetta
			.getVariable((TokenVariable) subTokens[0]);
		Zetta.getVariable((TokenVariable) subTokens[0]).value = lhs.dType
			.operatorAssign(lhs, subTokens[2]).value;
		// }
	    } else if (subTokens[0] instanceof TokenMethodArgument) {
		for (final Variable v : Zetta.variables) {
		    if (v.name.equals(subTokens[0].value)) {
			new VariableAlreadyDefinedException(v.name);
		    }
		}
		if (Zetta.activeConstruct.size() > 0) {
		    for (final Variable v : Zetta.activeConstruct.peek().localVariables) {
			if (v.name.equals(subTokens[0].value)) {
			    new VariableAlreadyDefinedException(v.name);
			}
		    }
		}
		final Variable lhs = new Variable(
			((TokenMethodArgument) subTokens[0]).dType, "",
			new Object[0]);
		final Variable v = ((TokenMethodArgument) subTokens[0]).dType
			.operatorAssign(lhs, subTokens[2]);
		v.name = subTokens[0].value;
		if (Zetta.activeConstruct.size() == 0) {
		    Zetta.variables.add(v);
		} else {
		    Zetta.activeConstruct.peek().localVariables.add(v);
		}
	    } else {
		new UnexpectedTokenException(subTokens[0], Zetta.tokenVariable);
	    }
	} else {
	    new InvalidSyntaxException(this);
	}
	return true;
    }

}