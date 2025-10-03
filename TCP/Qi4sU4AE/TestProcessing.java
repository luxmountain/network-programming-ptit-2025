package Qi4sU4AE;

public class TestProcessing {
    public static void main(String[] args) {
        // Test example: "hello world programming is fun"
        String input = "hello world programming is fun";
        System.out.println("Input: " + input);
        
        String[] words = input.split("\\s+");
        System.out.println("Words: ");
        for (String word : words) {
            System.out.println("  '" + word + "'");
        }
        
        System.out.println("\nProcessing each word:");
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            System.out.println("\nWord: '" + word + "'");
            
            // Reverse the word
            String reversedWord = new StringBuilder(word).reverse().toString();
            System.out.println("  Reversed: '" + reversedWord + "'");
            
            // Apply RLE encoding
            String encodedWord = applyRLE(reversedWord);
            System.out.println("  RLE encoded: '" + encodedWord + "'");
            
            result.append(encodedWord);
            if (i < words.length - 1) {
                result.append(" ");
            }
        }
        
        String finalResult = result.toString();
        System.out.println("\nFinal result: " + finalResult);
        System.out.println("Expected: ol2eh dlrow gnim2argorp si nuf");
    }
    
    // Method to apply Run-Length Encoding (RLE)
    private static String applyRLE(String input) {
        if (input.length() == 0) {
            return input;
        }
        
        StringBuilder result = new StringBuilder();
        char currentChar = input.charAt(0);
        int count = 1;
        
        for (int i = 1; i < input.length(); i++) {
            if (input.charAt(i) == currentChar) {
                count++;
            } else {
                // Add the character and its count to result
                result.append(currentChar);
                if (count > 1) {
                    result.append(count);
                }
                
                // Update for next character
                currentChar = input.charAt(i);
                count = 1;
            }
        }
        
        // Add the last character and its count
        result.append(currentChar);
        if (count > 1) {
            result.append(count);
        }
        
        return result.toString();
    }
}