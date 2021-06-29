package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class JavaScriptUtils {
	
static JavascriptExecutor js = (JavascriptExecutor)Driver.getDriver();

	public static void main(String[] args) throws InterruptedException {
		Driver.getDriver().get("https://amazon.com");
		jsCreateAlert();
		WebElement searchBox = Driver.getDriver().findElement(By.id("twotabsearchtextbox"));
		
		js.executeScript("arguments[0].s"
				+ "tyle.border = '4px solid red' ", searchBox);
		Thread.sleep(2000);
	}
	
	public static void jsCreateAlert() {
		js.executeScript("alert('Hey yo, whats up')");
	}
	
	public static void highLightElement(WebElement element) throws InterruptedException {
		for (int i = 0; i < 4; i++) {
			js.executeScript("arguments[0].style.border = '4px solid red' ", element);
			Thread.sleep(300);
			js.executeScript("arguments[0].style.border = '4px solid black' ", element);
			Thread.sleep(300);
			}
	}
	
	public static void scrollToElement(WebElement element) {
	js.executeScript("arguments[0].scrollIntoView(true)", element);
	}
	
	public static void scrollDown(int scrollDownLength) {
		js.executeScript("javascript:window.scrollBy(0," + scrollDownLength + ")");
	}
	
	
	public static void jsClickElement(WebElement element) {
		js.executeScript("arguments[0].click();", element);
	}
	
	public static void jsRefresh(WebElement element) {
		js.executeScript("history.go(0)", element);
	}
	
	
}
