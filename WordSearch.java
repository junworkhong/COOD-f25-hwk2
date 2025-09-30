import java.io.*;
import java.util.*;

/*
 * Implements a text search engine for a collection of documents in the same directory.
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
		LinkedList<String> myList = new LinkedList<>();

		for (String term : terms) {
			if (map.containsKey(term)) {
				Set<String> files = map.get(term);
				if (!myList.contains(files)) {
					myList.addAll(files);
				}
			}
		}
		Collections.sort(myList);
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
