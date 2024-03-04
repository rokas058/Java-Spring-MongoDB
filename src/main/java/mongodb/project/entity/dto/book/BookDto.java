package mongodb.project.entity.dto.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mongodb.project.entity.Student;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    @Id
    private String id;
    @NotNull
    private String authorFullName;
    @NotNull
    private String bookName;
    @NotNull
    private LocalDate givenDate;
    @NotNull
    private LocalDate returnDate;
    @NotNull
    private String studentId;
}
