package com.pega.uiframework.uitest.pages;

import com.pega.uiframework.core.BasePage;
import com.pega.uiframework.core.ElementFinder;
import com.pega.uiframework.exception.InvalidLocatorStrategyException;
import com.pega.uiframework.exception.PropertyNotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marac on 7/5/2017.
 */
public class HomePage extends BasePage{
    private List<WebElement> elements;
    public HomePage(WebDriver driver) throws IOException {
        super(driver);
    }

    //Switch to pega teams frame.
    public void switchFrame() throws PropertyNotFoundException, InvalidLocatorStrategyException {
        switchToFrameByName("PegaTeamsIfr");
    }

    //Clicks on Create button on home page.
    public void ClickonCreate() throws PropertyNotFoundException, InvalidLocatorStrategyException {
        click("MyTeamsCreate");
    }

    //Returns number of active teams depending on teams displayed in home page.
    public int numberOfTeams() throws PropertyNotFoundException, InvalidLocatorStrategyException{
        this.elements = ElementFinder.findElements(this.driver, "TeamsNames");
        int size = this.elements.size();
        return size;
    }

    //Return active teams count in homepage displayed in braces.
    public int activeTeamsCount() throws PropertyNotFoundException, InvalidLocatorStrategyException{
        String activeTeams = getText("ActiveOrArchiveTeamsCount");
        activeTeams = activeTeams.substring(activeTeams.indexOf("(")+1,activeTeams.indexOf(")"));
        int count =Integer.parseInt(activeTeams);
        return count;
    }

    //Verifies text on home page.
    public boolean verifyActiveTeamsText() throws PropertyNotFoundException, InvalidLocatorStrategyException{
        if(getText("ActiveOrArchiveTeamsCount").contains("Active Teams"))
            return true;
        else
            return false;
    }

    //Return archive teams count displayed in braces.
    public int archiveTeamsCount() throws PropertyNotFoundException, InvalidLocatorStrategyException{
        String archiveTeams = getText("ActiveOrArchiveTeamsCount");
        archiveTeams = archiveTeams.substring(archiveTeams.indexOf("(")+1,archiveTeams.indexOf(")"));
        int count =Integer.parseInt(archiveTeams);
        return count;
    }

    //Clicks on view all teams in corresponding active teams page or archive teams page.
    public void clickOnViewActiveOrArchiveTeams() throws PropertyNotFoundException, InvalidLocatorStrategyException{
        click("ViewActiveOrArchiveTeams");
    }

    //Returns active teams names in grid view.
    public ArrayList<String> teamslist() throws PropertyNotFoundException, InvalidLocatorStrategyException{
        this.elements = ElementFinder.findElements(this.driver, "TeamsNames");
        ArrayList<String> teams=new ArrayList<String>();
        for(WebElement team:elements){
            System.out.println(""+team.getText());
            teams.add(team.getText());
        }
        return teams;
    }

    //Returns active teams names in list view.
    public ArrayList<String> listViewTeams() throws PropertyNotFoundException, InvalidLocatorStrategyException{
        this.elements = ElementFinder.findElements(this.driver, "TeamsListView");
        ArrayList<String> teams=new ArrayList<String>();
        for(WebElement team:elements){
            System.out.println(""+team.getText());
            teams.add(team.getText());
        }
        return teams;
    }

    //Compares teams in grid and list views.
    public boolean compareGridAndListViews(ArrayList<String> gridTeams, ArrayList<String> listTeams) throws PropertyNotFoundException, InvalidLocatorStrategyException{
        if(gridTeams.size()==listTeams.size()){
            for(int index=0;index<gridTeams.size();index++){
                if(!gridTeams.get(index).equalsIgnoreCase(listTeams.get(index)))
                    return false;
            }
            return true;
        }
        else
            return false;
    }

    //Clicks on grid or list icons in home page.
    public void clickOnGridOrListView(String view) throws PropertyNotFoundException, InvalidLocatorStrategyException{
        if(view.equalsIgnoreCase("Grid"))
            click("GridView");
        else
            click("ListView");
    }

    //Verifies team exist or not.
    public boolean verifyTeamExist(String teamName,String teamType) throws PropertyNotFoundException, InvalidLocatorStrategyException{
        if(teamType.equalsIgnoreCase("Active")) {
            if (activeTeamsCount() != 0) {
                this.elements = ElementFinder.findElements(this.driver, "TeamsNames");
                for (WebElement team : elements) {
                    if (team.getText().equalsIgnoreCase(teamName)) {
                        return true;
                    }
                }
            }
        }
        if(teamType.equalsIgnoreCase("Archive")) {
            if (archiveTeamsCount() != 0) {
                this.elements = ElementFinder.findElements(this.driver, "TeamsNames");
                for (WebElement team : elements) {
                    if (team.getText().equalsIgnoreCase(teamName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //Clicks on team with corresponding team name passed as parameter.
    public boolean clickOnTeam(String teamName) throws PropertyNotFoundException, InvalidLocatorStrategyException{
        int i=0, teamFound = 0;
        this.elements = ElementFinder.findElements(this.driver, "Team$value");
        for(WebElement team:elements){
            i++;
            if(teamName.equalsIgnoreCase(team.getText())){
                click("Team$value",Integer.toString(i));
                teamFound++;
                break;
            }
        }
        if(teamFound==0)
            return false;
        else return true;
    }

    //Verifies No Access text on home page for guest login.
    public boolean noAccess() throws PropertyNotFoundException, InvalidLocatorStrategyException {
        if(getText("NoAccess").equalsIgnoreCase("Access Denied"))
            return true;
        else
            return false;
    }

    //Clicks on logout.
    public void Logout() throws PropertyNotFoundException, InvalidLocatorStrategyException {
        driver.switchTo().defaultContent();
        click("MyTeamsLogout");
    }

    //Clicks in logout for pega user.
    public void PegaUserLogout() throws PropertyNotFoundException, InvalidLocatorStrategyException {
        driver.switchTo().defaultContent();
        click("PegaUserLogout");
    }

}
