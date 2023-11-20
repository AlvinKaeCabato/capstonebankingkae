package callPack;


import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class SetupEnv {
	String userName = "alvinkaecabato_Avn8dc";
	//System.getenv("BROWSERSTACK_USERNAME");
	String accessKey = "JJPHDKxYzHu68nd6MVS7";
			//System.getenv("BROWSERSTACK_ACCESS_KEY");
	//String browserstackLocal = System.getenv("BROWSERSTACK_LOCAL");
	String buildName = "Capstone Build Kae";
			//System.getenv("BROWSERSTACK_BUILD_NAME");
	//String browserstackLocalIdentifier = System.getenv("BROWSERSTACK_LOCAL_IDENTIFIER");
	String app = System.getenv("BROWSERSTACK_APP_ID");
    public AndroidDriver driver;
    
    @BeforeMethod(alwaysRun=true)
    public void setUp() throws Exception {
    	/*
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("app", app);
        caps.setCapability("device", "Samsung Galaxy S21");
        caps.setCapability("build", buildName);
        driver = new AndroidDriver(new URL("https://"+userName+":"+accessKey+"@hub-cloud.browserstack.com/wd/hub"), caps);
    	*/
    	System.out.println("HERE_-------------------------->>>: " + buildName);
    	System.out.println("HERE_-------------------------->>>: ");
    	System.out.println("HERE_-------------------------->>>: ");
    	System.out.println("HERE_-------------------------->>>: ");
    	System.out.println("HERE_-------------------------->>>: ");
    	System.out.println("HERE_-------------------------->>>: " + userName);
    	System.out.println("HERE_-------------------------->>>: ");
    	System.out.println("HERE_-------------------------->>>: ");
    	System.out.println("HERE_-------------------------->>>: ");
    	System.out.println("HERE_-------------------------->>>: " + accessKey);
    	
        MutableCapabilities capabilities = new UiAutomator2Options();
		capabilities.setCapability("deviceName", "Samsung Galaxy S21");
		capabilities.setCapability("os_version", "12.0");
		capabilities.setCapability("Project", "API demo App automation");
		capabilities.setCapability("build", buildName);
		capabilities.setCapability("name", "Capstone Test");
        capabilities.setCapability("app", "bs://9356b925037e89a16ec29ac7e380c1d6b3f1954c");
        driver = new AndroidDriver(new URL("https://"+userName+":"+accessKey+"@hub-cloud.browserstack.com/wd/hub"),capabilities);
        /*
		UiAutomator2Options options = new UiAutomator2Options();
		
		options.setUdid("RF8N403BDGA");
		options.setCapability("appium:appPackage","io.appium.android.apis");
		options.setCapability("appium:appActivity","io.appium.android.apis.ApiDemos");
		options.setCapability("platformName", "Android");
		options.setCapability("appium:platformVersion", "12");
		options.setCapability("appium:automationName","uiautomator2");
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723"),options);
		*/
    }

    @AfterMethod(alwaysRun=true)
    public void tearDown() throws Exception {
        driver.quit();
    }
    
    /*--
     * 
     * 
     * Main Page
     * 
     * 
     */
    
	public WebElement createAccount() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("marcusobyrne.bankingapp:id/createAccount")));
	}
	
	public WebElement loginApp() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("marcusobyrne.bankingapp:id/loginButton")));
	}
    
    /*
     * 
     * Create Account Page 
     * 
     * 
     */
	public WebElement nameText() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("marcusobyrne.bankingapp:id/Name")));
	}
	
	public WebElement add1Text() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("marcusobyrne.bankingapp:id/Address1")));
	}
	
	public WebElement add2Text() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("marcusobyrne.bankingapp:id/Address2")));
	}
	
	public WebElement accNumText() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("marcusobyrne.bankingapp:id/AccountNumber")));	
	}
	public WebElement pinNumText() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("marcusobyrne.bankingapp:id/PINno")));
	}
	
	public WebElement currBalText() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("marcusobyrne.bankingapp:id/Balance")));
	}
	
	public WebElement saveUserBtn() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("marcusobyrne.bankingapp:id/saveUser")));
	}
	
	public WebElement alertTitle() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("android:id/alertTitle")));
	}
	
	
	
	/*
	 * 
	 * Login Page
	 * 
	 */
	public WebElement enterPin() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("marcusobyrne.bankingapp:id/enteredPIN")));
	}
	public WebElement loginBtn() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("marcusobyrne.bankingapp:id/loginButton")));
	}
	/*
	 * 
	 * User Page
	 * 
	 * 
	 */
	public WebElement accInfo() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("marcusobyrne.bankingapp:id/button")));
	}
	
	public WebElement addTranc() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("marcusobyrne.bankingapp:id/buttonAddTrans")));
	}
	
	public WebElement viewTranc() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("marcusobyrne.bankingapp:id/buttonViewTrans")));
	}
	
	public WebElement logout() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("marcusobyrne.bankingapp:id/buttonLogout")));
	}
	
	/*
	 * 
	 * Account page
	 * 
	 */
	public WebElement nameView() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("marcusobyrne.bankingapp:id/textViewName")));
	}
	
	public WebElement add1View() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("marcusobyrne.bankingapp:id/textViewAddress1")));
	}
	
	public WebElement add2View() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("marcusobyrne.bankingapp:id/textViewAddress2")));
	}
	
	public WebElement accNumView() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("marcusobyrne.bankingapp:id/textViewAccNo")));
	}
	
	public WebElement pinNumView() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("marcusobyrne.bankingapp:id/textViewAccNo")));
	}
	
	public WebElement currBalView() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("marcusobyrne.bankingapp:id/textViewBalance")));
	}
	
	
}

    

