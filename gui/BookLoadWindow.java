package gui;

import java.awt.Dimension;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import appDomain.SourceProcessor;
import dataStore.InvertedIndex;

/**
 * GUI Class for creating Panel for Load Books tab of PirexWindow.
 * 
 * @author Group G
 * @author Brandon Domonoski
 * @version 1.0
 * @since 2017-10-22
 */
public class BookLoadWindow extends JPanel
{
  public static final long serialVersionUID = 1L;
  private JPanel textFile, textFileType, textFileDetails, separator, processButtonPanel,
      processPanel;
  private JTextField textFileField, titleField, authorField;
  private JComboBox<String> fileTypeComboBox;
  private JLabel textFileLabel, textFileTypeLabel, titleLabel, authorLabel;
  private JButton browseButton, processButton;
  private JSeparator sep;
  private JTextPane processText;
  private JFileChooser chooseFile;
  private String[] fileTypes = {"Project Gutenberg File", "Other", "Rich Text Format", "HTML"};

  /**
   * BookLoadWindow panel constructor.
   */
  public BookLoadWindow()
  {
    super();
    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

    // Initialize variables for textFile panel.
    textFileLabel = new JLabel("Text File: ");
    textFileField = new JTextField();
    TextFileFieldListener tl = new TextFileFieldListener();
    textFileField.getDocument().addDocumentListener(tl);
    browseButton = new JButton("Browse");
    BrowseButtonListener bbl = new BrowseButtonListener();
    browseButton.addActionListener(bbl);

    // Setup Text File panel.
    textFile = new JPanel();
    textFile.setLayout(new BoxLayout(textFile, BoxLayout.LINE_AXIS));
    textFile.add(Box.createRigidArea(new Dimension(20, 0)));
    
    // Add Label and TextField.
    textFile.add(textFileLabel);
    textFile.add(Box.createRigidArea(new Dimension(10, 0)));
    textFile.add(textFileField);
    textFile.add(Box.createRigidArea(new Dimension(10, 0)));
    
    // Add Browse button.
    textFile.add(browseButton);
    textFile.add(Box.createRigidArea(new Dimension(20, 0)));
    textFile.setMaximumSize(new Dimension(Integer.MAX_VALUE, textFile.getPreferredSize().height));

    // Initialize variables for textFileType panel.
    fileTypeComboBox = new JComboBox<String>(fileTypes);
    textFileTypeLabel = new JLabel("Text File Type: ");

    // Setup textFileType Panel.
    textFileType = new JPanel();
    textFileType.setLayout(new BoxLayout(textFileType, BoxLayout.LINE_AXIS));
    
    // Add Label and ComboBox.
    textFileType.add(Box.createRigidArea(new Dimension(20, 0)));
    textFileType.add(textFileTypeLabel);
    textFileType.add(Box.createRigidArea(new Dimension(10, 0)));
    textFileType.add(fileTypeComboBox);
    textFileType.add(Box.createRigidArea(new Dimension(20, 0)));
    textFileType
        .setMaximumSize(new Dimension(Integer.MAX_VALUE, textFileType.getPreferredSize().height));

    // Initialize variables for textFileDetails panel.
    titleLabel = new JLabel("Title: ");
    authorLabel = new JLabel("Author: ");
    titleField = new JTextField("");
    authorField = new JTextField("");

    // Setup textFileDetails Panel.
    textFileDetails = new JPanel();
    textFileDetails.setLayout(new BoxLayout(textFileDetails, BoxLayout.X_AXIS));
    
    // Add Title label and Title field.
    textFileDetails.add(Box.createRigidArea(new Dimension(20, 0)));
    textFileDetails.add(titleLabel);
    textFileDetails.add(Box.createRigidArea(new Dimension(10, 0)));
    textFileDetails.add(titleField);
    textFileDetails.add(Box.createRigidArea(new Dimension(20, 0)));
    
    // Add Author label and Author field.
    textFileDetails.add(authorLabel);
    textFileDetails.add(Box.createRigidArea(new Dimension(10, 0)));
    textFileDetails.add(authorField);
    textFileDetails.add(Box.createRigidArea(new Dimension(20, 0)));
    textFileDetails.setMaximumSize(
        new Dimension(Integer.MAX_VALUE, textFileDetails.getPreferredSize().height));

    // Initialize variables for separator Panel.
    separator = new JPanel();
    sep = new JSeparator();
    
    // Setup separator panel.
    separator.setLayout(new BoxLayout(separator, BoxLayout.LINE_AXIS));
    separator.add(Box.createRigidArea(new Dimension(20, 0)));
    
    // Add separator.
    separator.add(sep);
    separator.add(Box.createRigidArea(new Dimension(20, 0)));

    // Initialize processButtonPanel variables.
    processButton = new JButton("Process");
    processButton.setEnabled(false);
    ProcessButtonListener pbl = new ProcessButtonListener();
    processButton.addActionListener(pbl);
    processButtonPanel = new JPanel();
    
    // Setup processButtonPanel
    processButtonPanel.setLayout(new BoxLayout(processButtonPanel, BoxLayout.LINE_AXIS));
    processButtonPanel.add(Box.createRigidArea(new Dimension(20, 0)));
    
    // Add processButton
    processButtonPanel.add(processButton);
    processButtonPanel.add(Box.createHorizontalGlue());

    // Initialize processPanel variables.
    Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    processPanel = new JPanel();
    processText = new JTextPane();
    processText.setBorder(border);
    processText.setFont(new Font("LabelStyle", textFileLabel.getFont().getStyle(), 15));
    processText.setEditable(false);

    // Setup processPanel
    processPanel.setLayout(new BoxLayout(processPanel, BoxLayout.LINE_AXIS));
    processPanel.add(Box.createRigidArea(new Dimension(20, 0)));
   
    // Add process text field.
    processPanel.add(processText);
    processPanel.add(Box.createRigidArea(new Dimension(20, 0)));

    // Adds Text File search components.
    this.add(Box.createRigidArea(new Dimension(0, 10)));
    this.add(textFile);
    
    // Adds File Type selection components.
    this.add(Box.createRigidArea(new Dimension(0, 20)));
    this.add(textFileType);
    
    // Adds File detail components (Title/Author fields)
    this.add(Box.createRigidArea(new Dimension(0, 20)));
    this.add(textFileDetails);
    
    // Adds separator between choosing file functionality and processing a chosen file.
    this.add(Box.createRigidArea(new Dimension(0, 20)));
    this.add(separator);
    
    // Adds Process Button.
    this.add(Box.createRigidArea(new Dimension(0, 20)));
    this.add(processButtonPanel);
    
    // Adds Process text field.
    this.add(Box.createRigidArea(new Dimension(0, 10)));
    this.add(processPanel);
    this.add(Box.createRigidArea(new Dimension(0, 20)));
  }

