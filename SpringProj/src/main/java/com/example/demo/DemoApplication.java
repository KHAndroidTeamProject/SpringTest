package com.example.demo;

import com.example.demo.decoupled.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        System.out.println("Hello Spring");

        // Command Line Argument
        if(args.length > 0) {
            for(int i = 0; i < args.length; i++) {
                System.out.println(args[i]);
            }
        } else {
            System.out.println("There are no argument");
        }

        // Decoupled Action
        MessageRenderer mr = new OutputMessageRenderer();
        MessageProvider mp = new HelloSpringMessageProvider();
        mr.setMessageProvider(mp);
        mr.render();

        // XML Based Action
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "spring/app-context.xml"
        );

        MessageRenderer mr2 = ctx.getBean(
                "renderer",
                MessageRenderer.class
        );
        mr2.render();

        // Annotation Based Action
        ApplicationContext ctx2 =
                new AnnotationConfigApplicationContext(
                        HelloSpringConfiguration.class
                );

        MessageRenderer mr3 = ctx2.getBean(
                "renderer",
                MessageRenderer.class
        );

        mr3.render();

        SpringApplication.run(DemoApplication.class, args);
    }

}
