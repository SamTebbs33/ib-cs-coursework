package zetta.token;

import java.util.Arrays;

import zetta.Zetta;
import zetta.exception.InvalidMethodOrPointerException;
import zetta.exception.UnexpectedPointerException;
import zetta.lang.Method;
import zetta.lang.Token;
import zetta.lang.Variable;

/**
 * 
 * @author samueltebbs, 10:39:20 - 22 Aug 2014
 */
public class TokenNestedMethodCall extends TokenMethodCall {

    public TokenNestedMethodCall(final String method, final String[] args,
	    final String object, final String dType) {
	super(method, args, object, dType);
    }

    public TokenNestedMethodCall(final String str) {
	super(str);
    }

    public TokenNestedMethodCall() {}

    @Override
    public Token getTokenFromString(final String str) {
	Token result = null;
	final int dotInd = indexOf(str, '.', true);
	if (dotInd != indexOf(str, '.', false)) {
	    String[] calls = splitByDot(str);
	    Token t = getTokenType(calls[0]);
	    if (t instanceof TokenVariable) {
		calls[1] = calls[0] + "." + calls[1];
		calls = Arrays.copyOfRange(calls, 1, calls.length);
	    }
	    t = getTokenType(calls[0]);
	    if (t instanceof TokenMethodCall) {
		result = new TokenNestedMethodCall("");
		result.subTokens = new Token[calls.length];
		result.subTokens[0] = t;
		for (int c = 1; c < calls.length; c++) {
		    final int openInd = indexOfOpeningBracket(calls[c], '(');
		    final int closeInd = indexOfClosingBracket(calls[c], ')');
		    if ((openInd > 0) && (closeInd > openInd)) {
			for (final Method m : Zetta.methods) {
			    if (m.pointerMode.equals(Method.POINTER_SPECIFIC)
				    || m.pointerMode
					    .equals(Method.POINTER_STANDARD)) {
				if (m.name.equals(calls[c]
					.substring(0, openInd))) {
				    if (m.pointerDType
					    .equals(Zetta
						    .getMethodReturnType((TokenMethodCall) t).name)
					    || m.pointerMode
						    .equals(Method.POINTER_STANDARD)) {
					result.subTokens[c] = new TokenMethodCall(
						calls[c].substring(0, openInd),
						split(calls[c].substring(
							openInd + 1, closeInd)),
						Zetta.RESERVED_VAR_NAME,
						m.pointerDType);
					t = result.subTokens[c];
					break;
				    } else if (!m.pointerDType
					    .equals(Zetta
						    .getMethodReturnType((TokenMethodCall) t).name)) {
					new UnexpectedPointerException(
						m.pointerDType,
						Zetta.getMethodReturnType((TokenMethodCall) t).name);
				    }
				}
			    }
			}
			if (result.subTokens[c] == null) {
			    new InvalidMethodOrPointerException(
				    calls[c].substring(0, openInd));
			    return null;
			}
		    }
		}
	    }
	}
	return result;
    }

    public Variable execute() {
	Variable v = Zetta.executeMethod((TokenMethodCall) subTokens[0]);
	final Token varDec = new TokenVariable(Zetta.RESERVED_VAR_NAME, null);
	for (int c = 1; c < subTokens.length; c++) {
	    v.name = Zetta.RESERVED_VAR_NAME;
	    Zetta.variables.add(v);
	    final Variable oldV = v;
	    v = ((TokenMethodCall) subTokens[c]).method.execute(
		    subTokens[c].subTokens, varDec);
	    Zetta.variables.remove(oldV);
	}
	return v;
    }

}
