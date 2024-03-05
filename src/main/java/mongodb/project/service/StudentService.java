package mongodb.project.service;

import lombok.RequiredArgsConstructor;
import mongodb.project.entity.Student;
import mongodb.project.entity.dto.student.StudentRequestDto;
import mongodb.project.entity.dto.student.StudentDto;
import mongodb.project.exception.StudentNotFoundException;
import mongodb.project.mapper.StudentMapper;
import mongodb.project.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentService {

    private final StudentRepository repository;
    @Transactional
    public StudentDto createStudent(StudentRequestDto studentDto) {
        Student student = StudentMapper.toStudent(studentDto);
        Student studentSave = repository.save(student);
        return StudentMapper.toStudentDto(studentSave);
    }

    public List<StudentDto> getStudents() {
        List<Student> students = repository.findAll();
        return students.stream()
                .map(StudentMapper::toStudentDto)
                .toList();
    }

    public StudentDto getStudentById(String id) {
        return repository.findById(id)
                .map(StudentMapper::toStudentDto)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    @Transactional
    public StudentDto updateStudent(String id, StudentRequestDto studentDto) {
        Student existStudent = repository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
        Student student = StudentMapper.updatedStudent(existStudent, studentDto);
        repository.save(student);
        return StudentMapper.toStudentDto(student);
    }

    @Transactional
    public void deleteStudent(String id) {
        Student student = repository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
        repository.delete(student);
    }
}
