import java.util.*;
import java.io.*;
public class WordUtilities {
    private String[] words;     // the dictionary of words
    
    // File containing dictionary of almost 100,000 words.
    private final String WORD_FILE = "randomWords.txt";
    private final int FILE_LENGTH = 48832;
    private final int ASCII_BASE_VALUE = 97;
    
    /* Constructor */
    public WordUtilities() { }
    
    /** Load all of the dictionary from a file into words array. */
    public void loadWords () throws IOException{ 
       Scanner in = new Scanner(new File(WORD_FILE));
       words = new String[FILE_LENGTH];
       for (int i=0;i<FILE_LENGTH;i++) {
           words[i] = in.next().toLowerCase();
       }
    }
    
    /** Find all words that can be formed by a list of letters.
     *  @param letters  string containing list of letters
     *  @return         array of strings with all words found.
     */
    public String [] findAllWords (String letters)
    {       
        int[] alphacount = new int[26];
        for (int i=0;i<letters.length();i++) {
            alphacount[(int)(letters.charAt(i))-ASCII_BASE_VALUE]++;
          }
          ArrayList<String> tempwords = new ArrayList<String>();
            for (int i=0;i<FILE_LENGTH;i++) {
              int[] tempalphacount = new int[26];
                for (int k=0;k<words[i].length();k++) {
                    if((int)(words[i].charAt(k)) >= 97) {
                    tempalphacount[(int)(words[i].charAt(k))-ASCII_BASE_VALUE]++;
                }
                }
                boolean wordisgood = true;
                for (int k=0;k<26;k++) {
                    if (tempalphacount[k] > alphacount[k]) {
                        wordisgood=false;
                    }
                }
                if (wordisgood) {
                    tempwords.add(words[i]);
                }
          }
          String[] returnval = new String[tempwords.size()];
          for (int i=0;i<returnval.length;i++) {
              returnval[i] = tempwords.get(i);
            }
            return returnval;
    }
    
    /** Print the words found to the screen.
     *  @param words    array containing the words to be printed
     */
    public void printWords (String [] wordList) {
        int counter=0;
        for (int i=0;i<wordList.length;i++) {
            System.out.printf("%-14s",wordList[i]);
            counter++;
            if (counter == 5) {
                System.out.println();
                counter=0;
            }                    
        }
    }
    
    /** Finds the highest scoring word according to a score table.
     *
     *  @param word         An array of words to check
     *  @param scoreTable   An array of 26 integer scores in letter order
     *  @return             The word with the highest score
     */
    public String bestWord (String [] wordList, int [] scoreTable)
    {
        int bestscore=0;
        String bestword="";
        for (int i=0;i<wordList.length;i++) {
            if (getScore(wordList[i],scoreTable) > bestscore) {
                bestscore = getScore(wordList[i],scoreTable);
                bestword = wordList[i];
            }
        }
        return bestword;
    }
    
    /** Calculates the score of one word according to a score table.
     *
     *  @param word         The word to score
     *  @param scoreTable   An array of 26 integer scores in letter order
     *  @return             The integer score of the word
     */
    public int getScore (String word, int [] scoreTable)
    {
        int currscore=0;
        for (int i=0;i<word.length();i++) {
            currscore += scoreTable[(int)(word.charAt(i))-ASCII_BASE_VALUE];
        }
        boolean bonus=false;
        for (int i=0;i<word.length()-1;i++) {
            if (word.charAt(i) == word.charAt(i+1)) {
                bonus = true;
            }
        }
        if (bonus) {
            return currscore*2;
        }else{
            return currscore;
        }
    }
    
    /***************************************************************/
    /************************** Testing ****************************/
    /***************************************************************/
    public static void main (String [] args) throws IOException
    {
        WordUtilities wu = new WordUtilities();
        wu.run();
    }
    
    public void run() throws IOException{
        String letters = Prompt.getString("Please enter a list of letters, from 3 to 12 letters long, without spaces");
        loadWords();
        String [] word = findAllWords(letters);
        System.out.println();
        printWords(word);
        
        // Score table in alphabetic order according to Scrabble
        int [] scoreTable = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
        String best = bestWord(word,scoreTable);
        System.out.println("\nHighest scoring word: " + best + "\nScore = " 
                            + getScore(best, scoreTable) + "\n");
    }
    /**
     *  Determines if a word's characters match a group of letters
     *  @param word     the word to check
     *  @param letters  the letters
     *  @return         true if the word's chars match; false otherwise
     */
    private boolean wordMatch(String word, String letters) {
        // if the word is longer than letters return false
        if (word.length() > letters.length()) return false;
        
        // while there are still characters in word, check each word character
        // with letters
        while (word.length() > 0) {
            // using the first character in word, find the character's index inside letters
            // and ignore the case
            int index = letters.toLowerCase().indexOf(Character.toLowerCase(word.charAt(0)));
            // if the word character is not in letters, then return false
            if (index < 0) return false;
            
            // remove character from word and letters
            word = word.substring(1);
            letters = letters.substring(0, index) + letters.substring(index + 1);
        }
        // all word letters were found in letters
        return true;
    }    
    /**
     *  finds all words that match some or all of a group of alphabetic characters
     *  Precondition: letters can only contain alphabetic characters a-z and A-Z
     *  @param letters      group of alphabetic characters
     *  @return             an ArrayList of all the words that match some or all
     *                      of the characters in letters
     */
    public ArrayList<String> allWords(String letters) {
        ArrayList<String> wordsFound = new ArrayList<String>();
        // check each word in the database with the letters
        for (String word: words)
            if (wordMatch(word, letters))
                wordsFound.add(word);
        return wordsFound;
    }
}
