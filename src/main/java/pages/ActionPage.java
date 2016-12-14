package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Created by numash on 07.12.2016.
 */
public class ActionPage {

    @FindBy(className = "ui-state-default")
    private List<WebElement> boxList;

    @FindBy(id="drop")
    private WebElement trashElement;

    WebDriver driver;
    private final static String URL = System.getProperty("user.dir") + "\\drag_and_drop\\index.html";

    public ActionPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void open(){
        driver.get(URL);
    }

    public void moveBoxToTrash(String index){
        WebElement sourceElement = getBoxElement(index);

        Actions action = new Actions(driver);
        action.dragAndDrop(sourceElement, trashElement).perform();
    }

    public WebElement getBoxElement(String textValue){
        try{
            for (WebElement box: boxList) {
                if (box.getText().equals(textValue))
                    return box;
            }
        } catch(NoSuchElementException e){
            return null;
        }
        return null;
    }

    public void sortDescending(boolean straight){
        List<WebElement> list = boxList;

        //bubble sort
        for (int i = 0; i < list.size(); i++){
            for (int j = 0; j < list.size()-1; j++) {

                WebElement source = list.get(j);
                WebElement target = list.get(j + 1);

                if (straight) {
                    if (Integer.parseInt(source.getText()) > Integer.parseInt(target.getText())) {
                        performSort(source, target);
                    }
                } else{
                    if (Integer.parseInt(source.getText()) < Integer.parseInt(target.getText())) {
                        performSort(source, target);
                    }
                }
            }
        }
    }

    private void performSort(WebElement source, WebElement target){
        Actions action = new Actions(driver);

        int targetX = target.getLocation().getX();
        int targetY = target.getLocation().getY();
        int sourceY = source.getLocation().getY();

        //action.dragAndDropBy(source, targetX, targetY-sourceY);
        action.clickAndHold(source).perform();
        action.moveByOffset(targetX, targetY-sourceY+2).perform();
        action.release().perform();
    }

    public boolean checkSort(boolean straight){
        int previous = Integer.parseInt(boxList.get(0).getText());
        for (WebElement box:boxList) {
            if(straight){
                if (previous > Integer.parseInt(box.getText())) {
                    return false;
                }
            } else{
                if (previous < Integer.parseInt(box.getText())) {
                    return false;
                }
            }
            previous = Integer.parseInt(box.getText());
        }
        return true;
    }

    public String getMessage(){
        String message = driver.switchTo().alert().getText();
        return message;
    }

    public void clickOk(){
        driver.switchTo().alert().accept();
    }

    public void clickCancel(){
        driver.switchTo().alert().dismiss();
    }
}
