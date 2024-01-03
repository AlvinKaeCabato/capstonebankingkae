package casesPack;


import org.testng.annotations.Test;
import callPack.SetupEnv;
import utilsPack.ExtentReportsUtil;
import callPack.BaseMethods;
import pagesPack.AddTransactionPage;
import pagesPack.CreateAccountPage;
import pagesPack.LoginPage;
import pagesPack.SplashPage;
import pagesPack.UserPage;
import pagesPack.ViewAccountPage;
import pagesPack.ViewTransactionPage;

public class MainRunClass extends SetupEnv{
	
	
	/*
	 * BAA_01
	 * ​Step 1: Launch BankingApp  using .apk file. 	 
	 * Step 2: Create an account  
	 * Step 3: Login into account using valid mpin 
	 * Step 4: Click on Details  
	 * Step 5: Validate all the details are same as entered. 
	 * 
	 */
	@Test(priority=0)
	public void BAA_01() throws Exception{
		ExtentReportsUtil.logstep("BAA_01 - Create Account - " + driver.getCapabilities().getCapability("deviceModel"));
		BaseMethods bm = new BaseMethods(driver);
		//Step 1 - Setup run to launch app before class is started
		
		//Step 2 - Create an account
		bm.clickElement(SplashPage.createAccount,"BAA_01");
		bm.sendTextToElement(CreateAccountPage.nameText, getInputData(configProj.getProperty("user1"),"username"));
		bm.sendTextToElement(CreateAccountPage.add1Text, getInputData(configProj.getProperty("user1"),"address1"));
		bm.sendTextToElement(CreateAccountPage.add2Text, getInputData(configProj.getProperty("user1"),"address2"));
		bm.sendTextToElement(CreateAccountPage.accNumText, getInputData(configProj.getProperty("user1"),"accnum"));
		bm.sendTextToElement(CreateAccountPage.pinNumText, getInputData(configProj.getProperty("user1"),"pinnum"));
		bm.sendTextToElement(CreateAccountPage.currBalText, getInputData(configProj.getProperty("user1"),"currbal"));
		bm.clickElement(CreateAccountPage.saveUserBtn,"BAA_01");
		bm.mobileAlertHandle();
		
		//Step 3 - Login using mpin
		bm.clickElement(SplashPage.loginApp,"BAA_01");
		bm.sendTextToElement(LoginPage.enterPin,getInputData(configProj.getProperty("user1"),"pinnum"));
		bm.clickElement(LoginPage.loginBtn,"BAA_01");
		
		//Step 4 - Click on Account informations
		bm.clickElement(UserPage.accInfo,"BAA_01");
		
		//Step 5 - Validate values from the page is equal to the input items
		bm.verifyDataIsSame(ViewAccountPage.nameView, getInputData(configProj.getProperty("user1"),"username"));
		bm.verifyDataIsSame(ViewAccountPage.add1View, getInputData(configProj.getProperty("user1"),"address1"));
		bm.verifyDataIsSame(ViewAccountPage.add2View, getInputData(configProj.getProperty("user1"),"address2"));
		bm.verifyDataIsSame(ViewAccountPage.accNumView, getInputData(configProj.getProperty("user1"),"accnum"));
		bm.verifyDataIsSame(ViewAccountPage.pinNumView, getInputData(configProj.getProperty("user1"),"pinnum"));
		Float f = Float.parseFloat(bm.getTextFromElement(ViewAccountPage.currBalView));
		Float g = Float.parseFloat(getInputData(configProj.getProperty("user1"),"currbal"));
		bm.verifyDataIsSameFloat(f, g);

		//Clean up step - Logging out
		navigateBack();
		bm.clickElement(UserPage.logout,"BAA_01"); 
	}
	
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
	public void BAA_02() throws Exception{
		ExtentReportsUtil.logstep("BAA_02 - transaction check - " + driver.getCapabilities().getCapability("deviceModel"));
		//Step 1 - App is already launch. Closing the app from the previous TC causes data to be not present
		BaseMethods bm = new BaseMethods(driver);
		//Step 2 - Login into account
		bm.clickElement(SplashPage.loginApp,"BAA_02");
		bm.sendTextToElement(LoginPage.enterPin, getInputData(configProj.getProperty("user1"),"pinnum"));
		bm.clickElement(LoginPage.loginBtn,"BAA_02");
		
		//Step 3 - Click on Add Transaction
		bm.clickElement(UserPage.addTranc,"BAA_02");
		
		//Step 4 - Edit information for transfer
		bm.sendTextToElement(AddTransactionPage.editTextDesc,getInputData(configProj.getProperty("user1"),"activeloan"));
		bm.sendTextToElement(AddTransactionPage.editTextAmt,getInputData(configProj.getProperty("user1"),"loanamt"));
		bm.clickElement(AddTransactionPage.addTrancButton,"BAA_02");

		//Step 5 - Returning after clicking on Alert
		bm.mobileAlertHandle();
		
		//Step 6 - View transaction
		bm.clickElement(UserPage.viewTranc,"BAA_02");
		
		//Step 7 - Validate info on page to input items
		String s = bm.getTextFromElement(ViewTransactionPage.viewTrancText);
		String[] x = s.split("\\n");
		String desc, amt;
		desc = x[0];
		amt = x[1];
		bm.verifyDataIsSameDirComp(desc, getVerifyData(configProj.getProperty("user1"),"activeloan"));
		bm.verifyDataIsSameDirComp(amt, getVerifyData(configProj.getProperty("user1"),"loanamt"));
		
		//Clean up Step - Logout and tear down
		tearDown();
	}
	
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
	public void BAA_03() throws Exception{
		ExtentReportsUtil.logstep("BAA_03 - Combined Use Case - " + driver.getCapabilities().getCapability("deviceModel"));
		//Step 1 - Launch Banking App with Setup
		BaseMethods bm = new BaseMethods(driver);
		setUp();
		
		//Step 2 - Create another account 
		bm.clickElement(SplashPage.createAccount,"BAA_02");
		bm.sendTextToElement(CreateAccountPage.nameText, getInputData(configProj.getProperty("user2"),"username"));
		bm.sendTextToElement(CreateAccountPage.add1Text, getInputData(configProj.getProperty("user2"),"address1"));
		bm.sendTextToElement(CreateAccountPage.add2Text, getInputData(configProj.getProperty("user2"),"address2"));
		bm.sendTextToElement(CreateAccountPage.accNumText, getInputData(configProj.getProperty("user2"),"accnum"));
		bm.sendTextToElement(CreateAccountPage.pinNumText, getInputData(configProj.getProperty("user2"),"pinnum"));
		bm.sendTextToElement(CreateAccountPage.currBalText, getInputData(configProj.getProperty("user2"),"currbal"));
		bm.clickElement(CreateAccountPage.saveUserBtn,"BAA_02");
		bm.mobileAlertHandle();

		//Step 3 - Login app using created account
		bm.clickElement(SplashPage.loginApp,"BAA_02");
		bm.sendTextToElement(LoginPage.enterPin,getInputData(configProj.getProperty("user2"),"pinnum"));
		bm.clickElement(LoginPage.loginBtn,"BAA_02");

		//Step 4 - Transfer money on add transaction
		bm.clickElement(UserPage.addTranc,"BAA_02");
		bm.sendTextToElement(AddTransactionPage.editTextDesc,getInputData(configProj.getProperty("user2"),"activeloan"));
		bm.sendTextToElement(AddTransactionPage.editTextAmt,getInputData(configProj.getProperty("user2"),"loanamt"));
		bm.clickElement(AddTransactionPage.addTrancButton,"BAA_02");
		
		//Step 5 - Return by handling the Alert
		bm.mobileAlertHandle();

		//Step 6 - Click on View Transaction
		bm.clickElement(UserPage.viewTranc,"BAA_02");
		
		//Step 7 - Validate items on page are same as input items
		String s = bm.getTextFromElement(ViewTransactionPage.viewTrancText);
		String[] x = s.split("\\n");
		String desc, amt;
		desc = x[0];
		amt = x[1];
		bm.verifyDataIsSameDirComp(desc, getVerifyData(configProj.getProperty("user2"),"activeloan"));
		bm.verifyDataIsSameDirComp(amt, getVerifyData(configProj.getProperty("user2"),"loanamt"));
	}
	
}
