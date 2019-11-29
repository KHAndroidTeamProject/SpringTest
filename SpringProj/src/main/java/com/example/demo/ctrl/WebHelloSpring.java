package com.example.demo.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebHelloSpring {
    @RequestMapping
    public String sayHi() {
        return "Hello Spring ~~!!~!";
    }
}
