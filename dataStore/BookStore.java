package dataStore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * BookStore class stores Books in a HashMap<Integer, Book>.
 * 
 * @author Group G
 */
public class BookStore implements Serializable
{

  private static final long serialVersionUID = 1L;
  private static BookStore bsInstance;
  private static HashMap<Integer, Book> bookMap;
  private static int size;
  private static int paragraphCount;
  final String directory = "src/dataStore/books.txt";

  /**
   * Private Constructor.
   */
  private BookStore()
  {
    bookMap = new HashMap<Integer, Book>();
    size = bookMap.size();
    paragraphCount = 0;
  }

  /**
   * Clears out the library. For testing purposes.
   */
  public void clear()
  {
    bookMap.clear();
    size = 0;
    paragraphCount = 0;
  }

  /**
   * Singleton constructor.
   * 
   * @return library - singleton instance of the library
   */
  public static BookStore getInstance()
  {
    if (bsInstance == null)
      bsInstance = new BookStore();

    return bsInstance;
  }

  /**
   * Add a book to the library.
   * 
   * @param bookID  - the uniqueID of the book being added
   * @param book - the book being stored
   */
  public void storeBook(Integer bookID, Book book)
  {
    bookMap.put(bookID, book);
    size++;
    paragraphCount += book.getParagraphs().size();

  }

  /**
   * Remove a book from the library.
   * 
   * @param index - the book key being removed
   */
  public static void removeBook(Integer index)
  {
    paragraphCount -= bookMap.get(index).getParagraphs().size();
    bookMap.remove(index);
    size--;
  }

  /**
   * getBook returns the book object with the matching title.
   * 
   * @param bookID - Integer key of Book to get.
   * @return Book the Book with key in map matching bookID.
   */
  public Book getBook(Integer bookID)
  {
    return bookMap.get(bookID);
  }

  /**
   * Getter method to access list of currently loaded books.
   * 
   * @return Map<Integer,Book> The map of current Book objects in Library.
   */
  public HashMap<Integer, Book> getBooksList()
  {
    return bookMap;
  }

  /**
   * Keys are book titles.
   * 
   * @return - a set of book titles.
   */
  public ArrayList<String> getTitles()
  {
    ArrayList<String> titles = new ArrayList<>();
    if (size == 0)
      return titles;
    else
    {
      for (Integer i = 1; i <= size; i++)
      {
        titles.add(bookMap.get(i).getTitle());
      }
    }
    return titles;
  }

  /**
   * getter method for size.
   * 
   * @return - size of the hashmap
   */
  public int getSize()
  {
    return size;
  }

  /**
   * Load the serialized file in to the library.
   */
  @SuppressWarnings("unchecked")
  public void loadBookStore()
  {
    try
    {
      FileInputStream fis = new FileInputStream(new File(directory));
      ObjectInputStream ois = new ObjectInputStream(fis);
      bookMap = (HashMap<Integer, Book>) ois.readObject();
      paragraphCount = (Integer) ois.readInt();
      ois.close();
      fis.close();
    }
    catch (IOException | ClassNotFoundException c)
    {
      System.out.println("Exception in loadLibrary.");
    }

    // update the size variable
    size = bookMap.size();
  }

  /**
   * Writes library object to a file for future use.
   */
  public void exit()
  {
    try
    {
      FileOutputStream fos = new FileOutputStream(directory);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(bookMap);
      oos.writeInt(paragraphCount);
      oos.flush();
      fos.flush();
      oos.close();
      fos.close();
    }
    catch (IOException ioe)
    {
      System.out.println("Exception in exit.");
    }
  }

  /**
   * getter method for number of paragraphs in the bookStore.
   * 
   * @return paragraphCount - the sum of all paragraphs of all books in the book store.
   */
  public static int getParagraphCount()
  {
    return paragraphCount;
  }
}
