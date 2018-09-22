package com.willowtreeapps;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created on 5/23/17.
 */
public class HomePage extends BasePage {


    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void waitForClassLoaded(int secondsToWait, String className){
        WebDriverWait wait = new WebDriverWait(driver, secondsToWait);
        wait.until(ExpectedConditions.elementToBeClickable(By.className(className)));
    }

    public boolean clickCorrectAnswer(){
        String nameToFind = driver.findElement(By.cssSelector("h1.text-center > span")).getText();
        String currentPictureName = "";
        List<WebElement> allPictures = driver.findElements(By.className("photo"));

        for(WebElement currentPicture : allPictures) {
            currentPictureName = currentPicture.findElement(By.className("name")).getText();
            if (currentPictureName.equals(nameToFind)){
                currentPicture.click();
                while(driver.findElement(By.cssSelector("h1.text-center > span")).getText().equals(nameToFind))
                        sleep(1000);
                return true;
            }
        }
        return false;
    }

    public boolean clickIncorrectAnswer(){
        String nameToFind = driver.findElement(By.cssSelector("h1.text-center > span")).getText();
        String currentPictureName = "";
        List<WebElement> allPictures = driver.findElements(By.className("photo"));
        List<WebElement> clickedPics = driver.findElements(By.className("wrong"));

        for(WebElement currentPicture : allPictures) {
            currentPictureName = currentPicture.findElement(By.className("name")).getText();
            if(!currentPictureName.equals(nameToFind) && !clickedPics.contains(currentPicture)) {
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
        waitForClassLoaded(10, "photo");
        int count = Integer.parseInt(driver.findElement(By.className("attempts")).getText());
        driver.findElement(By.className("photo")).click();
        waitForClassLoaded(10, "attempts");
        int countAfter = Integer.parseInt(driver.findElement(By.className("attempts")).getText());
        Assert.assertTrue("countafter " + countAfter + ", countbefore " + count,countAfter > count);
    }

    public void validateStreakCounterIncrementing() {
        //Wait for page to load
        waitForClassLoaded(10, "photo");
        int streakCountBefore = Integer.parseInt(driver.findElement(By.className("streak")).getText());
        clickCorrectAnswer();
        int streakCountAfter = Integer.parseInt(driver.findElement(By.className("streak")).getText());
        Assert.assertTrue(streakCountAfter == streakCountBefore + 1);

    }

    public void validateStreakCounterResetting() {
        //Wait for page to load
        waitForClassLoaded(10, "photo");
        boolean successfulCorrect = clickCorrectAnswer();
        waitForClassLoaded(10, "photo");
        boolean successfulIncorrect = clickIncorrectAnswer();
        waitForClassLoaded(10, "streak");
        int streakCount = Integer.parseInt(driver.findElement(By.className("streak")).getText());
        Assert.assertTrue(streakCount == 0 && successfulCorrect && successfulIncorrect);
    }

    public void validateCountersAfterRandomSelections(int numberOfGuesses) {
        //Wait for page to load
        waitForClassLoaded(10, "photo");
        int internalStreakCount = 0;
        int internalTriesCount = 0;
        int internalCorrectCount = 0;
        while (internalTriesCount < numberOfGuesses) {
            int rand = (int)(Math.random() * 10);
            if(rand % 2 == 0){
                if(clickCorrectAnswer()) {
                    internalStreakCount++;
                    internalCorrectCount++;
                    internalTriesCount++;
                    waitForClassLoaded(10, "photo");
                }
            }
            else if(rand % 2 == 1){
                if(clickIncorrectAnswer()) {
                    internalStreakCount = 0;
                    internalTriesCount++;
                }
            }
        }
        sleep(1000);
        int calculatedStreakCount = Integer.parseInt(driver.findElement(By.className("streak")).getText());
        int calculatedTriesCount = Integer.parseInt(driver.findElement(By.className("attempts")).getText());
        int calculatedCorrectCount = Integer.parseInt(driver.findElement(By.className("correct")).getText());
        Assert.assertTrue(internalStreakCount == calculatedStreakCount &&
                internalTriesCount == calculatedTriesCount &&
                internalCorrectCount == calculatedCorrectCount);
    }

    public void validateNameAndPictureChangeAfterCorrectAnswer() {
        //Wait for page to load
        waitForClassLoaded(10, "photo");

        String oldNameToFind = driver.findElement(By.cssSelector("h1.text-center > span")).getText();
        List<WebElement> oldPictures = driver.findElements(By.className("photo"));

        clickCorrectAnswer();
        boolean newPictures = true;
        waitForClassLoaded(10, "photo");

        String currentNameToFind = driver.findElement(By.cssSelector("h1.text-center > span")).getText();
        List<WebElement> currentPictures = driver.findElements(By.className("photo"));
        if (currentPictures.containsAll(oldPictures))
            newPictures=false;

        Assert.assertTrue(!oldNameToFind.equals(currentNameToFind) && newPictures);
    }
}
