package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import appDomain.SearchEngine;

/**
 * @author Group G
 * @author Bryan Secoy
 * @version 1.1
 */
public class SearchWindow extends JPanel
{
  public static final long serialVersionUID = 1L;

  private static DefaultListModel<String> model;
  private JPanel topButtons, highSearchPane, lowSearchPan, shortPane, longPane;
  private JRadioButton andRadio, orRadio;
  private JLabel radioText, highWeight, lowWeight;
  private JButton clear;
  private ButtonGroup radioButtons;
  private JTextField highTerms, lowTerms, reviewedField;
  private JList<String> shortFormList;
  private JTextPane longForm;
  private JScrollPane shortScroll, longScroll;
  private ListSelectionListener lsl;

  /**
   * SearchWindow - JPanel that allows for searching of words in books.
   */
  public SearchWindow()
  {
    // call parent and set layout
    super();
    this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    TextFieldListener tfl = new TextFieldListener();
    model = new DefaultListModel<String>();
    lsl = new SelectionListener();

    radioText = new JLabel("Combine Terms Using:");

    // create and add the radio buttons and the clear button
    topButtons = new JPanel();
    topButtons.setLayout(new BoxLayout(topButtons, BoxLayout.LINE_AXIS));
    topButtons.add(Box.createRigidArea(new Dimension(20, 0)));

    // can be accessed by pressing A plus Alt (or whatever the users look-and-feel is)
    andRadio = new JRadioButton("And");
    andRadio.setMnemonic(KeyEvent.VK_A);

    // can be accessed by pressing O plus Alt
    orRadio = new JRadioButton("Or");
    orRadio.setMnemonic(KeyEvent.VK_O);

    // associate the buttons
    radioButtons = new ButtonGroup();
    radioButtons.add(andRadio);
    radioButtons.add(orRadio);
    orRadio.setSelected(true); // default setting

    // Listener to perform search if radio buttons are changed (UID req. 2.1)
    ChangeRadioListener crl = new ChangeRadioListener();
    orRadio.addActionListener(crl);
    andRadio.addActionListener(crl);

    // create Clear All Terms button with action listener
    clear = new JButton("Clear All Terms");
    clear.setEnabled(false);
    ClearButtonListener cbl = new ClearButtonListener();
    clear.addActionListener(cbl);

    // add everything to our top panel
    topButtons.add(radioText);
    topButtons.add(Box.createRigidArea(new Dimension(20, 0)));
    topButtons.add(orRadio);
    topButtons.add(andRadio);
    topButtons.add(Box.createHorizontalGlue());
    topButtons.add(clear);
    topButtons.add(Box.createRigidArea(new Dimension(20, 0)));
    topButtons
        .setMaximumSize(new Dimension(Integer.MAX_VALUE, topButtons.getPreferredSize().height));
    topButtons
        .setMaximumSize(new Dimension(Integer.MAX_VALUE, topButtons.getPreferredSize().height));

    // add text fields for search terms
    highSearchPane = new JPanel();
    highSearchPane.setLayout(new BoxLayout(highSearchPane, BoxLayout.LINE_AXIS));
    highWeight = new JLabel("High Weight Search Terms:");
    highTerms = new JTextField();
    highTerms.addKeyListener(tfl);
    highSearchPane.add(Box.createRigidArea(new Dimension(20, 0)));
    highSearchPane.add(highWeight);
    highSearchPane.add(Box.createRigidArea(new Dimension(20, 0)));
    highSearchPane.add(highTerms);
    highSearchPane.add(Box.createRigidArea(new Dimension(20, 0)));
    highSearchPane
        .setMaximumSize(new Dimension(Integer.MAX_VALUE, highSearchPane.getPreferredSize().height));
    lowSearchPan = new JPanel();
    lowSearchPan.setLayout(new BoxLayout(lowSearchPan, BoxLayout.LINE_AXIS));
    lowWeight = new JLabel("Low Weight Search Terms:");
    lowTerms = new JTextField();
    lowTerms.addKeyListener(tfl);
    lowTerms.setMaximumSize(new Dimension(Integer.MAX_VALUE, lowTerms.getPreferredSize().height));
    lowSearchPan.add(Box.createRigidArea(new Dimension(20, 0)));
    lowSearchPan.add(lowWeight);
    lowSearchPan.add(Box.createRigidArea(new Dimension(20, 0)));
    lowSearchPan.add(lowTerms);
    lowSearchPan.add(Box.createRigidArea(new Dimension(20, 0)));

    // Create short form panel

    shortFormList = new JList<String>(model);
    shortFormList.addListSelectionListener(lsl);
    shortFormList.setFont(new Font("LabelStyle", Font.BOLD, 15));
    shortFormList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    shortFormList.setModel(model);
    shortFormList.setLayoutOrientation(JList.VERTICAL);

    shortPane = new JPanel();
    shortPane.setLayout(new BoxLayout(shortPane, BoxLayout.LINE_AXIS));
    shortScroll = new JScrollPane(shortFormList);
    shortPane.add(Box.createRigidArea(new Dimension(20, 0)));
    shortPane.add(shortScroll);
    shortPane.setPreferredSize(new Dimension(Integer.MAX_VALUE, 3000));
    shortPane.add(Box.createRigidArea(new Dimension(20, 0)));

    // create books retrieved JTextField
    reviewedField = new JTextField();
    reviewedField.setOpaque(false);
    reviewedField
        .setMaximumSize(new Dimension(Integer.MAX_VALUE, reviewedField.getPreferredSize().height));
    reviewedField.setBorder(BorderFactory.createEmptyBorder());
    reviewedField.setFont(new Font("LabelStyle", Font.BOLD, 12));

    // create long form panel

    longPane = new JPanel();
    longPane.setLayout(new BoxLayout(longPane, BoxLayout.LINE_AXIS));
    longForm = new JTextPane();
    longScroll = new JScrollPane(longForm);
    longForm.setEditable(false);
    longForm.setFont(new Font("LabelStyle", Font.BOLD, 15));
    longPane.add(Box.createRigidArea(new Dimension(20, 0)));
    longPane.add(longScroll);
    longPane.setPreferredSize(new Dimension(Integer.MAX_VALUE, 3000));
    longPane.add(Box.createRigidArea(new Dimension(20, 0)));

    this.add(Box.createRigidArea(new Dimension(0, 10)));
    this.add(topButtons);
    this.add(Box.createRigidArea(new Dimension(0, 10)));
    this.add(highSearchPane);
    this.add(Box.createRigidArea(new Dimension(0, 10)));
    this.add(lowSearchPan);
    this.add(Box.createRigidArea(new Dimension(0, 20)));
    this.add(shortPane);
    this.add(Box.createRigidArea(new Dimension(0, 5)));
    this.add(reviewedField);
    this.add(Box.createRigidArea(new Dimension(0, 5)));
    this.add(longPane);
    this.add(Box.createRigidArea(new Dimension(0, 10)));
  }

