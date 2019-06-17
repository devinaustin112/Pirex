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

public class InvertedIndexTest
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

  // ===========================================================================
  // Tests for counting postings and terms
  // ===========================================================================

  @Test
  public void testTermAndPostingCount()
  {
    Indexer.populateInvertedIndex(1, simpleBook);
    assertEquals(InvertedIndex.getInstance().getTermCount(), 5);
    assertEquals(InvertedIndex.getInstance().getPostingCount(), 6);
  }

  @Test
  public void testTermAndPostingAddedCount()
  {
    Indexer.populateInvertedIndex(1, simpleBook);
    assertEquals(InvertedIndex.getInstance().getAddedTermCount(), 5);
    assertEquals(InvertedIndex.getInstance().getAddedPostingCount(), 6);
  }

  @Test
  public void testTermAndPostingAddedCountAfterReset()
  {
    Indexer.populateInvertedIndex(1, simpleBook);
    InvertedIndex.getInstance().resetAdded();
    assertEquals(InvertedIndex.getInstance().getAddedTermCount(), 0);
    assertEquals(InvertedIndex.getInstance().getAddedPostingCount(), 0);
  }
  
  // ===========================================================================
  // Tests for removing from the inverted index
  // ===========================================================================
  
  @Test
  public void testRemoveSingleBook()
  {
    Indexer.populateInvertedIndex(1, simpleBook);
    Indexer.populateInvertedIndex(2, simpleBook);
    InvertedIndex.removePostings(1);
    assertEquals(InvertedIndex.getInstance().getTermCount(), 5);
    assertEquals(InvertedIndex.getInstance().getPostingCount(), 6);
  }
  
  @Test
  public void testRemoveMultipleBooks()
  {
    Indexer.populateInvertedIndex(1, simpleBook);
    Indexer.populateInvertedIndex(2, simpleBook);
    InvertedIndex.removePostings(1);
    InvertedIndex.removePostings(2);
    assertEquals(InvertedIndex.getInstance().getTermCount(), 0);
    assertEquals(InvertedIndex.getInstance().getPostingCount(), 0);
  }
  
  // ===========================================================================
  // Tests for serialization
  // ===========================================================================
  
  @Test
  public void testExitAndLoad()
  {
    InvertedIndex.getInstance().clear();
    Indexer.populateInvertedIndex(1, simpleBook);
    InvertedIndex.getInstance().exit();
    InvertedIndex.getInstance().loadInvertedIndex();
    
    assertEquals(InvertedIndex.getInstance().getTermCount(), 5);
    assertEquals(InvertedIndex.getInstance().getPostingCount(), 6);
  }

}
