package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dataStore.Posting;

public class PostingTest
{
  private Posting posting;
  
  @Before
  public void setUp()
  {
    posting = new Posting(1, 2, 3);
  }

  @Test
  public void testBookID()
  {
    assertEquals(posting.getBookNumber(), 1);
  }
  
  @Test
  public void testParagraphNumber()
  {
    assertEquals(posting.getParagraphNumber(), 2);
  }
  
  @Test
  public void testFrequency()
  {
    assertEquals(posting.getFrequency(), 3);
  }

}
