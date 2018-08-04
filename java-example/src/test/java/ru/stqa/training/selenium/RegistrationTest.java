package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class RegistrationTest extends BaseTest {

    private String url = "http://localhost/litecart/";

    @Test
    public void shouldRegisterSuccessfully() {
        String firstName = "Test";
        String lastName = "Account";
        String address1 = "Test Street 3";
        String postCode = "12345";
        String city = "New York";
        String zone = "New Jersey";
        String country = "United States";
        String email = "test.account+" + System.currentTimeMillis() + "@gmail.com";
        String phone = "+1234567890";
        String password = "password";

        driver.get(url);
        driver.findElement(By.cssSelector("#box-account-login a")).click();
        wait.until(presenceOfElementLocated(By.id("create-account")));
        driver.findElement(By.cssSelector("input[name=firstname]")).sendKeys(firstName);
        driver.findElement(By.cssSelector("input[name=lastname]")).sendKeys(lastName);
        driver.findElement(By.cssSelector("input[name=address1]")).sendKeys(address1);
        driver.findElement(By.cssSelector("input[name=postcode]")).sendKeys(postCode);
        driver.findElement(By.cssSelector("input[name=city]")).sendKeys(city);

        Select citySelector = new Select(driver.findElement(By.className("select2-hidden-accessible")));
        citySelector.selectByVisibleText(country);

        Select zoneSelector = new Select(driver.findElement(By.cssSelector("select[name=zone_code]")));
        zoneSelector.selectByVisibleText(zone);

        driver.findElement(By.cssSelector("input[name=email]")).sendKeys(email);
        driver.findElement(By.cssSelector("input[name=phone]")).sendKeys(phone);
        driver.findElement(By.cssSelector("input[name=password]")).sendKeys(password);
        driver.findElement(By.cssSelector("input[name=confirmed_password]")).sendKeys(password);
        driver.findElement(By.cssSelector("button[type=submit]")).click();

        wait.until(presenceOfElementLocated(By.linkText("Logout"))).click();
        wait.until(presenceOfElementLocated(By.cssSelector("input[name=email]"))).sendKeys(email);
        driver.findElement(By.cssSelector("input[name=password]")).sendKeys(password);
        driver.findElement(By.cssSelector("button[name=login]")).click();

        wait.until(presenceOfElementLocated(By.linkText("Logout"))).click();
    }

}
