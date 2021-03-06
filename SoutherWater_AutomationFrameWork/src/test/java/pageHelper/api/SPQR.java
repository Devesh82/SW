package pageHelper.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.DocumentException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import core.apiHelper;
import core.baseDriverHelper;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pageHelper.bddDriver;
import com.cucumber.listener.Reporter;


public class SPQR{
	String URI;
	
	String EmployeeID;
	public apiHelper apiDriver;
	private bddDriver DriverInstance;
	
	public SPQR(RequestSpecification dr,Response respoence)
	{
		apiDriver=new baseDriverHelper(dr,respoence);	
	}

	public SPQR(bddDriver contextSteps) throws Exception {
		this.DriverInstance = contextSteps;
		
		System.out.println(this.DriverInstance);
		apiDriver=new baseDriverHelper(DriverInstance.getApiDriver(),DriverInstance.API_RESPONCE_THREAD_LOCAL.get());
	}
	
	
	
	@Given("^I have submit a SPQR request for \"([^\"]*)\"$") 
	public void SubmitSPQRRequest(String s) throws Exception {
		if(s.equals("EIRCODE1"))
			URI="spqr?Scenario=1";
		else
			URI="spqr?Scenario=2";	
		apiDriver.updateAttributeInRequestBody("SPQR_Input_Request.xml","//ORDER/TARGET_DETAILS/TARGET_IDENTIFIER/EIRCODE",s);
		//apiDriver.updateAttributeInRequestBody("SPQR_Input_Request.xml","//ORDER/TARGET_DETAILS/TARGET_IDENTIFIER/Telephone",s2);
		apiDriver.generatePayLoad();
		apiDriver.submitRequest(Method.POST,URI);
		apiDriver.assertStatusCode(apiDriver.RESPONSE_CODE_200);
		//apiDriver.assertStringInResponceBody(s);
		
	}
	
	
	@Then("^Responce should contains the Servey Required as \"([^\"]*)\"$")
	public void VerifyEmployeeDetailCreated(String s) throws IOException, SAXException, ParserConfigurationException, DocumentException {
		
		apiDriver.assertResponceBodyAttribute("//NOTIFICATION/NOTIFICATION_DATA/AVAILABILITY/AVAIL_SERVICES/AVAIL_SERVICE/SURVEY_DETAILS/SURVEY_REQUIRED", s);
		
	}
	
	
	
	}

