package mongodb.project.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class BookNotFoundException extends ResponseStatusException {
    public BookNotFoundException(String id) {
        super(HttpStatus.NOT_FOUND, String.format("Book with id %s does not exist.", id));
        log.error("Book not found with id: {}", id);
    }
}
