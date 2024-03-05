package mongodb.project.repository;

import mongodb.project.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.bson.assertions.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testFindAllByReturnDateBefore() {
        // Mock data
        LocalDate currentDate = LocalDate.now();
        // Call the method under test
        List<Book> books = bookRepository.findAllByReturnDateBefore(currentDate);
        // Assertions
        assertNotNull(books); // Ensure the result is not null
        // Add more assertions as needed
    }

    @Test
    public void testFindAllByStudentId() {
        // Mock data
        String studentId = "1";
        // Call the method under test
        List<Book> books = bookRepository.findAllByStudentId(studentId);
        // Assertions
        assertNotNull(books); // Ensure the result is not null
        // Add more assertions as needed
    }

    @Test
    public void testFindBooksByAuthorFullNameAndBookName() {
        // Mock data
        String authorFullName = "John Doe";
        String bookName = "Some Book";
        // Call the method under test
        List<Book> books = bookRepository.findBooksByAuthorFullNameAndBookName(authorFullName, bookName);
        // Assertions
        assertNotNull(books); // Ensure the result is not null
        // Add more assertions as needed
    }
}
