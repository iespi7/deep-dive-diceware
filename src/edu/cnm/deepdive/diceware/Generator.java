package edu.cnm.deepdive.diceware;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * This class will generate a randomized array of strings from a given array of strings.
 *
 */
public class Generator {

  private static final String NULL_RNG_MESSAGE = "Random number generator must not be null.";
  private static final String NULL_WORDS_MESSAGE = "Array of words must not be null.";
  private static final String EMPTY_WORDS_MESSAGE = "Array of words must not be empty.";
  private static final String NEGATIVE_NUMBER_WORDS_MESSAGE = "Number of words to be selected must not be negative.";
  private static final String INSUFFICIENT_WORDS_MESSAGE = "Number of distinct words requested must not exceed number of words in pool.";

  private String[] words;
  private Random rng;

  /**
   *
   * @param words A String array of initial words that will be randomized.
   * @param rng Random number generator used to randomize the original string of words.
   * @throws NullPointerException Exception will be thrown if no rng is specified or if no initial string is provided.
   * @throws IllegalArgumentException Exception will be thrown if number of words provided in the initial string of words is zero.
   */
  public Generator(String[] words, Random rng)
      throws NullPointerException, IllegalArgumentException {
    if (rng == null) {
      throw new NullPointerException(NULL_RNG_MESSAGE);
    }
    if (words == null) {
      throw new NullPointerException(NULL_WORDS_MESSAGE);
    }
    if (words.length == 0) {
      throw new IllegalArgumentException(EMPTY_WORDS_MESSAGE);
    }
    Set<String> pool = new HashSet<>();
    for (String word : words) {
      word = word.toLowerCase();
      if(!pool.contains(word)) {
        pool.add(word);
      }
    }
    this.words = pool.toArray(new String[pool.size()]);
    this.rng = rng;
  }

  /**
   *
   * @return Returns a random word or a randomized set of words from the original string.
   */
  public String next() {
    return words[rng.nextInt(words.length)];
  }

  /**
   *
   * @param numWords Number of words requested by user to be returned.
   * @param duplicatesAllowed Specifies whether duplicate results in the randomized list will be allowed.
   * @return Returns the randomized word into the new array list of words that will result.
   * @throws NegativeArraySizeException Exception will be thrown if a negative int value is entered.
   * @throws IllegalArgumentException Exception will be thrown if the number of
   */
  public String[] next(int numWords, boolean duplicatesAllowed)
      throws NegativeArraySizeException, IllegalArgumentException{
    if (numWords < 0) {
      throw new NegativeArraySizeException(NEGATIVE_NUMBER_WORDS_MESSAGE);
    }
    if (!duplicatesAllowed && numWords > words.length) {
      throw new IllegalArgumentException(INSUFFICIENT_WORDS_MESSAGE);
    }
    List<String> selection = new LinkedList<>();
    while(selection.size() < numWords){
      String pick = next();
      if (duplicatesAllowed || !selection.contains(pick)) {
        selection.add(pick);
      }
    }
    return selection.toArray(new String[selection.size()]);
  }

  /**
   *
   * @param numWords
   * @return
   * @throws NegativeArraySizeException
   */
  public String[] next(int numWords) throws NegativeArraySizeException {
    return next(numWords, true);
  }
}
