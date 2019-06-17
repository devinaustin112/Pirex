package pirex;

import appDomain.SourceProcessor;
import dataStore.BookStore;
import dataStore.InvertedIndex;
import gui.PirexWindow;

/**
 * Entry point for Pirex program.
 * 
 * @author Group G
 */
public class Pirex
{
  /**
   * Main method used as entry point of Pirex program.
   * 
   * @param args
   *          - not used.
   */
  public static void main(String[] args)
  {
    // Open Pirex GUI

    // These are here while the remove functionality isnt implemented
    BookStore.getInstance().loadBookStore();
    InvertedIndex.getInstance().loadInvertedIndex();

    new PirexWindow();

    SourceProcessor.initSourceProcessor();
  }
  
  /**
   * Method for Resetting Pirex.
   */
  public static void clearPirex()
  {
    BookStore.getInstance().clear();
    InvertedIndex.getInstance().clear();
    BookStore.getInstance().exit();
    InvertedIndex.getInstance().exit();
  }
}
