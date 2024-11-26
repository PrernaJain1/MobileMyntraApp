package com.myntra.mobile.Base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import java.util.Random;

public class BaseClass {

    public AndroidDriver driver;
    public AppiumDriverLocalService service;

    public boolean checkIfServerIsRunning(int port){
        boolean isServerRunning = false;
        ServerSocket serverSocket;
        try{
            serverSocket = new ServerSocket(port);
            serverSocket.close();
        }catch(Exception e){
            isServerRunning = true;
        } finally{
            serverSocket = null;
        }
        return  isServerRunning;
    }

    public AppiumDriverLocalService startServer() throws IOException, InterruptedException {
        try {
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("cmd.exe /c start cmd.exe /k \"appium -a 127.0.0.1 -p 4723 --session-override -dc \"{\"\"noReset\"\": \"\"false\"\"}\"\"");
            Thread.sleep(8000);
            System.out.println("Hold server creation");

            boolean flag = checkIfServerIsRunning(4723);

            if (!flag) {
                System.out.println("Server not started");
                service = AppiumDriverLocalService.buildDefaultService();
                service.start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return service;
    }

    public void stopServer(){
        System.out.println("Server Stopped");
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("taskkill /F /IM node.exe");
            runtime.exec("taskkill /F /IM cmd.exe");
            Thread.sleep(3000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void startEmulator(){
        try{
            Runtime.getRuntime().exec(System.getProperty("user.dir")+"//src//test//resources//apk//7aNew.bat");
            Thread.sleep(7000);
            System.out.println("Started Emulator");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void closeEmulator(){
        try{
            Process process = Runtime.getRuntime().exec("adb emu kill");
            process.waitFor();
            System.out.println("Emulator closed successfully");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public AndroidDriver setUp(String appName) throws MalformedURLException {
        try{
            FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//Files//application.properties");
            Properties properties = new Properties();
            properties.load(fileInputStream);

            String device = properties.getProperty("device");

            if(device.contains("Emulator")){
                startEmulator();
            }
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("deviceName","Pixel_7aNew");
            capabilities.setCapability("platformName","ANDROID");
            capabilities.setCapability("platformVersion","13");
            capabilities.setCapability("appPackage","com.myntra.android");
            capabilities.setCapability("appActivity","com.myntra.android.activities.react.ReactActivity");
            capabilities.setCapability("automationName","UIAutomator2");
    //        capabilities.setCapability("disableNotification","true");
    //        capabilities.setCapability("disableWindowAnimation","true");
            capabilities.setCapability("autoGrantPermissions", "false");

            URL url = new URL("http://127.0.0.1:4723/wd/hub");
            driver = new AndroidDriver(url,capabilities);
        }catch (Exception e){
            e.printStackTrace();
        }
        return driver;
    }

    public int generateRandomNumber(int min, int max){
        Random random = new Random();
        return random.nextInt((max-min)+1);
    }

    public void screenshot(AndroidDriver driver,int i){
        String localFilePath = "src/test/resources/Files/Screenshots/abc"+i+".png";
        String remoteFilePath = "sdcard/DCIM/";

        // Set up DesiredCapabilities for Appium
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "emulator-5554");
        capabilities.setCapability("appPackage", "com.android.settings");
        capabilities.setCapability("appActivity", ".Settings");

        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
        File destFile = new File("src/test/resources/Files/Screenshots/abc"+i+".png");
        try{
            FileUtils.copyFile(srcFile,destFile);
            System.out.println("Screenshot captured Successfully!");

            //To push file to Emulator
            String command = "adb push "+ localFilePath + " " + remoteFilePath;

            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            if(process.exitValue() == 0){
                System.out.println("File pushed successfully to emulator");
            }
            else{
                System.out.println("Failed to push file to emulator");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void tearDown(){
        driver.quit();
    }
}
