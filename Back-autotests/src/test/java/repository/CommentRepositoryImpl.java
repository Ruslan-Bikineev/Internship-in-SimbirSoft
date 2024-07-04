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
                comment.setId(resultSet.getLong("id"));
                comment.setCommentPostId(resultSet.getLong("comment_post_id"));
                comment.setCommentAuthor(resultSet.getString("comment_author"));
                comment.setCommentAuthorEmail(resultSet.getString("comment_author_email"));
                comment.setCommentAuthorUrl(resultSet.getString("comment_author_url"));
                comment.setCommentAuthorIp(resultSet.getString("comment_author_ip"));
                comment.setCommentDate(resultSet.getTimestamp("comment_date"));
                comment.setCommentDateGmt(resultSet.getTimestamp("comment_date_gmt"));
                comment.setCommentContent(new Content(resultSet.getString("comment_content")));
                comment.setCommentKarma(resultSet.getInt("comment_karma"));
                comment.setCommentApproved(resultSet.getString("comment_approved"));
                comment.setCommentAgent(resultSet.getString("comment_agent"));
                comment.setCommentType(resultSet.getString("comment_type"));
                comment.setCommentParent(resultSet.getInt("comment_parent"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
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
            preparedStatement.setLong(1, ((Comment) entity).getCommentPostId());
            preparedStatement.setString(2, ((Comment) entity).getCommentAuthor());
            preparedStatement.setString(3, ((Comment) entity).getCommentAuthorEmail());
            preparedStatement.setString(4, ((Comment) entity).getCommentAuthorUrl());
            preparedStatement.setString(5, ((Comment) entity).getCommentAuthorIp());
            preparedStatement.setTimestamp(6, ((Comment) entity).getCommentDate());
            preparedStatement.setTimestamp(7, ((Comment) entity).getCommentDateGmt());
            preparedStatement.setString(8, ((Comment) entity).getCommentContent().getRendered());
            preparedStatement.setInt(9, ((Comment) entity).getCommentKarma());
            preparedStatement.setString(10, ((Comment) entity).getCommentApproved());
            preparedStatement.setString(11, ((Comment) entity).getCommentAgent());
            preparedStatement.setString(12, ((Comment) entity).getCommentType());
            preparedStatement.setInt(13, ((Comment) entity).getCommentParent());
            preparedStatement.setLong(14, ((Comment) entity).getUserId());
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
}
