package com.sdet.utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for capturing and attaching screenshots to Allure reports.
 */
public class ScreenshotUtil {

    private static final Logger log = LoggerFactory.getLogger(ScreenshotUtil.class);
    private static final String SCREENSHOT_DIR = "target/screenshots/";

    private ScreenshotUtil() {}

    /**
     * Captures screenshot and attaches it to the Allure report.
     */
    public static void captureAndAttach(String testName) {
        WebDriver driver = DriverFactory.getDriver();
        if (driver == null) {
            log.warn("Driver is null — cannot capture screenshot");
            return;
        }

        try {
            byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

            // Attach to Allure report
            Allure.addAttachment(
                    "Screenshot - " + testName,
                    "image/png",
                    new ByteArrayInputStream(screenshotBytes),
                    "png"
            );

            // Save to disk
            saveScreenshot(screenshotBytes, testName);
            log.info("Screenshot captured for: {}", testName);

        } catch (Exception e) {
            log.error("Failed to capture screenshot", e);
        }
    }

    private static void saveScreenshot(byte[] bytes, String testName) {
        try {
            Path dir = Paths.get(SCREENSHOT_DIR);
            Files.createDirectories(dir);

            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName  = testName + "_" + timestamp + ".png";
            Path filePath    = dir.resolve(fileName);

            Files.write(filePath, bytes);
            log.info("Screenshot saved: {}", filePath.toAbsolutePath());
        } catch (IOException e) {
            log.error("Could not save screenshot to disk", e);
        }
    }
}
