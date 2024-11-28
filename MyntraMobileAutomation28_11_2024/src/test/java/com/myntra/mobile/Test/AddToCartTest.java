package com.myntra.mobile.Test;

import com.myntra.mobile.Base.BaseClass;
import com.myntra.mobile.Pages.AddToCart;
import org.testng.annotations.*;

import java.io.IOException;

public class AddToCartTest extends BaseClass{

    @BeforeMethod
    public void initialize() throws IOException, InterruptedException {
        startServer();
        driver = setUp(driver,"Myntra");
        a = new AddToCart(driver);
    }

    @Test
    public void addingToCart() throws InterruptedException {
        a.addToCart();
    }

    @AfterMethod
    public void dinitialize(){
        tearDown();
        closeEmulator();
        stopServer();
    }
}