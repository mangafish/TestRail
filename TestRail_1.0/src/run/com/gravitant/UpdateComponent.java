package com.gravitant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
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
	int numberOfLevel1SubSections = 0;
	int numberOfLevel2SubSections = 0;
	int numberOfLevel3SubSections = 0;
	int testsInSection = 0;
	String tableXpath = null;
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
		this.tableXpath = "html/body/div[1]/table";
		totalTestSuiteNumber = util.getTestSuiteNumber("xpath", "html/body/div[1]/table"); //get total number of test suites in cloudMatrix project
		for(int i=11; i<=totalTestSuiteNumber-1;i++){
			String testSuiteXpath = tableXpath + "/tbody/tr/td[1]/div[3]/table/tbody/tr[" + i + "]/td[2]/div[1]/a";
			util.openTestSuite(testSuiteXpath, i);
			sectionsInTestSuite = util.getNumberOfSubItems(tableXpath, "//div[3]/child::*/div/div/div[starts-with(@id,'section')]");
			System.out.println("No. of Sections: " + sectionsInTestSuite);
			if(sectionsInTestSuite > 0){
				for(int j=1;j<=sectionsInTestSuite;j++){
					System.out.println("*******************************************");
					System.out.println("\t" + "Section : " + util.getSectionName(tableXpath + "/tbody/tr/td[1]/div[3]/child::*/div/div/div[" + j + "]/div[1]/span[1]"));
					testsInSection = (util.getNumberOfTestCases(tableXpath + "/tbody/tr/td[1]/div[3]/child::*/div/div/div[" + j + "]/table/tbody")) -1;
					util.updateTestCases(testsInSection, "/tbody/tr/td[1]/div[3]/child::*/div/div/div[" + j + "]/table/tbody");
					/******************************************/
					System.out.println("\t\t" + "**************************");
					numberOfLevel1SubSections = util.getNumberOfSubItems(tableXpath, "/tbody/tr/td[1]/div[3]/child::*/div/div/div[" + j + "]/div[4]/div");
					System.out.println("\t\t" + "No. of Level 1 Sub-sections: " + numberOfLevel1SubSections );
 					if(numberOfLevel1SubSections > 0){
 						for(int k=0;k<=numberOfLevel1SubSections;k++){
 							testsInSection = util.getNumberOfTestCases(tableXpath + "/tbody/tr/td[1]/div[3]/child::*/div/div/div[" + j + "]/div[4]/div[" + k + "]/table/tbody");
 	 						util.updateTestCases(testsInSection, "/tbody/tr/td[1]/div[3]/child::*/div/div/div[" + j + "]/div[4]/div[" + k + "]/table/tbody");
 						}
 					}
					/******************************************/
					System.out.println("\t\t\t" + "**************************");
					numberOfLevel2SubSections = util.getNumberOfSubItems(tableXpath, "/tbody/tr/td[1]/div[3]/child::*/div/div/div[" + j + "]/div[4]/div/div[4]/div");
					System.out.println("\t\t\t" + "No. of Level 2 Sub-sections: " + numberOfLevel2SubSections );
					if(numberOfLevel2SubSections > 0){
						for(int m=0;m<=numberOfLevel2SubSections;m++){
							testsInSection = util.getNumberOfTestCases(tableXpath + "/tbody/tr/td[1]/div[3]/child::*/div/div/div[" + j + "]/div[4]/div/div[4]/div[" + m + "]/table/tbody");
							util.updateTestCases(testsInSection, "/tbody/tr/td[1]/div[3]/child::*/div/div/div[" + j + "]/div[4]/div/div[4]/div[" + m + "]/table/tbody");
 						}
					}
				}
			}else{
				util.clickLink("xpath", "//*[@id='breadcrumb']/a");//if there are no test cases in the test suite go back to list of test suites
			}
			System.out.println("*******************************************");
			util.clickLink("xpath", "html/body/div[1]/table/tbody/tr/td[1]/div[2]/a");
		}
	}
}
