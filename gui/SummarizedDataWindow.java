package gui;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import dataStore.Book;
import dataStore.BookStore;
import dataStore.InvertedIndex;

/**
 * Summarized data window provides a gui that displays summarized book information.
 * 
 * @author Group G
 * @version 1.0
 * @since 2017-10-27
 */
public class SummarizedDataWindow extends JPanel
{
  public static final long serialVersionUID = 1L;
  private static JTextArea summary;
  private JScrollPane bookPane;
  private JPanel scrollPanel;
  private Border border;

  /**
   * SummarizedDataWindow - Displays a summary of data in Pirex.
   */
  public SummarizedDataWindow()
  { 
    super();
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

    // setup for the summarized book information
    scrollPanel = new JPanel();
    scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
    scrollPanel.setBackground(Color.WHITE);
    scrollPanel.setBorder(border);

    // ScrollPane that displays books loaded in to Pirex.
    bookPane = new JScrollPane(scrollPanel);
    bookPane.getViewport().setBackground(Color.WHITE);

    summary = new JTextArea();
    summary.setFont(new Font("LabelStyle", Font.BOLD, 15));
    summary.setEditable(false);
    scrollPanel.add(summary);

    // add bookPane and spacer to scroll panel
    this.add(bookPane);
    this.add(Box.createRigidArea(new Dimension(0, 20)));
    
    // Update Summary on startup incase books are already in Library.
    updateSummary();
    
    // create buttons in RemoveBookWindow for any existing Books.
    RemoveBookWindow.updateRemoveBookWindow();
  }

  /**
   * Static method that is a signal that some action (Load/Remove) has edited the Library, and the
   * summary of SummarizedDataWindow needs to be updated. Call at the end of any Action Listener
   * that will change what is stored in the Library.
   */
  public static void updateSummary()
  {
    summary.setText("");
    String books = "";
    HashMap<Integer, Book> map = BookStore.getInstance().getBooksList();
    int indexCounter = 0;
    int i = 0;

    // Get new summary string representation of the Library data.
    while (indexCounter < BookStore.getInstance().getSize())
    {
      try
      {
        Book curr = map.get(i);
        books += String.format("Book %d: %s %s %d paragraphs\n\t%s\n", curr.getUniqueID(),
            curr.getAuthor(), curr.getTitle(), curr.getParagraphs().size(),
            curr.getFile().getAbsolutePath());
        indexCounter++;
        i++;
      }
      catch (NullPointerException npe)
      {
        i++;
      }
    }
    books += "\nIndex terms: " + InvertedIndex.getInstance().getTermCount() + "\nPostings: "
        + InvertedIndex.getInstance().getPostingCount();
    summary.append(books); 
  }
}
