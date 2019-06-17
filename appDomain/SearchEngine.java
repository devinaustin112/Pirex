package appDomain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import dataStore.Book;
import dataStore.BookStore;
import dataStore.InvertedIndex;
import dataStore.Posting;

/**
 * Utility class for Search functionality.
 *
 * @author Group G
 * @version 1.0
 */
public class SearchEngine
{
  private static LinkedList<String> highWtTerms;
  private static LinkedList<String> lowWtTerms;
  private static LinkedList<String> fullPars;
  private static ArrayList<String> shortPars;
  private static LinkedList<String> parIdents;
  private static HashMap<String, Double> parMap;
  private static HashMap<String, Integer> termCount;

  /**
   * Conducts search on Inverted index and returns the results of the search.
   *
   * @param highTermText - String of high weight search postings
   * @param lowTermText - String of low weight search postings
   */
  public static void search(String highTermText, String lowTermText)
  {
    fullPars = new LinkedList<>();
    parIdents = new LinkedList<>();
    retrievePostings(highTermText, lowTermText);

    int size = parMap.size();

    // sorts the postings retrieved using the search terms
    while (parIdents.size() != size)
    {
      double max = -1;
      String posting = null;
      for (String key : parMap.keySet())
      {
        if (parMap.get(key) > max)
        {
          posting = key;
          max = parMap.get(key);
        }
      }
      parMap.remove(posting);
      parIdents.add(posting);

      updatePars(posting);

    }
    createShortForm();
  }

  /**
   * Search method using the and operator.
   *
   * @param highTermText - String of high weight search postings
   * @param lowTermText - String of low weight search postings
   */
  public static void searchAnd(String highTermText, String lowTermText)
  {
    fullPars = new LinkedList<>();
    parIdents = new LinkedList<>();
    retrievePostings(highTermText, lowTermText);

    removeInvalidParagraphs();

    int size = parMap.size();

    // sorts the postings retrieved using the search terms
    while (parIdents.size() != size)
    {
      double max = 0;
      String posting = null;

      for (String key : parMap.keySet())
      {
        if (parMap.get(key) > max)
        {
          posting = key;
          max = parMap.get(key);
        }
      }
      parMap.remove(posting);
      parIdents.add(posting);

      updatePars(posting);

    }
    createShortForm();
  }

  /**
   * Creates an ArrrayList of the short form representations of the retrieved paragraphs.
   */
  private static void createShortForm()
  {
    shortPars = new ArrayList<>();

    // uses the sorted postings to create a list of sorted short form descriptions
    for (String parID : parIdents)
    {
      String s = "";

      //Simplifying long lines of code
      int bookNumber = Integer.parseInt(parID.substring(0, parID.indexOf(".")));
      int parNum = Integer.parseInt(parID.substring(parID.indexOf(".") + 1));

      Book b = BookStore.getInstance().getBook(bookNumber);
      String par = b.getParagraphs().get(parNum).getParagraph();
      String firstLine;
      if (par.contains("\n"))
      {
        firstLine = par.substring(0, par.indexOf("\n"));
      }
      else
      {
        firstLine = par;
      }
      s += b.getAuthor() + " " + b.getTitle() + " " + parNum + " " + firstLine;
      shortPars.add(s);
    }
  }

  /**
   * Returns an LinkedList of the paragraphs, in order, retrieved from the search.
   *
   * @return LinkedList of the paragraphs
   */
  public static LinkedList<String> getLongForm()
  {
    return fullPars;
  }

  /**
   * Returns an ArrayList of the paragraphs in short form, in order, retrieved from the search.
   *
   * @return ArrayList of the paragraphs in short form
   */
  public static ArrayList<String> getShortForm()
  {
    return shortPars;
  }

  /**
   * Removes all paragraphs that don't contain all search terms. This method is used when searching
   * using the "and" operator.
   */
  private static void removeInvalidParagraphs()
  {
    for (String key : termCount.keySet())
    {
      if (termCount.get(key) < highWtTerms.size() + lowWtTerms.size())
      {
        parMap.remove(key);
      }
    }
  }

  /**
   * Populates the postings ArrayList using the provided search terms.
   *
   * @param highTermText - terms which postings are given a higher weight
   * @param lowTermText - terms which postings are given a lower weight
   */
  private static void retrievePostings(String highTermText, String lowTermText)
  {
    highWtTerms = new LinkedList<>();
    lowWtTerms = new LinkedList<>();
    parMap = new HashMap<>();
    termCount = new HashMap<>();

    highWtTerms = Indexer.removeStopWords(highTermText);
    lowWtTerms = Indexer.removeStopWords(lowTermText);

    InvertedIndex ii = InvertedIndex.getInstance();

    for (String highWeightTerm : highWtTerms)
    {
      updateMaps(ii, highWeightTerm, 3);
    }

    for (String lowWeightTerm : lowWtTerms)
    {
      updateMaps(ii, lowWeightTerm, 1);
    }
  }
  
  /**
   * updates paragraphs based on postings.
   * @param posting 
   */
  private static void updatePars(String posting)
  {
    if (posting != null)
    {
      //simplify long line of code
      int id = Integer.parseInt(posting.substring(0, posting.indexOf(".")));
      int oNum = Integer.parseInt(posting.substring(posting.indexOf(".") + 1));
      Book book = BookStore.getInstance().getBook(id);

      fullPars.add(book.getParagraphs().get(oNum).getParagraph());
    }
  }

  /**
   * Update the paragraphMap and termCountMaps based on given term.
   *
   * @param ii - inverted index
   * @param term - search term
   */
  private static void updateMaps(InvertedIndex ii, String term, int weights)
  {
    if (ii.getIndexMap().containsKey(term))
    {
      // simplify long lines of code.
      int hwtSize = ii.getIndexMap().get(term).size();
      int origSize = InvertedIndex.getInstance().getIndexMap().get(term).size();

      for (int j = 0; j < hwtSize; j++)
      {
        // Added original so this doesn't mess with the postings in the index.
        Posting original = ii.getIndexMap().get(term).get(j);

        // Simplify long lines of code
        double avgParCountLog = Math.log(BookStore.getParagraphCount() / origSize);
        double freqLog = (Math.log(original.getFrequency()) + 1);

        double weight = weights * avgParCountLog * freqLog;

        // Simplify long lines of code
        int bkNum = original.getBookNumber();
        int parNum = original.getParagraphNumber();

        if (parMap.containsKey(bkNum + "." + parNum))
        {
          parMap.put(bkNum + "." + parNum,parMap.get(bkNum + "." + parNum) + weight);
          termCount.put(bkNum + "." + parNum, termCount.get(bkNum + "." + parNum) + 1);
        }
        else
        {
          parMap.put(bkNum + "." + parNum, weight);
          termCount.put(bkNum + "." + parNum, 1);
        }
      }
    }
  }
}
