package minyon;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
public class WordGram{
	
	private static int count;
	
	//save word list to HashSet
	public static StringTable getWords(String file){
		String strLine;
		StringTable wordTable = new StringTable();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			while((strLine = br.readLine()) != null){
				wordTable.insert(strLine);
			}
			br.close();
		} catch (Exception e) {}	
		return wordTable; 
	}
	
	//save prefixes to HashSet
	public static StringTable getPrefixes(String file){
		String strLine;
		StringTable prefixTable = new StringTable();
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			while((strLine = br.readLine()) != null){
				for(int i = 0; i < strLine.length() + 1; i ++){
					prefixTable.insert(strLine.substring(0,i));
				}
			}
			br.close();
		}catch(Exception e){}
		
		return prefixTable;
	}
	
	//grab specific character from string
	public static char grab(int i, String s){
		return s.charAt(i);
	}

	//grab single character substring from string
	public static String grabString(int i, String s){
		return s.substring(i, i+1);
	}

	//remove specified character from string
	public static String takeAway(int i, String s){
		return (s.substring(0, i) + s.substring(i+1));
	}
	
	//search through word list for anagrams
	public static void anagram(String agram, String check, String notUsed, StringTable words, StringTable prefixes,ArrayList<String> anagrams){
		//no more letters to check, no unused characters, and we haven't already found the anagram
		if((check.length() == 0) && (notUsed.length() == 0) && !anagrams.contains(agram)){
			anagrams.add(agram);
			count++;
			System.out.println(agram);
		}

		//is the word in our list?
		if(words.contains(check)){
			anagram(agram + " " + check, "", notUsed, words, prefixes, anagrams);
		}

		//we need to keep checking unused characters for words
		else if((check.length() == 0) && (notUsed.length()!=0)){
			for(int i = 0; i < notUsed.length(); i++){
				anagram(agram, (grabString(i, notUsed)), takeAway(i, notUsed), words, prefixes,anagrams);
			}
		}		

		//current string exists in our prefix list (so see if we can finish the word)
		if(prefixes.contains(check)){
			for(int i = 0; i < notUsed.length(); i++){
				anagram(agram, check + grab(i, notUsed), takeAway(i, notUsed), words, prefixes, anagrams);
			}
		}
	}

	public static void main(String[] args){
		String fileName = "Words.txt";		
		String scrambledInput = "";
		StringTable Words = getWords(fileName);
		StringTable Prefixes = getPrefixes(fileName);
		Scanner scan_in = new Scanner(System.in);
		
		//continually get user input
		while(true)
		{			
			count = 0;
			ArrayList<String>SolvedAnagrams = new ArrayList<String>();
			System.out.println("Enter your WordGram (No Spaces)");
			scrambledInput = scan_in.nextLine();
			anagram("","", scrambledInput, Words, Prefixes, SolvedAnagrams);
			System.out.println(count + " WordGrams Found!");
		}
	}
}