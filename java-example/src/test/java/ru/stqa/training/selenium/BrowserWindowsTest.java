package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class BrowserWindowsTest extends BaseTest {

    private String url = "http://localhost/litecart/admin";

    private String userName = "admin";

    private String password = "admin";

    private By externalLink = By.cssSelector("i.fa-external-link");

    @Test
    public void shouldOpenLinksInSeparateWindows() {
        logIn();

        driver.findElement(By.linkText("Countries")).click();
        wait.until(presenceOfElementLocated(By.linkText("Add New Country"))).click();
        wait.until(presenceOfElementLocated(externalLink));

        int currentLink = 0;

        while (currentLink < driver.findElements(externalLink).size()) {
            String originalWindow = driver.getWindowHandle();
            Set<String> existingWindows = driver.getWindowHandles();

            driver.findElements(externalLink).get(currentLink).click();
            String newWindow = wait.until(anyWindowOtherThan(existingWindows));

            driver.switchTo().window(newWindow);
            driver.close();
            driver.switchTo().window(originalWindow);

            currentLink++;
        }

    }

    private void logIn() {
        driver.get(url);
        driver.findElement(By.name("username")).sendKeys(userName);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("login")).click();
        wait.until(visibilityOf(driver.findElement(By.id("box-apps-menu-wrapper"))));

        assertTrue(driver.findElement(By.className("fa-database")).isDisplayed());
    }

    private ExpectedCondition<String> anyWindowOtherThan(Set<String> oldWindows) {
        return new ExpectedCondition<String>() {

            public String apply(WebDriver driver) {
                Set<String> handles = driver.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size() > 0 ? handles.iterator().next() : null;
            }
        };
    }
}
