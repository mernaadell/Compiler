package parser;

import java.util.ArrayList;
import java.util.Stack;

import lexer.Lexer;

public class InfixToPostfix {

	public static ArrayList<String> saveTemps = new ArrayList<String>();
	private static int counter = 1;

	/**
	 * Checks if the input is operator or not
	 * 
	 * @param c
	 *            input to be checked
	 * @return true if operator
	 */
	static boolean isOperator(String c) {
		if (c.equals("+") || c.equals("-") || c.equals("*") || c.equals("/") || c.equals("^"))
			return true;
		return false;
	}

	/**
	 * Checks if c2 has same or higher precedence than c1
	 * 
	 * @param c1
	 *            first operator
	 * @param c2
	 *            second operator
	 * @return true if c2 has same or higher precedence
	 */
	static boolean checkPrecedence(String c1, String c2) {
		if ((c2.equals("+") || c2.equals("-")) && (c1.equals("+") || c1.equals("-")))
			return true;
		else if ((c2.equals("*") || c2.equals("/"))
				&& (c1.equals("+") || c1.equals("-") || c1.equals("*") || c1.equals("/")))
			return true;
		else if (c2.equals("^") && (c1.equals("+") || c1.equals("-") || c1.equals("*") || c1.equals("/")))
			return true;
		else
			return false;
	}

	/**
	 * Converts infix expression to postfix s.peek()
	 * 
	 * @param infix
	 *            infix expression to be converted
	 * @return postfix expression
	 */
	static ArrayList<String> convert(ArrayList<String> infix) {
		ArrayList<String> test = new ArrayList<String>();
		Stack<String> s = new Stack<String>(); // stack to hold symbols
		s.push("#"); // symbol to denote end of stack

		for (int i = 0; i < infix.size(); i++) {
			String inputSymbol = infix.get(i); // symbol to be processed
			if (isOperator(inputSymbol)) { // if a operator
				// repeatedly pops if stack top has same or higher precedence
				while (checkPrecedence(inputSymbol, (s.peek()))) {
					test.add(s.pop());
				}
				s.push(inputSymbol);
			} else if (inputSymbol.equals("("))
				s.push(inputSymbol); // push if left parenthesis
			else if (inputSymbol.equals(")")) {
				// repeatedly pops if right parenthesis until left parenthesis is found
				while (!s.peek().equals("(")) {
					test.add(s.pop());
				}
				s.pop();
			} else
				test.add(inputSymbol);
		}

		// pops all elements of stack left
		while (!s.peek().equals("#")) {
			test.add(s.pop());

		}

		return test;
	}

