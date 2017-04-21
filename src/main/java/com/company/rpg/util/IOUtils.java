package com.company.rpg.util;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Contains IO utility methods for interaction with user
 * @author Vipin
 */
public class IOUtils {
	public static String PROMPT = ">";
	private static final String Yes = "yes";
	private static final String No = "no";
	private static final Set<String> choices = new HashSet<String>(Arrays.asList(Yes, No));

	public IOUtils() {
		requireNonNull(System.in);
	}
	Scanner sc =new Scanner(System.in);
	/**
	 * Retrieves non-empty input from user.
	 * 
	 * @param question
	 *            formattable question to get input for
	 * @param args
	 *            parameters
	 * @return user input
	 */
	public String ask(String question, Object... args) {
		while (true) {
			System.out.printf("%s %s", PROMPT, String.format(question, args));
			String answer = sc.nextLine();
			if (!answer.isEmpty()) {
				return answer;
			}
		}
	}

	/**
	 * Retrieves non-empty input from user with limited number of choices
	 * 
	 * @param question
	 *            formattable question to get input for
	 * @param args
	 *            parameters
	 * @return user input
	 */
	public boolean askYesNo(String question, Object... args) {
		while (true) {
			System.out.printf("%s %s [Yes/No]? ", PROMPT,
					String.format(question, args));
			String answer = sc.nextLine();
			if (!answer.isEmpty() && choices.contains(answer.toLowerCase())) {
				return Yes.equals(answer);
			}
		}
	}

	/**
	 * Prints formatted text to output
	 * 
	 * @param text
	 *            to print
	 * @param args
	 *            format arguments
	 */
	public void say(String text, Object... args) {
		System.out.printf(text, args);
	}

	/**
	 * Prints a single character
	 * 
	 * @param character
	 */
	public void say(Character character) {
		System.out.printf("%c", character);
	}
	
	/**
	 * Prints 
	 * @param text
	 * @param args
	 */
	public void print(String text) {
		System.out.printf(text +"\n");
	}
	/**
	 * Printing contens in a list.
	 * @param list
	 */
	public void printList(List<String> list){
		  for (int i = 0; i < list.size(); i++) {
	            int printIndex = i + 1;
	            System.out.println("    " + printIndex + ". " + list.get(i));
	        }
	}

	/**
	 * Closing the scanner resource.
	 */
	public void close(){
		sc.close();
	}

}
