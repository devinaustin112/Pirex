package gui;

import java.awt.Component;
import javax.swing.JTabbedPane;

/**
 * TabbedWindow for Pirex GUI.
 * @author Group G
 */
public class TabbedWindow extends JTabbedPane
{
  private static final long serialVersionUID = 1L;

  /**
   * Constructor.
   */
  public TabbedWindow()
  {
    Component panel1 = new SearchWindow();
    addTab("Search For Paragraphs", null, panel1, "Search For Paragraphs");
    
    Component panel2 = new BookLoadWindow();
    addTab("Load Books", null, panel2, "Add book to database");
    
    Component panel3 = new RemoveBookWindow();
    addTab("Remove Books", null, panel3, "Remove books to database");
    
    Component panel4 = new SummarizedDataWindow();
    addTab("Summarize Datastore", null, panel4, "Summary of search");
  }
}
