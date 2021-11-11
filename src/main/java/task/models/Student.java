package task.models;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student{
    private Long id;
    private String lastName;
    private String firstName;
    private String patronymic;

}

