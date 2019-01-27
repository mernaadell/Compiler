package parser;

import java.util.ArrayList;

import lexer.Lexer;
import parser.CodeGenerator;

public class Parser {
	private static int index = 0;
	private static int idListIndex = 0; // for idList
	private static int tempIndex = 0; // to save start index for every function
	private static String str = ""; // to get any function's return
	private static Boolean flagIdList = false; // to take IdList once
	private static Boolean flagSmsm = false;
	public static String isProgStr = ""; // to save all the prog
	private static String isIdStr = "";
	public static ArrayList<String> temp = new ArrayList<String>();

	static Boolean isRead(ArrayList<String> arr) {
		boolean found = false;
		if (!flagSmsm)
			str += "SMSM ";
		if (arr.get(index).equals("READ")) {
			tempIndex = index;
			index++;
			if (arr.get(index).equals("(")) {
				index++;
				if (isIDList(arr)) {
					if (arr.get(index).equals(")")) {
						found = true;
						str += CodeGenerator.codeIsRead(arr, tempIndex, index);
						index++;
					}
				}
			}
		}
		return found;
	}

	static Boolean isFor(ArrayList<String> arr) {
		boolean found = false;
		if (!flagSmsm)
			str += "SMSM ";
		if (arr.get(index).equals("FOR")) {
			tempIndex = index;
			index++;
			if (isIDList(arr)) {
				if (arr.get(index).equals("=")) {
					index++;
					if (isExp(arr)) {
						if (arr.get(index).equals("TO")) {
							index++;
							if (isExp(arr)) {
								if (arr.get(index).equals("DO")) {
									index++;
									if (isBody(arr)) {
										found = true;
										index++;
									}
								}
							}
						}
					}
				}
			}

		}
		return found;
	}

	static Boolean isBody(ArrayList<String> arr) {
		boolean found = false;
		@SuppressWarnings("unused")
		boolean chckr = false;
		if (arr.get(index).equals("BEGIN")) {
			index++;
			if (isStmt(arr)) {
				while ((chckr = isStmt(arr) && !arr.get(index).equals("END")) && found) {
				}
				if (arr.get(index).equals("END")) {
					found = true;
				}
			}
		}
		return found;
	}

	static Boolean isWrite(ArrayList<String> arr) {
		boolean found = false;
		if (!flagSmsm)
			str += "SMSM ";
		if (arr.get(index).equals("WRITE")) {
			tempIndex = index;
			index++;
			if (arr.get(index).equals("(")) {
				index++;
				if (isIDList(arr)) {
					if (arr.get(index).equals(")")) {
						found = true;
						str += CodeGenerator.codeIsWrite(arr, tempIndex, index) + "\n";
						index++;
					}
				}
			}
		}
		return found;
	}

	static Boolean isProgName(ArrayList<String> arr) {
		boolean found = false;
		if (Lexer.hsn.get(arr.get(index)) == 17) {
			if (!arr.get(index).toString().matches("[a-zA-Z][a-zA-Z_0-9]*"))
				return false;
			found = true;
		}
		return found;
	}

	public static Boolean isProg(ArrayList<String> arr) {
		boolean found = false;
		if (arr.get(index).equals("PROGRAM")) {
			index++;
			if (isProgName(arr)) {
				index++; 
				if (arr.get(index).equals("VAR")) {
					index++;
					if (isIDList(arr)) {
						flagIdList = true; // to save ID Lists
						if (arr.get(index).equals("BEGIN")) {
							index++;
							if (isStmtList(arr)) {
								if (arr.get(index).equals("END.")) {
									isProgStr = CodeGenerator.codeIsProg(arr, 0, 1);
									isProgStr = isProgStr.concat(isIdStr);
									isProgStr = isProgStr.concat(str);
									isProgStr = isProgStr.concat("\tLDL\tRETADR" + System.getProperty("line.separator")
											+ "\tRSUB" + System.getProperty("line.separator"));
									isProgStr = isProgStr.replaceAll("\n", "");
									for (int i = 0; i < InfixToPostfix.saveTemps.size(); i++)
										isProgStr += InfixToPostfix.saveTemps.get(i) + "\tRESW" + "\t1\n";
									isProgStr += "\tEND\t" + (arr.get(1));
									found = true;
								}
							}
						}
					}
				}
			}
		}
		return found;
	}

	static Boolean isStmtList(ArrayList<String> arr) {
		boolean found = false;
		@SuppressWarnings("unused")
		boolean chckr = false;
		if (isStmt(arr)) {
			found = true;
			while ((chckr = isStmt(arr) && !arr.get(index).equals("END.")) && found) {
			}
			if (index != arr.size() - 1)
				found = false;
		}
		return found;
	}

	static Boolean isStmt(ArrayList<String> arr) {
		boolean found = false;
		if (isRead(arr)) {
			found = true;
			flagSmsm = true;
			return found;
		} else if (isWrite(arr)) {
			found = true;
			flagSmsm = true;
			return found;
		} else if (isAssign(arr)) {
			found = true;
			flagSmsm = true;
			return found;
		} else if (isFor(arr)) {
			found = true;
			flagSmsm = true;
			return found;
		} else
			return found;
	}

	static Boolean isIDList(ArrayList<String> arr) {
		boolean found = false;
		idListIndex = index;
		if (Lexer.hsn.get(arr.get(index)) == 17) {
			if (!arr.get(index).toString().matches("[a-zA-Z][a-zA-Z_0-9]*"))
				return false;
			found = true;
			index++;
			while (arr.get(index).equals(",") && found) {
				index++;
				if (Lexer.hsn.get(arr.get(index)) == 17) {
					if (!arr.get(index).toString().matches("[a-zA-Z][a-zA-Z_0-9]*"))
						return false;
					index++;
				} else {
					found = false;
				}
			}
		}
		if (!flagIdList)
			isIdStr += CodeGenerator.codeIsIdList(arr, idListIndex, index - 1) + "\n"; // Before "BEGIN"
		return found;
	}

	static Boolean isAssign(ArrayList<String> arr) {
		boolean found = false;
		if (!flagSmsm)
			str += "SMSM ";
		tempIndex = index;
		if (Lexer.hsn.get(arr.get(index)) == 17) {
			index++;
			if (Lexer.hsn.get(arr.get(index)) == 12) {
				index++;
				if (isExp(arr)) {
					if (arr.get(index).equals(";")) {
						found = true;
						str += CodeGenerator.codeIsStmt(arr, tempIndex, index); // Including the ;
						index++;
					} else
						found = false;
				}
			}
		}
		return found;
	}

	static Boolean isExp(ArrayList<String> arr) {
		boolean found = false;
		if (isTerm(arr)) {
			found = true;
			while ((arr.get(index).equals("+") || (arr.get(index).equals("-"))) && found) {
				index++;
				if (!isTerm(arr)) {
					found = false;
				}
			}
		}
		return found;
	}

	static Boolean isTerm(ArrayList<String> arr) {
		boolean found = false;
		if (isFactor(arr)) {
			found = true;
			while ((arr.get(index).equals("*") || (arr.get(index).equals("/"))) && found) {
				index++;
				if (!isFactor(arr)) {
					found = false;
				}
			}
		}
		return found;
	}

	static Boolean isFactor(ArrayList<String> arr) {
		boolean found = false;
		if (Lexer.hsn.get(arr.get(index)) == 17) {
			found = true;
			index++;
		} else {
			if (arr.get(index).equals("(")) {
				index++;
				if (isExp(arr)) {
					if (arr.get(index).equals(")")) {
						found = true;
						index++;
					}
				}
			}
		}
		return found;
	}

}