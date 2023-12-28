package casesPack;

import org.testng.annotations.Test;

import callPack.BaseMethods;
import callPack.SetupEnv;
import pagesPack.AddTransactionPage;
import pagesPack.LoginPage;
import pagesPack.SplashPage;
import pagesPack.UserPage;
import pagesPack.ViewTransactionPage;
import utilsPack.ExtentReportsUtil;

public class BAA_02 extends SetupEnv {
	/*
	 * BAA_02
	 * Step 1: Launch BankingApp app using .apk file. 
	 * Step 2: Login into account using valid pin 
	 * Step 3: Click on Add Transaction 
	 * Step 4: Transfer money with a valid description. 
	 * Step 5: Click on back  
	 * Step 6: Click on View transaction 
	 * Step 7: Validate the  transaction description made is present 
	 */
	@Test(priority=1)
	public void main() throws Exception{
		
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
