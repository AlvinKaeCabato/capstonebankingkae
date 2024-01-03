package casesPack;

import org.testng.annotations.Test;

import callPack.BaseMethods;
import callPack.SetupEnv;
import pagesPack.AddTransactionPage;
import pagesPack.CreateAccountPage;
import pagesPack.LoginPage;
import pagesPack.SplashPage;
import pagesPack.UserPage;
import pagesPack.ViewTransactionPage;
import utilsPack.ExtentReportsUtil;

public class BAA_03 extends SetupEnv {
	/*
	 * BAA_03
	 * ​Step 1: Launch BankingApp  using .apk file. 
	 * Step 2: Create a 2nd account  
	 * Step 3: Login into account using valid mpin 
	 * Step4:  Click on Add Transaction 
	 * Step 4: Transfer money with a valid description. 
	 * Step 5: Click on back  
	 * Step 6: Click on View transaction 
	 * Step 7: Validate the  transaction description from only current user should be present.  
	 */
	@Test(priority=2)
	public void BAA03() throws Exception{
		ExtentReportsUtil.logstep("BAA_03 - Combined Use Case - " + driver.getCapabilities().getCapability("deviceModel"));
		//Step 1 - Launch Banking App with Setup
		BaseMethods bm = new BaseMethods(driver);
		setUp();
		//ExtentReportsUtil.info("Reopening App");
		
		//Step 2 - Create another account 
		bm.clickElement(SplashPage.createAccount);
		bm.sendTextToElement(CreateAccountPage.nameText, configProp.getProperty("user2name"));
		bm.sendTextToElement(CreateAccountPage.add1Text, configProp.getProperty("user2add1"));
		bm.sendTextToElement(CreateAccountPage.add2Text, configProp.getProperty("user2add2"));
		bm.sendTextToElement(CreateAccountPage.accNumText, configProp.getProperty("user2acc"));
		bm.sendTextToElement(CreateAccountPage.pinNumText, configProp.getProperty("user2pin"));
		bm.sendTextToElement(CreateAccountPage.currBalText, configProp.getProperty("user2currbal"));
		bm.clickElement(CreateAccountPage.saveUserBtn);
		bm.mobileAlertHandle();

		//Step 3 - Login app using created account
		bm.clickElement(SplashPage.loginApp);
		bm.sendTextToElement(LoginPage.enterPin,configProp.getProperty("user2pin"));
		bm.clickElement(LoginPage.loginBtn);

		//Step 4 - Transfer money on add transaction
		bm.clickElement(UserPage.addTranc);
		bm.sendTextToElement(AddTransactionPage.editTextDesc,configVer.getProperty("user2trandesc"));
		bm.sendTextToElement(AddTransactionPage.editTextAmt,configVer.getProperty("user2trancamt"));
		bm.clickElement(AddTransactionPage.addTrancButton);
		
		//Step 5 - Return by handling the Alert
		bm.mobileAlertHandle();

		//Step 6 - Click on View Transaction
		bm.clickElement(UserPage.viewTranc);
		
		//Step 7 - Validate items on page are same as input items
		String s = bm.getTextFromElement(ViewTransactionPage.viewTrancText);
		String[] x = s.split("\\n");
		String desc, amt;
		desc = x[0];
		amt = x[1];
		
		bm.verifyDataIsSameDirComp(desc, configProp.getProperty("user2trandesc"));
		bm.verifyDataIsSameDirComp(amt, configProp.getProperty("user2trancamt"));
		
		
	}
}