	/**
	 * Formats the input stack string
	 * 
	 * @param It
	 *            is a stack converted to string
	 * @return formatted input
	 */
	static String evaluatePostfix(ArrayList<String> exp) {
		String str = ""; // to save All the things
		String accumlator = "Nothing new!";
		// create a stack
		Stack<String> stack = new Stack<>();

		// Scan all characters one by one
		for (int i = 0; i < exp.size(); i++) {
			String c = exp.get(i);

			// If the scanned character is an operand (number here),
			// push it to the stack.
			if (exp.size() == 1) // A = B
			{
				str += "\tLDA\t" + c + System.getProperty("line.separator");
			} else {

				if (Lexer.hsn.get(exp.get(i)) == 17)
					stack.push(exp.get(i));

				// If the scanned character is an operator, pop two
				// elements from stack apply the operator
				else {
					String val1 = stack.pop();
					String val2 = stack.pop();
					switch (c) {
					case "+":
						if (val1.equals(accumlator)) {
							str += "\tADD\t" + val2 + System.getProperty("line.separator");
							if (i != exp.size() - 1) {
								str += "\tSTA\t" + "T" + counter + System.getProperty("line.separator");
								stack.push("T" + counter);
								accumlator = "T" + counter;
								saveTemps.add(accumlator);
								counter++;
							}
						} else {
							if (val2.equals(accumlator)) {
								str += "\tADD\t" + val1 + System.getProperty("line.separator");
								if (i != exp.size() - 1) {
									str += "\tSTA\t" + "T" + counter + System.getProperty("line.separator");
									stack.push("T" + counter);
									accumlator = "T" + counter;
									saveTemps.add(accumlator);
									counter++;
								}
							} else {
								str += "\tLDA\t" + val1 + System.getProperty("line.separator");
								str += "\tADD\t" + val2 + System.getProperty("line.separator");
								if (i != exp.size() - 1) {
									str += "\tSTA\t" + "T" + counter + System.getProperty("line.separator");
									stack.push("T" + counter);
									accumlator = "T" + counter;
									saveTemps.add(accumlator);
									counter++;
								}
							}
						}
						break;

					case "-":
						if (val1.equals(accumlator)) {
							str += "\tSUB\t" + val2 + System.getProperty("line.separator");
							if (i != exp.size() - 1) {
								str += "\tSTA\t" + "T" + counter + System.getProperty("line.separator");
								stack.push("T" + counter);
								accumlator = "T" + counter;
								saveTemps.add(accumlator);
								counter++;
							}
						} else {
							if (val2.equals(accumlator)) {
								str += "\tSUB\t" + val1 + System.getProperty("line.separator");
								if (i != exp.size() - 1) {
									str += "\tSTA\t" + "T" + counter + System.getProperty("line.separator");
									stack.push("T" + counter);
									accumlator = "T" + counter;
									saveTemps.add(accumlator);
									counter++;
								}
							} else {
								str += "\tLDA\t" + val1 + System.getProperty("line.separator");
								str += "\tSUB\t" + val2 + System.getProperty("line.separator");
								if (i != exp.size() - 1) {
									str += "\tSTA\t" + "T" + counter + System.getProperty("line.separator");
									stack.push("T" + counter);
									accumlator = "T" + counter;
									saveTemps.add(accumlator);
									counter++;
								}
							}
						}
						break;

					case "/":
						if (val1.equals(accumlator)) {
							str += "DIV " + val2 + System.getProperty("line.separator");
							if (i != exp.size() - 1) {
								str += "\tSTA\t" + "T" + counter + System.getProperty("line.separator");
								stack.push("T" + counter);
								accumlator = "T" + counter;
								saveTemps.add(accumlator);
								counter++;
							}
						} else {
							if (val2.equals(accumlator)) {
								str += "\tDIV\t" + val1 + System.getProperty("line.separator");
								if (i != exp.size() - 1) {
									str += "\tSTA\t" + "T" + counter + System.getProperty("line.separator");
									stack.push("T" + counter);
									accumlator = "T" + counter;
									saveTemps.add(accumlator);
									counter++;
								}
							} else {
								str += "\tLDA\t" + val1 + System.getProperty("line.separator");
								str += "\tDIV\t" + val2 + System.getProperty("line.separator");
								if (i != exp.size() - 1) {
									str += "\tSTA\t" + "T" + counter + System.getProperty("line.separator");
									stack.push("T" + counter);
									accumlator = "T" + counter;
									saveTemps.add(accumlator);
									counter++;
								}
							}
						}
						break;

					case "*":
						if (val1.equals(accumlator)) {
							str += "MUL " + val2 + System.getProperty("line.separator");
							if (i != exp.size() - 1) {
								str += "\tSTA\t" + "T" + counter + System.getProperty("line.separator");
								stack.push("T" + counter);
								accumlator = "T" + counter;
								saveTemps.add(accumlator);
								counter++;
							}
						} else {
							if (val2.equals(accumlator)) {
								str += "\tMUL\t" + val1 + System.getProperty("line.separator");
								if (i != exp.size() - 1) {
									str += "\tSTA\t" + "T" + counter + System.getProperty("line.separator");
									stack.push("T" + counter);
									accumlator = "T" + counter;
									saveTemps.add(accumlator);
									counter++;
								}
							} else {
								str += "\tLDA\t" + val1 + System.getProperty("line.separator");
								str += "\tMUL\t" + val2 + System.getProperty("line.separator");
								if (i != exp.size() - 1) {
									str += "\tSTA\t" + "T" + counter + System.getProperty("line.separator");
									stack.push("T" + counter);
									accumlator = "T" + counter;
									saveTemps.add(accumlator);
									counter++;
								}
							}
						}
						break;
					}
				}
			}
		}

		return str;
	}
}