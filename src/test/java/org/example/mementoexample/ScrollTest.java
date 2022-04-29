package org.example.mementoexample;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * <b>short description</b>
 *
 * <p>detailed description</p>
 *
 * @since <version tag>
 */
class ScrollTest {

  @Test
  @DisplayName("Scroll ecrypts a message given a shift correctly")
  void scrollEcryptsAMessageGivenAShiftCorrectly() {
    Scroll scroll = new Scroll(1);
    scroll.setMessage("A");
    Assertions.assertEquals("B", scroll.getEncryptedMessage());
    scroll.setMessage("a");
    Assertions.assertEquals("b", scroll.getEncryptedMessage());
    scroll.setMessage("z");
    Assertions.assertEquals("a", scroll.getEncryptedMessage());
    scroll.setMessage("Z");
    Assertions.assertEquals("A", scroll.getEncryptedMessage());
    scroll.setMessage("AZ");
    Assertions.assertEquals("BA", scroll.getEncryptedMessage());
  }
}
