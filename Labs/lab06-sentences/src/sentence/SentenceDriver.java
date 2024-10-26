package sentence;

/**
 * This class is the driver for testing the functionality of the sentence classes,
 * such as WordNode, PunctuationNode, and EmptyNode.
 * It builds various sentence structures and performs operations like merging,
 * counting words, finding the longest word, and duplicating sentences.
 */
public class SentenceDriver {

  /**
   * The main method serves as an entry point to test different sentence operations.
   * It creates, merges, and manipulates sentences while displaying the results.
   *
   * @param args the command-line arguments (not used in this implementation)
   */
  public static void main(String[] args) {

    // Creating an empty sentence
    Sentence emptySentence = new EmptyNode();
    System.out.println("Empty sentence: " + emptySentence);

    // Creating a sentence: "Hello world!"
    Sentence sentence1 = new WordNode("Hello",
            new WordNode("world",
                    new PunctuationNode("!", new EmptyNode())));
    System.out.println("Sentence 1: " + sentence1);
    System.out.println("Number of words in Sentence 1: " + sentence1.getNumberOfWords());
    System.out.println("Longest word in Sentence 1: " + sentence1.longestWord());

    // Creating another sentence: "Java is awesome."
    Sentence sentence2 = new WordNode("Java",
            new WordNode("is",
                    new WordNode("awesome",
                            new PunctuationNode(".", new EmptyNode()))));
    System.out.println("\nSentence 2: " + sentence2);
    System.out.println("Number of words in Sentence 2: " + sentence2.getNumberOfWords());
    System.out.println("Longest word in Sentence 2: " + sentence2.longestWord());

    // Creating another sentence: "Is coding fun?"
    Sentence sentence3 = new WordNode("Is",
            new WordNode("coding",
                    new WordNode("fun",
                            new PunctuationNode("?", new EmptyNode()))));
    System.out.println("\nSentence 3: " + sentence3);
    System.out.println("Number of words in Sentence 3: " + sentence3.getNumberOfWords());
    System.out.println("Longest word in Sentence 3: " + sentence3.longestWord());

    // Merging sentence1, sentence2, and sentence3
    Sentence mergedSentences = sentence1.merge(sentence2).merge(sentence3);
    System.out.println("\nMerged Sentence: " + mergedSentences);
    System.out.println("Number of words in Merged Sentence: " + mergedSentences.getNumberOfWords());
    System.out.println("Longest word in Merged Sentence: " + mergedSentences.longestWord());

    // Duplicating sentence1
    Sentence duplicateSentence = sentence1.duplicate();
    System.out.println("\nDuplicated Sentence 1: " + duplicateSentence);
    System.out.println("Is Sentence 1 equal to its duplicate? "
            + sentence1.equals(duplicateSentence));

    // Testing edge case with punctuation-only sentence
    Sentence punctuationOnly = new PunctuationNode("!",
            new PunctuationNode(".",
                    new PunctuationNode("?", new EmptyNode())));
    System.out.println("\nPunctuation-only sentence: " + punctuationOnly);
    System.out.println("Number of words in punctuation-only sentence: "
            + punctuationOnly.getNumberOfWords());
  }
}
