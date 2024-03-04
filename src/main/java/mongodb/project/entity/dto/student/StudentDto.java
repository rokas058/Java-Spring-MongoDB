package mongodb.project.entity.dto.student;

import lombok.*;
import mongodb.project.entity.Book;
import org.jetbrains.annotations.NotNull;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

    @NonNull
    private String id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
}
