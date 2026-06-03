package com.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.time.Duration;
import org.testng.annotations.DataProvider;
public class AppTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://opensource-demo.orangehrmlive.com/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        System.out.println("Browser opened");
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
        System.out.println("Browser closed");
    }

    @Test
    public void loginTest() {
        WebElement username = wait.until(ExpectedConditions
            .visibilityOfElementLocated(By.name("username")));
        username.sendKeys("Admin");

        driver.findElement(By.name("password")).sendKeys("admin123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        wait.until(ExpectedConditions.titleContains("OrangeHRM"));
        Assert.assertEquals(driver.getTitle(), "OrangeHRM");
        System.out.println("Login test passed");
    }

    @Test
    public void wrongPasswordTest() {
        WebElement username = wait.until(ExpectedConditions
            .visibilityOfElementLocated(By.name("username")));
        username.sendKeys("Admin");

        driver.findElement(By.name("password")).sendKeys("wrongpassword");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        WebElement error = wait.until(ExpectedConditions
            .visibilityOfElementLocated(By.cssSelector(".oxd-alert-content-text")));
        Assert.assertTrue(error.isDisplayed());
        System.out.println("Wrong password test passed: " + error.getText());
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
    WebElement usernameField = wait.until(ExpectedConditions
        .visibilityOfElementLocated(By.name("username")));
    usernameField.sendKeys(username);

    driver.findElement(By.name("password")).sendKeys(password);
    driver.findElement(By.cssSelector("button[type='submit']")).click();

    if (shouldPass) {
        wait.until(ExpectedConditions.titleContains("OrangeHRM"));
        Assert.assertEquals(driver.getTitle(), "OrangeHRM");
        System.out.println("Login passed for: " + username);
    } else {
        WebElement error = wait.until(ExpectedConditions
            .visibilityOfElementLocated(By.cssSelector(".oxd-alert-content-text")));
        Assert.assertTrue(error.isDisplayed());
        System.out.println("Login failed as expected for: " + username);
    }
}
}