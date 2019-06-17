package dataStore;

import java.io.Serializable;

/**
 * creates a paragraph object from a String.
 * 
 * @author Bryan Secoy
 * @author Group G
 * @version 1.0
 */
public class Paragraph implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String paragraph;
  private int oNumber;

  /**
   * Constructor.
   * 
   * @param par - the string that creates a paragraph.
   * @param ordinalNumber - the number of paragraphs since the start of the book.
   */
  public Paragraph(String par, int ordinalNumber)
  {
    paragraph = par;
    oNumber = ordinalNumber;
  }

  /**
   * getParagraph - getter method.
   * 
   * @return paragraph - the String representation of a paragraph.
   */
  public String getParagraph()
  {
    return paragraph;
  }

  /**
   * setParagraph - setter method.
   * 
   * @param paragraph - the string representation of a paragraph.
   */
  public void setParagraph(String paragraph)
  {
    this.paragraph = paragraph;
  }

  /**
   * Getter method for oNumber.
   * 
   * @return - oNumber
   */
  public int getoNumber()
  {
    return oNumber;
  }
}
