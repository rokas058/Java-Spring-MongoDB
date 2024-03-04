package mongodb.project.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import mongodb.project.entity.Student;
import mongodb.project.entity.dto.student.StudentDto;
import mongodb.project.entity.dto.student.StudentRequestDto;
import org.springframework.beans.BeanUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StudentMapper {

    public static StudentDto toStudentDto(Student student){
        return StudentDto.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .build();

    }

    public static Student toStudent(StudentRequestDto studentDto){
        return Student.builder()
                .firstName(studentDto.getFirstName())
                .lastName(studentDto.getLastName())
                .email(studentDto.getEmail())
                .build();
    }

    public static Student updatedStudent(Student student, StudentRequestDto requestDto) {
        BeanUtils.copyProperties(requestDto, student);
        return student;
    }
}
