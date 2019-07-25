package zetta.token;

/**
 * @(#)TokenConstruct.java
 * 
 * 
 * @author
 * @version 1.00 2014/6/27
 */

import java.util.LinkedList;

import zetta.Zetta;
import zetta.exception.InvalidConstructException;
import zetta.lang.Construct;
import zetta.lang.Token;
import zetta.lang.Variable;

public class TokenConstruct extends Token {

    public Construct construct;
    public Token[] subTokens;
    public LinkedList<Token> body = new LinkedList<Token>();
    public LinkedList<Variable> localVariables = new LinkedList<Variable>();

    public TokenConstruct(final String str, final int openPos,
	    final int closePos, final int openCPos) {
	super(str.substring(0, openPos));
	if ((openPos < closePos) && ((closePos + 1) == openCPos)) {
	    String cStr = "";
	    if (openPos > -1) {
		cStr = str.substring(0, openPos);
	    }
	    construct = getConstruct(cStr);
	    if (construct == null) {
		new InvalidConstructException(str);
	    }
	    final String[] args = Token.split(str.substring(openPos + 1,
		    closePos));
	    subTokens = new Token[args.length];
	    for (int c = 0; c < args.length; c++) {
		if (args[c] != null) {
		    if (!args[c].equals("")) {
			subTokens[c] = Token.getTokenType(args[c]);
		    }
		}
	    }
	    Zetta.address++;
	    for (; Zetta.address < Zetta.lines.length; Zetta.address++) {
		if (Zetta.lines[Zetta.address] != null) {
		    if (Zetta.lines[Zetta.address].equals("}")) {
			break;/*
			       * { Zetta. lines [Zetta .address ] = Zetta .lines
			       * [Zetta .address ] .substring (1); break; }
			       */
		    }
		    body.add(Token.getTokenType(Zetta.lines[Zetta.address]));
		}
	    }
	    construct.canConstruct(subTokens, this);
	}

    }

    public TokenConstruct() {
	super();
    }

    public static String string() {
	return "construct";
    }

    public static Construct getConstruct(final String cStr) {
	for (final Construct con : Zetta.constructs) {
	    if (con != null) {
		if (con.name.equals(cStr)) return con;
	    }
	}
	return null;
    }

    @Override
    public Token getTokenFromString(final String str) {

	final int openInd = indexOfOpeningBracket(str, '(');
	final int closeInd = indexOfClosingBracket(str, ')');
	if ((openInd > 1) && (closeInd > openInd)) { // Makes
						     // sure
						     // that
						     // (
						     // comes
						     // after
						     // .
						     // and
						     // )
						     // comes
						     // after
						     // (
	    if (str.endsWith("{")) return new TokenConstruct(str, openInd,
		    closeInd, str.length() - 1);
	}
	return null;
    }

    @Override
    public boolean runToken() {
	construct.run(this);
	return true;
    }

}