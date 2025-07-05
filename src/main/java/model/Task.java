package model;

import lombok.*;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Task {
    private String id;
    private String name;
    private String description;
    private Date date;
    private String time;
    private Integer statusId;
    private String userId;

}
