package casesPack;

import org.testng.annotations.Test;

import callPack.BaseMethods;
import callPack.SetupEnv;
import pagesPack.AddTransactionPage;
import pagesPack.CreateAccountPage;
import pagesPack.LoginPage;
import pagesPack.SplashPage;
import pagesPack.UserPage;
import pagesPack.ViewAccountPage;
import pagesPack.ViewTransactionPage;
import utilsPack.ExtentReportsUtil;

public class BAA_01 extends SetupEnv {
	/*
	 * BAA_01
	 * ​Step 1: Launch BankingApp  using .apk file. 	 * Step 2: Create an account  
	 * Step 3: Login into account using valid mpin 
	 * Step 4: Click on Details  
	 * Step 5: Validated all the details are same as entered. 
	 * 
	 */
	@Test(priority=1)
	public void BAA01() throws Exception{
		ExtentReportsUtil.logstep("BAA_01 - Create Account - " + driver.getCapabilities().getCapability("deviceModel"));
		BaseMethods bm = new BaseMethods(driver);
		//Step 1 - Setup run to launch app before class is started
		
		//Step 2 - Create an account
		bm.clickElement(SplashPage.createAccount);
		bm.sendTextToElement(CreateAccountPage.nameText, configProp.getProperty("user1name"));
		bm.sendTextToElement(CreateAccountPage.add1Text, configProp.getProperty("user1add1"));
		bm.sendTextToElement(CreateAccountPage.add2Text, configProp.getProperty("user1add2"));
		bm.sendTextToElement(CreateAccountPage.accNumText, configProp.getProperty("user1acc"));
		bm.sendTextToElement(CreateAccountPage.pinNumText, configProp.getProperty("user1pin"));
		bm.sendTextToElement(CreateAccountPage.currBalText, configProp.getProperty("user1currbal"));
		bm.clickElement(CreateAccountPage.saveUserBtn);
		mobileAlertHandle();
		
		//Step 3 - Login using mpin
		bm.clickElement(SplashPage.loginApp);
		bm.sendTextToElement(LoginPage.enterPin,configProp.getProperty("user1pin"));
		bm.clickElement(LoginPage.loginBtn);
		
		//Step 4 - Click on Account informations
		bm.clickElement(UserPage.accInfo);
		
		//Step 5 - Validate values from the page is equal to the input items
		bm.verifyDataIsSame(ViewAccountPage.nameView, configVer.getProperty("user1name"));
		bm.verifyDataIsSame(ViewAccountPage.add1View, configVer.getProperty("user1add1"));
		bm.verifyDataIsSame(ViewAccountPage.add2View, configVer.getProperty("user1add2"));
		bm.verifyDataIsSame(ViewAccountPage.accNumView, configVer.getProperty("user1acc"));
		bm.verifyDataIsSame(ViewAccountPage.pinNumView, configVer.getProperty("user1pin"));
		Float f = Float.parseFloat(bm.getTextFromElement(ViewAccountPage.currBalView));
		Float g = Float.parseFloat(configVer.getProperty("user1currbal"));
		bm.verifyDataIsSameFloat(f, g);

		//Clean up step - Logging out
		navigateBack();
		bm.clickElement(UserPage.logout); 
	}
	@Test(priority=2)
	public void BAA02() throws Exception{
		
		ExtentReportsUtil.logstep("BAA_02 - transaction check - " + driver.getCapabilities().getCapability("deviceModel"));
		//Step 1 - App is already launch. Closing the app from the previous TC causes data to be not present
		BaseMethods bm = new BaseMethods(driver);
		//Step 2 - Login into account
		bm.clickElement(SplashPage.loginApp);
		bm.sendTextToElement(LoginPage.enterPin, configProp.getProperty("user1pin"));
		bm.clickElement(LoginPage.loginBtn);
		
		//Step 3 - Click on Add Transaction
		bm.clickElement(UserPage.addTranc);
		
		//Step 4 - Edit information for transfer
		bm.sendTextToElement(AddTransactionPage.editTextDesc,configVer.getProperty("user1trandesc"));
		bm.sendTextToElement(AddTransactionPage.editTextAmt,configVer.getProperty("user1trancamt"));
		bm.clickElement(AddTransactionPage.addTrancButton);

		//Step 5 - Returning after clicking on Alert
		mobileAlertHandle();
		
		//Step 6 - View transaction
		bm.clickElement(UserPage.viewTranc);
		
		//Step 7 - Validate info on page to input items
		String s = bm.getTextFromElement(ViewTransactionPage.viewTrancText);
		String[] x = s.split("\\n");
		String desc, amt;
		desc = x[0];
		amt = x[1];


		bm.verifyDataIsSameDirComp(desc, configProp.getProperty("user1trandesc"));
		bm.verifyDataIsSameDirComp(amt, configProp.getProperty("user1trancamt"));

		
		//Clean up Step - Logout and tear down
		//navigateBack();
		//bm.clickElement(UserPage.logout);
		tearDown();
	}
}
