package mongodb.project.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import mongodb.project.entity.Book;
import mongodb.project.entity.Student;
import mongodb.project.entity.dto.book.BookDto;
import mongodb.project.entity.dto.book.BookRequestDto;
import org.springframework.beans.BeanUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookMapper {

    public static Book toBook(BookRequestDto bookDto, Student student) {
        return Book.builder()
                .authorFullName(bookDto.getAuthorFullName())
                .bookName(bookDto.getBookName())
                .givenDate(bookDto.getGivenDate())
                .returnDate(bookDto.getReturnDate())
                .student(student)
                .build();
    }

    public static BookDto toBookDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .authorFullName(book.getAuthorFullName())
                .bookName(book.getBookName())
                .givenDate(book.getGivenDate())
                .returnDate(book.getReturnDate())
                .studentId(book.getStudent().getId())
                .build();
    }

    public static Book updateBook(Book book, BookRequestDto requestDto, Student student) {
        BeanUtils.copyProperties(requestDto, book);
        book.setStudent(student);
        return book;
    }
}
