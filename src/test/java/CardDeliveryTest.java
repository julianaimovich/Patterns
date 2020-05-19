import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.io.IOException;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    private Faker faker;

    @BeforeEach
    void setUpAll() {
        faker = new Faker(new Locale("ru"));
    }

    @Test
    @DisplayName("Should request confirmation to postpone delivery date")
    void shouldRequestConfirmationToPostponeDeliveryDate () throws IOException {
        open("http://localhost:9999/");
        String city = DataGenerator.randomCity();
        $("[data-test-id=city] input").setValue(city);
        dataFiller();
        String fullName = DataGenerator.randomFullName();
        $("[data-test-id=name] input").setValue(fullName);
        String phoneNumber = faker.phoneNumber().phoneNumber();
        $("[data-test-id=phone] input").setValue(phoneNumber);
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $(withText("Успешно!")).waitUntil(visible, 15000);
        dataFiller();
        $$("button").find(exactText("Запланировать")).click();
        $(withText("Необходимо подтверждение")).should(appear);
        $$("button").find(exactText("Перепланировать")).click();
        $(withText("Успешно!")).waitUntil(visible, 15000);
    }

    public void dataFiller() {
        String date = DataGenerator.dateCalculating();
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL,"a");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
    }
}