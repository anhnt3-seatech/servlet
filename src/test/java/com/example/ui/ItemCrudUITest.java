package com.example.ui;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.*;

public class ItemCrudUITest {
    static String baseUrl = "http://localhost:8080/login-crud-servlet"; // adjust context if needed
    WebDriver driver;
    WebDriverWait wait;

    @BeforeAll
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setup() {
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--headless=new");
        opts.addArguments("--no-sandbox");
        opts.addArguments("--disable-dev-shm-usage");
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        driver = new ChromeDriver(opts);
        wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    }

    @AfterEach
    public void teardown() {
        if (driver != null) driver.quit();
    }

    @Test
    public void testCreateEditDeleteItem() {
        driver.get(baseUrl + "/items");

        // --- CREATE ---
        driver.findElement(By.linkText("Create new")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("name")));
        driver.findElement(By.id("name")).sendKeys("MyItem");
        driver.findElement(By.id("description")).sendKeys("Desc1");
        driver.findElement(By.id("saveBtn")).click();

        // verify created
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='MyItem']")));
        assertTrue(driver.getPageSource().contains("MyItem"));

        // --- EDIT ---
        driver.findElement(By.xpath("//tr[td/text()='MyItem']//a[text()='Edit']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("name")));
        WebElement name = driver.findElement(By.id("name"));
        name.clear();
        name.sendKeys("MyItemEdited");
        driver.findElement(By.id("saveBtn")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='MyItemEdited']")));
        assertTrue(driver.getPageSource().contains("MyItemEdited"));

        // --- DELETE ---
        driver.findElement(By.xpath("//tr[td/text()='MyItemEdited']//a[text()='Delete']")).click();
        // confirm alert (there is confirm in link onclick)
        // The delete link uses confirm() which the browser handles; headless still does confirm - Selenium dismisses? 
        // If confirm is present, handle alert:
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
        } catch (TimeoutException te) {
            // no alert -> continue
        }

        // back to list -> ensure not present
        // short wait for redirect
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("table")));
        assertFalse(driver.getPageSource().contains("MyItemEdited"));
    }
}