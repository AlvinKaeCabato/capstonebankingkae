package casesPack;

import org.testng.annotations.Test;

import callPack.BaseMethods;
import callPack.SetupEnv;
import pagesPack.CreateAccountPage;
import pagesPack.LoginPage;
import pagesPack.SplashPage;
import pagesPack.UserPage;
import pagesPack.ViewAccountPage;
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
	@Test(priority=0)
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

}
