import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.javafaker.Faker;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @BeforeAll
    static void setUpAll () {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll () {
        SelenideLogger.removeListener("allure");
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
        String phoneNumber = DataGenerator.randomPhoneNumber();
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