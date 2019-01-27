package lexer;

import java.util.ArrayList;
import java.util.HashMap;

public class Lexer {

	// -----------------------------------------------------------------------------------------------
	public static HashMap<String, Integer> hsn = new HashMap<String, Integer>(); // Identifiers + Reserved words
	public static ArrayList<String> tokens = new ArrayList<String>();
	public static int counter = 1;
	// -----------------------------------------------------------------------------------------------

	@SuppressWarnings("unchecked")
	public Lexer() {
		Keyword.reserveItNow(); // reserve keywords into mernaya
		hsn = (HashMap<String, Integer>) Keyword.mernaya.clone(); // All identifers + Keywords in hsn hashMap

	}

	public static String read(String s) {
		String tempString = "";
		StringBuilder str = new StringBuilder("");
		String[] arrayOfStrings = s.split("\\s+");
		for (int i = 0; i < arrayOfStrings.length; i++) {
			for (int j = 0; j < arrayOfStrings[i].length(); j++) {
				if ((arrayOfStrings[i].charAt(j) != '(' && arrayOfStrings[i].charAt(j) != ')'
						&& arrayOfStrings[i].charAt(j) != ',' && arrayOfStrings[i].charAt(j) != ';'
						&& arrayOfStrings[i].charAt(j) != '*' && arrayOfStrings[i].charAt(j) != '+'
						&& arrayOfStrings[i].charAt(j) != '=' && arrayOfStrings[i].charAt(j) != '-'
						&& arrayOfStrings[i].charAt(j) != '/')) {
					tempString = tempString.concat(Character.toString(arrayOfStrings[i].charAt(j)));
				} else {
					if (!tempString.isEmpty()) {
						if (!hsn.containsKey(tempString))
							hsn.put(tempString, 17);
						tokens.add(tempString);
						// For table file
						str = str.append(counter + "\t" + tempString + "\t" + hsn.get(tempString)
								+ System.getProperty("line.separator"));
						tempString = "";
					}
					tokens.add(Character.toString(arrayOfStrings[i].charAt(j)));
					// For table file
					str = str.append(counter + "\t" + Character.toString(arrayOfStrings[i].charAt(j)) + "\t"
							+ hsn.get(Character.toString(arrayOfStrings[i].charAt(j)))
							+ System.getProperty("line.separator"));
				}
			}
			if (!tempString.isEmpty()) {
				if (!hsn.containsKey(tempString))
					hsn.put(tempString, 17);
				tokens.add(tempString);
				// For table file
				str = str.append(counter + "\t" + tempString + "\t" + hsn.get(tempString)
						+ System.getProperty("line.separator"));
				tempString = "";
			}
		}
		counter++;
		return str.toString();
	}
}