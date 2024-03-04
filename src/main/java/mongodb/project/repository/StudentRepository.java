package mongodb.project.repository;

import mongodb.project.entity.Student;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface StudentRepository extends MongoRepository<Student, String> {

}
