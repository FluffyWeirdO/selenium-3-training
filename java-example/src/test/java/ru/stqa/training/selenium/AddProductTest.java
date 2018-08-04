package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import java.io.File;

import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class AddProductTest extends BaseTest {

    private String url = "http://localhost/litecart/admin";

    private String userName = "admin";

    private String password = "admin";

    private File image = new File("src/test/resources/qa-icon.png");

    @Test
    public void shouldAddProduct() {
        String productName = "ProductName" + System.currentTimeMillis();
        String quantity = "15";
        String validFrom = "08/04/2018";
        String validTo = "08/04/2028";
        String keyword = "test keyword";
        String shortDescription = "Test Short";
        String description = "Test Description";
        String title = "Test Title";
        String purchasePrice = "156";
        String priceUsd = "180";
        String priceEur = "155";

        logIn();

        driver.findElement(By.linkText("Catalog")).click();
        driver.findElement(By.linkText("Add New Product")).click();
        wait.until(presenceOfElementLocated(By.id("tab-general")));

        driver.findElement(By.cssSelector("input[name=name\\[en\\]]")).sendKeys(productName);
        driver.findElement(By.cssSelector("input[name=quantity]")).sendKeys(quantity);
        driver.findElement(By.cssSelector("input[name=new_images\\[\\]]")).sendKeys(image.getAbsolutePath());
        driver.findElement(By.cssSelector("input[name=date_valid_from]")).sendKeys(validFrom);
        driver.findElement(By.cssSelector("input[name=date_valid_to]")).sendKeys(validTo);

        driver.findElement(By.linkText("Information")).click();
        wait.until(presenceOfElementLocated(By.cssSelector(".trumbowyg-editor")));

        Select manufacturer = new Select(driver.findElement(By.cssSelector("select[name=manufacturer_id]")));
        manufacturer.selectByIndex(0);

        driver.findElement(By.cssSelector("input[name=keywords]")).sendKeys(keyword);
        driver.findElement(By.cssSelector("input[name=short_description\\[en\\]]")).sendKeys(shortDescription);
        driver.findElement(By.cssSelector(".trumbowyg-textarea")).sendKeys(description);
        driver.findElement(By.cssSelector("input[name=head_title\\[en\\]]")).sendKeys(title);

        driver.findElement(By.linkText("Prices")).click();
        wait.until(presenceOfElementLocated(By.cssSelector("input[name=purchase_price]"))).sendKeys(purchasePrice);

        Select currency = new Select(driver.findElement(By.cssSelector("select[name=purchase_price_currency_code]")));
        currency.selectByVisibleText("Euros");

        driver.findElement(By.cssSelector("input[name=prices\\[USD\\]]")).sendKeys(priceUsd);
        driver.findElement(By.cssSelector("input[name=prices\\[EUR\\]]")).sendKeys(priceEur);
        driver.findElement(By.cssSelector("button[name=save]")).click();

        wait.until(presenceOfElementLocated(By.cssSelector(".dataTable")));

        assertTrue(driver.findElement(By.linkText(productName)).isDisplayed());
    }

    private void logIn() {
        driver.get(url);
        driver.findElement(By.name("username")).sendKeys(userName);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("login")).click();
        wait.until(visibilityOf(driver.findElement(By.id("box-apps-menu-wrapper"))));

        assertTrue(driver.findElement(By.className("fa-database")).isDisplayed());
    }
}

