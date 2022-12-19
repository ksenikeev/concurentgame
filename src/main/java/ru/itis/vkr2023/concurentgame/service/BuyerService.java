package ru.itis.vkr2023.concurentgame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.vkr2023.concurentgame.model.Buyer;
import ru.itis.vkr2023.concurentgame.repository.BuyerRepository;

@Service
public class BuyerService {

    @Autowired
    private BuyerRepository repository;

    @Transactional
    public Buyer save(Buyer buyer) {
        return repository.save(buyer);
    }
}
