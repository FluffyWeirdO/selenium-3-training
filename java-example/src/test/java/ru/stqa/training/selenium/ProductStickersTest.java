package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class ProductStickersTest extends BaseTest {

    private String url = "http://localhost:8080/litecart/";

    private By mainDiv = By.id("main");

    private By productsList = By.cssSelector("ul.products li.product");

    private By sticker = By.cssSelector(".sticker");

    @Test
    public void checkProductsHaveOneSticker() {
        driver.get(url);
        wait.until(presenceOfElementLocated(mainDiv));

        List<WebElement> products = driver.findElements(productsList);
        System.out.println("Number of products: " + products.size());

        for (WebElement element : products) {
            assertEquals(1, element.findElements(sticker).size());

            System.out.println("Product " + (products.indexOf(element) + 1) +
                    " has sticker " + element.findElement(sticker).getText());
        }
    }
}
