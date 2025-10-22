import java.io.*;
import java.util.*;

/*
 * Implements a text search engine for a collection of documents in the same directory.
 *
 * Justification:
 * For the buildMap() method, I chose HashMap and HashSet, because the buildMap() method
 * doesn't require the files to be sorted in any order. HashMaps and HashSets are faster
 * for operations than TreeMaps and TreeSets, generally.
 *
 * For the search() method, I decided to go with a TreeSet so that the results would be sorted
 * in order. For the List, I chose a LinkedList instead of an ArrayList because I'm only
 * copying over the results of the set into the list one time, and LinkedLists only use the
 * memory that it needs.
 *
 * Case 4 and 5 are not implemented, because I tried for a few days but could not get it to work.
 */

public class WordSearch {
	
	public static Map<String, Set<String>> buildMap(String dirName) throws FileNotFoundException {
		File dir = new File(dirName);	// create a File object for this directory
		
		// make sure it exists and is actually a directory
		if (dir.exists() == false || dir.isDirectory() == false) {
            // this tells the caller "you gave me bad input"
			throw new IllegalArgumentException(dirName + " does not exist or is not a directory");
		}
		
		File[] files = dir.listFiles();		// get the Files in the specified directory
		
		// Implement the rest of this method starting from here!
		HashMap<String, Set<String>> hash = new HashMap<>();

		if (files == null) {
			throw new IllegalArgumentException("Directory is empty");
		}

		for (File file : files) {
			Scanner scanFile = new Scanner(file);
			while (scanFile.hasNext()) {
				String key = scanFile.next().toLowerCase();
				if (!hash.containsKey(key)) {
					hash.put(key, new HashSet<>());
				}
				hash.get(key).add(file.getName());
			}

		}

//		// this is for debugging, just to make sure it's reading the right files
//        for (File file : files) {
//            System.out.println(file.getName());
//        }
		
		return hash; // change this as necessary
		
	}
	
	public static List<String> search(String[] terms, Map<String, Set<String>> map) {
		// Implement this method starting from here!
		Set<String> mySet = new TreeSet<>();

        if (terms == null || terms.length == 0) {
            return new LinkedList<>();
        }

        Map<String, Integer> countMap = new HashMap<>();
//        countMap.put("file1.txt", 0);
//        countMap.put("file2.txt", 0);
//        countMap.put("file3.txt", 0);

		for (String term : terms) {
            int counter = 0;
//            if (map.containsKey(term) && countMap.containsKey(term)) {
            if (map.containsKey(term)) {
//                Set<String> files = map.get(term);
//                mySet.addAll(files);
                mySet.addAll(map.get(term));
//                countMap.put(term, countMap.get(term) + 1);
            }
//            for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
//                if (entry.getValue().contains(term))
//                    countMap.put(term, entry.getValue().size());
//            }

//            countMap.put(term, counter);
//            else if (map.containsKey(term) && !countMap.containsKey(term)) {
//                Set<String> files = map.get(term);
//                mySet.addAll(files);
////                countMap.put(term, 1);
//            }
//            if (!map.containsKey(term)) {
//                return new LinkedList<>();
//            }
        }

        //make counters for all 3, do switch?
        //make new list and keep order
        //use map.getValue
        //maybe another Map?
//        System.out.println(countMap);

        List<String> myList = new LinkedList<>(mySet);
		//Collections.sort(mySet);
		return myList; // change this as necessary
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Map<String, Set<String>> map = buildMap(args[0]);
//		System.out.println(map); 					// for debugging purposes
		for (String word : map.keySet()) {
			Set<String> files = map.get(word);
			System.out.println(word + " -> " + files);
		}

		try (Scanner in = new Scanner(System.in)) { // create a Scanner to read from stdin
			while (true) {
				System.out.print("Enter a term to search for: ");
				String input = in.nextLine();            // read the entire line that was entered
				if (input.isEmpty()) {
					break;
				}
				String[] terms = input.split(" ");        // separate tokens based on a single whitespace
				List<String> list = search(terms, map);    // search for the tokens in the Map
				for (String file : list) {                // print the results
					System.out.println(file);
				}
			}
		}catch(Exception e){
			// oops! something went wrong
			e.printStackTrace();
		}
	}
}
