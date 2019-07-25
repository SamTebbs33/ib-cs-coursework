package zetta.lang;

import zetta.Zetta;
import zetta.token.TokenConstruct;

/**
 * Constructs such as if statements and loops
 * 
 * @author samueltebbs
 * 
 */

public class Construct {

    public String name;

    public Construct(final String name) {
	this.name = name;
	Zetta.constructs.add(this);
	Zetta.currentLib.constructs.add(this);
    }

    public String[][] docArguments() {
	return new String[][] {};
    }

    public String[] docDescriptions() {
	return new String[] { "" };
    }

    public String[][] docThrows() {
	return new String[][] {};
    }

    public String[] docCodeExamples() {
	return new String[] {};
    }

    public boolean canConstruct(final Token[] tokens, final TokenConstruct t) {
	return true;
    }

    public Variable run(final TokenConstruct token) {
	return null;
    }

}