package repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import models.Comment;
import models.sub.models.Content;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class CommentRepositoryImpl implements CommentRepository {
    DataSource dataSource;

    public CommentRepositoryImpl() {
        HikariConfig config = new HikariConfig(
                System.getProperty("user.dir") + "\\src\\test\\resources\\db.properties");
        dataSource = new HikariDataSource(config);
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

    @Override
    public Comment findById(long id) {
        Comment comment = null;
        String sqlQuery = "SELECT * FROM wp_comments WHERE id = " + id;
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(sqlQuery);
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                comment = resultSetToComment(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return comment;
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public void save(Object entity) {
        String sqlQuery = "INSERT INTO wp_comments (comment_post_ID, comment_author, comment_author_email, " +
                "comment_author_url, comment_author_IP, comment_date, comment_date_gmt, comment_content, comment_karma, comment_approved, " +
                "comment_agent, comment_type, comment_parent, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            fillPreparedStatementInComment(preparedStatement, (Comment) entity);
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
        String sqlQuery = "DELETE FROM wp_comments WHERE comment_id = " + id;
        try (Connection connection = dataSource.getConnection()) {
            connection.createStatement().execute(sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillPreparedStatementInComment(PreparedStatement preparedStatement, Comment comment) throws SQLException {
        preparedStatement.setLong(1, comment.getCommentPostId());
        preparedStatement.setString(2, comment.getCommentAuthor());
        preparedStatement.setString(3, comment.getCommentAuthorEmail());
        preparedStatement.setString(4, comment.getCommentAuthorUrl());
        preparedStatement.setString(5, comment.getCommentAuthorIp());
        preparedStatement.setTimestamp(6, comment.getCommentDate());
        preparedStatement.setTimestamp(7, comment.getCommentDateGmt());
        preparedStatement.setString(8, comment.getCommentContent().getRendered());
        preparedStatement.setInt(9, comment.getCommentKarma());
        preparedStatement.setString(10, comment.getCommentApproved());
        preparedStatement.setString(11, comment.getCommentAgent());
        preparedStatement.setString(12, comment.getCommentType());
        preparedStatement.setInt(13, comment.getCommentParent());
        preparedStatement.setLong(14, comment.getUserId());
    }

    private Comment resultSetToComment(ResultSet resultSet) throws SQLException {
        return new Comment(
                resultSet.getLong("id"),
                resultSet.getLong("comment_post_id"),
                resultSet.getString("comment_author"),
                resultSet.getString("comment_author_email"),
                resultSet.getString("comment_author_url"),
                resultSet.getString("comment_author_ip"),
                resultSet.getTimestamp("comment_date"),
                resultSet.getTimestamp("comment_date_gmt"),
                new Content(resultSet.getString("comment_content")),
                resultSet.getInt("comment_karma"),
                resultSet.getString("comment_approved"),
                resultSet.getString("comment_agent"),
                resultSet.getString("comment_type"),
                resultSet.getInt("comment_parent"),
                resultSet.getInt("user_id"));
    }
}
