package tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import appDomain.SourceProcessor;

public class SourceProcessorTest
{

  private File file;

  @Before
  public void setUp()
  {
    SourceProcessor.initSourceProcessor();
    file = new File("src/books/War and Peace.txt");
    SourceProcessor.parseNewBook(file.getAbsolutePath());
  }

  @Test
  public void testGetAuthor()
  {
    SourceProcessor.parseBook(file);
    assertEquals(SourceProcessor.getParsedBook().getAuthor(), "Leo Tolstoy");
  }

  @Test
  public void testGetTitle()
  {
    assertEquals(SourceProcessor.getBookIDCounter(), 0, .1);
  }

  @Test
  public void testProcess()
  {
    SourceProcessor.process("Magic Tree House", "");
    assertEquals(SourceProcessor.getParsedBook().getAuthor(), "anonymous");
  }

  @Test
  public void testReset()
  {
    SourceProcessor.resetCurrParsedBook();
    assertEquals(SourceProcessor.getParsedBook().getAuthor(), null);
  }
}
