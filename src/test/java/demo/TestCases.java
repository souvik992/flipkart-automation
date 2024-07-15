package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.base.Equivalence.Wrapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.time.Duration;

// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;
    private Wrappers Wrappers;

    /*
     * TODO: Write your tests here with testng @Test annotation.
     * Follow `testCase01` `testCase02`... format or what is provided in
     * instructions
     */
    @Test
    public void testCase01() {
        try {
            System.out.println("TestCase 01 Started...........");
            Wrappers.openUrl("https://flipkart.com/");
            System.out.println("Page Load successfull!");
            Wrappers.type(By.xpath("//input[@type='text']"), "Washing Machine");
            System.out.println("Search successfull!");
            List<WebElement> sortOptions = Wrappers.findElements(By.xpath("//div[@class='zg-M3Z']"));
            String expectedOption = "Popularity";
            for (WebElement sortOption : sortOptions) {
                String currentOption = Wrappers.getText(sortOption);
                if (currentOption.equals(expectedOption)) {
                    System.out.println("Sort By: " + currentOption);
                    Wrappers.click(sortOption);
                    break;
                } else {
                    System.out.println("sort option not found! " + currentOption);
                }
            }

            Thread.sleep(3000);

            WebElement productsSection = Wrappers.findElement(By.xpath("//div[@class='DOjaWF gdgoEp']"));
            List<WebElement> productRating = Wrappers.findChildElements(productsSection,
                    By.xpath(".//div[@class='XQDdHH']"));
            float expectedRating = 5;
            int count = 0;
            for (WebElement rating : productRating) {
                float currentRating = Float.parseFloat(Wrappers.getText(rating));
                System.out.println(currentRating);
                if (currentRating <= expectedRating) {
                    count += 1;
                }
            }
            System.out.println(count);

            Thread.sleep(5000);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCase02() {
        try {
            System.out.println("TestCase 02 Started..............");
            Wrappers.openUrl("https://flipkart.com/");
            System.out.println("Page Load successfull!");
            Wrappers.type(By.xpath("//input[@type='text']"), "iPhone");
            System.out.println("Search successfull!");

            Thread.sleep(5000);

            // WebElement productsSection =
            // Wrappers.findElement(By.xpath("//div[@class='DOjaWF gdgoEp']"));
            List<WebElement> products = Wrappers.findElements(By.xpath("//div[@class='tUxRFH']"));
            int expectedDiscount = 17;
            for (WebElement product : products) {
                String productDiscount = Wrappers
                        .getText(Wrappers.findChildElement(product, By.xpath(".//div[@class='UkUFwK']/span")));
                String productTitle = Wrappers
                        .getText(Wrappers.findChildElement(product, By.xpath(".//div[@class='KzDlHZ']")));
                int currentDiscount = Wrappers.extractNumber(productDiscount);

                if (currentDiscount > expectedDiscount) {
                    System.out.print(productTitle);
                    System.out.println(productDiscount);
                }
            }

            Thread.sleep(5000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCase03() {
        try {
            System.out.println("TestCase 03 Started..............");
            Wrappers.openUrl("https://flipkart.com/");
            System.out.println("Page Load successfull!");
            Wrappers.type(By.xpath("//input[@type='text']"), "Coffee Mug");
            System.out.println("Search successfull!");

            Thread.sleep(5000);

            WebElement ratingOptions = Wrappers.findElement(By.xpath("//div[@class='SDsN9S']"));
            List<WebElement> ratings = Wrappers.findChildElements(ratingOptions,
                    By.xpath(".//div[@class='ewzVkT _3DvUAf']"));
            String expectedRating = "4★ & above";
            for (WebElement rating : ratings) {
                String currentRating = Wrappers.getText(rating);
                WebElement checkBox = Wrappers.findChildElement(rating, By.xpath(".//div[@class='QCKZip hpLdC3']"));
                if (currentRating.equals(expectedRating)) {

                    System.out.println("4 plus rating selected");
                    Wrappers.click(checkBox);
                    break;
                }
            }

            Thread.sleep(3000);

            // Find all products
            List<WebElement> products = Wrappers.findElements(By.xpath("//div[@class='slAVV4']"));

            // Extract product details (title, image URL, and number of reviews)
            List<WebElement> productTitles = new ArrayList<>();
            List<WebElement> productImages = new ArrayList<>();
            List<Integer> productReviews = new ArrayList<>();

            for (WebElement product : products) {

                WebElement titleElement = product.findElement(By.xpath(".//a[@class='wjcEIp']"));
                WebElement imageElement = product.findElement(By.xpath(".//img"));
                WebElement reviewsElement = product.findElement(By.xpath(".//span[@class='Wphh3N']"));

                productTitles.add(titleElement);
                productImages.add(imageElement);
                productReviews.add(Wrappers.extractNumber(Wrappers.getText(reviewsElement)));
            }

            // Sort products by number of reviews in descending order
            List<Integer> sortedIndices = new ArrayList<>();
            for (int i = 0; i < productReviews.size(); i++) {
                sortedIndices.add(i);
            }
            sortedIndices.sort(Comparator.comparingInt(productReviews::get).reversed());

            // Print the title and image URL of the top 5 products
            for (int i = 0; i < 5; i++) {
                int index = sortedIndices.get(i);
                System.out.println("Title: " + Wrappers.getText(productTitles.get(index)));
                System.out.println("Image URL: " + productImages.get(index).getAttribute("src"));
            }

            Thread.sleep(5000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Do not change the provided methods unless necessary, they will help in
     * automation and assessment
     */
    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        driver = new ChromeDriver(options);

        // driver.manage().window().maximize();

        Wrappers = new Wrappers(driver, Duration.ofSeconds(10));
    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();

    }
}