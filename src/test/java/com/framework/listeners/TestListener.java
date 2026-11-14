package com.framework.listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.framework.driver.DriverManager;
import com.framework.reports.ExtentManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        startTest(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentManager.getTest().pass("Test passed");
        ExtentManager.endTest();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String screenshot = captureScreenshot();
        if (screenshot != null) {
            ExtentManager.getTest().fail(result.getThrowable(),
                    MediaEntityBuilder.createScreenCaptureFromBase64String(screenshot, "Failure screenshot").build());
        } else {
            ExtentManager.getTest().fail(result.getThrowable());
        }
        ExtentManager.endTest();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (ExtentManager.getTest() == null) {
            startTest(result);
        }
        ExtentManager.getTest().skip(result.getThrowable() != null ? result.getThrowable().getMessage() : "Test skipped");
        ExtentManager.endTest();
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.flush();
    }

    private void startTest(ITestResult result) {
        String name = result.getTestClass().getRealClass().getSimpleName() + " - " + result.getMethod().getMethodName();
        ExtentManager.startTest(name);
    }

    private String captureScreenshot() {
        WebDriver driver = DriverManager.getDriver();
        if (driver == null) {
            return null;
        }
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        } catch (WebDriverException e) {
            return null;
        }
    }
}
