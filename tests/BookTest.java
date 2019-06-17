package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import appDomain.SourceProcessor;
import dataStore.Book;
import dataStore.Paragraph;

public class BookTest
{
  private File file1;
  //private File file2;
  private Book wap;
  //private Book peterPan;
  private ArrayList<Paragraph> paragraphs;

  @Before
  public void setUp()
  {
    SourceProcessor.initSourceProcessor();
    file1 = new File("src/books/war and peace.txt");
    //file2 = new File("src/books/pan.txt");
    
    wap = SourceProcessor.parseBook(file1); 
    //peterPan = SourceProcessor.parseBook(file1);

  }

  // ===========================================================================================
  // Test for default constructor
  // ===========================================================================================

  @Test
  public void testDefaultConstructor()
  {
    Book book = new Book();
    assertEquals(book.getAuthor(), null);
    assertEquals(book.getTitle(), "");
    assertEquals(book.getFile(), null);
  }

  // ===========================================================================================
  // Tests for get methods
  // ===========================================================================================

  @Test
  public void testGetParagraphs()
  {
    ArrayList<Paragraph> paragraph = wap.getParagraphs();
    assertEquals(paragraph.get(9).getParagraph(), "CHAPTER V");
  }

  @Test
  public void testGetTitle()
  {
    assertEquals(wap.getTitle(),"War and Peace");
  }
  
  @Test
  public void testUniqueID()
  {
    assertEquals(wap.getUniqueID(), 0);
  }

  
  @Test
  public void testGetAuthor()
  {
    assertEquals(wap.getAuthor(), "Leo Tolstoy");
  }

  @Test
  public void testGetFile()
  {
    assertEquals(wap.getFile(), file1);
  }

  // ===========================================================================================
  // Tests for set methods
  // ===========================================================================================

  @Test
  public void testSetAuthor()
  {
    wap.setAuthor("Steve");
    assertEquals(wap.getAuthor(), "Steve");
  }

  @Test
  public void testSetTitle()
  {
    wap.setTitle("Steve's Book");
    assertEquals(wap.getTitle(), "Steve's Book");
  }
}
