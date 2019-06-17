package appDomain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import dataStore.Book;
import dataStore.BookStore;
import dataStore.Paragraph;

/**
 * Utility class containing static methods for LoadBookWindow.
 * 
 * @author Group G
 *
 */
public final class SourceProcessor
{
  private static final String BEGINING_OR_END = "***";
  private static BufferedReader textReader;
  private static Integer bookIDCounter;
  private static Book currParsedBook;

  /**
   * Utility class, constructor should never be called. Make private and empty so nothing can if it
   * tries.
   */
  private SourceProcessor()
  {
  }

  /**
   * Initialize global variables of SourceProcessor Utility Class.
   */
  public static void initSourceProcessor()
  {
    bookIDCounter = 0;
    currParsedBook = new Book();
  }

  /**
   * Getter method for bookIDCounter class variable. Checks that no Book already in Library has an
   * ID equal to bookIDCounter. If one does, increment bookIDCounter and check again. Repeat until
   * bookIDCounter us a unique Integer. This method ensures there can never be two different Book
   * objects with the same ID.
   * 
   * @return bookIDCounter - the counter variable for the book.
   */
  public static Integer getBookIDCounter()
  {
    while (BookStore.getInstance().getBooksList().containsKey(bookIDCounter))
    {
      bookIDCounter++;
    }
    return bookIDCounter;
  }

  /**
   * Parse new book from file specified in parameter.
   * 
   * @param filePath
   *          path of file to load as new Book.
   */
  public static void parseNewBook(String filePath)
  {
    File file = new File(filePath);
    currParsedBook = parseBook(file);
  }

  /**
   * Returns the current parsed book.
   * 
   * @return current parsed book
   */
  public static Book getParsedBook()
  {
    return currParsedBook;
  }

  /**
   * Non-GUI based Process Button functionality. Finalize Title and Author of new Book, add book to
   * Database (Library), and returns formatted Load Book Summary string.
   * 
   * @param titleFieldText
   *          String in titleField to set as final title of new Book.
   * @param authorFieldText
   *          String in authorField to set as final author of new Book.
   */
  public static void process(String titleFieldText, String authorFieldText)
  {
    // Set to parsed title, unless user changes title field.
    if (titleFieldText.length() == 0)
      currParsedBook.setTitle("None");
    else
      currParsedBook.setTitle(titleFieldText);
    // Set to parsed author, unless user changes author field.
    if (authorFieldText.length() == 0)
      currParsedBook.setAuthor("anonymous");
    else
      currParsedBook.setAuthor(authorFieldText);

    // add currParsedBook to Library.
    BookStore.getInstance().storeBook(currParsedBook.getUniqueID(), currParsedBook);
    Indexer.populateInvertedIndex(currParsedBook.getUniqueID(), currParsedBook);
  }

  /**
   * Resets the current parsed book.
   */
  public static void resetCurrParsedBook()
  {
    currParsedBook = new Book();
  }

  /**
   * Parses file into a book object.
   * 
   * @param currFile
   *          File object to parse Book from.
   * @return Book new book object based on currFile.
   */
  public static Book parseBook(File currFile)
  {
    try
    {
      String title = "";
      String author = "";

      InputStreamReader input = new InputStreamReader(new FileInputStream(currFile), "UTF-8");
      BufferedReader infoReader = new BufferedReader(input);
      String line = infoReader.readLine();

      while (line != null)
      {
        if (line.startsWith("Title:"))
        {
          title = line.substring(line.indexOf(":") + 2, line.length());
        }
        if (line.startsWith("Author:"))
        {
          author = line.substring(line.indexOf(":") + 2, line.length());
        }
        if (line.trim().startsWith(BEGINING_OR_END))
        {
          textReader = infoReader;
          break;
        }
        line = infoReader.readLine();
      }
      /* For now, assume only correct file types are loaded */
      return new Book(title, author, getBookIDCounter(), readParagraphs(), currFile);
    }
    catch (IOException e)
    {
      System.out.println("IOException in Book Class.");
    }
    return null;
  }

  /**
   * Creates paragraph ArrayList from paragraphs in the file.
   * 
   * @return ArrayList<Paragraph> list of paragraphs in this book.
   * @throws IOException
   *           - failed to read Line.
   */
  private static ArrayList<Paragraph> readParagraphs() throws IOException
  {
    ArrayList<Paragraph> paragraphs = new ArrayList<>();
    String line;
    StringBuilder paragraph = new StringBuilder();
    int paragraphIndex = 0;
    Boolean incrementIndex = false;

    while ((line = textReader.readLine()) != null)
    {

      if (line.trim().startsWith(BEGINING_OR_END))
      {
        break;
      }
      else if (!line.isEmpty())
      {
        paragraph.append(line.trim()).append("\n");
        incrementIndex = true;
      }
      else if (incrementIndex)
      {
        paragraphs.add(new Paragraph(paragraph.toString().trim(), paragraphIndex));
        paragraph = new StringBuilder();
        incrementIndex = false;
        paragraphIndex++;
      }
    }
    return paragraphs;
  }

}
