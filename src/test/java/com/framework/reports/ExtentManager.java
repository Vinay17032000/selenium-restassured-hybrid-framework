package com.framework.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public final class ExtentManager {

    private static final ThreadLocal<ExtentTest> TEST = new ThreadLocal<>();
    private static ExtentReports reports;

    private ExtentManager() {
    }

    public static synchronized ExtentReports getReports() {
        if (reports == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("target/extent-report.html");
            spark.config().setDocumentTitle("Automation Report");
            spark.config().setReportName("UI and API Test Results");
            reports = new ExtentReports();
            reports.attachReporter(spark);
        }
        return reports;
    }

    public static ExtentTest startTest(String name) {
        ExtentTest test = getReports().createTest(name);
        TEST.set(test);
        return test;
    }

    public static ExtentTest getTest() {
        return TEST.get();
    }

    public static void endTest() {
        TEST.remove();
    }

    public static synchronized void flush() {
        if (reports != null) {
            reports.flush();
        }
    }
}
