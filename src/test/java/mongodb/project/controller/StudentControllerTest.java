package mongodb.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mongodb.project.entity.dto.student.StudentDto;
import mongodb.project.entity.dto.student.StudentRequestDto;
import mongodb.project.service.StudentService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(StudentController.class)
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    public void testCreateStudent() throws Exception {
        StudentRequestDto studentRequestDto = new StudentRequestDto();
        studentRequestDto.setFirstName("John");
        studentRequestDto.setLastName("Doe");
        studentRequestDto.setEmail("john.doe@example.com");

        // Mock student DTO returned by the service
        StudentDto mockedStudentDto = new StudentDto();
        mockedStudentDto.setId("1");
        mockedStudentDto.setFirstName("John");
        mockedStudentDto.setLastName("Doe");
        mockedStudentDto.setEmail("john.doe@example.com");

        // Stub service method to return simulated data
        when(studentService.createStudent(any(StudentRequestDto.class)))
                .thenReturn(mockedStudentDto);

        // Perform POST request and verify the response
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(studentRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(mockedStudentDto)));

        // Verify that the service method was called with the correct parameters
        verify(studentService, times(1)).createStudent(any(StudentRequestDto.class));
    }

    @Test
    public void testGetStudents() throws Exception {
        // Mock student DTO list returned by the service
        List<StudentDto> mockedStudentDtoList = new ArrayList<>();
        mockedStudentDtoList.add(
                new StudentDto("1", "John", "Doe", "john.doe@example.com"));
        mockedStudentDtoList.add(
                new StudentDto("2", "Jane", "Smith", "jane.smith@example.com"));

        // Stub service method to return simulated data
        when(studentService.getStudents()).thenReturn(mockedStudentDtoList);

        // Perform GET request and verify the response
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(mockedStudentDtoList)));

        // Verify that the service method was called
        verify(studentService, times(1)).getStudents();
    }

    @Test
    public void testGetStudentById() throws Exception {
        // Mock student DTO returned by the service
        StudentDto mockedStudentDto = new StudentDto();
        mockedStudentDto.setId("1");
        mockedStudentDto.setFirstName("John");
        mockedStudentDto.setLastName("Doe");
        mockedStudentDto.setEmail("john.doe@example.com");

        // Stub service method to return simulated data
        when(studentService.getStudentById("1")).thenReturn(mockedStudentDto);

        // Perform GET request and verify the response
        mockMvc.perform(get("/api/students/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(mockedStudentDto)));

        // Verify that the service method was called with the correct parameter
        verify(studentService, times(1)).getStudentById("1");
    }

    @Test
    public void testUpdateStudent() throws Exception {
        // Mock student request DTO
        StudentRequestDto studentRequestDto = new StudentRequestDto();
        studentRequestDto.setFirstName("John");
        studentRequestDto.setLastName("Doe");
        studentRequestDto.setEmail("john.doe@example.com");

        // Mock student DTO returned by the service after update
        StudentDto updatedStudentDto = new StudentDto();
        updatedStudentDto.setId("1");
        updatedStudentDto.setFirstName("John");
        updatedStudentDto.setLastName("Doe");
        updatedStudentDto.setEmail("john.doe@example.com");

        // Stub service method to return simulated data
        when(studentService.updateStudent(eq("1"), any(StudentRequestDto.class))).thenReturn(updatedStudentDto);

        // Perform PUT request and verify the response
        mockMvc.perform(put("/api/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(studentRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(updatedStudentDto)));

        // Verify that the service method was called with the correct parameters
        verify(studentService, times(1)).updateStudent(eq("1"),
                any(StudentRequestDto.class));
    }

    @Test
    public void testDeleteStudent() throws Exception {
        // Perform DELETE request and verify the response
        mockMvc.perform(delete("/api/students/1"))
                .andExpect(status().isOk());

        // Verify that the service method was called with the correct parameter
        verify(studentService, times(1)).deleteStudent("1");
    }

    // Helper method to convert objects to JSON string
    private static String toJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
