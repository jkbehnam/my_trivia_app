package com.trivia.trivia.activities.LoginRegisteration.Registration;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RegistrationPresenterTest extends TestCase {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void setUserPass() {

    }
    @Test
    public void username_check() {
        assertEquals("Conversion from celsius to fahrenheit failed", "ok");

    }
    @Test
    public void hhh(){
        RegistrationPresenter R=new RegistrationPresenter(new RegisterationActivity());
       int i= R.hhh(2);
        assertEquals(1, i);
    }
}