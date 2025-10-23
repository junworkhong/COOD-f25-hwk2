/**
 * @author [Jun Hong]
 *
 * This class contains the methods used for conducting a simple sentiment analysis.
 *
 * Justification:
 * I chose a HashMap instead of a TreeMap because there was no need for an order.
 * HashMaps are faster for get, put, and remove operations so I went with the
 * HashMap instead of the TreeMap.
 */

import java.io.FileNotFoundException;
import java.util.*;

public class Analyzer {

	/**
	 * This method calculates the weighted average for each word in all the Sentences.
	 * This method is case-insensitive and all words should be stored in the Map using
	 * only lowercase letters.
	 //* @param sentences Set containing Sentence objects with words to score
	 * @return Map of each word to its weighted average; null if input is null
	 */

//    @Override
//    public int hashCode() {
//        return 7;
//    }

    private static Map<Double, String> storeValues = new HashMap<>();

	public static Map<String, Double> calculateWordScores(Set<Sentence> sentences) {
		/*
		 * Implement this method in Step 2
		 */
        if (sentences == null) {
            return null;
        }

        if (sentences.isEmpty()) {
            return new HashMap<>();
        }

        Map<String, Double> myMap = new HashMap<>();
        Map<String, Integer> countMap = new HashMap<>();

        for (Sentence sentence : sentences) {
            if (sentence.getText() == null)
                continue;

            String line = sentence.getText();
            int score = sentence.getScore();

            if (line.length() < 7) {
                continue;
            }

            if (!Character.isAlphabetic(line.charAt(0)))
                continue;

            boolean startsWithDigit = score >= 0 && score <= 2;
            boolean startsWithNegative = score < 0 && score >= -2;
            if ((!startsWithDigit && !startsWithNegative)) {
                continue;
            }

            String[] words = sentence.getText().split(" ");
            for (String word : words) {
                String newWord = word.toLowerCase();
                if (newWord.equals(",") || (newWord.equals("'s")) || (newWord.equals(".")))
                    continue;
                if (!Character.isAlphabetic(word.charAt(0)))
                    continue;
                if (!myMap.containsKey(newWord)) {
                    myMap.put(newWord, (double) sentence.getScore());
                    countMap.put(newWord, 1);
                }else {
                    double oldAvg = myMap.get(newWord);
                    myMap.put(newWord, (double) sentence.getScore() + oldAvg);
                    countMap.put(newWord, countMap.get(newWord) + 1);
                }
            }
        }

        //to get the averages
        for (Map.Entry<String, Double> entry : myMap.entrySet()) {
            myMap.put(entry.getKey(), entry.getValue()/countMap.get(entry.getKey()));
        }
		return myMap;
	}
	
	/**
	 * This method determines the sentiment of the input sentence using the average of the
	 * scores of the individual words, as stored in the Map.
	 * This method is case-insensitive and all words in the input sentence should be
	 * converted to lowercase before searching for them in the Map.
	 * 
	 * @param wordScores Map of words to their weighted averages
	 * @param sentence Text for which the method calculates the sentiment
	 * @return Weighted average scores of all words in input sentence; null if either input is null
	 */
	public static double calculateSentenceScore(Map<String, Double> wordScores, String sentence) {
		/*
		 * Implement this method in Step 3
		 */
        if (wordScores == null || sentence == null || wordScores.isEmpty() || sentence.isEmpty()) {
            return 0;
        }

        for (Map.Entry<Double, String> entry: storeValues.entrySet()) {
            if (entry.getValue().equals(sentence)) {
                return entry.getKey();
            }
        }

        String[] words = sentence.split(" ");
        Map<String, Integer> countMap = new HashMap<>();
        Map<String, Double> sumMap = new HashMap<>();

        for (String word : words) {
            String newWord = word.toLowerCase();
            if (newWord.equals(",") || (newWord.equals("'s")) || (newWord.equals(".")))
                continue;
            if (!wordScores.containsKey(newWord))
                continue;
            if (wordScores.containsKey(newWord) && !countMap.containsKey(newWord)) {
                countMap.put(newWord, 1);
                sumMap.put(newWord, wordScores.get(newWord));
            } else{
                countMap.put(newWord, countMap.get(newWord) + 1);
                sumMap.put(newWord, sumMap.get(newWord) + wordScores.get(newWord));
            }
        }

        double score = 0.0;
        int counter = 0;
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            score = score + sumMap.get(entry.getKey());
            counter = counter + countMap.get(entry.getKey());
        }

        double calculatedScore = score / counter;
        storeValues.put(calculatedScore, sentence);
		return calculatedScore;
	}

    /**
     * Use this main() method for testing your calculateWordScores and
     * calculateSentenceScore methods with different inputs.
     * Note that this is _NOT_ the main() method for the whole sentiment analysis program!
     * Just use it for testing this class. It is not considered for grading.
     */
    public static void main(String[] args) throws FileNotFoundException {
        Set<Sentence> newSet = Reader.readFile(args[0]);
        newSet = new HashSet<>();
        newSet.add(new Sentence(2, "I like cake and could eat cake all day ."));
        newSet.add(new Sentence(1, "I hope the dog does not eat my cake ."));
        System.out.println(calculateWordScores(newSet));
        Map<String, Double> scores = new HashMap<>();
        scores.put("dogs", 1.5);
        scores.put("are", 0.0);
        scores.put("cute", 2.0);
        double score = calculateSentenceScore(scores, "dogs are cute");
        System.out.println(score);
        score = calculateSentenceScore(scores, "dogs ?are cute");
        System.out.println(score);
    }

}
