package com.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import java.time.Duration;

public class AppTest {

    @Test
    public void loginTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://opensource-demo.orangehrmlive.com/");
        
        // Wait up to 10 seconds for username field to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Find username field and type
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        username.sendKeys("Admin");
        
        // Find password field and type
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("admin123");
        
        // Find login button and click
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();
        
        // Print page title after login
        System.out.println("Page title is: " + driver.getTitle());
        
        driver.quit();
    }
}