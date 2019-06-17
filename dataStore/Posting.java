package dataStore;

import java.io.Serializable;

/**
 * This class represents a posting.
 * 
 * @author Group G
 * @version 11/13/17
 */
public class Posting implements Serializable
{
  private static final long serialVersionUID = 1L;
  private int bookNumber;
  private int paragraphNumber;
  private int frequency;

  /**
   * Constructor for posting objects.
   * 
   * @param bookNumber - the ordinal number of the book
   * @param paragraphNumber - the ordinal number of the paragraph
   * @param frequency - the number of times the term appears in the paragraph
   */
  public Posting(int bookNumber, int paragraphNumber, int frequency)
  {
    this.bookNumber = bookNumber;
    this.paragraphNumber = paragraphNumber;
    this.frequency = frequency;
  }

  /**
   * Returns the ordinal number of the book.
   * 
   * @return ordinal number of the book
   */
  public int getBookNumber()
  {
    return bookNumber;
  }

  /**
   * Returns the ordinal number of the paragraph.
   * 
   * @return ordinal number of the paragraph
   */
  public int getParagraphNumber()
  {
    return paragraphNumber;
  }

  /**
   * Returns the term frequency of the term.
   * 
   * @return frequency
   */
  public int getFrequency()
  {
    return frequency;
  }
}
