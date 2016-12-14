package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Collections;
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
    private final static String URL = "file:///D:/Java_projects/DragAndDrop/drag_and_drop/index.html";

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

    public void sortDescending(){
        List<WebElement> list = boxList;
        for (int i = 0; i < list.size(); i++){
            for (int j = 0; j < list.size()-1; j++) {

                WebElement source = list.get(j);
                WebElement target = list.get(j + 1);

                if (Integer.parseInt(source.getText()) < Integer.parseInt(target.getText())) {

                    Actions action = new Actions(driver);
                    action.clickAndHold(source).perform();

                    int x = target.getLocation().getX();
                    int y = target.getLocation().getY();
                    int y1 = source.getLocation().getY();

                    int width = source.getSize().width;
                    int height = source.getSize().height;

                    action.moveToElement(target, (width+x)/2, y-y1-height/2).perform();
                    //action.moveToElement(target);
                    action.release().perform();

                    //action.dragAndDrop(source, target).perform();
                }
            }
        }
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
