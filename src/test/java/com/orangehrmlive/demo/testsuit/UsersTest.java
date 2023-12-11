package com.orangehrmlive.demo.testsuit;

import com.orangehrmlive.demo.customelisteners.CustomListeners;
import com.orangehrmlive.demo.pages.*;
import com.orangehrmlive.demo.testbase.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import testdata.TestData;

@Listeners(CustomListeners.class)
public class UsersTest extends BaseTest {

    LoginPage loginPage;
    HomePage homePage;
    DashboardPage dashboardPage;
    ViewSystemUsersPage viewSystemUsersPage;
    AddUsersPage addUsersPage;

    @BeforeMethod(alwaysRun = true)
    public void inIt() {
        loginPage = new LoginPage();
        homePage = new HomePage();
        dashboardPage = new DashboardPage();
        viewSystemUsersPage = new ViewSystemUsersPage();
        addUsersPage = new AddUsersPage();
    }

    @Test(groups = {"sanity", "smoke", "regression"})
    public void adminShouldAddUserSuccessFully() {
        loginPage.loginToTheApplication("Admin", "admin123");
        homePage.clickOnAdminLink();

        String expectedSystemUsersMessage = "System Users";
        String actualSystemUsersMessage = viewSystemUsersPage.getSystemUsersText();
        Assert.assertEquals(actualSystemUsersMessage, expectedSystemUsersMessage);

        viewSystemUsersPage.clickOnAddButton();

        String expectedAddUsersMessage = "Add User";
        String actualAddUserMessage = addUsersPage.verifyAddUserText();
        Assert.assertEquals(actualAddUserMessage, expectedAddUsersMessage);

        addUsersPage.mouseHoverAndClickOnUserRoleDropDownInAddUser();
        addUsersPage.mouseHoverAndCLickOnAdminOptionInUserRoleDropDownInAddUser();

        addUsersPage.sendDataToEmployeeNameFieldInAddUser(" x x Zenaida Schultz");
        addUsersPage.sendDataToUserNameFieldInAddUser("Prime78");
        addUsersPage.clickOnStatusDropDownMenuInAddUser();
        addUsersPage.mouseHoverAndClickOnDisabledOptionInAddUser();
        addUsersPage.sendDataToPasswordFieldInAddUser("prime123");
        addUsersPage.sendDataToConfirmPasswordFieldInAddUser("prime123");
        addUsersPage.clickOnSaveButtonInAddUser();

        String expectedSuccessfullySavedMessage = "Successfully Saved";
        String actualSuccessfullySavedMessage = addUsersPage.getSuccessfullySavedMessageInAddUser();
        Assert.assertEquals(actualSuccessfullySavedMessage, expectedSuccessfullySavedMessage);
    }

    @Test(groups = {"smoke", "regression"})
    public void searchTheUserCreatedAndVerifyIt() {
        loginPage.loginToTheApplication("Admin", "admin123");
        homePage.clickOnAdminLink();

        String expectedSystemUsersMessage = "System Users";
        String actualSystemUsersMessage = viewSystemUsersPage.getSystemUsersText();
        Assert.assertEquals(actualSystemUsersMessage, expectedSystemUsersMessage);

        viewSystemUsersPage.sendDataToUserNameFieldInSystemUsers("Prime78");
        viewSystemUsersPage.clickOnUserRoleDropDownInSystemUsers();
        viewSystemUsersPage.mouseHoverAndClickOnAdminOptionInUserRoleDropDownInSystemUsers();
        viewSystemUsersPage.clickOnStatusDropDownInSystemUsers();
        viewSystemUsersPage.mouseHoverAndCLickOndDisableStatusOptionInSystemUsers();
        viewSystemUsersPage.clickOnSearchButtonInSystemUsers();

        String expectedUserName = "Prime78";
        String actualUserName = viewSystemUsersPage.getDataFromUserNameInRecord();
        Assert.assertEquals(actualUserName, expectedUserName);
    }

    @Test(dataProvider = "userDetails", dataProviderClass = TestData.class, groups = {"regression"})
    public void verifyThatAdminShouldDeleteTheUserSuccessFully(String uName, String userRole, String eName, String status){
        loginPage.loginToTheApplication("Admin", "admin123");
        homePage.clickOnAdminLink();

        String expectedSystemUsersMessage = "System Users";
        String actualSystemUsersMessage = viewSystemUsersPage.getSystemUsersText();
        Assert.assertEquals(actualSystemUsersMessage, expectedSystemUsersMessage);

        viewSystemUsersPage.searchUserDetails(uName, userRole, eName, status);
    }
}
