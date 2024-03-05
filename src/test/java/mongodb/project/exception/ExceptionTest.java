package mongodb.project.exception;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExceptionTest {

    @Test
    public void testStudentNotFoundException() {
        String id = "123";
        String expectedMessage =
                HttpStatus.NOT_FOUND + " " + String.format("\"Student with id %s does not exist.\"", id);

        StudentNotFoundException exception = assertThrows(StudentNotFoundException.class, () -> {
            throw new StudentNotFoundException(id);
        });

        assertEquals(expectedMessage, exception.getMessage()); // Check only the error message
    }

    @Test
    public void testBookNotFoundException() {
        String id = "456";
        String expectedMessage =
                HttpStatus.NOT_FOUND + " " + String.format("\"Book with id %s does not exist.\"", id);

        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> {
            throw new BookNotFoundException(id);
        });

        assertEquals(expectedMessage, exception.getMessage()); // Check only the error message
    }
}
