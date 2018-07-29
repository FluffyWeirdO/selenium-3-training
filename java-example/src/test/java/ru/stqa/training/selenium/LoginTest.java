package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.By;

import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class LoginTest extends BaseTest {

    private String url = "http://localhost:8080/litecart/admin";

    private String userName = "admin";

    private String password = "admin";

    @Test
    public void shouldLogIn() {
        driver.get(url);
        driver.findElement(By.name("username")).sendKeys(userName);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("login")).click();
        wait.until(visibilityOf(driver.findElement(By.id("box-apps-menu-wrapper"))));

        assertTrue(driver.findElement(By.className("fa-database")).isDisplayed());
    }

}
