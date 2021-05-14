package org.loose.fis.cja.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.fis.cja.services.FileSystemService;
import org.loose.fis.cja.services.OrderService;
import org.loose.fis.cja.services.UserService;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class LoginControllerTest {

    private static final String CORRECT_USERNAME = "usernameCorrect";
    private static final String CORRECT_PASSWORD = "passwordCorrect";
    private static final String INCORRECT_USERNAME = "usernameIncorrect";
    private static final String INCORRECT_PASSWORD = "passwordIncorrect";
    private static final String CLIENT_ROLE = "Client";
    private static final String MANAGER_ROLE = "Manager";

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-jewellery-databases";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
        OrderService.initDatabase();
    }

    @AfterEach
    void tearDown() {
        UserService.close(); OrderService.close();
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/login.fxml"));
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setTitle("Login-test");
        primaryStage.show();
    }

    @Test
    @DisplayName("Login failed. Incorrect username")
    void testLoginButtonClickIncorrectUsername(FxRobot robot) {
        robot.clickOn("#username");
        robot.write(INCORRECT_USERNAME);
        robot.clickOn("#password");
        robot.write(INCORRECT_PASSWORD);
        robot.clickOn("#loginButton");

        assertThat(robot.lookup("#registrationMessage").queryText()).hasText(String.format("An account with the username %s doesn't exist!", INCORRECT_USERNAME));
    }

    @Test
    @DisplayName("Login failed. Incorrect password")
    void testLoginButtonClickIncorrectPassword(FxRobot robot) throws Exception{
        UserService.addUser(CORRECT_USERNAME, CORRECT_PASSWORD, CLIENT_ROLE);

        robot.clickOn("#username");
        robot.write(CORRECT_USERNAME);
        robot.clickOn("#password");
        robot.write(INCORRECT_PASSWORD);
        robot.clickOn("#loginButton");

        assertThat(robot.lookup("#registrationMessage").queryText()).hasText("Incorrect password!");
    }

    @Test
    @DisplayName("Testing client successfully login")
    void testClientSuccessfullyLogin(FxRobot robot) throws Exception{
        UserService.addUser(CORRECT_USERNAME, CORRECT_PASSWORD, CLIENT_ROLE);

        robot.clickOn("#username");
        robot.write(CORRECT_USERNAME);
        robot.clickOn("#password");
        robot.write(CORRECT_PASSWORD);
        robot.clickOn("#loginButton");

        robot.clickOn("#startUserFile");
    }

    @Test
    @DisplayName("Testing manager successfully login")
    void testManagerSuccessfullyLogin(FxRobot robot) throws Exception{
        UserService.addUser(CORRECT_USERNAME, CORRECT_PASSWORD, MANAGER_ROLE);

        robot.clickOn("#username");
        robot.write(CORRECT_USERNAME);
        robot.clickOn("#password");
        robot.write(CORRECT_PASSWORD);
        robot.clickOn("#loginButton");

        robot.clickOn("#startManagerFile");
    }

    @Test
    @DisplayName("Registration button correct functionality")
    void testRegistrationButtonCLick(FxRobot robot){
        robot.clickOn("#createAccountButton");
        robot.clickOn("#registerFile");
    }
}