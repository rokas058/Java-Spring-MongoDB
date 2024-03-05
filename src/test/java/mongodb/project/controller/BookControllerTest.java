package mongodb.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import mongodb.project.entity.dto.book.BookDto;
import mongodb.project.entity.dto.book.BookRequestDto;
import mongodb.project.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    public void testCreateBook() throws Exception {
        // Mock book request DTO
        BookRequestDto bookRequestDto = new BookRequestDto();
        bookRequestDto.setAuthorFullName("John Doe");
        bookRequestDto.setBookName("Sample Book");
        bookRequestDto.setGivenDate(LocalDate.now());
        bookRequestDto.setReturnDate(LocalDate.now().plusDays(7)); // Set return date to one week from now
        bookRequestDto.setStudentId("studentId"); // Set student ID

        // Mock book DTO returned by the service
        BookDto mockedBookDto = new BookDto();
        mockedBookDto.setId("1");
        mockedBookDto.setAuthorFullName("John Doe");
        mockedBookDto.setBookName("Sample Book");
        mockedBookDto.setGivenDate(LocalDate.now());
        mockedBookDto.setReturnDate(LocalDate.now().plusDays(7));
        mockedBookDto.setStudentId("studentId"); // Set a dummy student for testing

        // Stub service method to return simulated data
        when(bookService.createBook(any(BookRequestDto.class))).thenReturn(mockedBookDto);

        // Perform POST request and verify the response
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(bookRequestDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetBooks() throws Exception {
        // Create a list of book DTOs
        List<BookDto> mockedBookDtoList = new ArrayList<>();
        mockedBookDtoList.add(
                new BookDto("1", "John Doe", "Some Book 1", LocalDate.now(),
                        LocalDate.now().plusDays(7), "id1"));
        mockedBookDtoList.add(
                new BookDto("2", "Jane Smith", "Some Book 2", LocalDate.now(),
                        LocalDate.now().plusDays(7), "id2"));

        // Set up the data that should be returned from the service
        when(bookService.getBooks()).thenReturn(mockedBookDtoList);

        // Perform the GET request method and verify the response
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(mockedBookDtoList)));

        // Verify that the service method was called exactly once
        verify(bookService, times(1)).getBooks();
    }

    @Test
    public void testUpdateBook() throws Exception {
        // Mock book request DTO
        BookRequestDto bookRequestDto = new BookRequestDto();
        bookRequestDto.setAuthorFullName("John Doe");
        bookRequestDto.setBookName("Updated Book");
        bookRequestDto.setGivenDate(LocalDate.now());
        bookRequestDto.setReturnDate(LocalDate.now().plusDays(7));
        bookRequestDto.setStudentId("id");

        // Mock book DTO returned by the service after update
        BookDto updatedBookDto = new BookDto();
        updatedBookDto.setId("1");
        updatedBookDto.setAuthorFullName("John Doe");
        updatedBookDto.setBookName("Updated Book");
        updatedBookDto.setGivenDate(LocalDate.now());
        updatedBookDto.setReturnDate(LocalDate.now().plusDays(7));
        updatedBookDto.setStudentId("id1");

        // Stub service method to return simulated data
        when(bookService.updateBook(anyString(), any(BookRequestDto.class)))
                .thenReturn(updatedBookDto);

        // Perform PUT request and verify the response
        mockMvc.perform(put("/api/books/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(bookRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(updatedBookDto)));

        // Verify that the service method was called with the correct parameters
        verify(bookService, times(1)).updateBook(eq("1"), any(BookRequestDto.class));
    }

    @Test
    public void testDeleteBook() throws Exception {
        // Perform DELETE request and verify the response
        mockMvc.perform(delete("/api/books/{id}", "1"))
                .andExpect(status().isOk());

        // Verify that the service method was called with the correct parameters
        verify(bookService, times(1)).deleteBook("1");
    }

    @Test
    public void testGetBooksPastReturnDate() throws Exception {
        // Mock book DTO list returned by the service
        List<BookDto> mockedBookDtoList = new ArrayList<>();
        mockedBookDtoList.add(
                new BookDto("1", "John Doe", "Some Book 1", LocalDate.now(), LocalDate.now()
                        .minusDays(7), "id1"));
        mockedBookDtoList.add(
                new BookDto("2", "Jane Smith", "Some Book 2", LocalDate.now(), LocalDate.now()
                        .minusDays(7), "id2"));

        // Stub service method to return simulated data
        when(bookService.findBooksWithPastReturnDate()).thenReturn(mockedBookDtoList);

        // Perform GET request and verify the response
        mockMvc.perform(get("/api/books/past-return-date"))
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(mockedBookDtoList)));

        // Verify that the service method was called
        verify(bookService, times(1)).findBooksWithPastReturnDate();
    }

    @Test
    public void testGetBooksByStudentId() throws Exception {
        String studentId = "exampleStudentId";

        // Mock book DTO list returned by the service
        List<BookDto> mockedBookDtoList = new ArrayList<>();
        mockedBookDtoList.add(
                new BookDto("1", "John Doe", "Some Book 1", LocalDate.now(), LocalDate.now()
                        .plusDays(7), studentId));
        mockedBookDtoList.add(
                new BookDto("2", "Jane Smith", "Some Book 2", LocalDate.now(), LocalDate.now()
                        .plusDays(7), studentId));

        // Stub service method to return simulated data
        when(bookService.findBooksByStudentId(studentId)).thenReturn(mockedBookDtoList);

        // Perform GET request and verify the response
        mockMvc.perform(get("/api/books/by-student/{studentId}", studentId))
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(mockedBookDtoList)));

        // Verify that the service method was called
        verify(bookService, times(1)).findBooksByStudentId(studentId);
    }
    @Test
    public void testGetBooksByAuthorAndBookName() throws Exception {
        String authorFullName = "John Doe";
        String bookName = "Some Book";

        // Mock book DTO list returned by the service
        List<BookDto> mockedBookDtoList = new ArrayList<>();
        mockedBookDtoList.add(
                new BookDto("1", "John Doe", "Some Book 1", LocalDate.now(), LocalDate.now()
                        .plusDays(7), "studentId1"));
        mockedBookDtoList.add(
                new BookDto("2", "John Doe", "Some Book 2", LocalDate.now(), LocalDate.now()
                        .plusDays(7), "studentId2"));

        // Stub service method to return simulated data
        when(bookService.findBooksByAuthorFullNameAndBookName(authorFullName, bookName))
                .thenReturn(mockedBookDtoList);

        // Perform GET request and verify the response
        mockMvc.perform(get("/api/books/by-author-and-book")
                        .param("authorFullName", authorFullName)
                        .param("bookName", bookName))
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(mockedBookDtoList)));

        // Verify that the service method was called
        verify(bookService, times(1)).findBooksByAuthorFullNameAndBookName(authorFullName, bookName);
    }

    @Test
    public void testGetBookById() throws Exception {
        String bookId = "1";

        // Mock book DTO returned by the service
        BookDto mockedBookDto = new BookDto("1", "John Doe", "Some Book", LocalDate.now(), LocalDate.now()
                .plusDays(7), "studentId");

        // Stub service method to return simulated data
        when(bookService.getBookById(bookId)).thenReturn(mockedBookDto);

        // Perform GET request and verify the response
        mockMvc.perform(get("/api/books/{id}", bookId))
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(mockedBookDto)));

        // Verify that the service method was called
        verify(bookService, times(1)).getBookById(bookId);
    }


    // Helper method to convert objects to JSON string
    private static String toJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
