/*GameWordPicker is a subclass of GameScene used to scan a list of words from a .txt 
  file into an Arraylist and return a random word from that Arraylist.
*/

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameWordPicker extends GameScene {
private  ArrayList<String> totype = new ArrayList<>();
private Random rand= new Random();

//pick method that returns a random word from the text file
	public Word pick() {
		Scanner input = null;
        
		try {
			input = new Scanner(new File("words.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (input.hasNextLine()) {
			totype.add(input.nextLine());
		}
        
		Word word = new Word();
		word.str = totype.get(rand.nextInt(totype.size()));
		word.move = rand.nextInt(4) + 1;
		return word;
	}

}
