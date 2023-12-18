package testPack;
import callPack.SetupEnv;
import casesPack.MainRunClass;

import org.testng.Assert;
import org.testng.annotations.Test;

public class FullRun extends SetupEnv {
	MainRunClass mrc = new MainRunClass();
	
	@Test
	public void main() throws Exception {

			//BAA_01();
			
			//BAA_02();
			
			mrc.BAA_03();

	}

}
