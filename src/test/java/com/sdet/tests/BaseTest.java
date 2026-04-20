package com.sdet.tests;

import com.sdet.utils.DriverFactory;
import com.sdet.utils.ScreenshotUtil;
import io.qameta.allure.Allure;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base test class for all UI tests.
 * Handles driver init/teardown and screenshot capture on failure.
 */
public abstract class BaseTest {

    protected static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    @BeforeMethod(alwaysRun = true)
    public void setUp(ITestResult result) {
        log.info("Starting test: {}", result.getMethod().getMethodName());
        // Driver is initialized lazily via DriverFactory.getDriver()
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        String testName = result.getMethod().getMethodName();

        if (result.getStatus() == ITestResult.FAILURE) {
            log.error("Test FAILED: {}", testName);
            ScreenshotUtil.captureAndAttach(testName);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            log.info("Test PASSED: {}", testName);
        } else {
            log.warn("Test SKIPPED: {}", testName);
        }

        DriverFactory.quitDriver();
    }
}
