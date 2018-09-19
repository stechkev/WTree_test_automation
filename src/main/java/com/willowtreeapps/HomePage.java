package com.willowtreeapps;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created on 5/23/17.
 */
public class HomePage extends BasePage {


    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean clickCorrectAnswer(){
        String nameToFind = driver.findElement(By.cssSelector("h1.text-center > span")).getText();
        String currentPictureName = "";
        List<WebElement> allPictures = driver.findElements(By.className("photo"));

        for(WebElement currentPicture : allPictures) {
            currentPictureName = currentPicture.findElement(By.className("name")).getText();
            if (currentPictureName.equals(nameToFind)){
                currentPicture.click();
                return true;
            }
        }
        return false;
    }

    public boolean clickIncorrectAnswer(){
        String nameToFind = driver.findElement(By.cssSelector("h1.text-center > span")).getText();
        String currentPictureName = "";
        List<WebElement> allPictures = driver.findElements(By.className("photo"));

        for(WebElement currentPicture : allPictures) {
            currentPictureName = currentPicture.findElement(By.className("name")).getText();
            if(!currentPictureName.equals(nameToFind)) {
                currentPicture.click();
                return true;
            }
        }
        return false;
    }

    public void validateTitleIsPresent() {
        WebElement title = driver.findElement(By.cssSelector("h1"));
        Assert.assertTrue(title != null);
    }


    public void validateClickingFirstPhotoIncreasesTriesCounter() {
        //Wait for page to load
        sleep(6000);
        int count = Integer.parseInt(driver.findElement(By.className("attempts")).getText());
        driver.findElement(By.className("photo")).click();
        int countAfter = Integer.parseInt(driver.findElement(By.className("attempts")).getText());
        Assert.assertTrue(countAfter == count + 1);

    }

    public void validateStreakCounterIncrementing() {
        //Wait for page to load
        sleep(6000);
        int streakCountBefore = Integer.parseInt(driver.findElement(By.className("streak")).getText());
        clickCorrectAnswer();
        int streakCountAfter = Integer.parseInt(driver.findElement(By.className("streak")).getText());
        Assert.assertTrue(streakCountAfter == streakCountBefore + 1);

    }

    public void validateStreakCounterResetting() {
        //Wait for page to load
        sleep(6000);
        boolean successfulCorrect = clickCorrectAnswer();
        sleep(8000);
        boolean successfulIncorrect = clickIncorrectAnswer();
        int streakCount = Integer.parseInt(driver.findElement(By.className("streak")).getText());
        Assert.assertTrue(streakCount == 0 && successfulCorrect && successfulIncorrect);
    }

    public void validateCountersAfterRandomSelections(int numberOfGuesses) {
        //Wait for page to load
        sleep(6000);
        int internalStreakCount = 0;
        int internalTriesCount = 0;
        int internalCorrectCount = 0;
        for (int i = 0; i < numberOfGuesses; i ++) {
            double rand = Math.random();
            if(rand % 2 == 0){
                if(clickCorrectAnswer()) {
                    internalStreakCount++;
                    internalCorrectCount++;
                    internalTriesCount++;
                }
            }
            else if(rand % 2 == 1){
                if(clickIncorrectAnswer()) {
                    internalStreakCount = 0;
                    internalTriesCount++;
                }
            }
            sleep(8000);
        }
        int calculatedStreakCount = Integer.parseInt(driver.findElement(By.className("streak")).getText());
        int calculatedTriesCount = Integer.parseInt(driver.findElement(By.className("attempts")).getText());
        int calculatedCorrectCount = Integer.parseInt(driver.findElement(By.className("correct")).getText());
        Assert.assertTrue(internalStreakCount == calculatedStreakCount &&
                internalTriesCount == calculatedTriesCount &&
                internalCorrectCount == calculatedCorrectCount);
    }
}
