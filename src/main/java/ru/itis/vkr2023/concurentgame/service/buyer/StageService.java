package ru.itis.vkr2023.concurentgame.service.buyer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.vkr2023.concurentgame.model.Buyer;
import ru.itis.vkr2023.concurentgame.model.Game;
import ru.itis.vkr2023.concurentgame.model.ManufacturerStatus;
import ru.itis.vkr2023.concurentgame.repository.ManufacturerStatusRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StageService {

    private final ManufacturerStatusRepository repository;

    public void calculateStage(List<Buyer> buyers, List<ManufacturerStatus> manufacturerStatuses, Game game) {
        for (Buyer buyer : buyers) {
            calculateBuyer(buyer, manufacturerStatuses, game);
        }
    }

    public void calculateBuyer(Buyer buyer, List<ManufacturerStatus> manufacturerStatuses, Game game) {

        // доступный бюджет покупателя
        double budget = game.getBuyersBudget();

        // Расчитываем доли предпочтений на рекламу, цену, ассортимент
        //относительные количественные показатели из анкет
        double advertisementIndex =
                (buyer.getQuestion1() + buyer.getQuestion2() + buyer.getQuestion4() + buyer.getQuestion6()) * 0.05;

        double priceIndex = buyer.getQuestion5() * 0.2;

        double assortmentIndex = buyer.getQuestion3() * 0.2;

        // Бюджет покупок с предпочтением рекламируемых товаров
        double advertisementBudget = (advertisementIndex / (advertisementIndex + priceIndex + assortmentIndex)) * budget;

        // Бюджет покупок товаров у производителя с наименьшей ценой
        double priceBudget = (priceIndex / (advertisementIndex + priceIndex + assortmentIndex)) * budget;

        // Бюджет покупок товаров у производителя с наибольшим разнообразием
        double assortmentBudget = (assortmentIndex / (advertisementIndex + priceIndex + assortmentIndex)) * budget;


        // перебираем текущие статусы производителей для совершения покупок в найденных пропорциях
        int countManufacturer = manufacturerStatuses.size();
        ManufacturerStatus manufacturerStatus = null;

        int n = 0;
        while (advertisementBudget > 0 && n < countManufacturer) {
            // ищем производителя с максимальными затратами на рекламу
            manufacturerStatus = manufacturerStatuses.get(countManufacturer-1);
            for (int i = n; i < countManufacturer ; ++i ) {
                if (manufacturerStatuses.get(i).getAdvertisement() > manufacturerStatus.getAdvertisement()) {
                    manufacturerStatus = manufacturerStatuses.get(i);
                }
            }
            n++;

            // Берем от производителя сколько сможем (стоимость произведенной продукции минус то, что успели купить
            // раньше другие покупатели)
            double summ = Math.min(advertisementBudget,
                    manufacturerStatus.getProductCount() * manufacturerStatus.getPrice()
                            - manufacturerStatus.getIncome());
            advertisementBudget -= summ; // бюджет покупателя, связанный с предпочтением рекламы уменьшаем на сумму покупки

            manufacturerStatus.setIncome( manufacturerStatus.getIncome() + summ);
        }


        n = 0;
        while (priceBudget > 0 && n < countManufacturer) {
            // ищем производителя с минимальной ценой
            manufacturerStatus = manufacturerStatuses.get(countManufacturer-1);
            for (int i = n; i < countManufacturer ; ++i ) {
                if (manufacturerStatuses.get(i).getPrice() < manufacturerStatus.getPrice()) {
                    manufacturerStatus = manufacturerStatuses.get(i);
                }
            }
            n++;

            // Берем от производителя сколько сможем (стоимость произведенной продукции минус то, что успели купить
            // раньше другие покупатели)
            double summ = Math.min(priceBudget,
                    manufacturerStatus.getProductCount() * manufacturerStatus.getPrice()
                            - manufacturerStatus.getIncome());
            priceBudget -= summ;

            manufacturerStatus.setIncome( manufacturerStatus.getIncome() + summ);
        }

        n = 0;
        while (assortmentBudget > 0 && n < countManufacturer) {
            // ищем производителя с максимальным ассортиментом
            manufacturerStatus = manufacturerStatuses.get(countManufacturer-1);
            for (int i = n; i < countManufacturer ; ++i ) {
                if (manufacturerStatuses.get(i).getAssortment() > manufacturerStatus.getAssortment()) {
                    manufacturerStatus = manufacturerStatuses.get(i);
                }
            }
            n++;

            // Берем от производителя сколько сможем (стоимость произведенной продукции минус то, что успели купить
            // раньше другие покупатели)
            double summ = Math.min(assortmentBudget,
                    manufacturerStatus.getProductCount() * manufacturerStatus.getPrice()
                            - manufacturerStatus.getIncome());
            assortmentBudget -= summ;

            manufacturerStatus.setIncome( manufacturerStatus.getIncome() + summ);
        }
    }


}
