package com.example.dbdemo;

import com.example.dbdemo.dao.PlainSingerDao;
import com.example.dbdemo.dao.SingerDao;
import com.example.dbdemo.entities.Singer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.List;

@SpringBootApplication
public class DbdemoApplication {
    private static SingerDao singerDao = new PlainSingerDao();
    private static Logger logger = LoggerFactory.getLogger(DbdemoApplication.class);

    public static void main(String[] args) throws Exception {
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
                SpringApplication.run(DbdemoApplication.class, args);

        assert(ctx2 != null);

        logger.info("Start Application");

        System.in.read();
        ctx2.close();
    }

    private static void listAllSingers() {
        List<Singer> singers = singerDao.findAll();

        for(Singer singer: singers) {
            logger.info(singer.toString());
        }
    }
}
