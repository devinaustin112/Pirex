package dataStore;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Book class creates book objects from a file, as well as the book title, author.
 * 
 * @author Group G
 */
public class Book implements Serializable
{
  private static final long serialVersionUID = 1L;
  private File file;
  private String title;
  private String author;
  private int uniqueID;
  private ArrayList<Paragraph> paragraphs;

  /**
   * Default constructor.
   */
  public Book()
  {
    paragraphs = new ArrayList<>();
    author = null;
    title = null;
    file = null;
  }

  /**
   * Default constructor.
   * 
   * @param title String title of this book.
   * @param author String author of this book.
   * @param uniqueID Integer Unique ID number for this book.
   * @param paragraphs ArrayList<Paragraph> list of paragraphs in this book.
   * @param file File path this book was parsed from.
   */
  public Book(String title, String author, int uniqueID, ArrayList<Paragraph> paragraphs, File file)
  {
    this.title = title;
    this.author = author;
    this.uniqueID = uniqueID;
    this.paragraphs = paragraphs;
    this.file = file;
  }

  /**
   * Returns book title.
   * 
   * @return title
   */
  public String getTitle()
  {
    if (title == null)
      title = "";

    return title;
  }

  /**
   * Returns book author.
   * 
   * @return author
   */
  public String getAuthor()
  {
    return author;
  }

  /**
   * Returns arrayList of paragraphs.
   * 
   * @return paragraphs in the book
   */
  public ArrayList<Paragraph> getParagraphs()
  {
    return paragraphs;
  }

  /**
   * Returns the book's unique ID.
   * 
   * @return uniqueID of this book
   */
  public int getUniqueID()
  {
    return uniqueID;
  }

  /**
   * Getter method for file this Book object is created from.
   * 
   * @return File the File this Book was created from.
   */
  public File getFile()
  {
    return file;
  }

  /**
   * Title setter method.
   * 
   * @param newTitle - String to set as title of this Book.
   */
  public void setTitle(String newTitle)
  {
    title = newTitle;
  }

  /**
   * Author setter method.
   * 
   * @param newAuthor - String to set as author of this book.
   */
  public void setAuthor(String newAuthor)
  {
    author = newAuthor;
  }
}
