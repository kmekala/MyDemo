package com.pega.uiframework.uitest.pages;

import com.pega.uiframework.core.BasePage;
import com.pega.uiframework.exception.InvalidLocatorStrategyException;
import com.pega.uiframework.exception.PropertyNotFoundException;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

/**
 * Created by marac on 6/27/2017.
 */
public class MyTeamsLoginPage extends BasePage {
    public MyTeamsLoginPage(WebDriver driver) throws IOException {
        super(driver);
    }

    //Clicks on signin in my teams login page.
    public void ClickonSignInLandingPage() throws Exception {
        click("MyTeamsLoginPage");
    }

    //Enters login id.
    public void enterEmail(String enterEmail) throws PropertyNotFoundException, InvalidLocatorStrategyException {
        type("MyTeamsEmail", enterEmail);
    }

    //Enters password.
    public void enterPassword(String enterPassword) throws PropertyNotFoundException, InvalidLocatorStrategyException {
        type("MyTeamsPassword", enterPassword);
    }

    //Clicks on Submit in login page.
    public void ClickonSubmit() throws PropertyNotFoundException, InvalidLocatorStrategyException {
        click("MyTeamsLogin");
    }

}
