package ru.itis.vkr2023.concurentgame.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Очередной кон (или этап) игры.
 * Рассматривались два варианта создания очередного этапа: 1 - при создании этапа создавать для каждого производителя
 * пустой (по количеству, ассортименту, цене, и т.п.) ManufacturerStatus
 *
 * 2 - создание ManufacturerStatus отложить до первой отправки производителем параметров этапа (тогда, если кто-то
 * не отправит параметры в течении этапа, то экземпляра ManufacturerStatus не будет)
 *
 * Важно: при создании ManufacturerStatus необходимо рассчитывать входящий остаток от работы в предыдущем этапе
 *
 * TODO - сейчас работает 1-й вариант
 *
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

    @ManyToOne(cascade = CascadeType.MERGE)
    private Game game;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    /**
     * При создании очередного этапа игры создаем для каждого производителя
     */
    @OneToMany(mappedBy = "gameStage", fetch = FetchType.LAZY)
    private List<ManufacturerStatus> manufacturerStatusList = new ArrayList<>();
}
