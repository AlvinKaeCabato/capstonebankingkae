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
	 * ​Step 1: Launch BankingApp  using .apk file. 	 * Step 2: Create an account  
	 * Step 3: Login into account using valid mpin 
	 * Step 4: Click on Details  
	 * Step 5: Validated all the details are same as entered. 
	 * 
	 */
	@Test(priority=0)
	public void BAA_01() throws Exception{
		ExtentReportsUtil.logstep("BAA_01 - Create Account - " + driver.getCapabilities().getCapability("deviceModel"));
		BaseMethods bm = new BaseMethods(driver);
		//Step 1 - Setup run to launch app before class is started
		
		//Step 2 - Create an account
		bm.clickElement(SplashPage.createAccount);
		/*
		bm.sendTextToElement(CreateAccountPage.nameText, configProp.getProperty("user1name"));
		bm.sendTextToElement(CreateAccountPage.add1Text, configProp.getProperty("user1add1"));
		bm.sendTextToElement(CreateAccountPage.add2Text, configProp.getProperty("user1add2"));
		bm.sendTextToElement(CreateAccountPage.accNumText, configProp.getProperty("user1acc"));
		bm.sendTextToElement(CreateAccountPage.pinNumText, configProp.getProperty("user1pin"));
		bm.sendTextToElement(CreateAccountPage.currBalText, configProp.getProperty("user1currbal"));
		*/
		bm.sendTextToElement(CreateAccountPage.nameText, getInputData("Alvin Kae Cabato","username"));
		bm.sendTextToElement(CreateAccountPage.add1Text, getInputData("Alvin Kae Cabato","address1"));
		bm.sendTextToElement(CreateAccountPage.add2Text, getInputData("Alvin Kae Cabato","address2"));
		bm.sendTextToElement(CreateAccountPage.accNumText, getInputData("Alvin Kae Cabato","accnum"));
		bm.sendTextToElement(CreateAccountPage.pinNumText, getInputData("Alvin Kae Cabato","pinnum"));
		bm.sendTextToElement(CreateAccountPage.currBalText, getInputData("Alvin Kae Cabato","currbal"));
		bm.clickElement(CreateAccountPage.saveUserBtn);
		mobileAlertHandle();
		
		//Step 3 - Login using mpin
		bm.clickElement(SplashPage.loginApp);
		bm.sendTextToElement(LoginPage.enterPin,getInputData("Alvin Kae Cabato","pinnum"));
		bm.clickElement(LoginPage.loginBtn);
		
		//Step 4 - Click on Account informations
		bm.clickElement(UserPage.accInfo);
		
		//Step 5 - Validate values from the page is equal to the input items
		/*
		bm.verifyDataIsSame(ViewAccountPage.nameView, configVer.getProperty("user1name"));
		bm.verifyDataIsSame(ViewAccountPage.add1View, configVer.getProperty("user1add1"));
		bm.verifyDataIsSame(ViewAccountPage.add2View, configVer.getProperty("user1add2"));
		bm.verifyDataIsSame(ViewAccountPage.accNumView, configVer.getProperty("user1acc"));
		bm.verifyDataIsSame(ViewAccountPage.pinNumView, configVer.getProperty("user1pin"));
		*/
		bm.verifyDataIsSame(ViewAccountPage.nameView, getInputData("Alvin Kae Cabato","username"));
		bm.verifyDataIsSame(ViewAccountPage.add1View, getInputData("Alvin Kae Cabato","address1"));
		bm.verifyDataIsSame(ViewAccountPage.add2View, getInputData("Alvin Kae Cabato","address2"));
		bm.verifyDataIsSame(ViewAccountPage.accNumView, getInputData("Alvin Kae Cabato","accnum"));
		bm.verifyDataIsSame(ViewAccountPage.pinNumView, getInputData("Alvin Kae Cabato","pinnum"));
		Float f = Float.parseFloat(bm.getTextFromElement(ViewAccountPage.currBalView));
		//Float g = Float.parseFloat(configVer.getProperty("user1currbal"));
		Float g = Float.parseFloat(getInputData("Alvin Kae Cabato","currbal"));
		bm.verifyDataIsSameFloat(f, g);

		//Clean up step - Logging out
		navigateBack();
		bm.clickElement(UserPage.logout); 
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
		bm.clickElement(SplashPage.loginApp);
		//bm.sendTextToElement(LoginPage.enterPin, configProp.getProperty("user1pin"));
		bm.sendTextToElement(LoginPage.enterPin, getInputData("Alvin Kae Cabato","pinnum"));
		bm.clickElement(LoginPage.loginBtn);
		
		//Step 3 - Click on Add Transaction
		bm.clickElement(UserPage.addTranc);
		
		//Step 4 - Edit information for transfer
		/*
		bm.sendTextToElement(AddTransactionPage.editTextDesc,configVer.getProperty("user1trandesc"));
		bm.sendTextToElement(AddTransactionPage.editTextAmt,configVer.getProperty("user1trancamt"));
		*/
		bm.sendTextToElement(AddTransactionPage.editTextDesc,getInputData("Alvin Kae Cabato","activeloan"));
		bm.sendTextToElement(AddTransactionPage.editTextAmt,getInputData("Alvin Kae Cabato","loanamt"));
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

/*
		bm.verifyDataIsSameDirComp(desc, configProp.getProperty("user1trandesc"));
		bm.verifyDataIsSameDirComp(amt, configProp.getProperty("user1trancamt"));
*/
		bm.verifyDataIsSameDirComp(desc, getVerifyData("Alvin Kae Cabato","activeloan"));
		bm.verifyDataIsSameDirComp(amt, getVerifyData("Alvin Kae Cabato","loanamt"));
		
		//Clean up Step - Logout and tear down
		//navigateBack();
		//bm.clickElement(UserPage.logout);
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
		//ExtentReportsUtil.info("Reopening App");
		
		//Step 2 - Create another account 
		bm.clickElement(SplashPage.createAccount);
		/*
		bm.sendTextToElement(CreateAccountPage.nameText, configProp.getProperty("user2name"));
		bm.sendTextToElement(CreateAccountPage.add1Text, configProp.getProperty("user2add1"));
		bm.sendTextToElement(CreateAccountPage.add2Text, configProp.getProperty("user2add2"));
		bm.sendTextToElement(CreateAccountPage.accNumText, configProp.getProperty("user2acc"));
		bm.sendTextToElement(CreateAccountPage.pinNumText, configProp.getProperty("user2pin"));
		bm.sendTextToElement(CreateAccountPage.currBalText, configProp.getProperty("user2currbal"));
		*/
		bm.sendTextToElement(CreateAccountPage.nameText, getInputData("Alvin Jae Cabato","username"));
		bm.sendTextToElement(CreateAccountPage.add1Text, getInputData("Alvin Jae Cabato","address1"));
		bm.sendTextToElement(CreateAccountPage.add2Text, getInputData("Alvin Jae Cabato","address2"));
		bm.sendTextToElement(CreateAccountPage.accNumText, getInputData("Alvin Jae Cabato","accnum"));
		bm.sendTextToElement(CreateAccountPage.pinNumText, getInputData("Alvin Jae Cabato","pinnum"));
		bm.sendTextToElement(CreateAccountPage.currBalText, getInputData("Alvin Jae Cabato","currbal"));
		bm.clickElement(CreateAccountPage.saveUserBtn);
		mobileAlertHandle();

		//Step 3 - Login app using created account
		bm.clickElement(SplashPage.loginApp);
		bm.sendTextToElement(LoginPage.enterPin,getInputData("Alvin Jae Cabato","pinnum"));
		//bm.sendTextToElement(LoginPage.enterPin,configProp.getProperty("user2pin"));
		bm.clickElement(LoginPage.loginBtn);

		//Step 4 - Transfer money on add transaction
		bm.clickElement(UserPage.addTranc);
		/*
		bm.sendTextToElement(AddTransactionPage.editTextDesc,configVer.getProperty("user2trandesc"));
		bm.sendTextToElement(AddTransactionPage.editTextAmt,configVer.getProperty("user2trancamt"));
		*/
		bm.sendTextToElement(AddTransactionPage.editTextDesc,getInputData("Alvin Jae Cabato","activeloan"));
		bm.sendTextToElement(AddTransactionPage.editTextAmt,getInputData("Alvin Jae Cabato","loanamt"));
		bm.clickElement(AddTransactionPage.addTrancButton);
		
		//Step 5 - Return by handling the Alert
		mobileAlertHandle();

		//Step 6 - Click on View Transaction
		bm.clickElement(UserPage.viewTranc);
		
		//Step 7 - Validate items on page are same as input items
		String s = bm.getTextFromElement(ViewTransactionPage.viewTrancText);
		String[] x = s.split("\\n");
		String desc, amt;
		desc = x[0];
		amt = x[1];
		/*
		bm.verifyDataIsSameDirComp(desc, configProp.getProperty("user2trandesc"));
		bm.verifyDataIsSameDirComp(amt, configProp.getProperty("user2trancamt"));
		*/
		bm.verifyDataIsSameDirComp(desc, getVerifyData("Alvin Jae Cabato","activeloan"));
		bm.verifyDataIsSameDirComp(amt, getVerifyData("Alvin Jae Cabato","loanamt"));
	}
	
}
