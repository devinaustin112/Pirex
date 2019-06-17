package appDomain;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import dataStore.Book;
import dataStore.InvertedIndex;
import dataStore.Posting;

/**
 * Utility class for Indexing functionality.
 * @author Group G
 */
public class Indexer
{
  private static ArrayList<String> stopList;

  /**
   * Constructor for Indexer objects.
   */
  private Indexer()
  {
    stopList = new ArrayList<>();
    File file = new File("resources/stopList.txt");
    try
    {
      Scanner scan = new Scanner(file);
      while (scan.hasNext())
      {
        stopList.add(scan.next());
      }
      scan.close();
    }
    catch (IOException e)
    {
      System.out.println("Stop list does not exist");
    }
  }

  /**
   * Adds terms and postings to the inverted index when a new book is added.
   *
   * @param bookID - uniqueID of the added book
   * @param book - the new book added
   */
  public static void populateInvertedIndex(Integer bookID, Book book)
  {
    for (int i = 0; i < book.getParagraphs().size(); i++)
    {
      LinkedList<String> paragraph = removeStopWords(book.getParagraphs().get(i).getParagraph());
      while (paragraph.size() != 0)
      {
        String term = paragraph.removeFirst();
        int frequency = 1;
        Iterator<String> it = paragraph.iterator();
        while (it.hasNext())
        {
          if (it.next().equals(term))
          {
            frequency++;
            it.remove();
          }
        }
        InvertedIndex.getInstance().storePosting(term, new Posting(bookID, i, frequency));
      }
    }
  }

  /**
   * Removes stop words and punctuation and returns an ArrayList representing the paragraph.
   *
   * @param string - object to remove stop words from and convert into an ArrayList
   * @return LinkedList of the Paragraph objects.
   */
  static LinkedList<String> removeStopWords(String string)
  {
    new Indexer();
    LinkedList<String> newString = new LinkedList<>();
    String paragraphString = string.toLowerCase();
    paragraphString = paragraphString.replaceAll("[^-’a-z\\s]", "").replaceAll("’", "'");
    paragraphString = paragraphString.replaceAll("--", " ");
    String[] paragraphArray = paragraphString.split("\\s+");
    LinkedList<String> par = new LinkedList<>(Arrays.asList(paragraphArray));

    for (String s : par)
    {
      String result = s;
      while (result.startsWith("'") || result.startsWith("-"))
      {
        result = result.substring(1);
      }
      while (result.endsWith("'") || result.endsWith("-"))
      {
        result = result.substring(0, result.length() - 1);
      }

      if (!stopList.contains(result) && !result.equals(""))
      {
        newString.add(result);
      }
    }
    return newString;
  }
}
