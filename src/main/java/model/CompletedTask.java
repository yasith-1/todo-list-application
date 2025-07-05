package model;

import lombok.*;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CompletedTask {
    private String id;
    private String name;
    private String description;
    private Date date;
    private String time;
    private String userId;
    private Integer statusId;
}
