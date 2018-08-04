package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class ProductDetailsTest extends BaseTest {

    private String url = "http://localhost/litecart/";

    private By mainDiv = By.id("main");

    private By nameList = By.cssSelector("#box-campaigns .name");

    private By regularPriceList = By.cssSelector("#box-campaigns .regular-price");

    private By campaignPriceList = By.cssSelector("#box-campaigns .campaign-price");

    private By productDetailsContent = By.id("box-product");

    private By nameDetails = By.cssSelector("#box-product h1");

    private By regularPriceDetails = By.cssSelector(".information .regular-price");

    private By campaignPriceDetails = By.cssSelector(".information .campaign-price");

    @Test
    public void checkProductDetails() {
        driver.get(url);
        wait.until(presenceOfElementLocated(mainDiv));

        //main page
        String listName = driver.findElement(nameList).getText();
        String listRegularPrice = driver.findElement(regularPriceList).getText();
        String listCampaignPrice = driver.findElement(campaignPriceList).getText();
        List<String> listRegularPriceRGB = getRGBValues(regularPriceList);
        String listRegularPriceTag = driver.findElement(regularPriceList).getTagName();
        List<String> listCampaignPriceRGB = getRGBValues(campaignPriceList);
        String listCampaignPriceTag = driver.findElement(campaignPriceList).getTagName();
        double listRegularPriceFont = getFontSize(regularPriceList);
        double listCampaignPriceFont = getFontSize(campaignPriceList);

        driver.findElement(nameList).click();
        wait.until(presenceOfElementLocated(productDetailsContent));

        //details page
        String detailsName = driver.findElement(nameDetails).getText();
        String detailsRegularPrice = driver.findElement(regularPriceDetails).getText();
        String detailsCampaignPrice = driver.findElement(campaignPriceDetails).getText();
        List<String> detailsRegularPriceRGB = getRGBValues(regularPriceDetails);
        String detailsRegularPriceTag = driver.findElement(regularPriceDetails).getTagName();
        List<String> detailsCampaignPriceRGB = getRGBValues(campaignPriceDetails);
        String detailsCampaignPriceTag = driver.findElement(campaignPriceDetails).getTagName();
        double detailsRegularPriceFont = getFontSize(regularPriceDetails);
        double detailsCampaignPriceFont = getFontSize(campaignPriceDetails);

        assertEquals(listName, detailsName);
        assertEquals(listRegularPrice, detailsRegularPrice);
        assertEquals(listCampaignPrice, detailsCampaignPrice);
        assertEquals("s", listRegularPriceTag);
        assertEquals("s", detailsRegularPriceTag);
        assertEquals(listRegularPriceRGB.get(0), listRegularPriceRGB.get(1), listRegularPriceRGB.get(2));
        assertEquals(detailsRegularPriceRGB.get(0), detailsRegularPriceRGB.get(1), detailsRegularPriceRGB.get(2));
        assertEquals("strong", listCampaignPriceTag);
        assertEquals("strong", detailsCampaignPriceTag);
        assertEquals(String.valueOf(0), listCampaignPriceRGB.get(1), listCampaignPriceRGB.get(2));
        assertEquals(String.valueOf(0), detailsCampaignPriceRGB.get(1), detailsCampaignPriceRGB.get(2));
        assertTrue(listCampaignPriceFont > listRegularPriceFont);
        assertTrue(detailsCampaignPriceFont > detailsRegularPriceFont);
    }

    private List<String> getRGBValues(By locator) {
        String rgBColor = driver.findElement(locator)
                .getCssValue("color")
                .replaceAll("[^0-9]+", " ");

        return Arrays.asList(rgBColor.trim().split(" "));
    }

    private double getFontSize(By locator) {
        String font = driver.findElement(locator)
                .getCssValue("font-size")
                .replaceAll("[^\\d.]+", "");

        return Double.valueOf(font);

    }
}
