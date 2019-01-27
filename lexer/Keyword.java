package lexer;

import java.util.HashMap;

public class Keyword {
	public static String lexeme;
	public static int valu;
	public static HashMap<String, Integer> mernaya = new HashMap<String, Integer>(); // Reserved words

	private static void reserve(Keyword k) {
		mernaya.put(Keyword.lexeme, Keyword.valu);
	}

	static void reserveItNow(){
		reserve(new Keyword(1, "PROGRAM"));
		reserve(new Keyword(2, "VAR"));
		reserve(new Keyword(3, "BEGIN"));
		reserve(new Keyword(4, "END"));
		reserve(new Keyword(5, "END."));
		reserve(new Keyword(6, "FOR"));
		reserve(new Keyword(7, "READ"));
		reserve(new Keyword(8, "WRITE"));
		reserve(new Keyword(9, "TO"));
		reserve(new Keyword(10, "DO"));
		reserve(new Keyword(11, ";"));
		reserve(new Keyword(12, "="));
		reserve(new Keyword(13, "+"));
		reserve(new Keyword(14, ","));
		reserve(new Keyword(15, "("));
		reserve(new Keyword(16, ")"));
		reserve(new Keyword(18, "*"));
		reserve(new Keyword(19, "-"));
		reserve(new Keyword(20, "/"));
	}

	public Keyword(int valu, String lexeme) {
		Keyword.valu = valu;
		Keyword.lexeme = lexeme;
	}
}
