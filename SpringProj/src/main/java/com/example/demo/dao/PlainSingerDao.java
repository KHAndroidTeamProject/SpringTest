package com.example.demo.dao;

import com.example.demo.entities.Singer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlainSingerDao implements SingerDao {
    private static Logger logger =
            LoggerFactory.getLogger(PlainSingerDao.class);

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch(ClassNotFoundException e) {
            logger.error("Can't Load DB Driver", e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://192.168.99.100:5432/localdb?useSSL=true",
                "localdb",
                "1234abcd"
        );
    }

    private void closeConnection(Connection connection) {
        if(connection == null) {
            return;
        }
        try {
            connection.close();
        } catch(SQLException e) {
            logger.error("Can't close DB Connection", e);
        }
    }

    @Override
    public List<Singer> findAll() {
        List<Singer> result = new ArrayList<>();
        Connection connection = null;

        try{
            connection = getConnection();
            PreparedStatement statement =
                    connection.prepareStatement("select * from singer");
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                Singer singer = new Singer();
                singer.setId(resultSet.getLong("id"));
                singer.setFirstName(resultSet.getString("first_name"));
                singer.setLastName(resultSet.getString("last_name"));
                singer.setBirthDate(resultSet.getDate("birth_date"));
                result.add(singer);
            }

            statement.close();
        } catch(SQLException e) {
            logger.error("Can't operate select statement", e);
        } finally {
            closeConnection(connection);
        }

        return result;
    }

    @Override
    public List<Singer> findByFirstName(String firstName) {
        return null;
    }

    @Override
    public String findNameById(Long id) {
        return null;
    }

    @Override
    public String findLastNameById(Long id) {
        return null;
    }

    @Override
    public String findFirstNameById(Long id) {
        return null;
    }

    @Override
    public void insert(Singer singer) {
        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
    "insert into singer (first_name, last_name, birth_date) values (?, ?, ?)"
            );

            statement.setString(1, singer.getFirstName());
            statement.setString(2, singer.getLastName());
            statement.setString(3, singer.getBirthDate());
            statement.execute();

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if(generatedKeys.next()) {
                singer.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            logger.error("Can't operate insert statement", e);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void update(Singer singer) {

    }

    @Override
    public void delete(Long singerId) {
        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "delete from singer where id=?"
            );
            statement.setLong(1, singerId);
            statement.execute();
        } catch (SQLException e) {
            logger.error("Can't operate delete", e);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<Singer> findAllWithAlbums() {
        return null;
    }

    @Override
    public void insertWithAlbum(Singer singer) {

    }
}
