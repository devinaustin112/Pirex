package tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import appDomain.SearchEngine;
import appDomain.SourceProcessor;
import dataStore.Book;
import dataStore.BookStore;
import dataStore.InvertedIndex;
import pirex.Pirex;

/**
 * SearchEngine Class test suite
 * 
 * Running this test suite will clear the Pirex database.
 * 
 * @author Bryan
 *
 */
public class SearchEngineTest
{
  Book book;
  Book wap;
  BookStore bs;
  File file;
  File fileTwo;
  InvertedIndex ii;
  String highWeightTerms;
  String lowWeightTerms;
  SearchEngine se;

  @Before
  public void setup()
  {
    SourceProcessor.initSourceProcessor();
    file = new File("src/books/simple.txt");
    fileTwo = new File("src/books/War and Peace.txt");
    book = SourceProcessor.parseBook(file);
    wap = SourceProcessor.parseBook(fileTwo);

    highWeightTerms = "";
    lowWeightTerms = "";
    bs = BookStore.getInstance();
    ii = InvertedIndex.getInstance();

    se = new SearchEngine();
  }

  @Test
  public void testDefualtConstructord()
  {
    se = new SearchEngine();
  }

  @Test
  public void testHighWeightOneTerm()
  {
    highWeightTerms = "space";
    SourceProcessor.parseNewBook("src/books/simple.txt");
    SourceProcessor.process("", "");

    String result = new String("space space space");
    SearchEngine.search(highWeightTerms, lowWeightTerms);
    String actual = SearchEngine.getLongForm().get(0);
    assertEquals(result, actual);

    ii.clear();
    bs.clear();
  }

  @Test
  public void testLowWeightOneTerm()
  {
    lowWeightTerms = "space";
    SourceProcessor.parseNewBook("src/books/simple.txt");
    SourceProcessor.process("", "");

    String result = new String("space space space");
    SearchEngine.search(highWeightTerms, lowWeightTerms);
    String actual = SearchEngine.getLongForm().get(0);
    assertEquals(result, actual);

    ii.clear();
    bs.clear();
  }

  @Test
  public void testTermDoesntExist()
  {
    lowWeightTerms = "cow";
    highWeightTerms = "cat";

    SearchEngine.search(highWeightTerms, lowWeightTerms);
    assertEquals(0, SearchEngine.getLongForm().size());

    ii.clear();
    bs.clear();
  }

  @Test
  public void testShortFormMultipleLines()
  {
    lowWeightTerms = "ground";
    SourceProcessor.parseNewBook("src/books/simple.txt");
    SourceProcessor.process("", "");

    String result = "anonymous None 22 ground";
    SearchEngine.search(highWeightTerms, lowWeightTerms);
    String actual = SearchEngine.getShortForm().get(0);
    assertEquals(result, actual);

    ii.clear();
    bs.clear();
  }

  @Test
  public void testSearchAnd()
  {
    highWeightTerms = "CoW";
    lowWeightTerms = "PlaNs";
    SourceProcessor.parseNewBook("src/books/War and Peace.txt");
    SourceProcessor.process("", "");
    SearchEngine.searchAnd(highWeightTerms, lowWeightTerms);
    assertEquals(1, SearchEngine.getShortForm().size());

    ii.clear();
    bs.clear();
    
    Pirex.clearPirex();
  }
}
