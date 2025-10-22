/**
 * @author [Jun Hong]
 *
 * This class contains a method for reading from a file and creating Sentence objects
 * for a sentiment analysis program.
 *
 * Justification:
 * For the readFile() method I chose a HashSet because it doesn't need to be sorted in any order,
 * otherwise I would've chosen a TreeSet. A HashSet operates using constant time, so it is quicker than a
 * TreeSet as well, therefore I chose a HashSet.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Reader {
	/**
	 * This method reads sentences from the input file, creates a Sentence object
	 * for each, and returns a Set of the Sentences.
	 * 
	 * @param filename Name of the input file to be read
	 * @return Set containing one Sentence object per sentence in the input file; null if filename is null
	 */
	public static Set<Sentence> readFile(String filename) throws FileNotFoundException {
		/*
		 * Implement this method in Step 1
		 */
        File file = new File(filename);
        if (!file.exists() || !file.isFile()) {
//            throw new IllegalArgumentException("File " + filename + " does not exist or is not a file.");
            return null;
        }

//        File[] files = file.listFiles();
//
//        if (files == null) {
//            throw new IllegalArgumentException("Directory is empty");
////            return null;
//        }

        Set<Sentence> sentences = new HashSet<>();

        Scanner scanFile = new Scanner(file);
        while (scanFile.hasNextLine()) {
            String line = scanFile.nextLine();

            //To ensure that the review isn't too short (or that there actually is a review)
            if (line.length() < 7) {
                continue;
            }

            //To ensure that it either starts with a digit between 0 and 2 or a negative number between -1 and -2
            boolean startsWithDigit = Character.isDigit(line.charAt(0)) && (line.charAt(0) >= '0' || line.charAt(0) <= '2') && (line.charAt(1) == ' ');
            boolean startsWithNegative = (line.charAt(0) == '-') && Character.isDigit(line.charAt(1)) && (line.charAt(1) >= '0' || line.charAt(1) <= '2') && (line.charAt(2) == ' ');
            if ((!startsWithDigit && !startsWithNegative)) {
                continue;
            }

            Sentence sentence;
            if (startsWithDigit) {
                int firstDigit = Character.getNumericValue(line.charAt(0));
                sentence = new Sentence(firstDigit, line.substring(2));
            } else {
                int firstDigit = Character.getNumericValue(line.charAt(1)) * -1;
                sentence = new Sentence(firstDigit, line.substring(3));
            }
            sentences.add(sentence);
        }
        return sentences;
		//return null;
	}

    /**
     * Use this main() method for testing your Reader.readFile method with different inputs.
     * Note that this is _NOT_ the main() method for the whole sentiment analysis program!
     * Just use it for testing this class. It is not considered for grading.
     */
    public static void main(String[] args) throws FileNotFoundException {
        Set<Sentence> mySet = Reader.readFile(args[0]);
        for (Sentence sentence : mySet) {
            System.out.println(sentence.getScore() + " " + sentence.getText());
        }
    }
}
