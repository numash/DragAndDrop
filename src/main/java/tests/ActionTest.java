package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.ActionPage;

/**
 * Created by numash on 07.12.2016.
 */
public class ActionTest {
    private WebDriver driver;
    private ActionPage actionPage;
    private SoftAssert softAssert;

    @BeforeTest
    public void beforeTest(){
        driver = new FirefoxDriver();

        actionPage = new ActionPage(driver);
        softAssert = new SoftAssert();
    }

    /**
     * Precondition:
     * 1. Open application page
     */
    @BeforeMethod
    public void beforeMethod(){
        actionPage.open();
    }

    /*
     * 1. Perform drag and drop
     * 2. Check information message
     */
    @Parameters({"boxIndex", "informationMessage"})
    @Test
    public void elementMovingToTrashShowsAMessage(String index, String expectedMessage){
        actionPage.moveBoxToTrash(index);

        String actualMessage = actionPage.getMessage();
        actionPage.clickCancel();

        softAssert.assertEquals(actualMessage, expectedMessage, "Wrong information message when moving box to trash");
        softAssert.assertAll();
    }

    /**
     * 1. Move box 2
     * 2. Click "Cancel"
     * 3. Verify box 2 presence
     */
    @Parameters({"boxIndex"})
    @Test
    public void clickingCancelDoesNotDeletesTheBox(String boxIndex){
        actionPage.moveBoxToTrash(boxIndex);
        actionPage.clickCancel();

        softAssert.assertNotNull(actionPage.getBoxElement(boxIndex), "Element was deleted after clicking 'Cancel' on alert");
        softAssert.assertAll();
    }

    /**
     * 1. Move box 2
     * 2. Click "Cancel"
     * 3. Verify box 2 presence
     */
    @Parameters({"boxIndex"})
    @Test(dependsOnMethods = "clickingCancelDoesNotDeletesTheBox")
    public void clickingOkDeletesTheBox(String boxIndex){
        actionPage.moveBoxToTrash(boxIndex);
        actionPage.clickOk();

        softAssert.assertNull(actionPage.getBoxElement(boxIndex), "Element wasn't deleted after clicking 'Ok' on alert");
        softAssert.assertAll();
    }

    /**
     * 1. Sort boxes
     * 2. Verify boxes are sorted correctly
     */
    @Test
    public void checkSort(){
        actionPage.sortDescending();

        //softAssert.assertNull(actionPage.getBoxElement(boxIndex), "Element wasn't deleted after clicking 'Ok' on alert");
        softAssert.assertAll();
    }

    @AfterTest
    public void afterTest(){
     //   driver.quit();
    }
}
