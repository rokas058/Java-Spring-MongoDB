package mongodb.project.service;

import lombok.RequiredArgsConstructor;
import mongodb.project.entity.Book;
import mongodb.project.entity.Student;
import mongodb.project.entity.dto.book.BookDto;
import mongodb.project.entity.dto.book.BookRequestDto;
import mongodb.project.exception.BookNotFoundException;
import mongodb.project.exception.StudentNotFoundException;
import mongodb.project.mapper.BookMapper;
import mongodb.project.repository.BookRepository;
import mongodb.project.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository repository;

    private final StudentRepository studentRepository;
    @Transactional
    public BookDto createBook(BookRequestDto bookRequestDto) {
        Student student = studentRepository.findById(bookRequestDto.getStudentId())
                .orElseThrow(() -> new StudentNotFoundException(bookRequestDto.getStudentId()));
        Book book = BookMapper.toBook(bookRequestDto, student);
        Book savedBook = repository.save(book);
        return BookMapper.toBookDto(savedBook);
    }

    public List<BookDto> getBooks() {
        List<Book> books = repository.findAll();
        return books.stream()
                .map(BookMapper::toBookDto)
                .toList();
    }

    public BookDto getBookById(String id) {
        return repository.findById(id)
                .map(BookMapper::toBookDto)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @Transactional
    public BookDto updateBook(String id, BookRequestDto bookDto) {
        Book existBook = repository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        Student student = studentRepository.findById(bookDto.getStudentId())
                .orElseThrow(() -> new StudentNotFoundException(id));
        Book book = BookMapper.updateBook(existBook, bookDto, student);
        repository.save(book);
        return BookMapper.toBookDto(book);
    }

    @Transactional
    public void deleteBook(String id) {
        Book book = repository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        repository.delete(book);
    }

    public List<BookDto> findBooksWithPastReturnDate() {
        LocalDate currentDate = LocalDate.now();
        List<Book> books = repository.findAllByReturnDateBefore(currentDate);
        return books.stream()
                .map(BookMapper::toBookDto)
                .toList();
    }

    public List<BookDto> findBooksByStudentId(String studentId) {
        List<Book> books = repository.findAllByStudentId(studentId);
        return books.stream()
                .map(BookMapper::toBookDto)
                .toList();
    }

    public List<BookDto> findBooksByAuthorFullNameAndBookName(String authorFullName, String bookName) {
        List<Book> books = repository.findBooksByAuthorFullNameAndBookName(authorFullName, bookName);
        return books.stream()
                .map(BookMapper::toBookDto)
                .toList();
    }
}
