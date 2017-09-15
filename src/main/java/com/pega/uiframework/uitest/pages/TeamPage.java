package com.pega.uiframework.uitest.pages;

import com.pega.uiframework.core.BasePage;
import com.pega.uiframework.exception.InvalidLocatorStrategyException;
import com.pega.uiframework.exception.PropertyNotFoundException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Hashtable;

/**
 * Created by marac on 7/5/2017.
 */
public class TeamPage extends BasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasePage.class.getName());
    public TeamPage(WebDriver driver) throws IOException {
        super(driver);
    }

    //Verifies team details: both team name and description.
    public boolean verifyTeamDetails(String[] teamDetails) throws PropertyNotFoundException, InvalidLocatorStrategyException {
        String verifyTeamName = getText("TeamNameValidation");
        String verifyTeamDesc = getText("TeamDescriptionValidation");
        if(verifyTeamName.equalsIgnoreCase(teamDetails[0])&&verifyTeamDesc.equalsIgnoreCase(teamDetails[1])){
            LOGGER.info("Team Name and Description validated");
            return true;
        }
        else return false;
    }

    //Verifies team details: both team name and description.
    public boolean verifyTeamDetails(String teamName,String teamDesc) throws PropertyNotFoundException, InvalidLocatorStrategyException {
        String verifyTeamName = getText("TeamNameValidation");
        String verifyTeamDesc = getText("TeamDescriptionValidation");
        if(verifyTeamName.equalsIgnoreCase(teamName)&&verifyTeamDesc.equalsIgnoreCase(teamDesc)){
            LOGGER.info("Team Name and Description validated");
            return true;
        }
        else return false;
    }

    //Clicks on view all teams.
    public void ClickonViewAllTeams() throws PropertyNotFoundException, InvalidLocatorStrategyException {
        click("ViewAllTeams");
    }

    //Clicks on Actions menu.
    public void ClickActions() throws PropertyNotFoundException, InvalidLocatorStrategyException {
        click("ActionsMenu");
    }

    //Clicks on 'Add Memebers'
    public void ClickAddMemebers() throws PropertyNotFoundException, InvalidLocatorStrategyException {
        click("AddMembers");
    }

    //Enter member email id.
    public void enterEmail(String emailId) throws PropertyNotFoundException, InvalidLocatorStrategyException {
        type("AddEmail1", emailId);
    }

    //Click on submit after adding members.
    public void ClickSubmit() throws PropertyNotFoundException, InvalidLocatorStrategyException {
        click("AddMemeberSubmit");
    }

    //Returns count displayed below team name in team page.
    public int teamCount() throws PropertyNotFoundException, InvalidLocatorStrategyException {
        String count = getText("TeamMembersCount");
        count = count.substring(0, count.indexOf(' '));
        int teamMembersCount = Integer.parseInt(count);
        return teamMembersCount;
    }

    //Verifies team count particular to member type.
    public boolean verifyTeamCount(String MemberType) throws PropertyNotFoundException, InvalidLocatorStrategyException {
        if(MemberType.equalsIgnoreCase("Pega") && teamCount()==5)
            return true;
        else if(MemberType.equalsIgnoreCase("Client") && teamCount()==5)
            return true;
        else if(MemberType.equalsIgnoreCase("Partner") && teamCount()==2)
            return true;
        else if(MemberType.equalsIgnoreCase("Professional") && teamCount()==0)
            return true;
        else
            return false;
    }

    //Deletes all members in team.
    public boolean deleteAllTeamMembers() throws PropertyNotFoundException, InvalidLocatorStrategyException,Exception {
        int count=teamCount();
        for(int i=1;i<=count;i++) {
            click("DeleteTeamMember");
            Thread.sleep(5000);
            if(teamCount()!=(count-i))
                return false;
        }
        return true;
    }

    //Deletes team.
    public void deleteTeam()throws PropertyNotFoundException, InvalidLocatorStrategyException,Exception{
       ClickActions();
       Thread.sleep(5000);
       click("DeleteTeam");
       Thread.sleep(10000);
       click("DeleteArchiveRestoreButton");
       Thread.sleep(5000);
       ClickonViewAllTeams();
       Thread.sleep(5000);
    }

    //Archives team.
    public void archiveTeam()throws PropertyNotFoundException, InvalidLocatorStrategyException,Exception{
        ClickActions();
        Thread.sleep(5000);
        click("ArchiveTeam");
        Thread.sleep(10000);
        click("DeleteArchiveRestoreButton");
        Thread.sleep(5000);
        ClickonViewAllTeams();
        Thread.sleep(5000);
    }

    //Deletes archive team.
    public void deleteArchiveTeam()throws PropertyNotFoundException, InvalidLocatorStrategyException,Exception{
        ClickActions();
        Thread.sleep(5000);
        click("DeleteArchiveTeam");
        Thread.sleep(10000);
        click("DeleteArchiveRestoreButton");
        Thread.sleep(5000);
        ClickonViewAllTeams();
        Thread.sleep(5000);
    }

    //Restore archive team.
    public void restoreArchiveTeam()throws PropertyNotFoundException, InvalidLocatorStrategyException,Exception{
        ClickActions();
        Thread.sleep(5000);
        click("RestoreArchiveTeam");
        Thread.sleep(10000);
        click("DeleteArchiveRestoreButton");
        Thread.sleep(5000);
        ClickonViewAllTeams();
        Thread.sleep(5000);
    }

    //Change team info: nam and description.
    public void editTeamInfo(String name, String description) throws PropertyNotFoundException, InvalidLocatorStrategyException,Exception {
        ClickActions();
        Thread.sleep(5000);
        click("EditInfo");
        clear("TeamName");
        clear("TeamDescription");
        type("TeamName", name);
        type("TeamDescription", description);
        Thread.sleep(5000);
        click("EditSave");
        Thread.sleep(5000);
    }

    //Add single member.
    public void addmember(String emailID)throws PropertyNotFoundException, InvalidLocatorStrategyException{
        ClickActions();
        ClickAddMemebers();
        enterEmail(emailID);
        ClickSubmit();
    }

    //Clicks on delete member.
    public void deleteMember(String locator)throws Exception{
        click(locator);
        Thread.sleep(5000);
    }

    //Adds members from excel depending on membertype and validates error message.
    public void addmembers(Hashtable<String, String> data,String memberType)throws PropertyNotFoundException, InvalidLocatorStrategyException, Exception{
        String errorMessage;
        ClickActions();
        ClickAddMemebers();

        if(memberType.equalsIgnoreCase("Pega") || memberType.equalsIgnoreCase("Professional")){
            type("AddEmail1", data.get("PegaID"));
            click("AddAnotherMember");
            type("AddEmail2", data.get("PartnerID"));
            click("AddAnotherMember");
            type("AddEmail3", data.get("ClientID"));
            click("AddAnotherMember");
            type("AddEmail4", data.get("GuestID"));
            click("AddAnotherMember");
            type("AddEmail5", data.get("ProfessionalID"));
            click("AddAnotherMember");
            type("AddEmail6", data.get("ContractorID"));
            ClickSubmit();
        }

        else if(memberType.equalsIgnoreCase("Partner")){
            type("AddEmail1", data.get("PegaID"));
            click("AddAnotherMember");
            type("AddEmail2", data.get("PartnerSameSOrgID"));
            click("AddAnotherMember");
            type("AddEmail3", data.get("PartnerDiffSOrgID"));
            click("AddAnotherMember");
            type("AddEmail4", data.get("ClientID"));
            click("AddAnotherMember");
            type("AddEmail5", data.get("GuestID"));
            click("AddAnotherMember");
            type("AddEmail6", data.get("ProfessionalID"));
            click("AddAnotherMember");
            type("AddEmail7", data.get("ContractorID"));
            ClickSubmit();
        }
        else{
            type("AddEmail1", data.get("PegaID"));
            click("AddAnotherMember");
            type("AddEmail2", data.get("PartnerID"));
            click("AddAnotherMember");
            type("AddEmail3", data.get("ClientSameSOrgID"));
            click("AddAnotherMember");
            type("AddEmail4", data.get("ClientDiffSOrgID"));
            click("AddAnotherMember");
            type("AddEmail5", data.get("GuestID"));
            click("AddAnotherMember");
            type("AddEmail6", data.get("ProfessionalID"));
            click("AddAnotherMember");
            type("AddEmail7", data.get("ContractorID"));
            ClickSubmit();
        }

        if(memberType.equalsIgnoreCase("Pega")){
            errorMessage = "** Members from verizon.net are not allowed. Please add members from your organization or independent professionals." +
                    "Trying to save an invalid page: page is not valid";
            getText("ErrorMessage").equalsIgnoreCase(errorMessage);
            deleteMember("DeleteMember4");
            ClickSubmit();
        }

        if(memberType.equalsIgnoreCase("Client")){
            errorMessage = "** Members from wachovia.com are not allowed. Please add members from your organization or independent professionals.\n" +
                    "** Members from verizon.net are not allowed. Please add members from your organization or independent professionals.\n" +
                    "Trying to save an invalid page: page is not valid";
            getText("ErrorMessage").equalsIgnoreCase(errorMessage);
            deleteMember("DeleteMember4");
            deleteMember("DeleteMember4");
            ClickSubmit();
        }

        if(memberType.equalsIgnoreCase("Partner")){
            errorMessage = "** Members from in.pega.com are not allowed. Please add members from your organization or independent professionals.\n" +
                    "** Members from infosys.com are not allowed. Please add members from your organization or independent professionals.\n" +
                    "** Members from wellsfargo.com are not allowed. Please add members from your organization or independent professionals.\n" +
                    "** Members from verizon.net are not allowed. Please add members from your organization or independent professionals.\n" +
                    "** Members from pegasystems.com are not allowed. Please add members from your organization or independent professionals.\n" +
                    "Trying to save an invalid page: page is not valid";
            getText("ErrorMessage").equalsIgnoreCase(errorMessage);
            deleteMember("DeleteMember1");
            deleteMember("DeleteMember2");
            deleteMember("DeleteMember2");
            deleteMember("DeleteMember2");
            deleteMember("DeleteMember3");
            ClickSubmit();
        }

        if(memberType.equalsIgnoreCase("Professional")){
            errorMessage = "** Members from in.pega.com are not allowed. Please add members from your organization or independent professionals.\n" +
                    "** Members from accenture.com are not allowed. Please add members from your organization or independent professionals.\n" +
                    "** Members from wellsfargo.com are not allowed. Please add members from your organization or independent professionals.\n" +
                    "** Members from verizon.net are not allowed. Please add members from your organization or independent professionals.\n" +
                    "** Members from aarp.org are not allowed. Please add members from your organization or independent professionals.\n" +
                    "** Members from pegasystems.com are not allowed. Please add members from your organization or independent professionals.\n" +
                    "Trying to save an invalid page: page is not valid";
            getText("ErrorMessage").equalsIgnoreCase(errorMessage);
            deleteMember("DeleteMember1");
            deleteMember("DeleteMember1");
            deleteMember("DeleteMember1");
            deleteMember("DeleteMember1");
            deleteMember("DeleteMember1");
            deleteMember("DeleteMember1");
            ClickSubmit();
        }
    }
}
