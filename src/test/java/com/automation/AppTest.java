package com.automation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.time.Duration;

public class AppTest {

    WebDriver driver;
    LoginPage loginPage;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://opensource-demo.orangehrmlive.com/");
        loginPage = new LoginPage(driver);
        System.out.println("Browser opened");
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
        System.out.println("Browser closed");
    }

    @Test
    public void validLoginTest() {
        loginPage.login("Admin", "admin123");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleContains("OrangeHRM"));
        Assert.assertEquals(driver.getTitle(), "OrangeHRM");
        System.out.println("Valid login test passed");
    }

    @Test
    public void invalidLoginTest() {
        loginPage.login("Admin", "wrongpassword");
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Invalid"));
        System.out.println("Invalid login test passed: " + error);
    }

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][] {
            {"Admin", "admin123", true},
            {"Admin", "wrongpass", false},
            {"wronguser", "admin123", false}
        };
    }

    @Test(dataProvider = "loginData")
    public void dataDriverLoginTest(String username, String password, boolean shouldPass) {
        loginPage.login(username, password);
        if (shouldPass) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.titleContains("OrangeHRM"));
            Assert.assertEquals(driver.getTitle(), "OrangeHRM");
            System.out.println("Login passed for: " + username);
        } else {
            String error = loginPage.getErrorMessage();
            Assert.assertTrue(error.contains("Invalid"));
            System.out.println("Login failed as expected for: " + username);
        }
    }
}