package model;

import lombok.*;
import util.TaskStatus;
import java.sql.Date;
import java.sql.Time;

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
    private Time time;
    private TaskStatus status;
    private String userId;

}
