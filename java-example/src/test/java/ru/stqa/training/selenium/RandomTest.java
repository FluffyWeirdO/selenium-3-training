package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class RandomTest {

    private WebDriver driver;

    private WebDriverWait wait;

    @Before
    public void start() {
        System.setProperty("webdriver.chrome.driver","/Users/fluffyweirdo/selenium_drivers/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void shouldSearchForTrainings() {
        driver.get("http://amazon.de/");
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("iphone" + Keys.RETURN);
        wait.until(visibilityOf(driver.findElement(By.id("resultsCol"))));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}
