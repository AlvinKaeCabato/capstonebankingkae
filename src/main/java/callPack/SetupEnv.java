package callPack;

import utilsPack.ExtentReportsUtil;
import utilsPack.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
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
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.ITestResult;

import io.appium.java_client.Setting;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;


public class SetupEnv {	
	//For Browserstack and driver
	String userName = System.getenv("BROWSERSTACK_USERNAME");
	String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
	String buildName = System.getenv("BROWSERSTACK_BUILD_NAME");
	String app = System.getenv("BROWSERSTACK_APP_ID");
    public AndroidDriver driver;
    
    //For Input/OutputFiles
    protected FileInputStream configFis;
    protected FileInputStream configFisVer;
    public File outputCsv;
    private final String CONFIG_FILE_PATH="//src//main//resources//inputComp.xlsx";
    private final String VERIFY_FILE_PATH="//src//main//resources//VerifyPass.xlsx";
    private final String PROJ_CONFIG_FILE_PATH="//src//main//resources//capstone.properties";
    private final String LOG_OUTPUT_FILE="\\Reports\\ExtRepLog_" + java.time.LocalDate.now()+".html";
    private final String FINAL_LOG_FILE = LOG_OUTPUT_FILE;
    private final String CSV_LOG_OUTPUT_FILE="\\logs\\Log_" + java.time.LocalDate.now();
    private final String CSV_FINAL_LOG_FILE = CSV_LOG_OUTPUT_FILE;
    public static Properties configProp = new Properties();
    public static Properties configVer = new Properties();
    protected File file = new File("");
    protected File fileVer = new File("");
    protected File logfile = new File("");
    protected File rootConfig = new File("");
    public List<String[]> dataLines = new ArrayList<>();
    public static XSSFWorkbook workbook,workbookVer;
    public static XSSFSheet sheet,sheetVer;
    public static XSSFRow row,rowVer;
    public static Properties configProj = new Properties();
    
    
    public static int fail;
    
    @BeforeSuite
	public void setupReport() {
		ExtentReportsUtil.startExtentReport(FINAL_LOG_FILE);
	}  
    
    @BeforeMethod
    public void resetFlag() {
    	fail = 0;
    }
    @BeforeClass(alwaysRun=true)
    public void setUpWorkfiles() throws Exception{
     	//input files
    	configFis = new FileInputStream(file.getAbsoluteFile()
    			+ CONFIG_FILE_PATH);
    	outputCsv = new File(logfile.getAbsoluteFile()+ CSV_FINAL_LOG_FILE);
    	configProj.load(new FileInputStream(rootConfig.getAbsoluteFile()
    			+ PROJ_CONFIG_FILE_PATH));
    	
    	//Workbook for input items
    	try {
    	configFis = new FileInputStream(file.getAbsoluteFile()
        			+ CONFIG_FILE_PATH);
        workbook = new XSSFWorkbook(configFis);
        sheet = workbook.getSheet("in");
        sheetVer = workbook.getSheet("ver");
        System.out.println("Opening input items workbook");
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
        
        
      //Workbook for verification items
        /*
    	try {
    	configFisVer = new FileInputStream(fileVer.getAbsoluteFile()
        			+ VERIFY_FILE_PATH);
        workbookVer = new XSSFWorkbook(configFisVer);
        sheetVer = workbookVer.getSheet("in");
        System.out.println("Opening verification items workbook");
    	}catch(FileNotFoundException e) {
    		e.printStackTrace();
    	}catch (IOException e) {
            e.printStackTrace();
        }
    	
        try {
            configFisVer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }  
        */
        Iterator<Row> rowIteratorVer = sheetVer.iterator();
        DataFormatter formatterVer = new DataFormatter();
        String c,d;
        while( rowIteratorVer.hasNext() )
        {
            Row rowVer = rowIteratorVer.next();
            c = formatterVer.formatCellValue(rowVer.getCell(0));
            d = formatterVer.formatCellValue(rowVer.getCell(1));
            configVer.setProperty(c,d);
        }
        
    }
    
    @BeforeClass(alwaysRun=true)
    public void setUp() throws Exception {
    	fail = 0;    	
   
        //Setup driver
        MutableCapabilities capabilities = new UiAutomator2Options();
        
		capabilities.setCapability("Project", configProj.getProperty("project"));
		capabilities.setCapability("build", buildName);
		capabilities.setCapability("name", configProj.getProperty("name"));
        capabilities.setCapability("app", configProj.getProperty("app"));
        capabilities.setCapability("browserstack.networklogs", "true");
        driver = new AndroidDriver(new URL("https://"+userName+":"+accessKey+"@hub-cloud.browserstack.com/wd/hub"),capabilities);
        /*
        capabilities.setCapability("deviceName", "Pixel4KaeTest");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("platformVersion",13.0);
        driver = new AndroidDriver(new URL("https://"+userName+":"+accessKey+"@hub-cloud.browserstack.com/wd/hub"),capabilities);
        */
        driver.setSetting(Setting.WAIT_FOR_IDLE_TIMEOUT, 10);
        driver.setSetting(Setting.WAIT_FOR_SELECTOR_TIMEOUT, 10);
        
    }
    
    @AfterClass(alwaysRun=true)
    public void tearDown() throws Exception {
        driver.quit();
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
    @AfterMethod(alwaysRun=true)
    public void afterMethod(ITestResult result) throws Exception {		    	
    	ExtentReportsUtil.getExtentResult(result);
		Logger.log("Results Retrieved");
    }
    
    @AfterSuite
	public void endTest() throws Exception {
        try (PrintWriter pw = new PrintWriter(outputCsv)) {
            dataLines.stream()
              .map(this::convertToCSV)
              .forEach(pw::println);
        }
		ExtentReportsUtil.flushExtentReport();
		ExtentReportsUtil.closeExtentReport();
		Logger.log("End Report");
	}

	   
}

    

