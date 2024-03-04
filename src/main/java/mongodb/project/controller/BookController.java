package mongodb.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mongodb.project.entity.dto.book.BookDto;
import mongodb.project.entity.dto.book.BookRequestDto;
import mongodb.project.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {
    private final BookService service;

    @PostMapping
    public BookDto createBook(@RequestBody @Valid BookRequestDto bookRequestDto) {
        return service.createBook(bookRequestDto);
    }

    @GetMapping
    public List<BookDto> getBooks() { return service.getBooks(); }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable String id) { return service.getBookById(id); }

    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable String id, @RequestBody @Valid BookRequestDto bookdto) {
        return service.updateBook(id, bookdto);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable String id) { service.deleteBook(id); }

    @GetMapping("/past-return-date")
    public List<BookDto> getBooksPastReturnDate() { return service.findBooksWithPastReturnDate(); }

    @GetMapping("/by-student/{studentId}")
    public List<BookDto> getBooksByStudentId(@PathVariable String studentId) {
        return service.findBooksByStudentId(studentId);
    }

    @GetMapping("/by-author-and-book")
    public List<BookDto> getBooksByAuthorAndBookName(
            @RequestParam String authorFullName, @RequestParam String bookName) {
        return service.findBooksByAuthorFullNameAndBookName(authorFullName, bookName);
    }
}
