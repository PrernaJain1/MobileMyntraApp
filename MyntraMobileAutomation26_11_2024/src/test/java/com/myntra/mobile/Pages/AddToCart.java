package com.myntra.mobile.Pages;

import com.myntra.mobile.Base.BaseClass;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddToCart extends BaseClass {

    @FindBy(id = "com.myntra.android:id/closeDialog")
    public WebElement laterTakeMeBack;

    @FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"BANNER_2\"]/android.widget.ImageView")
    public WebElement myntraFashion;

    @FindBy(id = "com.myntra.android:id/ll_react_container")
    public WebElement frame1;

    @FindBy(xpath = "(//android.view.ViewGroup[@content-desc=\"TOPNAV_CAROUSEL_1_2\"])[2]/android.view.ViewGroup/android.view.ViewGroup")
    public WebElement tShirts;

    public AddToCart(AndroidDriver driver){
        this.driver =driver;
        PageFactory.initElements(driver,this);
    }

    public void addToCart() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(laterTakeMeBack)).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        myntraFashion.click();
        screenshot(driver,generateRandomNumber(1,100));
//        driver.switchTo().frame(frame1);
//        JavascriptExecutor script = (JavascriptExecutor) driver;
//        script.executeScript("arguments[0].scrollIntoView(true)",tShirts);
        Thread.sleep(3000);
//        tShirts.click();
    }
}
