package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.By;

import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class ListItemsTest extends BaseTest {

    private String url = "http://localhost/litecart/admin";

    private String userName = "admin";

    private String password = "admin";

    private By parentItems = By.cssSelector("ul#box-apps-menu li#app-");

    private By selectedParent = By.cssSelector("ul#box-apps-menu li#app-.selected");

    private By elementsAfterSelectedParent = By.cssSelector("ul#box-apps-menu li#app-.selected + li");

    private By selectedChild = By.cssSelector("ul#box-apps-menu li#app- ul.docs li.selected");

    private By elementsAfterSelectedChild = By.cssSelector("ul#box-apps-menu li#app- ul.docs li.selected + li");

    private By contentHeader = By.cssSelector("td#content h1");

    @Test
    public void clickAllMenuItems() {
        logIn();

        //open first menu item
        driver.findElement(parentItems).click();
        System.out.println("Selected parent element: " + driver.findElement(selectedParent).getText());
        System.out.println("Selected child element: " + driver.findElement(selectedChild).getText());
        wait.until(presenceOfElementLocated(contentHeader));

        assertTrue(driver.findElement(contentHeader).isDisplayed());

        //click on each child item and assert h1 is displayed
        openChildItems();

        //do the same for the remaining menu parent and child items
        while (!driver.findElements(elementsAfterSelectedParent).isEmpty()) {
            driver.findElements(elementsAfterSelectedParent).get(0).click();
            System.out.println("Selected parent element: " + driver.findElement(selectedParent).getText());
            wait.until(presenceOfElementLocated(contentHeader));

            assertTrue(driver.findElement(contentHeader).isDisplayed());

            openChildItems();
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

    private void openChildItems() {
        while (!driver.findElements(elementsAfterSelectedChild).isEmpty()) {
            driver.findElements(elementsAfterSelectedChild).get(0).click();
            System.out.println("Selected child element: " + driver.findElement(selectedChild).getText());
            wait.until(presenceOfElementLocated(contentHeader));

            assertTrue(driver.findElement(contentHeader).isDisplayed());
        }
    }

}
