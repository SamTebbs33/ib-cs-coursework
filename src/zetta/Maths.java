package zetta;

import java.util.Stack;

/**
 * Class to evaluate expressions.
 * 
 * @author Paul E. Davis (feedback@willcode4beer.com)
 */
public class Maths {

    /**
     * Operators in reverse order of precedence.
     */
    private static final String operators = "-+/*";
    private static final String operands = "0123456789";

    private int getPrecedence(final char operator) {
	int ret = 0;
	if ((operator == '-') || (operator == '+')) {
	    ret = 1;
	} else if ((operator == '*') || (operator == '/')) {
	    ret = 2;
	}
	return ret;
    }

    private boolean operatorGreaterOrEqual(final char op1, final char op2) {
	return getPrecedence(op1) >= getPrecedence(op2);
    }

    private boolean isOperator(final char val) {
	return operators.indexOf(val) >= 0;
    }

    private boolean isOperand(final char val) {
	return operands.indexOf(val) >= 0;
    }

    public String convert2Postfix(final String infixExpr) {
	final char[] chars = infixExpr.toCharArray();
	final Stack<Character> stack = new Stack<Character>();
	final StringBuilder out = new StringBuilder(infixExpr.length());

	for (final char c : chars) {
	    if (isOperator(c)) {
		while (!stack.isEmpty() && (stack.peek() != '(')) {
		    if (operatorGreaterOrEqual(stack.peek(), c)) {
			out.append(stack.pop());
		    } else {
			break;
		    }
		}
		stack.push(c);
	    } else if (c == '(') {
		stack.push(c);
	    } else if (c == ')') {
		while (!stack.isEmpty() && (stack.peek() != '(')) {
		    out.append(stack.pop());
		}
		if (!stack.isEmpty()) {
		    stack.pop();
		}
	    } else if (isOperand(c)) {
		out.append(c);
	    }
	}
	while (!stack.empty()) {
	    out.append(stack.pop());
	}
	return out.toString();
    }

    public int evaluatePostfix(final String postfixExpr) {
	final char[] chars = postfixExpr.toCharArray();
	final Stack<Integer> stack = new Stack<Integer>();
	for (final char c : chars) {
	    if (isOperand(c)) {
		stack.push(c - '0'); // convert char to int val
	    } else if (isOperator(c)) {
		final int op1 = stack.pop();
		final int op2 = stack.pop();
		int result;
		switch (c) {
		    case '*':
			result = op1 * op2;
			stack.push(result);
			break;
		    case '/':
			result = op2 / op1;
			stack.push(result);
			break;
		    case '+':
			result = op1 + op2;
			stack.push(result);
			break;
		    case '-':
			result = op2 - op1;
			stack.push(result);
			break;
		}
	    }
	}
	return stack.pop();
    }

    public int evalInfix(final String infix) {
	return evaluatePostfix(convert2Postfix(infix));
    }
}