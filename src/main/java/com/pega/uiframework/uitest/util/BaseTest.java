package com.pega.uiframework.uitest.util;


import com.pega.uiframework.utils.LoggerUtils;
import com.pega.uiframework.utils.ObjectRepository;
import com.pega.uiframework.utils.ProjectConfigurator;
import com.pega.uiframework.utils.Xls_Reader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.io.File;
import java.net.URL;
import java.util.Properties;

public class BaseTest
{
    public static String cbt=System.getProperty("user.dir")+"/SecureTunnel/cbt-tunnels-macos";
    protected static Properties config;
    public Xls_Reader xls = new Xls_Reader(Constants.DATA_XLS_PATH);
    public ExtentReports extent = ExtentManager.getInstance();
    public ExtentTest test;
    public Integer testiteration=0;
    protected WebDriver driver;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(ITestContext context) throws Exception {
        config = ProjectConfigurator.initializeProjectConfigurationsFromFile("project.properties");
        ObjectRepository.setRepositoryDirectory(config.getProperty("object.repository.dir"));
        // extent = new ExtentReports(System.getProperty("user.dir")+"/reports/TestReport.html", true); //Provide Desired Report Directory Location and Name
        // extent.loadConfig(new File("extent-config.xml")); //Supporting File for Extent Reporting
        // extent.addSystemInfo("Environment","QA"); //It will provide Execution Machine Information
        securetunnel();
    }

    @AfterSuite(alwaysRun = true)
    public void removeFile() throws Exception {
        String filepath = System.getProperty("user.dir") + "\\Data.properties";
        File file = new File(filepath);
        if (file.exists()) {
            System.out.println("File exists and deletion in progress..");
            Boolean status = file.delete();
            Thread.sleep(3000);
            System.out.println(" Deletion status: " + status);
        }

    }

    //To enable Cross Browser Testing

    @Parameters(value={"os", "browser"})
    @BeforeTest(alwaysRun = true)
    public void setUp(String os, String browser) throws Exception{

        test = extent.startTest( (this.getClass().getSimpleName())); //Test Case Start Here
        test.assignAuthor("kalyan Mekala"); //Test Script Author Name
        test.assignCategory( " :: API VERSION - "); //Test Category Defined Here
        DesiredCapabilities capability = new DesiredCapabilities();
        capability.setCapability("name","Pega Academy tests");
        capability.setCapability("os_api_name", os);
        capability.setCapability("browser_api_name", browser);
        capability.setCapability("record_video", "true");
        capability.setCapability("record_network", "true");
        capability.setCapability("max_duration", "6000");
        driver = new RemoteWebDriver(
                new URL("http://" + config.getProperty("username") + ":" + config.getProperty("auth_key") + "@hub.crossbrowsertesting.com:80/wd/hub"),
                capability);
        driver.manage().deleteAllCookies();
        driver.get(config.getProperty("url"));
        Dimension dimension=new Dimension(1400,900);
        driver.manage().window().setSize(dimension);
    }

    //To execute in local machine

       /* @BeforeTest(alwaysRun = true)
    public void beforeClass(ITestContext context) throws Exception {
        final String browser = context.getCurrentXmlTest().getParameter("browser");
        final String version = context.getCurrentXmlTest().getParameter("version");
       final String platform = context.getCurrentXmlTest().getParameter("platform");
        DesiredCapabilities caps = new WebCapabilitiesBuilder().addBrowser(browser)
                .addBrowserDriverExecutablePath(config.getProperty("chrome.driver.path")).addVersion(version)
                .addPlatform(platform).build();

        driver = new WebDriverFactory().createDriver(caps);
        driver.manage().deleteAllCookies();
        driver.get(config.getProperty("url"));
        Dimension dimension=new Dimension(1400,900);
        driver.manage().window().setSize(dimension);
    }*/

    @AfterTest(alwaysRun = true)
    public void afterClass(ITestContext context) throws Exception {
        if (null != driver) {
            driver.close();
            driver.quit();
        }
        if (extent != null) {
            extent.endTest(test);
            extent.flush();
        }
        LoggerUtils.stopTestLogging();
    }


    public void securetunnel() throws Exception{
        Process p = Runtime.getRuntime().exec(System.getProperty("user.dir")+"/SecureTunnel/cbt-tunnels-linux --username smitha.vijayakumar%40in.pega.com --authkey udbb51c6e48ebd0b");
        Thread.sleep(10000);
    }

}
