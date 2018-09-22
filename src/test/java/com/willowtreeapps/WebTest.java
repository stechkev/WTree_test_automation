package com.willowtreeapps;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebTest {

    private WebDriver driver;

    /**
     * Change the prop if you are on Windows or Linux to the corresponding file type
     * The chrome WebDrivers are included on the root of this project, to get the
     * latest versions go to https://sites.google.com/a/chromium.org/chromedriver/downloads
     */
    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        Capabilities capabilities = DesiredCapabilities.chrome();
        driver = new ChromeDriver(capabilities);
        driver.navigate().to("http://www.ericrochester.com/name-game/");
    }

    @Test
    public void test_validate_title_is_present() {
        new HomePage(driver)
                .validateTitleIsPresent();
    }

    @Test
    public void test_clicking_photo_increases_tries_counter() {
        new HomePage(driver)
                .validateClickingFirstPhotoIncreasesTriesCounter();
    }

    @Test
    public void test_clicking_correct_photo_increases_streak_counter() {
        new HomePage(driver)
                .validateStreakCounterIncrementing();
    }

    @Test
    public void test_clicking_incorrect_photo_resets_streak_counter() {
        new HomePage(driver)
                .validateStreakCounterResetting();
    }

    @Test
    public void test_counters_after_10_random_selections() {
        new HomePage(driver)
                .validateCountersAfterRandomSelections(10);
    }

    @Test
    public void test_name_and_picture_change_after_correct_answer() {
        new HomePage(driver)
                .validateNameAndPictureChangeAfterCorrectAnswer();
    }

    @After
    public void teardown() {
        driver.quit();
        System.clearProperty("webdriver.chrome.driver");
    }

}
