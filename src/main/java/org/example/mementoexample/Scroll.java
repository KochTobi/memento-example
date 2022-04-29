package org.example.mementoexample;

import java.util.Random;

/**
 * <b>short description</b>
 *
 * <p>detailed description</p>
 *
 * @since <version tag>
 */
public class Scroll {

  private String message = "";
  private int shift = 0; // shift of 0 means clear text

  private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

  public Scroll() {
    changeShift();
  }

  protected Scroll(int shift) {
    this.shift = shift;
  }

  public void changeShift() {
    this.shift = new Random().nextInt(0, 26);
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public String getEncryptedMessage() {
    return encrypt(message, shift);
  }

  public ScrollMemento createSnapshot() {
    return new ScrollMementoImpl(this.message, this.shift);
  }

  public void restore(ScrollMemento snapshot) {
    var state = (ScrollMementoImpl) snapshot;
    System.out.println("restoring snapshot = " + snapshot);
    this.shift = state.shift;
    this.message = state.message;
  }

  private static class ScrollMementoImpl implements ScrollMemento{
    final String message;
    final int shift;

    private ScrollMementoImpl(String message, int shift) {
      this.message = message;
      this.shift = shift;
    }
  }

  private static String encrypt(String message, int shift) {
    StringBuilder encrypted = new StringBuilder();
    for (int i = 0; i < message.length(); i++) {
      String currentLetter = message.substring(i, i + 1);
      String encryptedLetter;
      if (ALPHABET.toLowerCase().contains(currentLetter)) {
        encryptedLetter = encryptWithAlphabet(ALPHABET.toLowerCase(), currentLetter, shift);
      } else if (ALPHABET.toUpperCase().contains(currentLetter)) {
        encryptedLetter = encryptWithAlphabet(ALPHABET.toUpperCase(), currentLetter, shift);
      } else {
        encrypted.append("?");
        continue;
      }
      encrypted.append(encryptedLetter);
    }
    return encrypted.toString();
  }

  private static String encryptWithAlphabet(String ALPHABET, String currentLetter, int shift) {
    int currentLetterIndex = ALPHABET.indexOf(currentLetter);
    int encryptedLetterIndex = (currentLetterIndex + shift) % 26;
    return ALPHABET.substring(encryptedLetterIndex, encryptedLetterIndex + 1);
  }
}
