import com.github.javafaker.Faker;
import org.apache.commons.io.FileUtils;
import ru.netology.model.CardDeliveryData;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataGenerator {
    private DataGenerator() {}

    public CardDeliveryData someInfo(String locale) throws IOException {

        Faker faker = new Faker(new Locale("ru"));

        return new CardDeliveryData(
                DataGenerator.randomCity(),
                DataGenerator.dateCalculating(),
                DataGenerator.randomFullName(),
                faker.phoneNumber().phoneNumber());
    }

    public static String dateCalculating() {
        LocalDate today = LocalDate.now();
        Random rn = new Random();
        int numberOfDays = 3 + rn.nextInt(10);
        LocalDate deliveryDate = today.plusDays(numberOfDays);
        String datePattern = "dd.MM.yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        String deliveryDateStr = formatter.format(deliveryDate);
        return deliveryDateStr;
    }

    public static String randomCity() throws IOException {
        List<String> cities = FileUtils.readLines(new File("src/test/resources/CitiesList.txt"), "utf-8");
        Random cn = new Random();
        int cityNumber = cn.nextInt(cities.size());
        String cityName = cities.get(cityNumber);
        return cityName;
    }

    static String randomFullName() {

        Faker faker = new Faker(new Locale("ru"));

        String randomLastName = faker.name().lastName();
        String randomFirstName = faker.name().firstName();
        String generatedFullName = randomLastName + " " + randomFirstName;
        return generatedFullName;
    }
}