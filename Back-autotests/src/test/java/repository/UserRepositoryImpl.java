package repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    DataSource dataSource;

    public UserRepositoryImpl() {
        HikariConfig config = new HikariConfig(
                System.getProperty("user.dir") + "\\src\\test\\resources\\db.properties");
        dataSource = new HikariDataSource(config);
    }

    @Override
    public User findById(long id) {
        User user = null;
        String sqlQuery = "SELECT * FROM wp_users WHERE ID = " + id;
        try (Connection connection = dataSource.getConnection();) {
            Statement statement = connection.createStatement();
            statement.execute(sqlQuery);
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                user = resultSetToUser(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public void save(Object entity) {
        String sqlQuery = "INSERT INTO wp_users (user_login, user_pass, user_nicename, user_email, " +
                "user_url, user_registered, user_activation_key, user_status, display_name) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            fillPreparedStatementInUser(preparedStatement, (User) entity);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Object entity) {
    }

    @Override
    public void delete(long id) {
        String sqlQuery = "DELETE FROM wp_users WHERE ID = " + id;
        try (Connection connection = dataSource.getConnection()) {
            connection.createStatement().execute(sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long getLastInsertId() {
        long id;
        String sqlQuery = "SELECT LAST_INSERT_ID() as id";
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(sqlQuery);
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                id = resultSet.getLong("id");
            } else {
                throw new SQLException("Last insert id not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    private void fillPreparedStatementInUser(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getUserLogin());
        preparedStatement.setString(2, user.getUserPass());
        preparedStatement.setString(3, user.getUserNicename());
        preparedStatement.setString(4, user.getUserEmail());
        preparedStatement.setString(5, user.getUserUrl());
        preparedStatement.setTimestamp(6, user.getUserRegistered());
        preparedStatement.setString(7, user.getUserActivationKey());
        preparedStatement.setInt(8, user.getUserStatus());
        preparedStatement.setString(9, user.getDisplayName());
    }

    private User resultSetToUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getLong("ID"),
                resultSet.getString("user_login"),
                resultSet.getString("user_pass"),
                resultSet.getString("user_nicename"),
                resultSet.getString("user_email"),
                resultSet.getString("user_url"),
                resultSet.getTimestamp("user_registered"),
                resultSet.getString("user_activation_key"),
                resultSet.getInt("user_status"),
                resultSet.getString("display_name")
        );
    }
}
