package mongodb.project.service;

import mongodb.project.entity.Student;
import mongodb.project.entity.dto.student.StudentDto;
import mongodb.project.entity.dto.student.StudentRequestDto;
import mongodb.project.repository.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServiceTest {
    @Mock
    private StudentRepository repository;

    @InjectMocks
    private StudentService studentService;

    @Test
    public void testCreateStudent() {
        // Create mock student request DTO
        StudentRequestDto studentRequestDto = new StudentRequestDto(
                "John", "Doe", "john.doe@example.com");

        // Create mock student object
        Student student = new Student("1", "John", "Doe", "john.doe@example.com");

        // Specify behavior for repository.save() method to return the saved student object
        when(repository.save(any(Student.class))).thenReturn(student);

        // Call the method under test
        StudentDto result = studentService.createStudent(studentRequestDto);

        // Verify that the returned StudentDto object is not null
        assertNotNull(result);

        // Verify that the returned StudentDto object matches the expected data
        assertEquals("1", result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john.doe@example.com", result.getEmail());
    }

    @Test
    public void testGetStudents() {
        // Create a list of simulated students that the repository.findAll() method would return
        List<Student> mockedStudents = new ArrayList<>();
        mockedStudents.add(new Student("1", "John", "Doe", "john.doe@example.com"));
        mockedStudents.add(new Student("2", "Jane", "Smith", "jane.smith@example.com"));

        // Set up the repository.findAll() method to return your simulated list
        when(repository.findAll()).thenReturn(mockedStudents);

        // Call your method under test
        List<StudentDto> result = studentService.getStudents();

        // Check if the result matches your expected outcome
        assertNotNull(result);
        assertEquals(mockedStudents.size(), result.size()); // Check if the returned list size is correct
    }

    @Test
    public void testGetStudentById() {
        // Mock data
        String studentId = "1";
        Student mockedStudent = new Student(studentId, "John", "Doe", "john.doe@example.com");

        // Specify behavior for repository.findById() method to return the mocked student
        when(repository.findById(studentId)).thenReturn(Optional.of(mockedStudent));

        // Call the method under test
        StudentDto result = studentService.getStudentById(studentId);

        // Verify that the returned StudentDto object is not null
        assertNotNull(result);

        // Verify that the returned StudentDto object matches the expected data
        assertEquals(studentId, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john.doe@example.com", result.getEmail());
    }

    @Test
    public void testUpdateStudent() {
        // Mock data
        String studentId = "1";
        StudentRequestDto updatedStudentDto = new StudentRequestDto("John", "Doe", "john.doe@example.com");

        Student existingStudent = new Student(studentId, "John", "Doe", "john.doe@example.com");
        Student updatedStudent = new Student(studentId, "John", "Doe", "john.doe@example.com");

        // Specify behavior for repository.findById() method to return the existing student
        when(repository.findById(studentId)).thenReturn(Optional.of(existingStudent));

        // Call the method under test
        StudentDto result = studentService.updateStudent(studentId, updatedStudentDto);

        // Verify that the returned StudentDto object is not null
        assertNotNull(result);

        // Verify that the returned StudentDto object matches the expected data
        assertEquals(studentId, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john.doe@example.com", result.getEmail());
    }

    @Test
    public void testDeleteStudent() {
        // Mock data
        String studentId = "1";
        Student existingStudent = new Student(studentId, "John", "Doe", "john.doe@example.com");

        // Specify behavior for repository.findById() method to return the existing student
        when(repository.findById(studentId)).thenReturn(Optional.of(existingStudent));

        // Call the method under test
        studentService.deleteStudent(studentId);

        // Verify that the repository.delete() method is called with the correct student object
        verify(repository).delete(existingStudent);
    }


}
