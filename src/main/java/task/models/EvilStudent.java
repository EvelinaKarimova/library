package task.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class EvilStudent {
    private Long id;
    private String lastName;
    private String firstName;
    private String patronymic;
    private int times;
    private int numberOfDays;
}
