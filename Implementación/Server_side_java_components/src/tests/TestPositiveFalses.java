package tests;

import java.util.regex.Pattern;
import java.io.File;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class TestPositiveFalses {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    File file = new File("C:\\Users\\Mauricio\\Desktop\\geckodriver.exe");
    System.setProperty("webdriver.gecko.driver", file.getAbsolutePath());
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testPositiveFalses() throws Exception {
    driver.get(baseUrl + "/app/#/home");
    driver.findElement(By.id("selectVideoInput")).clear();
    driver.findElement(By.id("selectVideoInput")).sendKeys("C:\\Users\\Mauricio\\Desktop\\Dissolve1-15.mp4");
    Thread.sleep(500);
    driver.findElement(By.cssSelector("button.confirm")).click();
    Thread.sleep(300);
    driver.findElement(By.id("selectGroundTruth")).clear();
    driver.findElement(By.id("selectGroundTruth")).sendKeys("C:\\Users\\Mauricio\\Desktop\\groundtruth.csv");
    Thread.sleep(500);
    driver.findElement(By.cssSelector("button.confirm")).click();
    Thread.sleep(300);
    driver.findElement(By.xpath("//div[@id='start']/i")).click();
    Thread.sleep(500);
    driver.findElement(By.cssSelector("button.confirm")).click();
    Thread.sleep(15000);
    assertEquals("Positive Falses", driver.findElement(By.cssSelector("#results > div.ui.label")).getText());
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
