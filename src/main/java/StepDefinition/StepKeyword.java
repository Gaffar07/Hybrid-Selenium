package StepDefinition;

import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.ExtentTest;

//import com.codoid.products.fillo.Connection;


public class StepKeyword extends StepDefinition {
	ExtentTest test;
	public StepKeyword(WebDriver driver) {
		super(driver);
		//this.test=test;
		
		
		
	}
	
	public void executeTestStep(WebDriver driver,String testScenarioID, String step) throws Exception { 
		switch (step){
/*
  Anmol 11-06-2020 For creating and executing TCS Keyword 
 */
		case "Login_Creation":
			tcsLogin(driver, testScenarioID, step);
			break;
		case "Navigate_TO_Quote":
			//navigateToQuote(driver, testScenarioID, workbook, stepGroup,customAssert);
			break;
		case "Create_Individual_Pricing":
			//fillquickIndividualPricing(driver, testScenarioID, workbook, stepGroup,customAssert);
			break;	
		case "Create_FamilyFloater_Pricing":
			//fillquickFamilyPricing(driver, testScenarioID, workbook, stepGroup,customAssert);
			break;
		case "Create_MultiIndividual_Pricing":
			//fillquickMultiIndividualPricing(driver, testScenarioID, workbook, stepGroup,customAssert);
			break;
		case "Create_Individual_Quote_Creation":
			//fillquickIndividualQuote(driver, testScenarioID, workbook, stepGroup,customAssert);
			break;		
		case "Create_FamilyFloater_Quote_Creation":
			//fillquickFamilyQuoteCreation(driver, testScenarioID, workbook, stepGroup,customAssert);
			break;
		case "Create_MultiIndividual_Quote_Creation":
			//fillquickMultiIndividualQuoteCreation(driver, testScenarioID, workbook, stepGroup,customAssert);
			break;
			
			
		case "Create_Individual_Finalize":
			//fillIndividualQuoteCreationFinalize(driver, testScenarioID, workbook, stepGroup,customAssert);
			break;	
			
		case "Create_Individual_BasicDetails":
			//fillquickBasicDetails(driver, testScenarioID, workbook, stepGroup,customAssert);
			break;		
			
			
		case "Create_Individual_Collection":
		    //fillquickCollection(driver, testScenarioID, workbook, stepGroup,customAssert);
		break;		
		
		case "Create_Individual_Payment":
			//fillquickPayment(driver, testScenarioID, workbook, stepGroup,customAssert);
			break;
			
			
		case "Create_Individual_MemberInfo":
			//fillMemberInfo(driver, testScenarioID, workbook, stepGroup,customAssert);
			break;	
			
	
		case "Create_Individual_PPMC":
			//fillPPMCInfo(driver, testScenarioID, workbook, stepGroup,customAssert);
			break;	

		case "Create_Individual_UWR":
			//fillUWRInfo(driver, testScenarioID, workbook, stepGroup,customAssert);
	        break;
	        
	        
		case "Create_Individual_OtherUserLogin":
			//fillOtherUserLogin(driver, testScenarioID, workbook, stepGroup,customAssert);
			break;
	        
		case "Create_Discount_And_Loading":
			//DiscountLoading(driver, testScenarioID, workbook, stepGroup,customAssert);
			break;
			
		case "Create_Family_STP":
			//STPfamilyfill(driver, testScenarioID, workbook, stepGroup,customAssert);
			break;
			
		case "Create_Family_Multi_NSTP":
			//NSTPfamilyfill(driver, testScenarioID, workbook, stepGroup,customAssert);
			break;
			
		case "Create_Individual_NSTP":
			//NSTPindividualfill(driver, testScenarioID, workbook, stepGroup,customAssert);
			break;	
				
			
		case "Create_NSTP_UWR":
			//UWRNSTPindividualfill(driver, testScenarioID, workbook, stepGroup,customAssert);
			break;	
			
		case "Logout":
			tcsLogout(driver, testScenarioID, step);
			break;
					
			
	}
}
	
	
	
	

}