  /**
   * Action Listener for file entry TextField. Allows users to enter in files by typing them in.
   * 
   * @author Group G
   * @version 11/9/2017
   */
  private class TextFileFieldListener implements DocumentListener
  {

    @Override
    public void changedUpdate(DocumentEvent arg0)
    {
      // Not sure what this does.
    }

    @Override
    public void insertUpdate(DocumentEvent arg0)
    {
      SourceProcessor.initSourceProcessor();
      processText.setText("");
      titleField.setText("");
      authorField.setText("");
      File file = new File(textFileField.getText());
      if (file.exists())
      {
        SourceProcessor.parseNewBook(file.getPath());
        titleField.setText(SourceProcessor.getParsedBook().getTitle());
        authorField.setText(SourceProcessor.getParsedBook().getAuthor());
      }
      processButton.setEnabled(true);
    }

    @Override
    public void removeUpdate(DocumentEvent arg0)
    {
      SourceProcessor.initSourceProcessor();
      titleField.setText("");
      authorField.setText("");
      if (textFileField.getText().length() == 0)
      {
        processButton.setEnabled(false);
      }
      File file = new File(textFileField.getText());
      if (file.exists())
      {
        SourceProcessor.parseNewBook(file.getPath());
        if (SourceProcessor.getParsedBook() != null)
        {
          titleField.setText(SourceProcessor.getParsedBook().getTitle());
          authorField.setText(SourceProcessor.getParsedBook().getAuthor());
        }
      }
    }
  }

  /**
   * Action Listener for Browse button.
   * 
   * @author Group G
   * @author Brandon Domonoski
   * @since 2017-10-23
   * @version 1.0
   */
  private class BrowseButtonListener implements ActionListener
  {
    int returnVal;

    @Override
    public void actionPerformed(ActionEvent e)
    {
      processText.setText("");
      chooseFile = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
      FileNameExtensionFilter filter = new FileNameExtensionFilter("Project Gutenberg File", "txt");
      chooseFile.setFileFilter(filter);
      returnVal = chooseFile.showOpenDialog(null);

      if (returnVal == JFileChooser.APPROVE_OPTION)
      {
        textFileField.setText(chooseFile.getSelectedFile().getPath());
        SourceProcessor.parseNewBook(chooseFile.getSelectedFile().getPath());
        titleField.setText(SourceProcessor.getParsedBook().getTitle());
        authorField.setText(SourceProcessor.getParsedBook().getAuthor());
        processButton.setEnabled(true);
      }
    }
  }

  /**
   * Process button action listener class.
   * 
   * @author Group G
   * @author Brandon Domonoski
   * @since 2017-10-23
   * @version 1.0
   */
  private class ProcessButtonListener implements ActionListener
  {
    @Override
    public void actionPerformed(ActionEvent e)
    {
      if (SourceProcessor.getParsedBook().getAuthor() == null)
      {
        processText.setText("The book was not found");

        // Clear fields
        textFileField.setText("");
        titleField.setText("");
        authorField.setText("");
      }
      else
      {
        SourceProcessor.process(titleField.getText(), authorField.getText());
        SummarizedDataWindow.updateSummary();
        RemoveBookWindow.updateRemoveBookWindow();
        String summary = String.format(
            "Book: %s\nTitle: %s\nAuthor: %s\nBook size: %d paragraphs\n"
                + "Book number: %d\nNew index terms: %d\nNew postings: %d\n"
                + "Total index terms: %d\nTotal posings: %d\n",
            SourceProcessor.getParsedBook().getFile().getAbsolutePath(),
            SourceProcessor.getParsedBook().getTitle(), SourceProcessor.getParsedBook().getAuthor(),
            SourceProcessor.getParsedBook().getParagraphs().size(),
            SourceProcessor.getParsedBook().getUniqueID(),
            InvertedIndex.getInstance().getAddedTermCount(),
            InvertedIndex.getInstance().getAddedPostingCount(),
            InvertedIndex.getInstance().getTermCount(),
            InvertedIndex.getInstance().getPostingCount());
        InvertedIndex.getInstance().resetAdded();
        SourceProcessor.resetCurrParsedBook();
        // return process;
        processText.setText(summary);
        processButton.setEnabled(false);

        // Clear fields
        textFileField.setText("");
        titleField.setText("");
        authorField.setText("");
      }
    }
  }
}
