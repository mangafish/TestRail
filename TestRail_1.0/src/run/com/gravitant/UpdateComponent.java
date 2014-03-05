package com.gravitant;

import java.io.FileReader;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UpdateComponent {
	public  WebDriver driver;
	int totalTestSuiteNumber = 0;
	int testsInTestSuite = 0;
	
	public static void main(String[] args) throws Exception {
		UpdateComponent test = new UpdateComponent(); 
		test.start();
	}
	
	public void start() throws Exception{
		Util util = new Util();
		util.setGlobalWaitTime(10);
		util.launchBrowser("Firefox");
		util.navigateToUrl("https://gravitant.testrail.com/index.php?/auth/login");
		util.enterText("id", "name", "ramakanth.manga@gravitant.com");
		util.enterText("id", "password", "v2Piu&gr7");
		util.clickButton("xpath", "//*[@id='authContent']/form/ul/li[4]/button");//click login button
		util.clickLink("xpath", "//*[@id='project-1']/td[2]/div[1]/a");
		util.clickLink("xpath", "//*[@id='mainMenu']/ul/li[5]/a");
		//Thread.sleep(2000);
		totalTestSuiteNumber = util.getTestSuiteNumber("xpath", "html/body/div[1]/table");
		for(int i=1; i<=totalTestSuiteNumber-1;i++){
			String testSuiteXpath = "html/body/div[1]/table/tbody/tr/td[1]/div[3]/table/tbody/tr[" + i + "]/td[2]/div[1]/a";
			util.openTestSuite(testSuiteXpath);
			System.out.println(util.getNumberOfSections("html/body/div[1]/table/tbody/tr/td[1]/div[3]/div[7]/div/div"));
			//testsInTestSuite = util.getTestCaseNumber("xpath", "html/body/div[1]/table/tbody/tr/td[1]/div[3]/div[7]/div/div/div[1]/table");
			String testCaseXpath = "html/body/div[1]/table/tbody/tr/td[1]/div[3]";
			util.openTestCase(testCaseXpath);
		}
        
		//CSVReader testCaseReader = new CSVReader(new FileReader(currentTestPath));
	   // List<String[]> testCaseContent = testCaseReader.readAll();
    	/*for(int j=1; j<testCaseContent.size(); j++){
    		util.setErrorFlag(false);
	    	testStepRow = testCaseContent.get(j);
	    	if(!testStepRow[0].contains("#") && !testStepRow[0].contains("Step")){
	    		testStepNumber = Integer.parseInt(testStepRow[0]);
			    testStepPageName = testStepRow[2];
			    testStepObjectName = testStepRow[3];
			    action = testStepRow[4];
			    testStep = testStepRow[1];
	        	util.setCurrentTestStep(testStep);
	        	util.setCurrentTestStepNumber(testStepNumber);
			    testData = util.getTestData(testStepPageName, testStepObjectName);
			    util.executeAction(testStepPageName, testStepObjectName, action, testData);
	    	}
		}*/
	}
}
