package model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CompletedTask {
    private String id;
    private String name;
    private String description;
    private String date;
    private String time;
    private String userId;
    private Integer statusId;
}
