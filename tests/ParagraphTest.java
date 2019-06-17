package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import dataStore.Paragraph;
import pirex.Pirex;

/**
 * Test suite for paragraph class
 * 
 * Running these tests will clear out the Pirex database.
 * 
 * @author Bryan
 *
 */
public class ParagraphTest
{

  @Test
  public void testConstructor()
  {
    Paragraph par = new Paragraph("Happy birthday.", 1);
    assertNotNull(par);
  }
  
  @Test
  public void testgetParagraph()
  {
    Paragraph par = new Paragraph("Happy birthday.", 1);
    assertEquals("Happy birthday.", par.getParagraph());
  }
  
  @Test
  public void testsetParagraph()
  {
    Paragraph par = new Paragraph("Happy birthday.", 1);
    par.setParagraph("Happy New Year!");
    assertEquals("Happy New Year!", par.getParagraph());
  }
  
  @Test
  public void testsgetOnumber()
  {
    Paragraph par = new Paragraph("Happy birthday.", 648);
    assertEquals(648, par.getoNumber());
    Pirex.clearPirex();
  }

}
