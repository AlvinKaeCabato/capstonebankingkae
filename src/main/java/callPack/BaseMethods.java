package callPack;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import utilsPack.ExtentReportsUtil;
import utilsPack.Logger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class BaseMethods {
	protected AndroidDriver driver;
	protected WebDriverWait wait;
	
	public BaseMethods(AndroidDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}
	public String getScreenshot() {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
        //String for saving the screenshot to desired path
		String destination = System.getProperty("user.dir") + "\\ExtentReport\\FailedTestsScreenshots\\Error - "+dateName+".png";
		//String for the output for extent report
		String screenshotLoc = "../FailedTestsScreenshots/Error - "+dateName+".png";
		File finalDestination = new File(destination);
		try {
			FileUtils.copyFile(source, finalDestination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return screenshotLoc;
	}
	public String getScreenshotPass() {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
        //String for saving the screenshot to desired path
		String destination = System.getProperty("user.dir") + "\\ExtentReport\\PassedScreenshots\\Pass - "+dateName+".png";
		//String for the output for extent report
		String screenshotLoc = "../PassedScreenshots/Pass - "+dateName+".png";
		File finalDestination = new File(destination);
		try {
			FileUtils.copyFile(source, finalDestination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return screenshotLoc;
	}
	public void clickElement(String element) {
		//String locatorBy = element[0];
		boolean clicked = false;
		/*
		if (locatorBy == "accessibilityId") {
			WebElement elm = driver.findElement(AppiumBy.accessibilityId(element));
			
			try {
				elm.click();
				clicked = true;
			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.accessibilityId(element));
				elm.click();
				clicked = true;
			}
		}
		else if (locatorBy == "id") {
		*/
			WebElement elm = driver.findElement(AppiumBy.id(element));
			try {
				elm.click();
				clicked = true;
			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.id(element));
				elm.click();
				clicked = true;
			}
			/*
		}
		else if (locatorBy == "xpath") {
			WebElement elm = driver.findElement(AppiumBy.xpath(element));
			try {
				elm.click();
				clicked = true;
			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.xpath(element));
				elm.click();
				clicked = true;
			}	
		}
		*/
		
		if (clicked == true) {
			System.out.println(element + " was Clicked");
			ExtentReportsUtil.pass(element + " was Clicked");
			ExtentReportsUtil.logger.log(LogStatus.PASS, 
					ExtentReportsUtil.logger.addScreenCapture(getScreenshotPass()));
		}
		else {
			System.out.println(element + " was NOT Clicked");
			ExtentReportsUtil.fail(element + " was NOT Clicked");
			SetupEnv.fail = 1;
			ExtentReportsUtil.logger.log(LogStatus.FAIL, 
					ExtentReportsUtil.logger.addScreenCapture(getScreenshot()));
		}
		
	}
	
	public void sendTextToElement(String element, String value) {
		//String locatorBy = element[0];
		boolean enteredText = false;
		/*
		if (locatorBy == "accessibilityId") {
			WebElement elm = driver.findElement(AppiumBy.accessibilityId(element));
			try {
				elm.clear();
				elm.sendKeys(value);
				enteredText = true;
			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.accessibilityId(element));
				elm.clear();
				elm.sendKeys(value);			
				enteredText = true;
			}
		}
		else if (locatorBy == "xpath") {
			WebElement elm = driver.findElement(AppiumBy.xpath(element));
			try {
				elm.clear();
				elm.sendKeys(value);			
				enteredText = true;
			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.xpath(element));
				elm.clear();
				elm.sendKeys(value);			
				enteredText = true;
			}
			
		}
		else if (locatorBy == "id") {
		*/
			WebElement elm = driver.findElement(AppiumBy.id(element));
			try {
				elm.clear();
				elm.sendKeys(value);			
				enteredText = true;
			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.id(element));
				elm.clear();
				elm.sendKeys(value);			
				enteredText = true;
			}
		//}
		
		if (enteredText == true) {
			System.out.println(" Send text value to element: " + element);	
			ExtentReportsUtil.pass(" Send text value to element: " + element);
			ExtentReportsUtil.logger.log(LogStatus.PASS, ExtentReportsUtil.logger.addScreenCapture(getScreenshotPass()));
		}
		else {
			System.out.println(" Unable to send text value to element: " + element);	
			ExtentReportsUtil.fail(" Unable to send text value to element: " + element);
			SetupEnv.fail = 1;
			ExtentReportsUtil.logger.log(LogStatus.FAIL, ExtentReportsUtil.logger.addScreenCapture(getScreenshot()));
		}
	}
	
	public void verifyDataIsSame(String element, String value) {
		//String locatorBy = element[0];
		boolean enteredText = false;
		String inputText = "";
		/*
		if (locatorBy == "accessibilityId") {
			WebElement elm = driver.findElement(AppiumBy.accessibilityId(element));
			try {
				inputText = elm.getText();
				enteredText = true;
			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.accessibilityId(element));
				inputText = elm.getText();			
				enteredText = true;
			}
		}
		else if (locatorBy == "xpath") {
			WebElement elm = driver.findElement(AppiumBy.xpath(element));
			try {
				inputText = elm.getText();		
				enteredText = true;
			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.xpath(element));
				inputText = elm.getText();			
				enteredText = true;
			}

		}
		else if (locatorBy == "id") {
		*/
			WebElement elm = driver.findElement(AppiumBy.id(element));
			try {
				inputText = elm.getText();		
				enteredText = true;
			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.id(element));
				inputText = elm.getText();		
				enteredText = true;
			}
			
		//}
		
		if (inputText.trim().equals(value.trim())) {
			System.out.println(inputText + " from" + element + " is the same as expected value: " + value);	
			ExtentReportsUtil.pass(inputText + " from" + element + " is the same as expected value: " + value);
			ExtentReportsUtil.logger.log(LogStatus.PASS, ExtentReportsUtil.logger.addScreenCapture(getScreenshotPass()));
		}
		else {
			System.out.println(inputText + " from " + element + " is not the same as expected value: " + value);	
			ExtentReportsUtil.fail(inputText + " from " + element + " is not the same as expected value: " + value);
			SetupEnv.fail = 1;
			ExtentReportsUtil.logger.log(LogStatus.FAIL, ExtentReportsUtil.logger.addScreenCapture(getScreenshot()));
		}
	}
	public void verifyDataIsSame(float act, float exp) {
		if (act == exp) {
			System.out.println(act + " is the same as expected value: " + exp);	
			ExtentReportsUtil.pass(act + " is the same as expected value: " + exp);
			ExtentReportsUtil.logger.log(LogStatus.PASS, ExtentReportsUtil.logger.addScreenCapture(getScreenshotPass()));
		}
		else {
			System.out.println(act + " is not the same as expected value: " + exp);	
			ExtentReportsUtil.fail(act + "is not the same as expected value: " + exp);
			SetupEnv.fail = 1;
			ExtentReportsUtil.logger.log(LogStatus.FAIL, 
					ExtentReportsUtil.logger.addScreenCapture(getScreenshot()));
		}
	}
	public void verifyDataIsSameFloat(Float act, Float exp) {
		if (act.equals(exp)) {
			System.out.println(act + " is the same as expected value: " + exp);	
			ExtentReportsUtil.pass(act + " is the same as expected value: " + exp);
			ExtentReportsUtil.logger.log(LogStatus.PASS, 
					ExtentReportsUtil.logger.addScreenCapture(getScreenshotPass()));
		}
		else {
			System.out.println(act + " is not the same as input value: " + exp);	
			ExtentReportsUtil.fail(act + " is not the same as input value: " + exp);
			SetupEnv.fail = 1;
			ExtentReportsUtil.logger.log(LogStatus.FAIL, 
					ExtentReportsUtil.logger.addScreenCapture(getScreenshot()));
		}
	}
	public void verifyDataIsSameDirComp(String act, String exp) {
		if (act.equals(exp)) {
			System.out.println(act + " is the same as expected value: " + exp);	
			ExtentReportsUtil.pass(act + " is the same as expected value: " + exp);
			ExtentReportsUtil.logger.log(LogStatus.PASS, 
					ExtentReportsUtil.logger.addScreenCapture(getScreenshotPass()));
		}
		else {
			System.out.println(act + " is not the same as input value: " + exp);	
			ExtentReportsUtil.fail(act + " is not the same as input value: " + exp);
			SetupEnv.fail = 1;
			ExtentReportsUtil.logger.log(LogStatus.FAIL, 
					ExtentReportsUtil.logger.addScreenCapture(getScreenshot()));
		}
	}
	public String getTextFromElement(String element) {
		//String locatorBy = element[0];
		boolean retrieved = false;
		String returnText = "";
		/*
		if (locatorBy == "accessibilityId") {
			WebElement elm = driver.findElement(AppiumBy.accessibilityId(element));
			try {
				returnText = elm.getText();
				retrieved = true;
			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.accessibilityId(element));
				returnText = elm.getText();
				retrieved= true;
			}	
		}
		else if (locatorBy == "id") {
		*/
			WebElement elm = driver.findElement(AppiumBy.id(element));
			try {
				returnText = elm.getText();
				retrieved = true;
			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.id(element));
				returnText = elm.getText();
				retrieved = true;
			}	
			/*
		}
		else if (locatorBy == "xpath") {
			WebElement elm = driver.findElement(AppiumBy.xpath(element));
			try {
				returnText = elm.getText();
				retrieved = true;
			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.xpath(element));
				returnText = elm.getText();
				retrieved = true;
			}	
		}
		*/
		if(retrieved == true) {
			ExtentReportsUtil.pass(returnText + " retrieved from " + element);
			ExtentReportsUtil.logger.log(LogStatus.PASS, 	ExtentReportsUtil.logger.addScreenCapture(getScreenshotPass()));
		}else {
			ExtentReportsUtil.pass("Text was not retrieved from " + element);
			SetupEnv.fail = 1;
			ExtentReportsUtil.logger.log(LogStatus.FAIL, 	ExtentReportsUtil.logger.addScreenCapture(getScreenshot()));
		}
		return returnText;
	}
	/*
	public void verifyAccNumCount(String element) {
		WebElement elm = driver.findElement(AppiumBy.id(element));
		if(elm.getText().length() == 6) {
			ExtentReportsUtil.pass(element + "count is exactly 6");
			ExtentReportsUtil.logger.log(LogStatus.PASS, 	ExtentReportsUtil.logger.addScreenCapture(getScreenshotPass()));
		}else {
			ExtentReportsUtil.pass(element + "count is not 6");
			SetupEnv.fail = 1;
			ExtentReportsUtil.logger.log(LogStatus.FAIL, 	ExtentReportsUtil.logger.addScreenCapture(getScreenshot()));
		}
	}
	public void verifyAccPinCount(String element) {
		WebElement elm = driver.findElement(AppiumBy.id(element));
		if(elm.getText().length() == 4) {
			ExtentReportsUtil.pass(element + "count is exactly 4");
			ExtentReportsUtil.logger.log(LogStatus.PASS, 	ExtentReportsUtil.logger.addScreenCapture(getScreenshotPass()));
		}else {
			ExtentReportsUtil.pass(element + "count is not 4");
			SetupEnv.fail = 1;
			ExtentReportsUtil.logger.log(LogStatus.FAIL, 	ExtentReportsUtil.logger.addScreenCapture(getScreenshot()));
		}
	}
	*/
}
