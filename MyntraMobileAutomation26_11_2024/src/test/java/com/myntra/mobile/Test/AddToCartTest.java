package com.myntra.mobile.Test;

import com.myntra.mobile.Base.BaseClass;
import com.myntra.mobile.Pages.AddToCart;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddToCartTest extends BaseClass{

    @BeforeClass
    public void initialize() throws IOException, InterruptedException {
        startServer();
        setUp("Myntra");
    }

    @Test
    public void addingToCart() throws InterruptedException {
        AddToCart a = new AddToCart(driver);
        a.addToCart();
    }

    @AfterClass
    public void dinitialize(){
        tearDown();
        closeEmulator();
        stopServer();
    }
}
