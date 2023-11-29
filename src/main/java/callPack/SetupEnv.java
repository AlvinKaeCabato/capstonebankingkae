package callPack;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;

import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;

public class SetupEnv {
	String SPARK_LOG_FILE = "logs/ExtentSparkReport_" + java.time.LocalDate.now()+".html";
	protected ExtentReports report = new ExtentReports();
	ExtentSparkReporter spark = new ExtentSparkReporter(SPARK_LOG_FILE);
	
	
	//For Browserstack and driver
	
	String userName = System.getenv("BROWSERSTACK_USERNAME");
	String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
	String buildName = System.getenv("BROWSERSTACK_BUILD_NAME");
	String app = System.getenv("BROWSERSTACK_APP_ID");
    public AndroidDriver driver;
    
    //For Input/OutputFiles
    protected FileInputStream configFis;
    public File outputCsv;
    private final String CONFIG_FILE_PATH="//src//main//resources//input.xlsx";
    private final String LOG_OUTPUT_FILE="//logs//Log_" + java.time.LocalDate.now();
    private final String FINAL_LOG_FILE = LOG_OUTPUT_FILE;
    public static Properties configProp = new Properties();
    protected File file = new File("");
    protected File logfile = new File("");
    public List<String[]> dataLines = new ArrayList<>();
    public static XSSFWorkbook workbook;
    public static XSSFSheet sheet;
    public static XSSFRow row;
    
    @BeforeClass(alwaysRun=true)
    public void setUp() throws Exception {
    	
    	//REPORT
    	report.attachReporter(spark);
    	
    	
    	
    	//input files
    	configFis = new FileInputStream(file.getAbsoluteFile()
    			+ CONFIG_FILE_PATH);
    	//configProp.load(configFis);
    	outputCsv = new File(logfile.getAbsoluteFile()
    			+ FINAL_LOG_FILE);
    	
    	try {
    	configFis = new FileInputStream(file.getAbsoluteFile()
        			+ CONFIG_FILE_PATH);
        workbook = new XSSFWorkbook(configFis);
        sheet = workbook.getSheet("Input");
        System.out.println("Opening workbook");
    	}catch(FileNotFoundException e) {
    		e.printStackTrace();
    	}catch (IOException e) {
            e.printStackTrace();
        }
    	
        try {
            configFis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }  
        
        Iterator<Row> rowIterator = sheet.iterator();
        DataFormatter formatter = new DataFormatter();
        String a,b;
        while( rowIterator.hasNext() )
        {
            Row row = rowIterator.next();
            a = formatter.formatCellValue(row.getCell(0));
            b = formatter.formatCellValue(row.getCell(1));
            configProp.setProperty(a,b);
        }
        

    	System.out.println("HERE_-------------------------->>>: " + buildName);
    	System.out.println("HERE_-------------------------->>>: " + userName);
    	System.out.println("HERE_-------------------------->>>: " + accessKey);
    	
        MutableCapabilities capabilities = new UiAutomator2Options();
		capabilities.setCapability("Project", "API demo App automation");
		capabilities.setCapability("build", buildName);
		capabilities.setCapability("name", "Capstone Test");
        capabilities.setCapability("app", "bs://9356b925037e89a16ec29ac7e380c1d6b3f1954c");
        driver = new AndroidDriver(new URL("https://"+userName+":"+accessKey+"@hub-cloud.browserstack.com/wd/hub"),capabilities);
        report.createTest("Setup").log(Status.PASS, "App Launched!");

    }

    @AfterClass(alwaysRun=true)
    public void tearDown() throws Exception {
        try (PrintWriter pw = new PrintWriter(outputCsv)) {
            dataLines.stream()
              .map(this::convertToCSV)
              .forEach(pw::println);
        }
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
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("createAccount")));
	}
	
	public WebElement loginApp() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("loginButton")));
	}
    
    /*
     * 
     * Create Account Page 
     * 
     * 
     */
	public WebElement nameText() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("Name")));
	}
	
	public WebElement add1Text() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("Address1")));
	}
	
	public WebElement add2Text() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("Address2")));
	}
	
	public WebElement accNumText() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("AccountNumber")));	
	}
	public WebElement pinNumText() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("PINno")));
	}
	
	public WebElement currBalText() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("Balance")));
	}
	
	public WebElement saveUserBtn() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("saveUser")));
	}
	
	public WebElement alertTitle() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("alertTitle")));
	}
	public WebElement alertOK() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("button3")));
	}
	
	
	/*
	 * 
	 * Login Page
	 * 
	 */
	public WebElement enterPin() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("enteredPIN")));
	}
	public WebElement loginBtn() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("loginButton")));
	}
	/*
	 * 
	 * User Page
	 * 
	 * 
	 */
	public WebElement accInfo() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("button")));
	}
	
	public WebElement addTranc() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("buttonAddTrans")));
	}
	
	public WebElement viewTranc() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("buttonViewTrans")));
	}
	
	public WebElement logout() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("buttonLogout")));
	}
	
	/*
	 * 
	 * Account page
	 * 
	 */
	public WebElement nameView() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("textViewName")));
	}
	
	public WebElement add1View() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("textViewAddress1")));
	}
	
	public WebElement add2View() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("textViewAddress2")));
	}
	
	public WebElement accNumView() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("textViewAccNo")));
	}
	
	public WebElement pinNumView() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("textViewPIN")));
	}
	
	public WebElement currBalView() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("textViewBalance")));
	}
	
	/*
	 * 
	 * 
	 * Add Transaction
	 * 
	 * 
	 * 
	 */
	public WebElement editTextDesc() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("editTextDecription")));
	}
	public WebElement editTextAmt() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("editTextAmount")));
	}
	public WebElement addTrancBtn() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("button2")));
	}
	
	/*
	 * 
	 * View Transaction
	 * 
	 */
	public WebElement viewTrancText() {
		return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
		          ExpectedConditions.elementToBeClickable(AppiumBy.id("textViewTransactions")));
	}
	/*
	 * GENERAL
	 * 
	 * 
	 */
	public boolean isAlertPresent() {
	    try {
	        driver.switchTo().alert();
	        System.out.println("ALERT IS PRESENT !! ");
	        return true;
	    } catch (Exception e) {
	    	System.out.println("ALERT IS NOT PRESENT !! ");
	        return false;
	    }
	}
	public void mobileAlertHandle() {
	    if (isAlertPresent()) {
	        Alert alert = driver.switchTo().alert();
	        alert.accept();
	   }
	}
	public void navigateBack() {
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
	}
	public String convertToCSV(String[] data) {
	    return Stream.of(data)
	      .map(this::escapeSpecialCharacters)
	      .collect(Collectors.joining(","));
	}
	public String escapeSpecialCharacters(String data) {
	    String escapedData = data.replaceAll("\\R", " ");
	    if (data.contains(",") || data.contains("\"") || data.contains("'")) {
	        data = data.replace("\"", "\"\"");
	        escapedData = "\"" + data + "\"";
	    }
	    return escapedData;
	}



	   
}

    

