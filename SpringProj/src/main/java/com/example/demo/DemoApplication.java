package com.example.demo;

import com.example.demo.ctrl.WebHelloSpring;
import com.example.demo.decoupled.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication(
        scanBasePackageClasses = WebHelloSpring.class
)
public class DemoApplication {
    private static Logger logger =
            LoggerFactory.getLogger(
                    DemoApplication.class
            );

    public static void main(String[] args) throws Exception {
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

        /* Let's Bring Up Web Application! */
        ConfigurableApplicationContext ctx3 =
                SpringApplication.run(
                        DemoApplication.class,
                        args
                );

        assert(ctx3 != null);
        logger.info("Start Web Application!!!");

        System.in.read();
        ctx3.close();
    }
}

/* 문제 1.
   Annotation 을 활용하도록 합니다.
   그리고 Web 페이지에
   랜덤 숫자(3 ~ 33)를 출력하도록 만듭니다. */
