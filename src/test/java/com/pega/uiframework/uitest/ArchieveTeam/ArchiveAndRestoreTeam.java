/*
*Objective:
* Step1: Logon to my teams application with client member.
* Step2: Create team and add pega member.
* Step3: Redirect to home page and click on team created.
* Step4: Archive team and redirect to archive teams page.
* Step5: Restore archived team.
* Step6: Redirect to home page, goto archive teams page for validations, redirect to home page and logoff from application.
* Checks Included: Create team page text, Active teams count validated after team created, Verify team exist in active teams after team created,
*                  Verify Active teams count, archive teams count and team exists in active teams after team restored.
*/

package com.pega.uiframework.uitest.ArchieveTeam;

import com.pega.uiframework.core.BasePage;
import com.pega.uiframework.uitest.pages.CreateTeamPage;
import com.pega.uiframework.uitest.pages.HomePage;
import com.pega.uiframework.uitest.pages.MyTeamsLoginPage;
import com.pega.uiframework.uitest.pages.TeamPage;
import com.pega.uiframework.uitest.util.BaseTest;
import com.pega.uiframework.uitest.util.Constants;
import com.pega.uiframework.uitest.util.DataUtil;
import com.relevantcodes.extentreports.LogStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

/**
 * Created by marac on 7/12/2017.
 */
public class ArchiveAndRestoreTeam extends BaseTest {
    String testDataName = "Client";
    String testCaseName = "ArchiveAndRestoreTeam";
    private static final Logger LOGGER = LoggerFactory.getLogger(BasePage.class.getName());

    @Test(groups = {"regression"}, dataProvider = "getData")
    public void testArchiveAndRestore(Hashtable<String, String> data) throws Exception {
        try {
            test = extent.startTest(testCaseName);

            if (!DataUtil.isTestExecutable(xls, testCaseName) || data.get(Constants.RUNMODE_COL).equals("N")) {
                test.log(LogStatus.SKIP, "Skipping the test as Rnumode is N");
                DataUtil.setResult(xls, testCaseName, "Test Skipped");
                throw new SkipException("Skipping the test as Runmode is N");
            }
            DataUtil.setResult(xls, testCaseName, "Test Started");
            test.log(LogStatus.INFO, "Starting test");
            MyTeamsLoginPage loginpage = new MyTeamsLoginPage(driver);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
           // loginpage.ClickonSignInLandingPage();
            loginpage.enterEmail(data.get("Username"));
            loginpage.enterPassword(data.get("Password"));
            loginpage.ClickonSubmit();

            LOGGER.info("Now showing my teams home page.");
            HomePage homepage = new HomePage(driver);
            homepage.switchFrame();
            homepage.switchFrame();

            int count = homepage.activeTeamsCount();
            LOGGER.info("Active teams count:" + count);

            LOGGER.info("Clicking on create button");
            homepage.ClickonCreate();
            Thread.sleep(10000);
            LOGGER.info("Now showing create team page");
            CreateTeamPage createPage = new CreateTeamPage(driver);
            String details[] = createPage.enterTeamDetails();

            Thread.sleep(10000);
            LOGGER.info("Now showing team page");
            TeamPage teamPage = new TeamPage(driver);
            teamPage.addmember(data.get("PegaID"));
            Thread.sleep(5000);
            teamPage.ClickonViewAllTeams();

            Thread.sleep(10000);
            LOGGER.info("add-count:" + homepage.activeTeamsCount());
            if (count == (homepage.activeTeamsCount() - 1))
                LOGGER.info("Count validated after team created.");
            else {
                DataUtil.setResult(xls, testCaseName, "Test Failed: Count not correct");
                throw new Exception("Count not correct");
            }
            if (homepage.verifyTeamExist(details[0],"Active"))
                LOGGER.info("Team Created");
            else {
                DataUtil.setResult(xls, testCaseName, "Test Failed: Team not created");
                throw new Exception("Team not created.");
            }

            homepage.clickOnTeam(details[0]);
            Thread.sleep(10000);
            teamPage.archiveTeam();

            int archiveCount = homepage.archiveTeamsCount();

            LOGGER.info("Archive Teams Count:" + archiveCount);
            homepage.clickOnTeam(details[0]);
            teamPage.restoreArchiveTeam();


            if (count == (homepage.activeTeamsCount() - 1))
                LOGGER.info("Active teams count validated after team restored.");
            else{
                DataUtil.setResult(xls, testCaseName, "Test Failed: Active teams count not correct after team restored.");
                throw new Exception("Active teams count not correct after team restored.");
            }
            if (homepage.verifyTeamExist(details[0],"Active"))
                LOGGER.info("Team Restored");
            else{
                DataUtil.setResult(xls, testCaseName, "Test Failed: Active teams list not showing restored team.");
                throw new Exception("Active teams list not showing restored team.");
            }

            homepage.clickOnViewActiveOrArchiveTeams();
            Thread.sleep(5000);
            if (archiveCount == (homepage.archiveTeamsCount() + 1))
                LOGGER.info("Archive teams count validated after team restored.");
            else{
                DataUtil.setResult(xls, testCaseName, "Test Failed: Archive teams count not correct after team restored.");
                throw new Exception("Archive teams count not correct after team restored.");
            }
            homepage.clickOnViewActiveOrArchiveTeams();
            Thread.sleep(5000);

            homepage.Logout();
            DataUtil.setResult(xls, testCaseName, "Test Passed");
        }catch (Exception e) {
            DataUtil.setResult(xls, testCaseName, "Test Failed:" + e.getMessage());
        }
    }

    @AfterMethod
    public void quit() {
        if (extent != null) {
            extent.endTest(test);
            extent.flush();
        }
    }

    @DataProvider
    public Object[][] getData() {
        return DataUtil.getData(xls, testDataName);
    }
}
