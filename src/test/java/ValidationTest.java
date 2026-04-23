import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidationTest {

    private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void teardown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldShowErrorIfFieldIsEmpty() {
        driver.get("http://localhost:7777");

        driver.findElement(By.cssSelector("button"))
                .click();

        String error = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"))
                .getText();

        assertEquals("Поле обязательно для заполнения", error.trim());

    }

    @Test
    void shouldShowErrorIfNameEnglish() {
        driver.get("http://localhost:7777");

        driver.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Andrey");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input"))
                .sendKeys("+79999999999");
        driver.findElement(By.cssSelector("[data-test-id='agreement']"))
                .click();
        driver.findElement(By.cssSelector("button"))
                .click();
        String error = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"))
                .getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", error.trim());

    }

    @Test
    void shouldShowErrorIfNumberInvalid() {
        driver.get("http://localhost:7777");

        driver.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Андрей");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input"))
                .sendKeys("89999999999");
        driver.findElement(By.cssSelector("[data-test-id='agreement']"))
                .click();
        driver.findElement(By.cssSelector("button"))
                .click();
        String error = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"))
                .getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", error.trim());
    }

    @Test
    void shouldShowErrorIfCheckboxNotClicked() {

        driver.get("http://localhost:7777");

        driver.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Андрей");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input"))
                .sendKeys("89999999999");

        driver.findElement(By.cssSelector("button"))
                .click();
        boolean isInvalid = driver.findElement(By.cssSelector("[data-test-id='agreement']"))
                .getAttribute("class")
                .contains("input_invalid");
        assertEquals(false, isInvalid);
    }

}
