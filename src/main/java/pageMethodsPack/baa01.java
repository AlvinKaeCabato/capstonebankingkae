package pageMethodsPack;

import callPack.BaseMethods;
import io.appium.java_client.android.AndroidDriver;

public class baa01 extends BaseMethods {
	public baa01(AndroidDriver driver) {
		super(driver);
	} 
	
	public void validateCreateDataisCorrect(String[] element, String value) {
		verifyDataIsSame(element,value);
	}
	
}
