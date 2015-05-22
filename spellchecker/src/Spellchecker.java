
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import word.Word;

import dictionary.Dictionary;
import dictionary.FileDictionary;
import dictionary.MemDictionary;
import document.Document;


public class Spellchecker {

	public Spellchecker() {
		
	}
	
	public static Word consultUser(Word word, Dictionary dict, Dictionary dict_ignored) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String s;
		System.out.println("Palabra no  reconocida" + word.getWord() + ":");
		System.out.println("Aceptar (a) - Ignorar (i) - Reemplazar (r)");
		s = in.readLine();
		if(s == "a") {
			dict.add(word);
		}
		if(s == "i") {
			dict_ignored.add(word);
		}
		if(s == "r") {
			System.out.println("Ingrese una palabra:");
			word.setWord(in.readLine());
		}
		return word;
	}
	
	public static void proccesDocument(String docIn, String docOut, Dictionary dictIn, Dictionary dictIgnored) {
		try {
			Document document = new Document(docIn, docOut);
			Word word = new Word(); 
			while((word = document.getWord()) != null){			
				if(dictIn.contains(word) || dictIgnored.contains(word)) {
					document.putWord(word);
				} else {
					word = consultUser(word, dictIn, dictIgnored);
					document.putWord(word);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length < 2) {
			System.out.println("nro de argumentos erroneo. Deben ser <documento> [<diccionario>].");
			return;
		}

		String path = (args.length >= 3) ? args[2] : "dict.txt";
		String text = args[1];
		FileDictionary dictMain = new FileDictionary(path); 
		try {
			dictMain.load(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("No existe archivo");
			return;
		}
		   MemDictionary dictIgnored = new MemDictionary(); 
		   proccesDocument(text, "out.txt", dictMain, dictIgnored);
		   dictMain.save();
	}

}
