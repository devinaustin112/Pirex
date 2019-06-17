package dataStore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 * This class represents an inverted index.
 * 
 * @author Group G
 *
 */
public class InvertedIndex implements Serializable
{
  private static final long serialVersionUID = 1L;
  private static int size;
  private static InvertedIndex iiInstance;
  private static HashMap<String, LinkedList<Posting>> indexMap;
  private static int postingCount;
  final String indexDirectory = "src/dataStore/invertedIndex.txt";
  private int addedTerms;
  private int addedPostings;
  

  /**
   * Private Constructor.
   */
  private InvertedIndex()
  {
    indexMap = new HashMap<String, LinkedList<Posting>>();
    size = 0;
    postingCount = 0;
    addedTerms = 0;
    addedPostings = 0;
  }

  /**
   * Singleton constructor.
   * 
   * @return inverted index - singleton instance of the inverted index
   */
  public static InvertedIndex getInstance()
  {
    if (iiInstance == null)
      iiInstance = new InvertedIndex();

    return iiInstance;
  }

  /**
   * Returns the hash map with terms as keys and postings as values.
   * 
   * @return hash map with terms as keys and postings as values
   */
  public HashMap<String, LinkedList<Posting>> getIndexMap()
  {
    return indexMap;
  }

  /**
   * Returns the number of terms in the inverted index.
   * 
   * @return number of terms
   */
  public int getTermCount()
  {
    return size;
  }

  /**
   * Returns the number of postings in the inverted index.
   * 
   * @return number of postings
   */
  public int getPostingCount()
  {
    return postingCount;
  }

  /**
   * Getter method for the number of terms added by the current book.
   * 
   * @return number of terms added by the current book.
   */
  public int getAddedTermCount()
  {
    return addedTerms;
  }

  /**
   * Getter method for the number of postings added by the current book.
   * 
   * @return number of postings added by the current book.
   */
  public int getAddedPostingCount()
  {
    return addedPostings;
  }

  /**
   * Resets the addedTerms and addedPosting fields.
   */
  public void resetAdded()
  {
    addedTerms = 0;
    addedPostings = 0;
  }

  /**
   * Stores the given posting in the inverted index.
   * 
   * @param term - String being added to the inverted index
   * @param newPosting - Posting being stored in the inverted index
   */
  public void storePosting(String term, Posting newPosting)
  {
    if (InvertedIndex.getInstance().getIndexMap().containsKey(term))
    {
      indexMap.get(term).add(newPosting);
      postingCount++;
      addedPostings++;
    }
    else
    {
      LinkedList<Posting> newValue = new LinkedList<>();
      newValue.add(newPosting);
      indexMap.put(term, newValue);
      size++;
      postingCount++;
      addedPostings++;
      addedTerms++;
    }
  }

  /**
   * Removes all postings related to the given book from the inverted index.
   * 
   * @param bookID - bookID of the book being removed
   */
  public static void removePostings(int bookID)
  {
    Set<String> keys = indexMap.keySet();
    Iterator<String> keyIterator = keys.iterator();
    while (keyIterator.hasNext())
    {
      String key = keyIterator.next();
      Iterator<Posting> valueIterator = indexMap.get(key).iterator();
      while (valueIterator.hasNext())
      {
        Posting posting = valueIterator.next();
        if (bookID == posting.getBookNumber())
        {
          valueIterator.remove();
          postingCount = postingCount - 1;
        }
      }
      if (indexMap.get(key).size() == 0)
      {
        keyIterator.remove();
        size--;
      }
    }
  }

  /**
   * Load the serialized file in to the library.
   */
  @SuppressWarnings("unchecked")
  public void loadInvertedIndex()
  {
    try
    {
      FileInputStream fis = new FileInputStream(new File(indexDirectory));
      ObjectInputStream ois = new ObjectInputStream(fis);
      indexMap = (HashMap<String, LinkedList<Posting>>) ois.readObject();
      postingCount = (Integer) ois.readObject();
      ois.close();
      fis.close();
    }
    catch (IOException | ClassNotFoundException c)
    {
      System.out.println("Exception in loadLibrary.");
    }

    size = indexMap.size();
  }

  /**
   * Writes invertedIndex object to a file for future use.
   */
  public void exit()
  {
    try
    {
      FileOutputStream fos = new FileOutputStream(indexDirectory);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(indexMap);
      oos.writeObject(postingCount);
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
   * Resets all fields within the inverted index.
   */
  public void clear()
  {
    indexMap = new HashMap<String, LinkedList<Posting>>();
    size = 0;
    postingCount = 0;
    iiInstance.resetAdded();
  }
}
