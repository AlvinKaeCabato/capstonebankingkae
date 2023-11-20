package casesPack;

import org.testng.Assert;
import org.testng.annotations.Test;

import callPack.SetupEnv;

public class MainRunClass extends SetupEnv{
	
	@Test(priority=1)
	public void BAA_01(){
		createAccount().click();
		nameText().sendKeys("Alvin Kae Cabato");
		add1Text().sendKeys("96 Nordstrum Residences");
		add2Text().sendKeys("Quezon City");
		accNumText().sendKeys("123321");
		pinNumText().sendKeys("9231");
		currBalText().sendKeys("15992.0");
		saveUserBtn().click();
		loginApp().click();
		enterPin().sendKeys("9231");
		loginBtn().click();
		accInfo().click();
		Assert.assertEquals(nameView().getText(),"Alvin Kae Cabato");
		Assert.assertEquals(add1View().getText(),"96 Nordstrum Residences");
		Assert.assertEquals(add2View().getText(),"Quezon City");
		Assert.assertEquals(accNumView().getText(),"123321");
		Assert.assertEquals(pinNumView().getText(),"9231");
		Assert.assertEquals(currBalView().getText(),"15992.0");		
	}
	
	@Test(priority=2)
	public void BAA_02(){
		
	}
	
	@Test(priority=3)
	public void BAA_03(){
		
	}
	
}