  /**
   * Process button action listener class.
   */
  private class ClearButtonListener implements ActionListener
  {

    @Override
    public void actionPerformed(ActionEvent e)
    {
      highTerms.setText("");
      lowTerms.setText("");
      model.clear();
      longForm.setText("");
      reviewedField.setText("");
    }
  }

  /**
   * Initiates a search if the radio buttons are changed by the operator. UIDv1 requirement 2.1
   * 
   * @author Bryan
   */
  private class ChangeRadioListener implements ActionListener
  {
    @Override
    public void actionPerformed(ActionEvent e)
    {
      startSearch();
    }
  }

  /**
   * Process keys in text fields listener class.
   */
  private class TextFieldListener implements KeyListener
  {
    @Override
    public void keyTyped(KeyEvent e)
    {
      clear.setEnabled(true);
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
      if (e.getKeyCode() == KeyEvent.VK_ENTER)
      {
        startSearch();
      }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
      if (highTerms.getText().length() == 0 && lowTerms.getText().length() == 0 && model.isEmpty())
      {
        clear.setEnabled(false);
      }
    }
  }

  /**
   * Listener for when short form paragraphs are selected.
   * 
   * @author Bryan / Devin
   */
  private class SelectionListener implements ListSelectionListener
  {
    @Override
    public void valueChanged(ListSelectionEvent e)
    {
      LinkedList<String> paragraph = SearchEngine.getLongForm();
      longForm.setText("");
      shortFormList.getSelectedIndex();

      if (shortFormList.getSelectedIndex() > -1)
      {
        longForm.setText(paragraph.get(shortFormList.getSelectedIndex()));
      }
    }
  }

  /**
   * Initiates a search. Can be triggered by pressing enter in a high / low field or changing a
   * JRadio button.
   */
  private void startSearch()
  {
    model.clear();
    ArrayList<String> result;
    if (orRadio.isSelected())
    {
      SearchEngine.search(highTerms.getText(), lowTerms.getText());
      result = SearchEngine.getShortForm();
    }
    else
    {
      SearchEngine.searchAnd(highTerms.getText(), lowTerms.getText());
      result = SearchEngine.getShortForm();
    }
    for (String s : result)
    {
      model.addElement(s);
    }
    int paragraphsRetrieved = result.size();
    reviewedField.setText("       Retrieved " + paragraphsRetrieved + " paragraphs");
  }
}
