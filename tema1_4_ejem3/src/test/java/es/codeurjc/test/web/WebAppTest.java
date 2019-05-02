package es.codeurjc.test.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.remote.DesiredCapabilities.firefox;

public class WebAppTest {

    private static String sutURL;
    private static String eusURL;

    private WebDriver driver;

    @BeforeClass
    public static void setupClass() {

        WebApp.start();

        String sutHost = System.getenv("ET_SUT_HOST");
        if (sutHost == null) {
            sutURL = "http://localhost:8080/";
        } else {
            sutURL = "http://" + sutHost + ":8080/";
        }
        System.out.println("App url: " + sutURL);

        eusURL = System.getenv("ET_EUS_API");
        if (eusURL == null) {
            WebDriverManager.firefoxdriver().setup();
        }
    }

    @Before
    public void setupTest() throws MalformedURLException {
        //String eusURL = System.getenv("ET_EUS_API");
        if (eusURL == null) {
            // Local Firefox
            driver = new FirefoxDriver();
        } else {
            // Selenium Grid in ElasTest
            driver = new RemoteWebDriver(new URL(eusURL), firefox());
        }
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }

        WebApp.stop();
    }

    @Test
    public void test() throws InterruptedException {

        driver.get(sutURL);

        String newTitle = "MessageTitle";
        String newBody = "MessageBody";

        Thread.sleep(3000);

        driver.findElement(By.id("title-input")).sendKeys(newTitle);
        driver.findElement(By.id("body-input")).sendKeys(newBody);

        Thread.sleep(3000);

        driver.findElement(By.id("submit")).click();

        Thread.sleep(3000);

        String title = driver.findElement(By.id("title")).getText();
        String body = driver.findElement(By.id("body")).getText();

        assertThat(title).isEqualTo(newTitle);
        assertThat(body).isEqualTo(newBody);

        Thread.sleep(10000);
    }

}
