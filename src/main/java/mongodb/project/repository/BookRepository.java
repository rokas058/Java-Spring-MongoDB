package mongodb.project.repository;

import mongodb.project.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findAllByReturnDateBefore(LocalDate currentDate);

    List<Book> findAllByStudentId(String studentId);

    List<Book> findBooksByAuthorFullNameAndBookName(String authorFullName, String bookName);
}
