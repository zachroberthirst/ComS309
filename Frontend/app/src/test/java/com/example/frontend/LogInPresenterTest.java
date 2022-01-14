package com.example.frontend;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.frontend.model.MainApplication;
import com.example.frontend.model.helpers.HashPassword;
import com.example.frontend.model.server.IServerRequest;
import com.example.frontend.model.user.User;
import com.example.frontend.model.user.UserType;
import com.example.frontend.presenter.logIn.LogInPresenter;
import com.example.frontend.view.logInScreen.ILogInView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.json.JSONException;
import org.json.JSONObject;
import org.junit.*;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class LogInPresenterTest {
    @Rule public MockitoRule mokitoRule = MockitoJUnit.rule();

    @Mock
    ILogInView view;
    @Mock
    IServerRequest serverRequest;


    @Test
    public void testLogIn(){
        String username = "user1";
        String baseUrl = MainApplication.baseUrl;
        //String baseUrl = "http://coms-309-008.cs.iastate.edu:8080/";
        String url = baseUrl+ "users/username/"+username;

        LogInPresenter presenter = new LogInPresenter(view, serverRequest);
        presenter.logIn(username);
        verify(serverRequest, atLeastOnce()).sendJsonToServer(eq("getUser"), eq(url), any(JSONObject.class), eq("GET"));
    }
    @Test
    public void testSuccessJson_User(){
        String baseUrl = MainApplication.baseUrl;
        String salt = "salt";
        String password = "password";
        User u = new User("John", "Doe", "2001-01-01", "johndoe@gmail.com", "johnDoe1",  HashPassword.hash(password,salt), salt, UserType.USER);
        ObjectMapper mapper = new ObjectMapper();

        try {
            JSONObject response =  new JSONObject(mapper.writeValueAsString(u));
            LogInPresenter presenter = new LogInPresenter(view, serverRequest);
            when(view.getPassword()).thenReturn(password);

            presenter.logIn(u.getUsername());
            presenter.onSuccessJson("getUser", response);

            InOrder inOrder = inOrder(view);
            verify(serverRequest, never()).sendJsonToServer(anyString(), eq(baseUrl+ "shelters/username/"+u.getUsername()), any(JSONObject.class), anyString());
            verify(view, never()).setErrors("Invalid Username or Password");
            verify(view, atLeastOnce()).setErrors(null);
            verify(view, atMostOnce()).nextPage();
            inOrder.verify(view).setErrors(null);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testFailJson_User(){
        String salt = "salt";
        String password = "password";
        User u = new User("John", "Doe", "2001-01-01", "johndoe@gmail.com", "johnDoe1",  HashPassword.hash(password,salt), salt, UserType.USER);
        ObjectMapper mapper = new ObjectMapper();

        try {
            JSONObject response =  new JSONObject(mapper.writeValueAsString(u));
            LogInPresenter presenter = new LogInPresenter(view, serverRequest);
            when(view.getPassword()).thenReturn("WrongPassword");

            presenter.logIn(u.getUsername());
            presenter.onSuccessJson("getUser", response);

            InOrder inOrder = inOrder(view);
            verify(serverRequest, never()).sendJsonToServer(anyString(), eq("http://coms-309-008.cs.iastate.edu:8080/shelters/username/"+u.getUsername()), any(JSONObject.class), anyString());
            verify(view, atLeastOnce()).setErrors("Invalid Username or Password");
            verify(view, never()).setErrors(null);
            inOrder.verify(view).setErrors("Invalid Username or Password");

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


}
