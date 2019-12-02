package com.example.demo;

import com.example.demo.dao.PlainSingerDao;
import com.example.demo.dao.SingerDao;
import com.example.demo.decoupled.HelloSpringMessageProvider;
import com.example.demo.decoupled.MessageProvider;
import com.example.demo.decoupled.MessageRenderer;
import com.example.demo.decoupled.OutputMessageRenderer;
import com.example.demo.entities.Singer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@SpringBootApplication
public class DemoApplication {
    private static SingerDao singerDao = new PlainSingerDao();
    private static Logger logger = LoggerFactory.getLogger(DemoApplication.class);

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

        logger.info("Initial Singer Info:");

        listAllSingers();

        logger.info("Register Singer");

        Singer singer = new Singer();
        singer.setFirstName("Eden");
        singer.setLastName("Sheeran");
        singer.setBirthDate(new Date((
                new GregorianCalendar(1991, 2, 1991))
                .getTime().getTime()
        ));

        singerDao.insert(singer);

        logger.info("After Register New Singer: ");

        listAllSingers();

        logger.info("Delete New Singer");

        singerDao.delete(singer.getId());

        logger.info("After Delete New Singer: ");

        listAllSingers();

        ConfigurableApplicationContext ctx2 =
                SpringApplication.run(DemoApplication.class, args);

        assert(ctx2 != null);

        logger.info("Start Application");

        System.in.read();
        ctx2.close();
        //SpringApplication.run(DemoApplication.class, args);
    }

    private static void listAllSingers() {
        List<Singer> singers = singerDao.findAll();

        for(Singer singer: singers) {
            logger.info(singer.toString());
        }
    }
}
