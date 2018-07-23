package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class RandomTest extends BaseTest {

    @Test
    public void shouldPerformSearch() {
        driver.get("http://amazon.de/");
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("iphone" + Keys.RETURN);
        wait.until(visibilityOf(driver.findElement(By.id("resultsCol"))));
    }

}
