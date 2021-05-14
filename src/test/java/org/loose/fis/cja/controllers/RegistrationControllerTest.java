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
import org.loose.fis.cja.services.UserService;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.testfx.assertions.api.Assertions.assertThat;


@ExtendWith(ApplicationExtension.class)
class RegistrationControllerTest {

    private static final String CORRECT_USERNAME = "usernameCorrect";
    private static final String CORRECT_PASSWORD = "passwordCorrect";
    private static final String CLIENT_ROLE = "Client";
    private static final String MANAGER_ROLE = "Manager";

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-jewellery-databases";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
    }

    @AfterEach
    void tearDown() {
        UserService.close();
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/register.fxml"));
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setTitle("Register-test");
        primaryStage.show();
    }

    @Test
    @DisplayName("Creating an account for a client")
    void testRegistrationClient(FxRobot robot) {
        robot.clickOn("#username");
        robot.write(CORRECT_USERNAME);
        robot.clickOn("#password");
        robot.write(CORRECT_PASSWORD);
        robot.clickOn("#role");
        robot.clickOn(CLIENT_ROLE);
        robot.clickOn("#register");

        assertThat(UserService.getAllUsers().size()).isEqualTo(1);
        assertThat(UserService.getAllUsers().get(0).getUsername()).isEqualTo(CORRECT_USERNAME);
        assertThat(UserService.getAllUsers().get(0).getPassword()).isEqualTo(UserService.encodePassword(CORRECT_USERNAME, CORRECT_PASSWORD));
        assertThat(UserService.getAllUsers().get(0).getRole()).isEqualTo(CLIENT_ROLE);
        assertThat(robot.lookup("#registrationMessage").queryText()).hasText("Account created successfully!");
    }

    @Test
    @DisplayName("Creating an account for a client")
    void testRegistrationManager(FxRobot robot) {
        robot.clickOn("#username");
        robot.write(CORRECT_USERNAME);
        robot.clickOn("#password");
        robot.write(CORRECT_PASSWORD);
        robot.clickOn("#role");
        robot.clickOn(MANAGER_ROLE);
        robot.clickOn("#register");

        assertThat(UserService.getAllUsers().size()).isEqualTo(1);
        assertThat(UserService.getAllUsers().get(0).getUsername()).isEqualTo(CORRECT_USERNAME);
        assertThat(UserService.getAllUsers().get(0).getPassword()).isEqualTo(UserService.encodePassword(CORRECT_USERNAME, CORRECT_PASSWORD));
        assertThat(robot.lookup("#registrationMessage").queryText()).hasText("Account created successfully!");

    }

    @Test
    @DisplayName("Registration failed. Account already exists")
    void testRegistrationAccountAlreadyExists(FxRobot robot) {
        robot.clickOn("#username");
        robot.write(CORRECT_USERNAME);
        robot.clickOn("#password");
        robot.write(CORRECT_PASSWORD);
        robot.clickOn("#role");
        robot.clickOn(CLIENT_ROLE);
        robot.clickOn("#register");

        robot.clickOn("#register");
        assertThat(robot.lookup("#registrationMessage").queryText()).hasText(String.format("An account with the username %s already exists!", CORRECT_USERNAME));
    }

    @Test
    @DisplayName("Testing 'Back to login button' from registration")
    void testBackToLoginButtonFromRegistration(FxRobot robot) {
        robot.clickOn("#backToLogin");
        robot.clickOn("#loginFile");
    }
}