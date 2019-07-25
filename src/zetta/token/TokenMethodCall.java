package zetta.token;

import zetta.Zetta;
import zetta.exception.InvalidMethodOrPointerException;
import zetta.exception.UndefinedVariableException;
import zetta.lang.Method;
import zetta.lang.Token;

public class TokenMethodCall extends TokenPlaceholder {

    public Method method;
    public Token object;
    public String[] strings;

    public TokenMethodCall(final String method, final String[] args,
	    final String object, final String dType) {
	super(method);
	this.object = null;
	if (!object.equals("")) {
	    this.object = getTokenType(object);
	}
	strings = args;
	subTokens = new Token[args.length];
	int k = 0;
	for (int c = 0; c < subTokens.length; c++) {
	    subTokens[k] = Token.getTokenType(args[c]);
	    k++;
	}
	Method m = null;

	for (int c = 0; c < Zetta.methods.size(); c++) {
	    final Method m2 = Zetta.methods.get(c);
	    if (m2.name.equals(method)) {
		if (m2.pointerMode.equals(Method.POINTER_SPECIFIC)
			&& dType.equals(m2.pointerDType) && !object.equals("")) {
		    m = m2;
		} else if (m2.pointerMode.equals(Method.POINTER_STANDARD)
			&& !object.equals(Method.POINTER_PACKAGE)
			&& !object.equals("")) {
		    m = m2;
		} else if (m2.pointerMode.equals(Method.POINTER_ABSTRACT)
			&& object.equals(Method.POINTER_ABSTRACT)
			&& dType.equals("")) {
		    m = m2;
		} else if (m2.pointerMode.equals(Method.POINTER_PACKAGE)
			&& object.equals(Method.POINTER_PACKAGE)
			&& dType.equals(m2.pointerDType)) {
		    m = m2;
		}
	    }
	}
	if (m == null) {
	    new InvalidMethodOrPointerException(method);
	} else {
	    if (m.canConstruct(subTokens)) {
		this.method = m;
	    }
	}
    }

    public TokenMethodCall(final String str) {
	super(str);
    }

    public TokenMethodCall() {
	// TODO Auto-generated constructor stub
	super();
    }

    @Override
    public Object evaluate() {
	return value;
    }

    public static String string() {
	return "method call";
    }

    @Override
    public Token getTokenFromString(final String str) {
	if (containsNoOperators(str)) {
	    if (!str.startsWith("_")) {
		if (str.startsWith("$")) {
		    final int fullStopInd = indexOf(str, '.', true);
		    if (fullStopInd > 1) {
			final int openInd = indexOfOpeningBracket(str, '(');
			final int closeInd = indexOfClosingBracket(str, ')');
			if ((closeInd > fullStopInd) && (closeInd > openInd)
				&& (openInd > fullStopInd)) {
			    final String d = Zetta.compVariables.get(str
				    .substring(1, fullStopInd));
			    if (d == null) {
				new UndefinedVariableException(str.substring(1,
					fullStopInd));
			    } else return new TokenMethodCall(
				    str.substring(fullStopInd + 1, openInd),
				    split(str.substring(openInd + 1, closeInd)),
				    str.substring(0, fullStopInd), d);
			}
		    }
		} else if ((str.charAt(0) <= 'Z') && (str.charAt(0) >= 'A')) {
		    final int commaInd = indexOf(str, ',', true);
		    final int openInd = indexOfOpeningBracket(str, '(');
		    final int closeInd = indexOfClosingBracket(
			    commaInd > openInd ? str.substring(0, commaInd)
				    : str, ')');
		    final int fullStopInd = indexOf(str, '.', true);
		    if ((closeInd > openInd) && (openInd > 3)
			    && (fullStopInd > 1)) {
			if ((openInd > fullStopInd) && (closeInd > fullStopInd)
				&& (indexOf(str, '$', true) == -1)) return new TokenMethodCall(
				str.substring(fullStopInd + 1, openInd),
				split(str.substring(openInd + 1, closeInd)),
				Method.POINTER_PACKAGE, str.substring(0,
					fullStopInd));
		    }
		} else {
		    final int openInd = indexOfOpeningBracket(str, '(');
		    final int closeInd = indexOfClosingBracket(str, ')');
		    if ((openInd > 1) && (closeInd > openInd)
			    && containsNoOperators(str)) {
			if (str.endsWith("{")) {} else return new TokenMethodCall(
				str.substring(0, openInd), split(str.substring(
					openInd + 1, closeInd)),
				Method.POINTER_ABSTRACT, "");
		    }
		}
	    }
	}
	return null;
    }

    @Override
    public boolean runToken() {
	Zetta.executeMethod(this);
	return true;
    }

}
