package com.example.ui;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class ItemCrudPlaywrightTest {
    static Playwright playwright;
    static Browser browser;
    Page page;

    @BeforeAll
    static void setupAll() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
    }

    @AfterAll
    static void tearDownAll() {
        browser.close();
        playwright.close();
    }

    @BeforeEach
    void setup() {
        page = browser.newPage();
    }

    @AfterEach
    void cleanup() {
        page.close();
    }

    @Test
    void testLoginAndCreateItem() {
        page.navigate("http://localhost:8080/login-crud-servlet/login.jsp");

        // Login
        page.fill("#username", "admin");
        page.fill("#password", "123");
        page.click("#loginBtn");

        assertTrue(page.content().contains("Welcome"), "Login failed");

        // Navigate to CRUD page
        page.navigate("http://localhost:8080/login-crud-servlet/items");

        // Create Item
        page.click("a#addItemBtn");  // hoặc chỉ cần "#addItemBtn"
        page.waitForURL("**/items?action=create");
        
        //page.click("#addItemBtn");
        page.fill("#name", "Laptop");
        page.fill("#description", "Dell XPS 15");
        page.click("#saveBtn");

        assertTrue(page.content().contains("Laptop"), "Item not created");
    }

    @Test
    void testUpdateItem() {
        page.navigate("http://localhost:8080/login-crud-servlet/items");

        page.click("text=Edit"); // nút Edit đầu tiên
        page.fill("#name", "Laptop Updated");
        page.click("#saveBtn");

        assertTrue(page.content().contains("Laptop Updated"), "Item not updated");
    }

	/*
	 * @Test void testDeleteItem() {
	 * page.navigate("http://localhost:8080/login-crud-servlet/items");
	 * 
	 * page.click("text=Delete"); // nút Delete đầu tiên
	 * page.onDialog(Dialog::accept);
	 * 
	 * assertFalse(page.content().contains("Laptop Updated"), "Item not deleted"); }
	 */
}