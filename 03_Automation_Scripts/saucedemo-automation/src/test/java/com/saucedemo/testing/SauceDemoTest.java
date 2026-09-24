package com.saucedemo.testing;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SauceDemoTest {

    WebDriver driver;
    WebDriverWait wait;

    public static void main(String[] args) {
        SauceDemoTest runner = new SauceDemoTest();

        // 1. Valid Login
        System.out.println("Starting Test 1: Valid Login");
        runner.initDriver();
        runner.testValidLogin();
        runner.closeDriver();

        // 2. Locked Out User
        System.out.println("Starting Test 2: Locked Out User");
        runner.initDriver();
        runner.testLockedOutUser();
        runner.closeDriver();

        // 3. Add to Cart
        System.out.println("Starting Test 3: Add to Cart");
        runner.initDriver();
        runner.testAddToCart();
        runner.closeDriver();

        // 4. Cart Verification
        System.out.println("Starting Test 4: Cart Verification");
        runner.initDriver();
        runner.testCartItemVerification();
        runner.closeDriver();

        // 5. End to End Checkout
        System.out.println("Starting Test 5: End to End Checkout");
        runner.initDriver();
        runner.testEndToEndCheckout();
        runner.closeDriver();

        System.out.println("\n=======================================================");
        System.out.println(">>> ALL 5 AUTOMATED TESTS PASSED SUCCESSFULLY! <<<");
        System.out.println("=======================================================");
    }

    public void initDriver() {
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

    public void testValidLogin() {
        loginAsStandardUser();
        String currentUrl = driver.getCurrentUrl();
        if (!currentUrl.contains("inventory.html")) {
            throw new AssertionError("Valid Login Failed!");
        }
        System.out.println("-> Test 1 Passed!");
    }

    public void testLockedOutUser() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name"))).clear();
        driver.findElement(By.id("user-name")).sendKeys("locked_out_user");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        WebElement errorElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']")));
        if (!errorElement.getText().contains("Sorry, this user has been locked out.")) {
            throw new AssertionError("Locked user error not shown!");
        }
        System.out.println("-> Test 2 Passed!");
    }

    public void testAddToCart() {
        loginAsStandardUser();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-sauce-labs-backpack"))).click();
        WebElement cartBadge = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("shopping_cart_badge")));
        if (!cartBadge.getText().equals("1")) {
            throw new AssertionError("Cart badge count is incorrect!");
        }
        System.out.println("-> Test 3 Passed!");
    }

    public void testCartItemVerification() {
        loginAsStandardUser();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-sauce-labs-backpack"))).click();
        driver.findElement(By.className("shopping_cart_link")).click();

        WebElement itemName = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("inventory_item_name")));
        if (!itemName.getText().equals("Sauce Labs Backpack")) {
            throw new AssertionError("Item in cart does not match!");
        }
        System.out.println("-> Test 4 Passed!");
    }

    public void testEndToEndCheckout() {
        loginAsStandardUser();

        // 1. Add Product
        wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-sauce-labs-backpack"))).click();

        // 2. Go to Step One page
        driver.get("https://www.saucedemo.com/checkout-step-one.html");

        // 3. Fill Customer Details
        WebElement firstName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")));
        firstName.clear();
        firstName.sendKeys("Test");

        WebElement lastName = driver.findElement(By.id("last-name"));
        lastName.clear();
        lastName.sendKeys("Student");

        WebElement postalCode = driver.findElement(By.id("postal-code"));
        postalCode.clear();
        postalCode.sendKeys("390001");

        // 4. Click Continue using JavascriptExecutor (guarantees click trigger)
        WebElement continueBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("continue")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", continueBtn);

        // 5. Wait for Step Two page to load
        wait.until(ExpectedConditions.urlContains("checkout-step-two.html"));

        // 6. Click Finish Button
        WebElement finishBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("finish")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", finishBtn);

        // 7. Wait for Complete page & Verification
        wait.until(ExpectedConditions.urlContains("checkout-complete.html"));
        WebElement completeHeader = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("complete-header")));
        if (!completeHeader.getText().equals("Thank you for your order!")) {
            throw new AssertionError("Checkout failed!");
        }
        System.out.println("-> Test 5 Passed!");
    }

    public void closeDriver() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception ignored) {
            }
        }
    }
}