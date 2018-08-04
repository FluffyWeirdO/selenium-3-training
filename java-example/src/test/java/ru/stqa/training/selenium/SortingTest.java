package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class SortingTest extends BaseTest {

    private String urlCountries = "http://localhost/litecart/admin/?app=countries&doc=countries";

    private String urlGeo = "http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones";

    private String userName = "admin";

    private String password = "admin";

    private By countryNameCell = By.cssSelector(".dataTable tr.row > td:nth-of-type(5)");

    private By zonesCell = By.cssSelector(".dataTable tr.row > td:nth-of-type(6)");

    private By zoneNameCell = By.cssSelector("#table-zones tr > td:nth-of-type(3)");

    private By geoCountryCell = By.cssSelector(".dataTable tr.row > td:nth-of-type(3) > a");

    private By selectedZone = By.cssSelector("#table-zones tr > td:nth-of-type(3) [selected] ");

    @Test
    public void checkCountriesAreSortedAlphabetically() {
        logIn(urlCountries);

        List<WebElement> countries = driver.findElements(countryNameCell);
        List<String> countryNames = new ArrayList<>();

        for (WebElement country : countries) {
            countryNames.add(country.getText());
            System.out.println(country.getText());
        }

        ArrayList<String> expectedSortedCountriesList = new ArrayList<>(countryNames);
        Collections.sort(expectedSortedCountriesList);

        assertEquals(expectedSortedCountriesList, countryNames);
    }

    @Test
    public void checkZonesAreOrderedAlphabetically() {
        logIn(urlCountries);

        List<WebElement> countryZones = driver.findElements(zonesCell);
        List<Integer> nonZeroZonesIndex = new ArrayList<>();

        for (WebElement zone : countryZones) {
            if (Integer.valueOf(zone.getText()) > 0) {
                nonZeroZonesIndex.add(countryZones.indexOf(zone));
            }
        }

        if (!nonZeroZonesIndex.isEmpty()) {
            for (Integer index : nonZeroZonesIndex) {
                driver.findElements(countryNameCell).get(index).findElement(By.cssSelector("a")).click();
                wait.until(presenceOfElementLocated(By.cssSelector("h2")));

                List<WebElement> zones = driver.findElements(zoneNameCell);
                //remove the last non-data row
                zones.remove(zones.size() - 1);

                List<String> zoneNames = new ArrayList<>();

                for (WebElement element : zones) {
                    zoneNames.add(element.getText());
                    System.out.println(element.getText());
                }

                ArrayList<String> sortedZones = new ArrayList<String>(zoneNames);
                Collections.sort(sortedZones);

                System.out.println("Number of zones: " + zoneNames.size());
                assertEquals(sortedZones, zoneNames);

                driver.navigate().back();
            }
        }
    }

    @Test
    public void checkGeoZonesAreOrderedAlphabetically() {
        logIn(urlGeo);

        int indexOfOpenedCountry = 0;
        List<WebElement> countries = driver.findElements(geoCountryCell);

        while (indexOfOpenedCountry < countries.size()) {
            driver.findElements(geoCountryCell).get(indexOfOpenedCountry).click();
            wait.until(presenceOfElementLocated(By.cssSelector("h2")));

            List<WebElement> zones = driver.findElements(selectedZone);
            List<String> zoneNames = new ArrayList<>();

            for (WebElement zone : zones) {
                zoneNames.add(zone.getText());
                System.out.println(zone.getText());
            }

            ArrayList<String> sortedZones = new ArrayList<>(zoneNames);
            Collections.sort(sortedZones);

            System.out.println("Number of selected zones: " + zoneNames.size());
            assertEquals(sortedZones, zoneNames);

            indexOfOpenedCountry++;
            driver.navigate().back();
        }

    }

    private void logIn(String url) {
        driver.get(url);
        driver.findElement(By.name("username")).sendKeys(userName);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("login")).click();
        wait.until(visibilityOf(driver.findElement(By.id("box-apps-menu-wrapper"))));

        assertTrue(driver.findElement(By.className("fa-database")).isDisplayed());
    }

}
