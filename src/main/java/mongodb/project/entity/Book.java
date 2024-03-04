package mongodb.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Data
@Document(collection = "books")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
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
    @Field("student_id")
    @DocumentReference
    private Student student;
}
