package CvKfbf3T;

import java.util.*;

public class TestSorting {
    public static void main(String[] args) {
        // Test example from the problem: "hello world this is a test example"
        String input = "hello world this is a test example";
        System.out.println("Input: " + input);
        
        String[] words = input.split("\\s+");
        System.out.println("Original words: " + Arrays.toString(words));
        
        // Create a list of words with their original indices
        List<WordWithIndex> wordList = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            wordList.add(new WordWithIndex(words[i], i));
            System.out.println("Word: '" + words[i] + "' (length: " + words[i].length() + ", index: " + i + ")");
        }
        
        // Sort by length first, then by original index
        Collections.sort(wordList, new Comparator<WordWithIndex>() {
            @Override
            public int compare(WordWithIndex w1, WordWithIndex w2) {
                // First compare by length
                int lengthCompare = Integer.compare(w1.word.length(), w2.word.length());
                if (lengthCompare != 0) {
                    return lengthCompare;
                }
                // If lengths are equal, compare by original index
                return Integer.compare(w1.originalIndex, w2.originalIndex);
            }
        });
        
        System.out.println("\nAfter sorting by length, then by appearance order:");
        for (WordWithIndex w : wordList) {
            System.out.println("'" + w.word + "' (length: " + w.word.length() + ", original index: " + w.originalIndex + ")");
        }
        
        // Create result string
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < wordList.size(); i++) {
            result.append(wordList.get(i).word);
            if (i < wordList.size() - 1) {
                result.append(", ");
            }
        }
        
        String resultString = result.toString();
        System.out.println("\nFinal result: " + resultString);
        System.out.println("Expected: a, is, this, test, hello, world, example");
    }
    
    // Helper class to store word with its original index
    static class WordWithIndex {
        String word;
        int originalIndex;
        
        public WordWithIndex(String word, int originalIndex) {
            this.word = word;
            this.originalIndex = originalIndex;
        }
    }
}