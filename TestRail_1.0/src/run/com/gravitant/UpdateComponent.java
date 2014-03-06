package com.gravitant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UpdateComponent{
	public  WebDriver driver;
	int totalTestSuiteNumber = 0;
	int sectionsInTestSuite = 0;
	int testsInSection = 0;
	int numberOfSubSections = 0;
	String testCaseName = null;
	String currentComponentName = null;
	String changedComponentName = null;
	
	public static void main(String[] args) throws Exception {
		UpdateComponent test = new UpdateComponent(); 
		test.start();
	}
	
	public void start() throws Exception{
		Util util = new Util();
		util.setGlobalWaitTime(10);
		util.launchBrowser("Firefox");
		util.navigateToUrl("https://gravitant.testrail.com/index.php?/auth/login");
		util.enterText("id", "name", "ramakanth.manga@gravitant.com");//enter username
		util.enterText("id", "password", "v2Piu&gr7");//enter password
		util.clickButton("xpath", "//*[@id='authContent']/form/ul/li[4]/button");//click login button
		util.clickLink("xpath", "//*[@id='project-1']/td[2]/div[1]/a");//click 'cloudMatrix' link in Dashboard
		util.clickLink("xpath", "html/body/div[1]/div[2]/div[3]/ul/li[6]/a");//click 'Test Suites & Cases' tab
		totalTestSuiteNumber = util.getTestSuiteNumber("xpath", "html/body/div[1]/table"); //get total number of test suites in cloudMatrix project
		for(int i=3; i<=totalTestSuiteNumber-1;i++){
			String testSuiteXpath = "html/body/div[1]/table/tbody/tr/td[1]/div[3]/table/tbody/tr[" + i + "]/td[2]/div[1]/a";
			util.openTestSuite(testSuiteXpath);
			sectionsInTestSuite = util.getNumberOfSections("html/body/div[1]/table/tbody/tr/td[1]/div[3]/child::*/div/div");
			if(sectionsInTestSuite==1){
				System.out.println("*******************************************");
				System.out.println("\t" + "Section : " + util.getSectionName("html/body/div[1]/table/tbody/tr/td[1]/div[3]/div[6]/div/div/div/div[1]/span[1]"));
				numberOfSubSections = 
				testsInSection = util.getNumberOfTestCases("html/body/div[1]/table/tbody/tr/td[1]/div[3]/div[6]/div/div/div/table");
				for(int k=1; k<=testsInSection-1; k++){
					testCaseName = util.clickLink("xpath", "html/body/div[1]/table/tbody/tr/td[1]/div[3]/div[6]/div/div/div/table/tbody/tr[" + (k+1) + "]/td[4]/a[1]/span");//open the test case
					if(!testCaseName.contains("No test cases")){
						System.out.println("\t\t" + "******************************************");
						System.out.println("\t\t" + "Opening test case: " + testCaseName);
						util.clickButton("xpath", "html/body/div[1]/table/tbody/tr/td[1]/div[1]/div/span[1]/a");//click the 'Edit' button in the test case
						currentComponentName = util.getSelectedListBoxItem("xpath", "html/body/div[1]/table/tbody/tr/td[1]/div[3]/form/div/table/tbody/tr[2]/td[3]/select");
						if(currentComponentName!=null){
							util.changeComponentName("html/body/div[1]/table/tbody/tr/td[1]/div[3]/form/div/table/tbody/tr[2]/td[3]/select", currentComponentName);
							changedComponentName = util.getSelectedListBoxItem("xpath", "html/body/div[1]/table/tbody/tr/td[1]/div[3]/form/div/table/tbody/tr[2]/td[3]/select");
							System.out.println("\t\t" + "Changed: " + "\"" + currentComponentName + "\"" + " to: " + "\"" + changedComponentName + "\"");
							util.clickButton("xpath", "html/body/div[1]/table/tbody/tr/td[1]/div[3]/form/ul[3]/li/button");//click the 'Save Test Case' button
							util.clickLink("xpath", "html/body/div[1]/table/tbody/tr/td[1]/div[2]/a[3]");//click the test suite link to go back to list of test cases
						}else{
							util.clickLink("xpath", "//*[@id='content']/form/ul[3]/li/a[contains(., 'Cancel')]");//click the test suite link to go back to list of test cases
							util.clickLink("xpath", "//*[@id='breadcrumb']/a[3]");
						}
					}else{continue;}
				}
			}else{
				for(int j=1;j<=sectionsInTestSuite; j++){
					System.out.println("*******************************************");
					System.out.println("\t" + "Section : " + util.getSectionName("html/body/div[1]/table/tbody/tr/td[1]/div[3]/child::*/div/div/div[" + j + "]/div[1]/span[1]"));
					testsInSection = util.getNumberOfTestCases("html/body/div[1]/table/tbody/tr/td[1]/div[3]/child::*/div/div/div[" + j + "]/table");
					for(int k=1; k<=testsInSection-1; k++){
						testCaseName = util.clickLink("xpath", "html/body/div[1]/table/tbody/tr/td[1]/div[3]/child::*/div/div/div[" + j + "]/table/tbody/tr[" + (k+1) + "]/td[4]/a[1]/span");//open the test case
						if(!testCaseName.contains("No test cases")){
							System.out.println("\t\t" + "******************************************");
							System.out.println("\t\t" + "Opening test case: " + testCaseName);
							util.clickButton("xpath", "html/body/div[1]/table/tbody/tr/td[1]/div[1]/div/span[1]/a");//click the 'Edit' button in the test case
							currentComponentName = util.getSelectedListBoxItem("xpath", "html/body/div[1]/table/tbody/tr/td[1]/div[3]/form/div/table/tbody/tr[2]/td[3]/select");
							if(currentComponentName.isEmpty()){
								util.clickLink("xpath", "//*[@id='content']/form/ul[3]/li/a[contains(., 'Cancel')]");//click the test suite link to go back to list of test cases
								util.clickLink("xpath", "//*[@id='breadcrumb']/a[3]");
							}else{
								util.changeComponentName("html/body/div[1]/table/tbody/tr/td[1]/div[3]/form/div/table/tbody/tr[2]/td[3]/select", currentComponentName);
								changedComponentName = util.getSelectedListBoxItem("xpath", "html/body/div[1]/table/tbody/tr/td[1]/div[3]/form/div/table/tbody/tr[2]/td[3]/select");
								System.out.println("\t\t" + "Changed: " + "\"" + currentComponentName + "\"" + " to: " + "\"" + changedComponentName + "\"");
								util.clickButton("xpath", "html/body/div[1]/table/tbody/tr/td[1]/div[3]/form/ul[3]/li/button");//click the 'Save Test Case' button
								util.clickLink("xpath", "html/body/div[1]/table/tbody/tr/td[1]/div[2]/a[3]");//click the test suite link to go back to list of test cases
							}
						}else{continue;}
					}
				}
			}
			System.out.println("*******************************************");
			util.clickLink("xpath", "html/body/div[1]/table/tbody/tr/td[1]/div[2]/a");
		}
	}
}
