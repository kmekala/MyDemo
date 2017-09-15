package com.pega.uiframework.uitest.pages;

import com.pega.uiframework.core.BasePage;
import com.pega.uiframework.exception.InvalidLocatorStrategyException;
import com.pega.uiframework.exception.PropertyNotFoundException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by marac on 7/5/2017.
 */
public class CreateTeamPage extends BasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasePage.class.getName());
    public CreateTeamPage(WebDriver driver) throws IOException {
        super(driver);
    }

    //Verifies text on create team page.
    public void verifyCreateTeamtext() throws PropertyNotFoundException, InvalidLocatorStrategyException {
        String CreateTeamText = getText("CreateTeamPageText");
        if(CreateTeamText.equalsIgnoreCase("Create New team")){
            LOGGER.info("Create new team text is validated. Showing correctly." );
        }
    }

    //Enter team details: team name and team description. Clicks on next button on create team page and return team details(name and description)
    public String[] enterTeamDetails() throws PropertyNotFoundException, InvalidLocatorStrategyException {
        String TeamDetails[] = new String[2];
        TeamDetails[0] = "TestName"+System.currentTimeMillis();
        TeamDetails[1] = "TestDesc"+System.currentTimeMillis();
        type("TeamName", TeamDetails[0]);
        type("TeamDescription", TeamDetails[1]);
        ClickonNext();
        return TeamDetails;
    }

    //Enters team details: name and description are passed as parameters
    public void enterTeamInfo(String name, String description) throws PropertyNotFoundException, InvalidLocatorStrategyException {
        type("TeamName", name);
        type("TeamDescription", description);
        ClickonNext();
    }

    //Enter team details and click cancel
    public void enterTeamDetailsAndCancel() throws PropertyNotFoundException, InvalidLocatorStrategyException {
        type("TeamName", "TestName"+System.currentTimeMillis());
        type("TeamDescription", "TestDesc"+System.currentTimeMillis());
        click("TeamCancel");
    }

    //Clicks 'Next' button on create team page.
    public void ClickonNext() throws PropertyNotFoundException, InvalidLocatorStrategyException {
        click("Next");
    }

    //Enter team name and description with data passed in parameters.
    public void enterTeamDetails(String TeamName, String TeamDesc) throws PropertyNotFoundException, InvalidLocatorStrategyException {
        type("TeamName", TeamName);
        type("TeamDescription", TeamDesc);
        ClickonNext();
    }
}
