import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import person.Book;
import person.Person;


/**
 * A JUnit test class for the Book class.
 */
public class BookTest {
  private Person person;
  private Book book;

  /**
    * Sets up a book object before each test.
    */
  @Before
  public void setUp() {
    person = new Person("john", "doe", 1989);;
    book = new Book("sample", person, 19.99f);
  }

  @Test
  public void testTitle() {
    assertEquals("sample", book.getTitle());
  }

  @Test
  public void testAuthor() {
    assertEquals(person, book.getAuthor());
  }

  /**
   * Adding a delta of 0.01f for comparison
   * 0.01 f is the tolerance value
   * this allows for slight differences between the expected and actual values.
   */
  @Test
  public void testPrice() {
    assertEquals(19.99f, book.getPrice(), 0.01f);
  }
}