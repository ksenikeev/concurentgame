package ru.itis.vkr2023.concurentgame.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

/**
 * Покупатель
 */
@Entity
@Getter@Setter@NoArgsConstructor
public class Buyer {

    @Id
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    private String name;

    private String groupName;

    private Integer question1;

    private Integer question2;

    private Integer question3;

    private Integer question4;

    private Integer question5;

    private Integer question6;

    public Buyer(Long id, Date createDate, String name, String group, Integer q1, Integer q2, Integer q3, Integer q4, Integer q5, Integer q6) {
        this.id = id;
        this.createDate = createDate;
        this.name = name;
        this.groupName = group;
        this.question1 = q1;
        this.question2 = q2;
        this.question3 = q3;
        this.question4 = q4;
        this.question5 = q5;
        this.question6 = q6;
    }
}
