package com.automation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class AppTest {

    @Test
    public void openGoogle() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");
        System.out.println("Title is: " + driver.getTitle());
        driver.quit();
    }
}