package com.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class AppTest {

    @Test
    public void loginTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://opensource-demo.orangehrmlive.com/");
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Login
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        username.sendKeys("Admin");
        
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("admin123");
        
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();
        
        // Wait for dashboard to load
        wait.until(ExpectedConditions.titleContains("OrangeHRM"));
        
        // ASSERTION - verify login was successful
        String actualTitle = driver.getTitle();
        String expectedTitle = "OrangeHRM";
        
        Assert.assertEquals(actualTitle, expectedTitle);
        System.out.println("Login successful. Title verified: " + actualTitle);
        
        driver.quit();
    }
    
    @Test
    public void wrongPasswordTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://opensource-demo.orangehrmlive.com/");
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Login with wrong password
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        username.sendKeys("Admin");
        
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("wrongpassword");
        
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();
        
        // Verify error message appears
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector(".oxd-alert-content-text")));
        
        Assert.assertTrue(errorMessage.isDisplayed());
        System.out.println("Error message verified: " + errorMessage.getText());
        
        driver.quit();
    }
}