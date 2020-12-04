/*GameWordPicker is a subclass of GameScene used to scan a list of words from a .txt 
  file into an Arraylist and return a random word from that Arraylist.
*/

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameWordPicker {

	private static final long serialVersionUID = 7456833108090465278L;

	private ArrayList<String> totype = new ArrayList<>();
	private Random rand = new Random();
	private Scanner input;

	public GameWordPicker() {
		try {
			input = new Scanner(new File("words.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (input.hasNextLine()) {
			totype.add(input.nextLine());
		}
	}

	public String pick() {
		return totype.get(rand.nextInt(totype.size()));
	}

}
