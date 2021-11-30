package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MultiTest {
   static WebDriver driver;
   public static void click(By by){
        driver.findElement(by).click();
    }
    public static void sendkeyText(By by, String text)
    {
        driver.findElement(by).sendKeys(text);
    }
    public static String getTextFromElement(By by)
    {
        return driver.findElement(by).getText();
    }
    public static String currentTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("ddmmyymmss");
        return sdf.format(date);
    }
    public static void waitForClickable(By by,int timeInSeconds){
        WebDriverWait wait=new WebDriverWait(driver,timeInSeconds);
        wait.until(ExpectedConditions.elementToBeClickable(by));
    }
    public static void waitForVisible(By by, int timeInSeconds){
        WebDriverWait wait=new WebDriverWait(driver,timeInSeconds);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
    @AfterMethod
    public void closeBrowser(){
      // close the browser
       driver.close();
    }
    @BeforeMethod
    public void openBrowser(){
        System.setProperty("webdriver.chrome.driver", "src/test/drivers/chromedriver.exe");
        driver=new ChromeDriver();
        // open the browser
        driver.manage().window().maximize();
        //type url link
        driver.get("https://demo.nopcommerce.com/");
    }

    @Test
    public void userShouldBeAbleRegisterSuccessfully(){
        waitForVisible(By.xpath("//a[@class=\"ico-register\"]"),10);
        click(By.xpath("//a[@class=\"ico-register\"]"));
        //write user first name
        sendkeyText(By.id("FirstName"),"bhupesh");
        //write user last name
        sendkeyText(By.id("LastName"),"Patel");
        //Write user date of birth
        Select selectday=new Select(driver.findElement(By.name("DateOfBirthDay")));
        selectday.selectByVisibleText("20");
        //write user date of birth month
        Select selectmonth=new Select(driver.findElement(By.name("DateOfBirthMonth")));
        selectmonth.selectByValue("5");
        //write user date of birth year
        Select selectyear=new Select(driver.findElement(By.name("DateOfBirthYear")));
        selectyear.selectByVisibleText("1988");
        //write user valid user email id
        sendkeyText(By.id("Email"),"bupesh1710+"+currentTime()+"@gmail.com");
        // write user password
        sendkeyText(By.id("Password"),"bhup12345");
        //Write user confirm password.
        sendkeyText(By.id("ConfirmPassword"),"bhup12345");
        //click on the register button.
        waitForClickable(By.name("register-button"),125);
        // click on the register button
        click(By.id("register-button"));
        String actualRegisterSuccessMessage=getTextFromElement(By.xpath("//div[@class=\"result\"]"));
        String expectedRegisterSuccessMessage="Your registration completed";
        Assert.assertTrue(actualRegisterSuccessMessage.equals(expectedRegisterSuccessMessage),"Your registration completed");
    }
    @Test
    public void newsComments(){
        // Click on news details
       click(By.xpath("//div[@class=\"news-list-homepage\"]/div[2]/div[2]/div[3]/a"));
        //type Title
        sendkeyText(By.id("AddNewComment_CommentTitle"),"Nice website");
        //type comment
        sendkeyText(By.id("AddNewComment_CommentText"),"nopcommerce web Site is very User Friendly easy to assess ");
        //click on new comment
        click(By.xpath("//button[@class=\"button-1 news-item-add-comment-button\"]"));
        String actualNewsComment=getTextFromElement(By.xpath("//div[@class=\"result\"]"));
        String expectedNewsComment="News comment is successfully added.";
                Assert.assertTrue(actualNewsComment.equals(expectedNewsComment),"News comment is successfully added.");
    }
@Test
    public void userIsAbleToNavigateToDestop(){
       //click on the computer
       click(By.xpath("//ul[@class=\"top-menu notmobile\"]/li/a[@href=\"/computers\"]"));
       click(By.xpath("//img[@alt=\"Picture for category Desktops\"]"));

       String actualResult=getTextFromElement(By.xpath("//div[@class=\"center-2\"]/div/div/h1"));
       String expectedResult="Desktops";
       Assert.assertTrue(actualResult.equals(expectedResult),"Desktops");
}
@Test
    public void verifyRegisteredUserShouldBeAbleTOReferAProductToFriend(){
       //call userShouldBeAbleRegisterSuccessfully to verifyRegisteredUserShouldBeAbleTOReferAProductToFriend
       userShouldBeAbleRegisterSuccessfully();
       // click on  registration completed button
       click(By.xpath("//a[@class=\"button-1 register-continue-button\"]"));
       //click on build your on own computer picture
       click(By.xpath("//img[@alt=\"Picture of Build your own computer\"]"));
      //Click on Email A friend
       click(By.xpath("//button[@class=\"button-2 email-a-friend-button\"]"));
       // Then enter friend email id
        sendkeyText(By.xpath("//input[@class=\"friend-email\"]"),"xyz123+"+currentTime()+"@gmail.com");
        //then enter the message
        sendkeyText(By.xpath("//textarea[@placeholder=\"Enter personal message (optional).\"]"),"build you own computer configuration is very good might be you like it ");
        //then Click to send email
         click(By.name("send-email"));
        String actualResult=getTextFromElement(By.xpath("//div[@class=\"result\"]"));
         String expectedResult="Your message has been sent.";
          Assert.assertTrue(actualResult.equals(expectedResult),"Your message has been sent.");
}
}
