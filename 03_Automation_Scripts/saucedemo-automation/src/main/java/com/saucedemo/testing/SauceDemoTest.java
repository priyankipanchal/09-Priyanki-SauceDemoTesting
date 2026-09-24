package com.saucedemo.testing;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class SauceDemoTest {

    WebDriver driver;
    WebDriverWait wait;

    public static void main(String[] args) {
        SauceDemoTest test = new SauceDemoTest();

        // 1. Run Valid Login Test
        System.out.println("Starting Test 1: Valid Login");
        test.setUp();
        test.testValidLogin();
        test.tearDown();

        // 2. Run Locked Out User Test
        System.out.println("Starting Test 2: Locked Out User");
        test.setUp();
        test.testLockedOutUser();
        test.tearDown();

        // 3. Run Add To Cart Test
        System.out.println("Starting Test 3: Add to Cart");
        test.setUp();
        test.testAddToCart();
        test.tearDown();

        // 4. Run Cart Item Verification Test
        System.out.println("Starting Test 4: Cart Verification");
        test.setUp();
        test.testCartItemVerification();
        test.tearDown();

        // 5. Run End-to-End Checkout Flow Test
        System.out.println("Starting Test 5: End to End Checkout");
        test.setUp();
        test.testEndToEndCheckout();
        test.tearDown();

        System.out.println("\n=======================================================");
        System.out.println(">>> ALL 5 AUTOMATED TESTS PASSED SUCCESSFULLY! <<<");
        System.out.println("=======================================================");
    }

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-notifications");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com/");
    }

    private void loginAsStandardUser() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name"))).clear();
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        wait.until(ExpectedConditions.urlContains("inventory.html"));
    }

    // 1. Valid Login Test
    @Test(priority = 1)
    public void testValidLogin() {
        loginAsStandardUser();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("inventory.html"), "Valid Login Failed!");
        System.out.println("-> Test 1 Passed!");
    }

    // 2. Locked Out User Test
    @Test(priority = 2)
    public void testLockedOutUser() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name"))).clear();
        driver.findElement(By.id("user-name")).sendKeys("locked_out_user");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        WebElement errorElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']")));
        Assert.assertTrue(errorElement.getText().contains("Sorry, this user has been locked out."),
                "Locked user error not shown!");
        System.out.println("-> Test 2 Passed!");
    }

    // 3. Add Product to Cart Test
    @Test(priority = 3)
    public void testAddToCart() {
        loginAsStandardUser();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-sauce-labs-backpack"))).click();
        WebElement cartBadge = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("shopping_cart_badge")));
        Assert.assertEquals(cartBadge.getText(), "1", "Cart badge count is incorrect!");
        System.out.println("-> Test 3 Passed!");
    }

    // 4. Cart Page Verification Test
    @Test(priority = 4)
    public void testCartItemVerification() {
        loginAsStandardUser();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-sauce-labs-backpack"))).click();
        driver.findElement(By.className("shopping_cart_link")).click();

        WebElement itemName = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("inventory_item_name")));
        Assert.assertEquals(itemName.getText(), "Sauce Labs Backpack", "Item in cart does not match!");
        System.out.println("-> Test 4 Passed!");
    }

    // 5. Complete Checkout Flow Test
    @Test(priority = 5)
    public void testEndToEndCheckout() {
        loginAsStandardUser();

        // Add Product
        wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-sauce-labs-backpack"))).click();

        // Navigate directly to Step One URL
        driver.get("https://www.saucedemo.com/checkout-step-one.html");

        // Fill Customer Details
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name"))).sendKeys("Test");
        driver.findElement(By.id("last-name")).sendKeys("Student");
        driver.findElement(By.id("postal-code")).sendKeys("390001");

        // Continue Button
        WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("continue")));
        continueBtn.click();

        // Finish Button
        WebElement finishBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("finish")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", finishBtn);

        // Verification
        WebElement completeHeader = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("complete-header")));
        Assert.assertEquals(completeHeader.getText(), "Thank you for your order!", "Checkout failed!");
        System.out.println("-> Test 5 Passed!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception ignored) {
            }
        }
    }
}