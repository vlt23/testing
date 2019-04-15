package es.codeurjc.test.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class WebAppTest {

    private WebDriver driver;

    @BeforeClass
    public static void setupClass() {
        WebDriverManager.firefoxdriver().setup();
        WebApp.start();
    }

    @AfterClass
    public static void teardownClass() {
        WebApp.stop();
    }

    @Before
    public void setupTest() {
        driver = new FirefoxDriver();
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void test() {
        driver.get("http://localhost:8080/");

        String newTitle = "MessageTitle";
        String newBody = "MessageBody";

        driver.findElement(By.id("title-input")).sendKeys(newTitle);
        driver.findElement(By.id("body-input")).sendKeys(newBody);

        driver.findElement(By.id("submit")).click();

        String title = driver.findElement(By.id("title")).getText();
        String body = driver.findElement(By.id("body")).getText();

        assertThat(title).isEqualTo(newTitle);
        assertThat(body).isEqualTo(newBody);
    }

}
