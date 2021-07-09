package testng.test.groups;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import webdriver.utils.Listener;

/**
 * The purpose of this class is to know how to work with groups
 * @author Jose Sanjuan Gonzalez
 *
 */
@Listeners(Listener.class)
public class Test_class1 {
	
	@Test(groups = { "regression" })
	public void regressionTestsForClass1() {
		System.out.println("*******************");
		System.out.println("Class: 1");
		System.out.println("Group: regression");
		System.out.println("*******************");
	}

	@Test(groups = { "progression" })
	public void progressionTestsForClass1() {
		System.out.println("*******************");
		System.out.println("Class: 1");
		System.out.println("Group: progression");
		System.out.println("*******************");
	}

	@Test(groups = { "regression", "other" })
	public void regressionAndOtherTestsForClass1() {
		System.out.println("*******************");
		System.out.println("Class: 1");
		System.out.println("Group: regression,other");
		System.out.println("*******************");
	}

}
