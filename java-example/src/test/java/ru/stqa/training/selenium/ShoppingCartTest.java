package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class ShoppingCartTest extends BaseTest {

    private String url = "http://localhost/litecart/";

    @Test
    public void shouldAddAndRemoveProducts() {
        driver.get(url);

        By byCartItems = By.cssSelector("span.quantity");
        By bySizeSelect = By.cssSelector("select[name=options\\[Size\\]]");

        while (Integer.valueOf(driver.findElement(byCartItems).getText()) < 3) {
            driver.findElement(By.cssSelector("li.product")).click();
            int cartItems = Integer.valueOf(driver.findElement(byCartItems).getText());

            if (!driver.findElements(bySizeSelect).isEmpty()) {
                WebElement size = driver.findElement(bySizeSelect);
                Select sizeSelector = new Select(size);
                sizeSelector.selectByIndex(1);
            }

            wait.until(visibilityOfElementLocated(By.cssSelector("button[name=add_cart_product]"))).click();
            wait.until(textToBe(byCartItems, String.valueOf(cartItems + 1)));
            driver.findElement(By.id("logotype-wrapper")).click();
        }

        wait.until(visibilityOfElementLocated(By.partialLinkText("Checkout"))).click();

        while (!driver.findElements(By.cssSelector("#order_confirmation-wrapper tr")).isEmpty()) {
            WebElement amountCell = driver.findElement(By.cssSelector("tr.footer > td:nth-of-type(2)"));

            wait.until(elementToBeClickable(By.cssSelector("button[name=remove_cart_item]"))).click();
            wait.until(stalenessOf(amountCell));
        }
    }
}
