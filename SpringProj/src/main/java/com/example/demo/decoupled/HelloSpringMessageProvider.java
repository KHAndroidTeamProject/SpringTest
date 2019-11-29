package com.example.demo.decoupled;

public class HelloSpringMessageProvider
implements MessageProvider{
    @Override
    public String getMessage() {
        return "Hello Spring Decoupled Version";
    }
}
