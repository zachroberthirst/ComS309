package com.example.frontend;

import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import java.util.Iterator;
import org.junit.Test;


public class MockitoHelloWorld {
    @Test
    public void iterateHelloWorld(){
        //arrange
        Iterator i = mock(Iterator.class);
        when(i.next()).thenReturn("Hello").thenReturn("World");
        String result = i.next()+ " " + i.next();
        assertEquals("Hello World", result);
    }
}
