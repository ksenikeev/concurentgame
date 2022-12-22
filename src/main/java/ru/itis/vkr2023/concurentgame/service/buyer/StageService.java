package ru.itis.vkr2023.concurentgame.service.buyer;

import org.springframework.stereotype.Service;
import ru.itis.vkr2023.concurentgame.model.Buyer;
import ru.itis.vkr2023.concurentgame.model.Game;
import ru.itis.vkr2023.concurentgame.model.ManufacturerStatus;
import java.util.List;

@Service
public class StageService {

    public void calculateStage(List<Buyer> buyers, List<ManufacturerStatus> manufacturerStatuses, Game game) {
        for (Buyer buyer : buyers) {
            calculateBuyer(buyer, manufacturerStatuses, game);
        }
    }

    public void calculateBuyer(Buyer buyer, List<ManufacturerStatus> manufacturerStatuses, Game game) {

        // доступный бюджет покупателя
        double budget = game.getBuyersBudget();

        // Расчитываем доли предпочтений на рекламу, цену, ассортимент
        double advertisementIndex =
                (buyer.getQuestion1() + buyer.getQuestion2() + buyer.getQuestion4() + buyer.getQuestion6()) * 0.05;

        double priceIndex = buyer.getQuestion5() * 0.2;

        double assortmentIndex = buyer.getQuestion3() * 0.2;


        double advertisement = (advertisementIndex / (advertisementIndex + priceIndex + assortmentIndex)) * budget;

        double price = (priceIndex / (advertisementIndex + priceIndex + assortmentIndex)) * budget;

        double assortment = (assortmentIndex / (advertisementIndex + priceIndex + assortmentIndex)) * budget;

        // перебираем текущие статусы производителей для совершения покупок в найденных пропорциях
        int countManufacturer = manufacturerStatuses.size();
        ManufacturerStatus manufacturerStatus = manufacturerStatuses.get(0);

        int n = 0;
        while (advertisement > 0) {
            // ищем производителя с максимальными затратами на рекламу
            for (int i = n; i < countManufacturer ; ++i ) {
                if (manufacturerStatuses.get(i).getAdvertisement() > manufacturerStatus.getAdvertisement()) {
                    manufacturerStatus = manufacturerStatuses.get(i);
                }
            }
            n++;

            // Берем от производителя сколько сможем (стоимость произведенной продукции минус то, что успели купить
            // раньше другие покупатели)
            double summ = Math.min(advertisement,
                    manufacturerStatus.getProductCount() * manufacturerStatus.getPrice()
                            - manufacturerStatus.getIncome());
            advertisement -= summ;

            manufacturerStatus.setIncome( manufacturerStatus.getIncome() - summ);
        }

        n = 0;
        while (price > 0) {
            // ищем производителя с минимальной ценой
            for (int i = n; i < countManufacturer ; ++i ) {
                if (manufacturerStatuses.get(i).getPrice() < manufacturerStatus.getPrice()) {
                    manufacturerStatus = manufacturerStatuses.get(i);
                }
            }
            n++;

            // Берем от производителя сколько сможем (стоимость произведенной продукции минус то, что успели купить
            // раньше другие покупатели)
            double summ = Math.min(price,
                    manufacturerStatus.getProductCount() * manufacturerStatus.getPrice()
                            - manufacturerStatus.getIncome());
            price -= summ;

            manufacturerStatus.setIncome( manufacturerStatus.getIncome() - summ);
        }

        n = 0;
        while (assortment > 0) {
            // ищем производителя с максимальным ассортиментом
            for (int i = n; i < countManufacturer ; ++i ) {
                if (manufacturerStatuses.get(i).getAssortment() > manufacturerStatus.getAssortment()) {
                    manufacturerStatus = manufacturerStatuses.get(i);
                }
            }
            n++;

            // Берем от производителя сколько сможем (стоимость произведенной продукции минус то, что успели купить
            // раньше другие покупатели)
            double summ = Math.min(assortment,
                    manufacturerStatus.getProductCount() * manufacturerStatus.getPrice()
                            - manufacturerStatus.getIncome());
            assortment -= summ;

            manufacturerStatus.setIncome( manufacturerStatus.getIncome() - summ);
        }


    }
}
