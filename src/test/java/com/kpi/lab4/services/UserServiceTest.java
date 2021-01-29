package com.kpi.lab4.services;

import com.kpi.lab4.dao.UserDao;
import com.kpi.lab4.dto.LoginDto;
import com.kpi.lab4.dto.RegisterDto;
import com.kpi.lab4.entities.User;
import com.kpi.lab4.enums.UserType;
import com.kpi.lab4.exception.UnavailableException;
import com.kpi.lab4.exception.UserAlreadyExistException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mindrot.jbcrypt.BCrypt;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(BCrypt.class)
public class UserServiceTest {
    private UserDao userDao;

    @Before
    public void before() {
        userDao = mock(UserDao.class);
    }

    @Test
    public void whenLoginWithBadUsername_thenReturnNull() throws SQLException {
        LoginDto credentials = new LoginDto("test", "test123");
        when(userDao.findByUsername(credentials.getUsername())).thenReturn(Optional.empty());

        UserService service = new UserService(userDao);
        assertNull(service.login(credentials));
        verify(userDao).findByUsername(credentials.getUsername());
    }

    @Test
    public void whenLoginWithBadPassword_thenReturnNull() throws SQLException {
        LoginDto credentials = new LoginDto("test", "test123");
        User user = new User(
                UUID.randomUUID(),
                "test",
                "$2a$10$eNPZS7sOlxSxiWQ56Hp66ODQRiXPZISv8N1QNsElFDyERoPPB7vA2", // password: 123
                "Test User",
                "test@gmail.com",
                UserType.USER
        );
        when(userDao.findByUsername(credentials.getUsername())).thenReturn(Optional.of(user));
        PowerMockito.mockStatic(BCrypt.class);
        when(BCrypt.checkpw(credentials.getPassword(), user.getPassword())).thenReturn(false);

        UserService service = new UserService(userDao);
        assertNull(service.login(credentials));
        verify(userDao).findByUsername(credentials.getUsername());
    }

    @Test
    public void whenLogin_thenReturnUser() throws SQLException {
        LoginDto credentials = new LoginDto("test", "test123");
        User testUser = new User(
                UUID.randomUUID(),
                "test",
                "$2a$10$eNPZS7sOlxSxiWQ56Hp66ODQRiXPZISv8N1QNsElFDyERoPPB7vA2", // password: 123
                "Test User",
                "test@gmail.com",
                UserType.USER
        );
        when(userDao.findByUsername(credentials.getUsername())).thenReturn(Optional.of(testUser));
        PowerMockito.mockStatic(BCrypt.class);
        when(BCrypt.checkpw(credentials.getPassword(), testUser.getPassword())).thenReturn(true);

        User user = new UserService(userDao).login(credentials);
        assertNotNull(user);
        assertEquals(user, testUser);
        verify(userDao).findByUsername(credentials.getUsername());
    }

    @Test(expected = UnavailableException.class)
    public void whenLogin_thenThrowUnavailableException() throws SQLException {
        LoginDto credentials = new LoginDto("test", "test123");
        doThrow(new UnavailableException()).when(userDao).findByUsername("test");

        new UserService(userDao).login(credentials);
    }

    @Test(expected = UserAlreadyExistException.class)
    public void whenRegister_thenThrowUserAlreadyExistException() throws SQLException {
        RegisterDto registerDto = new RegisterDto("test", "test@gmail.com", "Test User", "test123");
        when(userDao.findByUsername(registerDto.getUsername())).thenReturn(Optional.of(new User()));

        new UserService(userDao).register(registerDto);
    }

    @Test(expected = UnavailableException.class)
    public void whenRegister_thenThrowUnavailableException() throws SQLException {
        RegisterDto registerDto = new RegisterDto("test", "test@gmail.com", "Test User", "test123");
        when(userDao.findByUsername(registerDto.getUsername())).thenReturn(Optional.empty());
        PowerMockito.mockStatic(BCrypt.class);
        when(BCrypt.gensalt()).thenReturn("some_salt");

        RegisterDto finalRegisterDto = new RegisterDto(
                "test",
                "test@gmail.com",
                "Test User",
                BCrypt.hashpw(registerDto.getPassword(), "some_salt"));
        doThrow(new SQLException()).when(userDao).save(User.fromRegisterData(finalRegisterDto));

        new UserService(userDao).register(registerDto);
    }

    @Test
    public void whenRegister_thenSaveUser() throws SQLException {
        RegisterDto registerDto = new RegisterDto("test", "test@gmail.com", "Test User", "test123");
        when(userDao.findByUsername(registerDto.getUsername())).thenReturn(Optional.empty());
        PowerMockito.mockStatic(BCrypt.class);
        when(BCrypt.gensalt()).thenReturn("some_salt");

        RegisterDto finalRegisterDto = new RegisterDto(
                "test",
                "test@gmail.com",
                "Test User",
                BCrypt.hashpw(registerDto.getPassword(), "some_salt"));
        new UserService(userDao).register(registerDto);
        verify(userDao).save(User.fromRegisterData(finalRegisterDto));
    }

}
