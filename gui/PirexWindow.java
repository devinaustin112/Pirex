package gui;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

import dataStore.BookStore;
import dataStore.InvertedIndex;

/**
 * Main window for the Pirex GUI.
 * 
 * @author Group G
 */
public class PirexWindow extends JFrame
{
  private static final long serialVersionUID = 1L;

  /**
   * Constructor.
   */
  public PirexWindow()
  {

    setTitle("Pirex");
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    setContentPane(new TabbedWindow());
    this.setPreferredSize(new Dimension(1200, 800));
    pack();
    setVisible(true);

    this.addWindowListener(new WindowAdapter()
    {
      @Override
      public void windowClosing(WindowEvent event)
      {
        BookStore.getInstance().exit();
        InvertedIndex.getInstance().exit();
        System.exit(0);
      }
    });
  }
}
