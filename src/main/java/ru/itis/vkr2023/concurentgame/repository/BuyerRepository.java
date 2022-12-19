package ru.itis.vkr2023.concurentgame.repository;

import org.springframework.data.repository.CrudRepository;
import ru.itis.vkr2023.concurentgame.model.Buyer;

public interface BuyerRepository extends CrudRepository<Buyer, Long> {

}
