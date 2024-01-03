package callPack;

import utilsPack.ExtentReportsUtil;
import utilsPack.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

import java.util.Properties;


import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.MutableCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
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
    private final String CONFIG_FILE_PATH="//src//main//resources//FullPath.xlsx";
    private final String PROJ_CONFIG_FILE_PATH="//src//main//resources//capstone.properties";
    private final String LOG_OUTPUT_FILE="\\Reports\\ExtRepLog_" + java.time.LocalDate.now()+".html";
    private final String FINAL_LOG_FILE = LOG_OUTPUT_FILE;
    public static Properties configProp = new Properties();
    public static Properties configVer = new Properties();
    protected File file = new File("");
    protected File fileVer = new File("");
    protected File logfile = new File("");
    protected File rootConfig = new File("");
    public static XSSFWorkbook workbook,workbookVer;
    public static XSSFSheet sheet,sheetVer;
    public static XSSFRow row,rowVer;
    public static Properties configProj = new Properties();
    
    
    //Setup Flags
    public static int fail;
    public static String prevsearchuser = "";
    public static int prevsearchindex = -1;
    
    @BeforeSuite
	public void setupReport() {
		ExtentReportsUtil.startExtentReport(FINAL_LOG_FILE);
	}  
    
    @BeforeMethod
    public void resetFlag() {
    	fail = 0;
    }
    @BeforeSuite(alwaysRun=true)
    public void setUpWorkfiles() throws Exception{
     	//input files
    	configFis = new FileInputStream(file.getAbsoluteFile()
    			+ CONFIG_FILE_PATH);
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
    
    @BeforeTest(alwaysRun=true)
    public void setUp() throws Exception {
    	fail = 0;    	
   
        //Setup driver and capabilities
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
    
    @AfterSuite(alwaysRun=true)
    public void tearDown() throws Exception {
    	//Driver teardown
    	 if(driver != null){
 		    driver.quit();
 		   }
    }
    

	/*
	 * GENERAL
	 * 
	 * 
	 */

	public void navigateBack() {
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
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

		ExtentReportsUtil.flushExtentReport();
		ExtentReportsUtil.closeExtentReport();
		Logger.log("End Report");
	}

	public String getInputData(String user,String col) {
		String Data = "";
        Iterator<Row> rowIterator = sheet.iterator();
        DataFormatter formatter = new DataFormatter();
        String a;
        int i = 0,cell=0;
        boolean found = false;
        switch(col) {
        	case "username": cell=0; break;
        	case "address1": cell=1; break;
        	case "address2": cell=2; break;
        	case "accnum": cell=3; break;
        	case "pinnum": cell=4; break;
        	case "currbal": cell=5; break;
        	case "activeloan": cell=6; break;
        	case "loanamt": cell=7; break;
        }
        
        if(!prevsearchuser.contains(user)) {
        	while( rowIterator.hasNext() )
        	{
        		Row row = rowIterator.next();
        		a = formatter.formatCellValue(row.getCell(0));
        		if(a.equals(user)) {
        			prevsearchuser = a;
        			prevsearchindex = i;
        			found = true;
        			break;
        		}
        		i++;
        	}
        	if(found=false) {
        		System.out.println("User not found");
        	}
        	System.out.println("User found, searching for data needed...");
        	Data = formatter.formatCellValue(sheet.getRow(prevsearchindex).getCell(cell));
        }else {
        	System.out.println("User already found previously, searching for data needed...");
        	Data = formatter.formatCellValue(sheet.getRow(prevsearchindex).getCell(cell));
        }
		return Data;
	}
	
	public String getVerifyData(String user,String col) {
		String Data = "";
        Iterator<Row> rowIterator = sheetVer.iterator();
        DataFormatter formatter = new DataFormatter();
        String a;
        int i = 0,cell=0;
        boolean found = false;
        switch(col) {
        	case "username": cell=0; break;
        	case "address1": cell=1; break;
        	case "address2": cell=2; break;
        	case "accnum": cell=3; break;
        	case "pinnum": cell=4; break;
        	case "currbal": cell=5; break;
        	case "activeloan": cell=6; break;
        	case "loanamt": cell=7; break;
        }
        
        if(!prevsearchuser.contains(user)) {
        	while( rowIterator.hasNext() )
        	{
        		Row row = rowIterator.next();
        		a = formatter.formatCellValue(row.getCell(0));
        		if(a.equals(user)) {
        			prevsearchuser = a;
        			prevsearchindex = i;
        			found = true;
        			break;
        		}
        		i++;
        	}
        	if(found=false) {
        		System.out.println("User not found");
        	}
        	System.out.println("User found, searching for data needed...");
        	Data = formatter.formatCellValue(sheet.getRow(prevsearchindex).getCell(cell));
        }else {
        	System.out.println("User already found previously, searching for data needed...");
        	Data = formatter.formatCellValue(sheet.getRow(prevsearchindex).getCell(cell));
        }
		return Data;
	}
}

    

