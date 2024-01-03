package callPack;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import utilsPack.ExtentReportsUtil;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
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
	
	//Driver declaration
	public BaseMethods(AndroidDriver driver) {
		this.driver = driver;
		
	}
	
	//Negative screenshot call
	public String getScreenshot() {
		//Variable Declaration
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
	
	//Positive screenshot call
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
	
	//method to click an element
	public void clickElement(String element) {
		boolean clicked = false;
			WebElement elm = driver.findElement(AppiumBy.id(element));
			try {
				elm.click();
				clicked = true;
			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.id(element));
				elm.click();
				clicked = true;
			}
		
		if (clicked == true) {
			System.out.println(element + " was Clicked");
			ExtentReportsUtil.pass(element + " was Clicked");
			ExtentReportsUtil.logger.log(LogStatus.PASS, 
					ExtentReportsUtil.logger.addScreenCapture(getScreenshotPass()));
			/*
			 * 
			 * Additional checks
			 * 
			 * 
			 */
			if(element.contains("AccountNumber")) {
				if(elm.getText().length() != 6) {
					System.out.println("Account created but account number is not the right size");
					ExtentReportsUtil.logger.log(LogStatus.FAIL, 
							ExtentReportsUtil.logger.addScreenCapture(getScreenshot()));
				}
			}
			if(element.contains("PINno")) {
				if(elm.getText().length() != 4) {
					System.out.println("Account created but pin number is not the right size");
					ExtentReportsUtil.logger.log(LogStatus.FAIL, 
							ExtentReportsUtil.logger.addScreenCapture(getScreenshot()));
				}
			}
		}
		else {
			System.out.println(element + " was NOT Clicked");
			ExtentReportsUtil.fail(element + " was NOT Clicked");
			SetupEnv.fail = 1;
			ExtentReportsUtil.logger.log(LogStatus.FAIL, 
					ExtentReportsUtil.logger.addScreenCapture(getScreenshot()));
		}
		
	}
	
	//method to send text to an element
	public void sendTextToElement(String element, String value) {
		
		boolean enteredText = false;

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
		/*
		if (enteredText == true) {
			System.out.println("Send text value to element: " + element);	
			ExtentReportsUtil.pass("Send text value to element: " + element);
			ExtentReportsUtil.logger.log(LogStatus.PASS, ExtentReportsUtil.logger.addScreenCapture(getScreenshotPass()));
		}
		else {
			System.out.println("Unable to send text value to element: " + element);	
			ExtentReportsUtil.fail("Unable to send text value to element: " + element);
			SetupEnv.fail = 1;
			ExtentReportsUtil.logger.log(LogStatus.FAIL, ExtentReportsUtil.logger.addScreenCapture(getScreenshot()));
		}
		*/
	}
	
	//method to verify input item is same as database items
	public void verifyDataIsSame(String element, String value) {
		String inputText = "";

			WebElement elm = driver.findElement(AppiumBy.id(element));
			try {
				inputText = elm.getText();		
			
			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.id(element));
				inputText = elm.getText();		
				
			}
			

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
	
	//method to verify float input is same as database
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
	
	//method for direct comparison to data
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
	
	//method to get text from element
	public String getTextFromElement(String element) {

		boolean retrieved = false;
		String returnText = "";

			WebElement elm = driver.findElement(AppiumBy.id(element));
			try {
				returnText = elm.getText();
				retrieved = true;
			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.id(element));
				returnText = elm.getText();
				retrieved = true;
			}	

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
	
	public boolean isAlertPresent() {
	    try {
	        driver.switchTo().alert();
	        System.out.println("Switch to Alert");
	        return true;
	    } catch (Exception e) {
	    	System.out.println("Exception alert not found");
	        return false;
	    }
	}
	public void mobileAlertHandle() {
	    if (isAlertPresent()) {
	        Alert alert = driver.switchTo().alert();
	        alert.accept();        
	   }else {
			System.out.println("Alert was expected here but was not found, case fails");
			ExtentReportsUtil.logger.log(LogStatus.FAIL, 
					ExtentReportsUtil.logger.addScreenCapture(getScreenshot()));
	   }
	}
}
