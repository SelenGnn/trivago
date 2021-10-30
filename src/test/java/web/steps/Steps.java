package web.steps;


import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import parser.Parser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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


    @And("I wait for {int} seconds")
    public void waitForSeconds(int seconds) throws InterruptedException {
        Thread.sleep(seconds * SECOND_AS_MILLIS);
    }


    @And("I press enter key to \"([^\"]*)\"")
    public void pressEnter(String elementKey) throws IOException, ParseException {
        getElement(elementKey).sendKeys(Keys.ENTER);
    }


    @Then("I verify that the list of \"([^\"]*)\" displays all hotels featured in the article")
    public void iVerifyThatTheListOfDisplaysAllHotelsFeaturedInTheArticle(String elementKey) {
        final List<WebElement> results = driver.findElements(By.xpath("//*[@id=\"app\"]/div[3]/article/div[1]/div[2]/div[1]/div[3]/div/ol/li"));
        final List<WebElement> hotels = driver.findElements(By.className("destination-and-hotel-box"));
        if (results == hotels) {
            for (int j = 1; j <= hotels.size(); j++) {
                driver.findElement(By.xpath("(" + "//*[@class=\"destination-and-hotel-box\"])[" + j + "]")).isDisplayed();
            }
        }
    }

    @And("I verify that there are no broken links in the article")
    public void iVerifyThatThereAreNoBrokenLinksInTheArticle() {
        driver.getCurrentUrl();
        List<WebElement> links = driver.findElements(By.tagName("a"));
        Iterator<WebElement> it = links.iterator();
        while (it.hasNext()) {
            String url = it.next().getAttribute("href");
            System.out.println(url);
            if (url == null || url.isEmpty()) {
                System.out.println("URL is broken");
                continue;
            }
            else{
                System.out.println("URL verified");
            }
        }
    }

    @Then("I verify that the Displayed Number Search Results is correct")
    public void iVerifyThatTheDisplayedNumberSearchResultsIsCorrect() {
        final List<WebElement> results = driver.findElements(By.xpath("//*[@id=\"search\"]/div[1]/div[8]/section/div/div"));
        for (int i = 1; i <= results.size(); i++)
            driver.findElement(By.xpath("//*[@id=\"search\"]/div[1]/div[8]/section/div/div[" + i + "]")).isDisplayed();
    }
}

