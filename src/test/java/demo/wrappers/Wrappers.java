package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;


public class Wrappers {
    private WebDriver driver;
    private WebDriverWait wait;

    public Wrappers(WebDriver driver, Duration timeout) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, timeout);
    }

    public void openUrl(String url) {
        driver.get(url);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
    }

    public WebElement findElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public List<WebElement> findElements(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public WebElement findChildElement(WebElement parent, By childLocator){
        return parent.findElement(childLocator);
    }

    public List<WebElement> findChildElements(WebElement parent, By childLocator) {
        return parent.findElements(childLocator);
    }

    public void click(WebElement element) {
        element.click();
    }

    public void type(By locator, String text) {
        WebElement element = findElement(locator);
        element.clear();
        element.sendKeys(text + Keys.ENTER);
    }

    public String getText(WebElement element) {
        return element.getText();
    }

    public int extractNumber(String str) {
        StringBuilder number = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                number.append(c);
            }
        }
        if (number.length() > 0) {
            return Integer.parseInt(number.toString());
        } else {
            throw new IllegalArgumentException("No number found in the string");
        }
    }
}