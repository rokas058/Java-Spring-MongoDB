package mongodb.project.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
@Slf4j
public class StudentNotFoundException extends ResponseStatusException {

    public StudentNotFoundException(String id) {
        super(HttpStatus.NOT_FOUND, String.format("Student with id %s does not exist.", id));
        log.error("Student not found with id: {}", id);
    }
}
