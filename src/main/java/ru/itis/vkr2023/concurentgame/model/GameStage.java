package ru.itis.vkr2023.concurentgame.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
    Очередной кон игры
 */
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameStage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gameStageGen")
    @SequenceGenerator(name = "gameStageGen", sequenceName = "gamestage_seq",allocationSize = 1)
    private Long id;

    @ManyToOne
    private Game game;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @OneToMany(mappedBy = "gameStage", fetch = FetchType.LAZY)
    private List<ManufacturerStatus> manufacturerStatusList;
}
