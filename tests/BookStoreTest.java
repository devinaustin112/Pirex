package tests;

import static org.junit.Assert.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import appDomain.SourceProcessor;
import dataStore.Book;
import dataStore.BookStore;

/**
 * Test suite for Library Class.
 * 
 * @author Group G
 */
public class BookStoreTest
{
  BookStore bs; 
  Book huckFin, peterPan, warAndPeace;
  File huck, pp, wap;
  String h, p, w;
  
  /**
   * Setup for following tests.
   */
  @Before
  public void initialize()
  {
    SourceProcessor.initSourceProcessor();
    bs = BookStore.getInstance();
    bs.clear();
    
    h = "src/books/huck.txt";
    p ="src/books/pan.txt";
    w = "src/books/war and peace.txt";
    
    huck = new File (h);
    pp = new File(p);
    wap = new File(w);
    
    huckFin = SourceProcessor.parseBook(huck);
    peterPan = SourceProcessor.parseBook(pp);
    warAndPeace = SourceProcessor.parseBook(wap);
    
  }
  
  /**
   * teststoreBook.
   */
  @Test
  public void testStoreBook()
  {
    bs.storeBook(1, huckFin);
    int result = bs.getSize();
    assertEquals("Size should be 1.", 1, result);
    
    bs.storeBook(2, peterPan);
    result = bs.getSize();
    assertEquals("Size should be 2.", 2, result);
    
    bs.storeBook(3, warAndPeace);
    result = bs.getSize();
    assertEquals("Size should be 3.", 3, result);
  }

  /**
   * testRemoveBook.
   */
  @Test
  public void testRemoveBook()
  { 
    bs.storeBook(1, peterPan);
    bs.storeBook(2, peterPan);
    bs.storeBook(3, warAndPeace);
    int result = bs.getSize();
    assertEquals("Size should be 3.", 3, result);
    
    BookStore.removeBook(3);
    result = bs.getSize();
    assertEquals("Size should be 2.", 2, result);
    
    BookStore.removeBook(1);
    result = bs.getSize();
    assertEquals("Size should be 1.", 1, result);
    
    BookStore.removeBook(2);
    result = bs.getSize();
    assertEquals("Size should be 0.", 0, result);
  }

  /**
   * teststoreBook.
   */
  @Test
  public void testGetBook()
  { 
    bs.storeBook(1, warAndPeace);
    assertEquals("Size should be 1.", 1, bs.getSize());
    
    Book result = bs.getBook(4);
    assertNull(result);
    
    result = bs.getBook(1);
    String title = "War and Peace";
    assertTrue(title.equals(result.getTitle()));
  }
  
  /**
   * teststoreBook.
   */
  @Test
  public void testGetTitles()
  { 
    String[] expected = {"Peter Pan", "War and Peace"};
    ArrayList<String> titles;
    bs.storeBook(1, peterPan);
    bs.storeBook(2, warAndPeace);
    titles = bs.getTitles();
    
    for (int i = 0; i < 2; i++)
    {
      assertTrue(titles.get(i).equals(expected[i]));
    }
    
    bs.clear();
    titles.clear();
    
    titles = bs.getTitles();
    assertEquals("Should be 0", 0, titles.size());
  }
  
  /**
   * testSerialization - verify library is serialized / deserialized correctly.
   */
  @Test
  public void testSerialization()
  {
    
    bs.storeBook(0, huckFin);
    bs.storeBook(1, peterPan);
    bs.storeBook(3, warAndPeace); 
    int origSize = bs.getSize();
    
    int id = SourceProcessor.getBookIDCounter();
    assertEquals( id, 2);
    
    assertEquals(3, origSize);
    
    bs.exit();
    bs.clear();
    bs.loadBookStore();
  
    HashMap<Integer,Book> map2= bs.getBooksList();
    
    assertEquals(origSize, map2.size());
    
    bs.clear();
    
  }
}
