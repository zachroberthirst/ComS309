package com.springboot.springboottesting;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloWorld {
    @GetMapping("/Hello")
    @ResponseBody
    public String HelloUser(){
        return "Hello: ";
    }
    @GetMapping("/Hello/{user}")
    @ResponseBody
    public String HelloUser(@PathVariable String user){
        return "Hello: " + user;
    }
    @GetMapping("/")
    @ResponseBody
    public String HelloWorld(){
        return "HelloWorld!";
    }


}
