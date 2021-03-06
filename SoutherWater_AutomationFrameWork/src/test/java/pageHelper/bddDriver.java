package pageHelper;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.Log;
import utils.pageController;

import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;

import com.cucumber.listener.Reporter;

import core.apiDriver;
import core.baseDriver;
import core.webDriver;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;


public class bddDriver
{
	public static int  itr;
	public static ThreadLocal<String> TestName= new InheritableThreadLocal<>();
	public static final ThreadLocal<WebDriver> WEB_DRIVER_THREAD_LOCAL = new InheritableThreadLocal<>();
	public static final ThreadLocal<RequestSpecification> API_DRIVER_THREAD_LOCAL = new InheritableThreadLocal<>();
	public static final ThreadLocal<Response> API_RESPONCE_THREAD_LOCAL = new InheritableThreadLocal<>();
	public static final ThreadLocal<String> Message = new InheritableThreadLocal<>();
	
	@Before("@API")
	public void APIsetup(Scenario s){
		apiDriver ApiDriver=new baseDriver();
		API_DRIVER_THREAD_LOCAL.set(ApiDriver.apiinit("https://d40105b4-005d-4643-afe4-8ff0c97b9374.mock.pstmn.io/"));
		
		API_RESPONCE_THREAD_LOCAL.set(null);
		
	}
	
	@Before("@WEB")
	public void Websetup(Scenario s) throws Exception{
	
		webDriver webDriver=new baseDriver();
		WEB_DRIVER_THREAD_LOCAL.set(webDriver.webinit("chrome", "https://www.southernwater.co.uk/pay-bill", false));
		//WEB_DRIVER_THREAD_LOCAL.set(webDriver.webinit("chrome", false));
		
		
		
	}
	@After("@WEB")
	public void TearDown(Scenario s) throws IOException{
	
		 if (s.isFailed()) {
			 String sourcePath = "data:image/png;base64,"+((TakesScreenshot)WEB_DRIVER_THREAD_LOCAL.get()).
		                getScreenshotAs(OutputType.BASE64);;
		                Reporter.addScreenCaptureFromPath(sourcePath);
			 
		 }
		WEB_DRIVER_THREAD_LOCAL.get().quit();
	}
	
	
	@org.testng.annotations.BeforeSuite
	public void BeforeSuite(){
	itr=0;
	DOMConfigurator.configure("log4j.xml");
	}
	
	public static WebDriver getWebDriver(){
		return WEB_DRIVER_THREAD_LOCAL.get();
	}
	public RequestSpecification getApiDriver(){
		return API_DRIVER_THREAD_LOCAL.get();
	}
}