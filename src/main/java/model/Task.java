package model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Task {
    private String id;
    private String name;
    private String description;
    private String date;
    private String time;
    private Integer statusId;
    private String userId;

}
