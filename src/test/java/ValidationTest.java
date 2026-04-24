import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
        driver.get("http://localhost:7777");
    }

    @AfterEach
    void teardown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldShowErrorIfNameIncorrect() {

        driver.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Andrey");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input"))
                .sendKeys("+79999999999");
        driver.findElement(By.cssSelector("[data-test-id='agreement']"))
                .click();
        driver.findElement(By.cssSelector("button"))
                .click();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"))
                        .getText().trim());
        assertTrue(driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"))
                .isDisplayed());

    }

    @Test
    void shouldBeFailedIfFieldNameIsEmpty() {

        driver.findElement(By.cssSelector("[data-test-id='phone'] input"))
                .sendKeys("+79999999999");
        driver.findElement(By.cssSelector("[data-test-id='agreement']"))
                .click();
        driver.findElement(By.cssSelector("button"))
                .click();
        assertEquals("Поле обязательно для заполнения",
                driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"))
                        .getText().trim());
        assertTrue(driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"))
                .isDisplayed());
    }

    @Test
    void shouldShowErrorIfNumberInvalid() {

        driver.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Андрей");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input"))
                .sendKeys("89999999999");
        driver.findElement(By.cssSelector("[data-test-id='agreement']"))
                .click();
        driver.findElement(By.cssSelector("button"))
                .click();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"))
                        .getText().trim());
        assertTrue(driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"))
                .isDisplayed());
    }

    @Test
    void shouldBeErrorIfFieldPhoneIsEmpty() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Андрей");
        driver.findElement(By.cssSelector("[data-test-id='agreement']"))
                .click();
        driver.findElement(By.cssSelector("button"))
                .click();
        assertEquals("Поле обязательно для заполнения",
                driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"))
                        .getText().trim());
        assertTrue(driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"))
                .isDisplayed());

    }

    @Test
    void shouldShowErrorIfCheckboxNotClicked() {

        driver.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Андрей");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input"))
                .sendKeys("+79999999999");
        driver.findElement(By.cssSelector("button"))
                .click();
        assertTrue(driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid"))
                .isDisplayed());
    }

}
