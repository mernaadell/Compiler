package driver;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import assemblerSICXE.SICXE;
import lexer.Lexer;
import parser.Parser;

public class InputDialogue  {


	public static void readAFile(String str) throws IOException {
		String fileName = str + ".txt";
		String line = null;

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileName);
			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			PrintWriter writer = new PrintWriter("Lexical_Analysis_Table.txt", "UTF-8");
			while ((line = bufferedReader.readLine()) != null) {
				writer.println(Lexer.read(line));
			}
			// Always close files.
			bufferedReader.close();
			writer.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
	}

	public static void whenWriteStringUsingBufferedWritter_thenCorrect(String str) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("compilerProg1.asm"));
		String lines[] = str.split("\r|\n");
		for (int i = 0; i < lines.length; i++) {
			writer.write(lines[i]);
			writer.newLine();
		}
		writer.close();
	}

	public static void main(String[] args) throws IOException {
		new Lexer(); // For reserving keywords
		readAFile("Prog"); // Reading Simplified Langugae file
		System.out.println("[LEXER] -- > Done");
		if (Parser.isProg(Lexer.tokens)) { // Parsing file based on (Lexical Analysis tokens)
			whenWriteStringUsingBufferedWritter_thenCorrect(Parser.isProgStr);
			System.out.println("[ASM] --> Done");
		//	SICXE.mainSICXE();
		} else
			System.out.println("-.-\"");

	}
}
