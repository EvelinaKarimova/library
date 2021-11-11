package task.models;

import lombok.*;


@Builder
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookLending {
    private Long id;
    private Long studentID;
    private Long bookID;
    private String lendingDate;
    private String returningDate;

}
