package ru.itis.vkr2023.concurentgame.service.buyer;

import ru.itis.vkr2023.concurentgame.model.Buyer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BuyerFactory {

    private static String QUESTIONNAIRE_NAME = "questionnaire.csv";

    public static List<Buyer> loadFromCSV() {
        List<Buyer> buyers = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(QUESTIONNAIRE_NAME));

            for (int i = 1; i < lines.size(); ++i) {
                if (lines.get(i).trim().isEmpty()) continue;
                System.out.println(lines.get(i));

                String[] data = lines.get(i).split(";");
                // data: id; createDate; modifyDate; fio; group; q1; q2; q3; q4; q5; q6
                Buyer buyer = new Buyer(
                        Long.parseLong(data[0]),
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data[1]),
                        data[3], data[4],
                        Integer.parseInt(data[5]),
                        Integer.parseInt(data[6]),
                        Integer.parseInt(data[7]),
                        Integer.parseInt(data[8]),
                        Integer.parseInt(data[9]),
                        Integer.parseInt(data[10])
                );
                buyers.add(buyer);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return buyers;
    }

}
