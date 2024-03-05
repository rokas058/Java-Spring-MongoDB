package mongodb.project.service;

import mongodb.project.entity.Book;
import mongodb.project.entity.Student;
import mongodb.project.entity.dto.book.BookDto;
import mongodb.project.entity.dto.book.BookRequestDto;
import mongodb.project.repository.BookRepository;
import mongodb.project.repository.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void testCreateBook() {
        // Mock data
        BookRequestDto bookRequestDto = new BookRequestDto("John Doe", "Some Book",
                LocalDate.now(), LocalDate.now().plusDays(7), "1");

        Student student = new Student("1", "John", "Doe", "john.doe@example.com");

        Book book = new Book("1", "John Doe", "Some Book",
                LocalDate.now(), LocalDate.now().plusDays(7), student);

        // Stubbing repository methods
        when(studentRepository.findById("1")).thenReturn(Optional.of(student));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // Call the service method
        BookDto result = bookService.createBook(bookRequestDto);

        // Assertions
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("John Doe", result.getAuthorFullName());
        assertEquals("Some Book", result.getBookName());
        assertEquals(LocalDate.now(), result.getGivenDate());
        assertEquals(LocalDate.now().plusDays(7), result.getReturnDate());
        assertEquals("1", result.getStudentId());
    }

    @Test
    public void testGetBooks() {
        // Create a list of books that the simulated repository.findAll() method would return
        Student student = new Student();
        student.setId("1");

        List<Book> mockedBooks = new ArrayList<>();
        mockedBooks.add(new Book("1", "Author1", "Book1",
                LocalDate.now(), LocalDate.now(), student));
        mockedBooks.add(new Book("2", "Author2", "Book2",
                LocalDate.now(), LocalDate.now(), student));

        // Set up that the repository.findAll() method returns your simulated list
        when(bookRepository.findAll()).thenReturn(mockedBooks);

        // Call your method under test
        List<BookDto> result = bookService.getBooks();

        // Check if the result matches your expected outcomes
        assertNotNull(result);
        assertEquals(mockedBooks.size(), result.size()); // Check if the size of the returned list is correct
    }

    @Test
    public void testGetBookById() {
        Student student = new Student();
        student.setId("1");
        // Create a mock book object
        Book mockedBook = new Book("1", "Author1", "Book1",
                LocalDate.now(), LocalDate.now(), student);

        // Specify behavior for repository.findById() method
        when(bookRepository.findById("1")).thenReturn(Optional.of(mockedBook));

        // Call the method under test
        BookDto result = bookService.getBookById("1");

        // Check if the result is not null
        assertNotNull(result);

        // Verify that the returned bookDto matches the expected values
        assertEquals(mockedBook.getId(), result.getId());
        assertEquals(mockedBook.getAuthorFullName(), result.getAuthorFullName());
        // Add assertions for other book properties
    }

    @Test
    public void testUpdateBook() {
        Student student = new Student();
        student.setId("1");
        // Create a mock existing book object
        Book existingBook = new Book("1", "Author1", "Book1",
                LocalDate.now(), LocalDate.now(), student);

        // Create a mock book request DTO
        BookRequestDto bookRequestDto = new BookRequestDto("New Author", "New Book",
                LocalDate.now(), LocalDate.now(), "1");

        // Specify behavior for repository.findById() method to return the existing book
        when(bookRepository.findById("1")).thenReturn(Optional.of(existingBook));

        // Specify behavior for studentRepository.findById() method to return null (simulate a case where the student is not found)
        when(studentRepository.findById("1")).thenReturn(Optional.of(student));

        // Call the method under test
        BookDto updatedBookDto = bookService.updateBook("1", bookRequestDto);

        // Check if the updated bookDto is not null
        assertNotNull(updatedBookDto);

        // Verify that the returned bookDto matches the expected values after update
        assertEquals("1", updatedBookDto.getId());
        assertEquals("New Author", updatedBookDto.getAuthorFullName());
        assertEquals("New Book", updatedBookDto.getBookName());
        assertEquals("1", updatedBookDto.getStudentId());
        // Add assertions for other book properties
    }

    @Test
    public void testDeleteBook() {
        // Create a mock book object with a known ID
        String bookId = "1";
        Book mockBook = new Book();
        mockBook.setId(bookId);

        // Specify behavior for repository.findById() method to return the mock book object
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockBook));

        // Call the method under test
        bookService.deleteBook(bookId);

        // Verify that the delete method of the repository is called with the mock book object
        verify(bookRepository, times(1)).delete(mockBook);
    }

    @Test
    public void testFindBooksWithPastReturnDate() {
        Student student = new Student();
        student.setId("1");
        // Create mock books with return dates in the past
        LocalDate currentDate = LocalDate.now();
        List<Book> mockBooks = new ArrayList<>();
        mockBooks.add(new Book("1", "Author1", "Book1",
                LocalDate.now().minusDays(7), currentDate.minusDays(2), student));
        mockBooks.add(new Book("2", "Author2", "Book2",
                LocalDate.now().minusDays(14), currentDate.minusDays(10), student));

        // Specify behavior for repository.findAllByReturnDateBefore() method to return the list of mock books
        when(bookRepository.findAllByReturnDateBefore(currentDate)).thenReturn(mockBooks);

        // Call the method under test
        List<BookDto> result = bookService.findBooksWithPastReturnDate();

        // Verify that the returned list is not null and contains the expected number of BookDto objects
        assertNotNull(result);
        assertEquals(mockBooks.size(), result.size());

        // Verify
    }
    @Test
    public void testFindBooksByStudentId() {
        Student student = new Student();
        student.setId("1");
        String studentId = "1";

        // Create mock books associated with the student ID
        List<Book> mockBooks = new ArrayList<>();
        mockBooks.add(new Book("1", "Author1", "Book1",
                LocalDate.now(), LocalDate.now().plusDays(7), student));
        mockBooks.add(new Book("2", "Author2", "Book2",
                LocalDate.now(), LocalDate.now().plusDays(7), student));

        // Specify behavior for repository.findAllByStudentId() method to return the list of mock books
        when(bookRepository.findAllByStudentId(studentId)).thenReturn(mockBooks);

        // Call the method under test
        List<BookDto> result = bookService.findBooksByStudentId(studentId);

        // Verify that the returned list is not null and contains the expected number of BookDto objects
        assertNotNull(result);
        assertEquals(mockBooks.size(), result.size());

        // Verify that each BookDto object in the list is associated with the correct student ID
        for (BookDto bookDto : result) {
            assertEquals(studentId, bookDto.getStudentId());
        }
    }

    @Test
    public void testFindBooksByAuthorFullNameAndBookName() {
        Student student = new Student();
        student.setId("1");
        // Mock author full name and book name
        String authorFullName = "John Doe";
        String bookName = "Some Book";

        // Create mock books that match the given author full name and book name
        List<Book> mockBooks = new ArrayList<>();
        mockBooks.add(new Book("1", authorFullName, bookName,
                LocalDate.now(), LocalDate.now().plusDays(7), student));
        // Add more mock books if needed

        // Specify behavior for repository.findBooksByAuthorFullNameAndBookName() method to return the list of mock books
        when(bookRepository.findBooksByAuthorFullNameAndBookName(authorFullName, bookName)).thenReturn(mockBooks);

        // Call the method under test
        List<BookDto> result = bookService.findBooksByAuthorFullNameAndBookName(authorFullName, bookName);

        // Verify that the returned list is not null and contains the expected number of BookDto objects
        assertNotNull(result);
        assertEquals(mockBooks.size(), result.size());

        // Verify that each BookDto object in the list matches the given author full name and book name
        for (BookDto bookDto : result) {
            assertEquals(authorFullName, bookDto.getAuthorFullName());
            assertEquals(bookName, bookDto.getBookName());
        }
    }


}
