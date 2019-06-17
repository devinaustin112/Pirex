package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import dataStore.Book;
import dataStore.BookStore;
import dataStore.InvertedIndex;

/**
 * RemoveBookWindow provides a gui that supports removing books from Pirex.
 * 
 * @author Group G
 */
public class RemoveBookWindow extends JPanel
{
  private static final long serialVersionUID = 1L;
  private static DefaultListModel<String> model;

  final int max = Integer.MAX_VALUE;

  private JList<String> removeList; // window that displays the list of books in the library.
  private JTextPane summary;        // window that displays summary after a removal is done.
  private JButton removeButton;
  private JPanel removePanel;       // The overall panel all other components are added to.
  private JScrollPane bookPane;     // removeList uses this.
  private RemoveButtonListener bl;
  private ListSelectionListener lsl;

  /**
   * Constructor.
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public RemoveBookWindow()
  {
    super();
    this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    model = new DefaultListModel();
    bl = new RemoveButtonListener();
    lsl = new SelectionListener();

    // Set up the remove panel
    removePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    removePanel.setMaximumSize(new Dimension(max, removePanel.getPreferredSize().height));
    removeButton = new JButton("REMOVE");
    removeButton.setEnabled(false);
    removeButton.addActionListener(bl);
    removePanel.add(removeButton);
    removePanel.add(Box.createRigidArea(new Dimension(40, 10)));

    // Create the removeList and scroll panel
    removeList = new JList<String>();
    removeList.setFont(new Font("LabelStyle", Font.BOLD, 15));
    removeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    removeList.setModel(model);
    removeList.addListSelectionListener(lsl);
    removeList.setLayoutOrientation(JList.VERTICAL);
    bookPane = new JScrollPane(removeList);
    
    // Create the summary window
    summary = new JTextPane();
    summary.setEditable(false);
    summary.setBackground(Color.WHITE);
    summary.setFont(new Font("LabelStyle", Font.BOLD, 15));
    summary.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    summary.add(Box.createRigidArea(new Dimension(40, 10)));

    // Add panels and panes
    this.add(removePanel);
    this.add(Box.createRigidArea(new Dimension(0, 10)));
    this.add(bookPane);
    this.add(Box.createRigidArea(new Dimension(0, 10)));
    this.add(summary);
    this.add(Box.createRigidArea(new Dimension(0, 10)));
  }

  /**
   * Method for adding books already in Library at startup to the RemoveBookWindow.
   */
  public static void updateRemoveBookWindow()
  {
    // Get book titles of books in the library
    model.clear();
    HashMap<Integer, Book> map = BookStore.getInstance().getBooksList();
    int indexCounter = 0;
    int i = 0;

    // Get new summary string representation of the Library data.
    while (indexCounter < BookStore.getInstance().getSize())
    {
      try
      {
        Book curr = map.get(i);
        String bookSummary = String.format("%s by %s ID# %s", curr.getTitle(), curr.getAuthor(),
            curr.getUniqueID());

        model.addElement(bookSummary);
        indexCounter++;
        i++;
      }
      catch (NullPointerException npe)
      {
        i++;
      }
    }
  }

  /**
   * listener for remove button.
   */
  private class RemoveButtonListener implements ActionListener
  {
    @Override
    public void actionPerformed(ActionEvent e)
    {
      /* Optional */
      /* Display summary of removed book data */
      
      String book = removeList.getSelectedValue();
      int bookNumber = Integer.parseInt(book.substring(book.indexOf('#') + 2, book.length()));
      BookStore.removeBook(bookNumber);
      InvertedIndex.removePostings(bookNumber);
      
      // Remove book from JList
      model.remove(removeList.getSelectedIndex());
      removeButton.setEnabled(false);
      SummarizedDataWindow.updateSummary();
    }
  }

  /**
   * listener for the list of books.
   * @author Bryan Secoy
   */
  private class SelectionListener implements ListSelectionListener
  {
    @Override
    public void valueChanged(ListSelectionEvent list)
    {
      removeButton.setEnabled(true);
    }
  }
}
