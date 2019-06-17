package tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import appDomain.Indexer;
import appDomain.SourceProcessor;
import dataStore.Book;
import dataStore.BookStore;
import dataStore.InvertedIndex;

/**
 * Test suite for Library Class.
 * 
 * @author Group G
 */
public class IndexerTest
{
  BookStore bs;
  InvertedIndex ii;
  Book simpleBook;
  File simple;

  /**
   * Setup for following tests.
   */
  @Before
  public void initialize()
  {
    SourceProcessor.initSourceProcessor();
    InvertedIndex.getInstance().clear();

    simple = new File("resources/simple.txt");
    simpleBook = new Book();
    simpleBook = SourceProcessor.parseBook(simple);
  }

  @Test
  public void testTermsAndPostings()
  {
    Indexer.populateInvertedIndex(1, simpleBook);
    assertEquals(InvertedIndex.getInstance().getTermCount(), 5);
    assertEquals(InvertedIndex.getInstance().getPostingCount(), 6);
  }
  
  @Test
  public void testTermsAndPostingsAfterRemove()
  {
    Indexer.populateInvertedIndex(2, simpleBook);
    InvertedIndex.removePostings(2);
    assertEquals(InvertedIndex.getInstance().getTermCount(), 0);
    assertEquals(InvertedIndex.getInstance().getPostingCount(), 0);
  }
}
