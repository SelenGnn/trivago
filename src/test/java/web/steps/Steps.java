package web.steps;


import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import parser.Parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Steps {
    public static WebDriver driver;
    Parser parser = new Parser();
    WebDriverWait wait;
    final long SECOND_AS_MILLIS = 1000L;
    final String EMPTY_STRING = "";
    final String NEW_LINE_STRING = "\n";
    final String DOUBLE_QUOTE_STRING = "\"";

    @After
    public void tearDown() {
        driver.quit();
    }

    @Given("I open browser")
    public void openBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
        final String EMPTY_STRING = "";
        final String NEW_LINE_STRING = "\n";
        final String DOUBLE_QUOTE_STRING = "\"";

    }

    @When("I open \"([^\"]*)\" page")
    public void openPage(String pageKey) throws IOException, ParseException {
        parser.setPageKey(pageKey);
        driver.get(parser.getPageObject("urlKey"));
    }

    @And("I maximize browser")
    public void maximizeBrowser() {
        driver.manage().window().maximize();
    }

    @And("I click \"([^\"]*)\"")
    public void clickElement(String elementKey) throws IOException, ParseException {
        if (getElement(elementKey) != null) {
            getElement(elementKey).click();
        } else {
            driver.findElement(By.xpath("//*[text()='" + elementKey + "']")).click();
        }
    }

    public WebElement getElement(String elementKey) throws IOException, ParseException {
        String elementValue = parser.getElementKey(elementKey);
        By selector = bySelector(elementValue);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
    }

    public By bySelector(String selector) {
        if (selector.matches("^#[\\w-]+$")) {
            return By.id(selector.substring(1));
        } else if (selector.charAt(0) == '/' || selector.charAt(0) == '(' || selector.startsWith("./")) {
            return By.xpath(selector);
        } else {
            return By.cssSelector(selector);
        }
    }

    @And("I fill:")
    public void fillDataMap(Map<String, String> dataMap) throws IOException, ParseException {
        for (Map.Entry<String, String> item : dataMap.entrySet()) {
            getElement(item.getKey()).clear();
            getElement(item.getKey()).sendKeys(item.getValue());
        }
    }

    @And("I switch \"([^\"]*)\" page")
    public void switchPage(String pageKey) throws IOException, ParseException {
        parser.setPageKey(pageKey);
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        System.out.println("URL : " + driver.getCurrentUrl());
    }

    @And("I see \"([^\"]*)\" page")
    public void seePage(String pageKey) throws IOException, ParseException {
        parser.setPageKey(pageKey);
        Assert.assertEquals(driver.getCurrentUrl(), parser.getPageObject("urlKey"));
    }

    @And("I wait for {int} seconds")
    public void waitForSeconds(int seconds) throws InterruptedException {
        Thread.sleep(seconds * SECOND_AS_MILLIS);
    }

    @Then("I see \"([^\"]*)\" text \"([^\"]*)\"")
    public void iSeeText(String text, String elementKey) throws IOException, ParseException {
        String originalElementText = getElement(elementKey).getText();
        String modifiedElementText = originalElementText.replaceAll(NEW_LINE_STRING, EMPTY_STRING).replaceAll(DOUBLE_QUOTE_STRING, EMPTY_STRING);
        Assert.assertEquals(text, modifiedElementText);
    }

    @And("I press enter key to \"([^\"]*)\"")
    public void pressEnter(String elementKey) throws IOException, ParseException {
        getElement(elementKey).sendKeys(Keys.ENTER);
    }
}

