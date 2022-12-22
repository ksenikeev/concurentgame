package ru.itis.vkr2023.concurentgame.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.itis.vkr2023.concurentgame.model.Buyer;

import java.util.List;

public interface BuyerRepository extends CrudRepository<Buyer, Long> {

    @Query("select b from Buyer b ")
    List<Buyer> findAll();

}
