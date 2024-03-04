package mongodb.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mongodb.project.entity.dto.student.StudentRequestDto;
import mongodb.project.entity.dto.student.StudentDto;
import mongodb.project.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService service;

    @PostMapping
    public StudentDto createStudent(@RequestBody @Valid StudentRequestDto studentDto) {
        return service.createStudent(studentDto);
    }

    @GetMapping
    public List<StudentDto> getStudents() { return service.getStudents(); }

    @GetMapping("/{id}")
    public StudentDto getStudentById(@PathVariable String id) { return service.getStudentById(id); }

    @PutMapping("/{id}")
    public StudentDto updateStudent(@PathVariable String id, @RequestBody @Valid StudentRequestDto studentDto) {
        return service.updateStudent(id, studentDto);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable String id) {
        service.deleteStudent(id);
    }
}
