import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author [your name here]
 *
 * This class holds the main() method for the sentiment analysis program.
 */

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        // implement this method in Step 4
        if (args.length != 1) {
            System.out.println("No input file");
            System.exit(0);
        }

        Set<Sentence> newSet = Reader.readFile(args[0]);
        if (newSet == null || newSet.isEmpty()) {
            System.out.println("Bad input file");
            System.exit(0);
        }

        Map<String, Double> wordScores = Analyzer.calculateWordScores(newSet);
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.print("Enter a sentence (type 'quit' to terminate program): ");
            String sentence = scanner.nextLine();
            if (sentence.equals("quit")) {
                System.exit(0);
            }
            double score = Analyzer.calculateSentenceScore(wordScores, sentence);
            System.out.println("Your sentence score is: " + score);
        }while (true);
    }
}
