package mongodb.project.entity.dto.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDto {
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
