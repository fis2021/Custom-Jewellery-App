package org.loose.fis.cja.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.loose.fis.cja.exceptions.IncorrectPasswordException;
import org.loose.fis.cja.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.cja.exceptions.UsernameNotExistsException;
import org.loose.fis.cja.model.User;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testfx.assertions.api.Assertions.assertThat;

class UserServiceTest {

    public static final String ADMIN = "admin";

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

    @Test
    @DisplayName("Database is initialized, and there are no users")
    void testDatabaseIsInitializedAndNoUserIsPersisted() {
        assertThat(UserService.getAllUsers()).isNotNull();
        assertThat(UserService.getAllUsers()).isEmpty();
    }

    @Test
    @DisplayName("User is successfully persisted to Database")
    void testUserIsAddedToDatabase() throws UsernameAlreadyExistsException {
        UserService.addUser(ADMIN, ADMIN, ADMIN, ADMIN, ADMIN, ADMIN, ADMIN, ADMIN);
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user = UserService.getAllUsers().get(0);
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo(ADMIN);
        assertThat(user.getPassword()).isEqualTo(UserService.encodePassword(ADMIN, ADMIN));
        assertThat(user.getRole()).isEqualTo(ADMIN);
        assertThat(user.getLastName()).isEqualTo(ADMIN);
        assertThat(user.getFirstName()).isEqualTo(ADMIN);
        assertThat(user.getAddress()).isEqualTo(ADMIN);
        assertThat(user.getPhone()).isEqualTo(ADMIN);
        assertThat(user.getSex()).isEqualTo(ADMIN);
    }

    @Test
    @DisplayName("User can not be added twice")
    void testUserCanNotBeAddedTwice() {
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            UserService.addUser(ADMIN, ADMIN, ADMIN, ADMIN, ADMIN, ADMIN, ADMIN, ADMIN);
            UserService.addUser(ADMIN, ADMIN, ADMIN, ADMIN, ADMIN, ADMIN, ADMIN, ADMIN);
        });
    }

    @Test
    @DisplayName("A specific exception thrown if the username can be found in the database")
    void testCheckUserDoesNotAlreadyExists() throws UsernameAlreadyExistsException {
        UserService.addUser(ADMIN, ADMIN, ADMIN, ADMIN, ADMIN, ADMIN, ADMIN, ADMIN);
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            UserService.checkUserDoesNotAlreadyExist(ADMIN);
        });
    }

    @Test
    @DisplayName("Correct parameters return correct user role")
    void testCorrectUserRoleReturned() throws Exception {
        UserService.addUser(ADMIN, ADMIN, ADMIN, ADMIN, ADMIN, ADMIN, ADMIN, ADMIN);
        assertThat(UserService.getUserRole(ADMIN, ADMIN)).isEqualTo(ADMIN);
    }

    @Test
    @DisplayName("Incorrect password returns a password exception")
    void testIncorrectPasswordForUserRole() throws Exception {
        UserService.addUser(ADMIN, ADMIN, ADMIN, ADMIN, ADMIN, ADMIN, ADMIN, ADMIN);
        assertThrows(IncorrectPasswordException.class, () -> {
            UserService.getUserRole(ADMIN, ADMIN + "1");
        });
    }

    @Test
    @DisplayName("A role cannot be returned if the user has no account")
    void testNoUserForUserRoleCall() throws Exception {
        UserService.addUser(ADMIN, ADMIN, ADMIN, ADMIN, ADMIN, ADMIN, ADMIN, ADMIN);
        assertThrows(UsernameNotExistsException.class, () -> {
            UserService.getUserRole(ADMIN + "1", ADMIN);
        });
    }
}