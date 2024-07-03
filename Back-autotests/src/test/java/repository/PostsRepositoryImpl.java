package repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import models.Post;
import models.sub.models.Content;
import models.sub.models.Excerpt;
import models.sub.models.Guid;
import models.sub.models.Title;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PostsRepositoryImpl implements PostsRepository {
    private DataSource dataSource;

    public PostsRepositoryImpl() {
        HikariConfig config = new HikariConfig(
                System.getProperty("user.dir") + "\\src\\test\\resources\\db.properties");
        dataSource = new HikariDataSource(config);
    }

    @Override
    public Post findById(long id) {
        String sqlQuery = "SELECT * FROM wp_posts WHERE id = " + id;
        Post post = null;
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(sqlQuery);
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                post = resultSetToPost(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return post;
    }

    @Override
    public List<Post> findAll() {
        List<Post> usersList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM wp_posts";
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(sqlQuery);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                Post post = resultSetToPost(resultSet);
                usersList.add(post);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usersList;
    }

    @Override
    public void save(Object entity) {
        String sqlQuery = "INSERT INTO wp_posts (post_author, post_date, post_date_gmt, post_content, " +
                "post_title, post_excerpt, post_status, comment_status, ping_status, post_password, post_name, " +
                "to_ping, pinged, post_modified, post_modified_gmt, post_content_filtered, post_parent, guid, " +
                "menu_order, post_type, post_mime_type, comment_count) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, new String[]{"ID"});
            fillPreparedStatementInPost(preparedStatement, (Post) entity);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                ((Post) entity).setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Object entity) {
        String sqlQuery = "UPDATE wp_posts SET post_author = ?, post_date = ?, post_date_gmt = ?, " +
                "post_content = ?, post_title = ?, post_excerpt = ?, post_status = ?, comment_status = ?, " +
                "ping_status = ?, post_password = ?, post_name = ?, to_ping = ?, pinged = ?, post_modified = ?, " +
                "post_modified_gmt = ?, post_content_filtered = ?, post_parent = ?, guid = ?, menu_order = ?, " +
                "post_type = ?, post_mime_type = ?, comment_count = ? WHERE ID = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            fillPreparedStatementInPost(preparedStatement, (Post) entity);
            preparedStatement.setLong(23, ((Post) entity).getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        String sqlQuery = "DELETE FROM wp_posts WHERE ID = " + id;
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlQuery);
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

    private void fillPreparedStatementInPost(PreparedStatement preparedStatement, Post post) throws SQLException {
        preparedStatement.setLong(1, post.getPostAuthor());
        preparedStatement.setTimestamp(2, post.getPostDate());
        preparedStatement.setTimestamp(3, post.getPostDate());
        preparedStatement.setString(4, post.getPostContent().getRendered());
        preparedStatement.setString(5, post.getPostTitle().getRendered());
        preparedStatement.setString(6, post.getPostExcerpt().getRendered());
        preparedStatement.setString(7, post.getPostStatus());
        preparedStatement.setString(8, post.getCommentStatus());
        preparedStatement.setString(9, post.getPingStatus());
        preparedStatement.setString(10, post.getPostPassword());
        preparedStatement.setString(11, post.getPostName());
        preparedStatement.setString(12, post.getToPing());
        preparedStatement.setString(13, post.getPinged());
        preparedStatement.setTimestamp(14, post.getPostModified());
        preparedStatement.setTimestamp(15, post.getPostModifiedGmt());
        preparedStatement.setString(16, post.getPostContentFiltered());
        preparedStatement.setLong(17, post.getPostParent());
        preparedStatement.setString(18, post.getGuid().getRendered());
        preparedStatement.setInt(19, post.getMenuOrder());
        preparedStatement.setString(20, post.getPostType());
        preparedStatement.setString(21, post.getPostMimeType());
        preparedStatement.setLong(22, post.getCommentCount());
    }

    private Post resultSetToPost(ResultSet resultSet) throws SQLException {
        return new Post(resultSet.getLong("id"),
                resultSet.getLong("post_author"),
                resultSet.getTimestamp("post_date"),
                resultSet.getTimestamp("post_date_gmt"),
                new Content(resultSet.getString("post_content"), false),
                new Title(resultSet.getString("post_title")),
                new Excerpt(resultSet.getString("post_excerpt"), false),
                resultSet.getString("post_status"),
                resultSet.getString("comment_status"),
                resultSet.getString("ping_status"),
                resultSet.getString("post_password"),
                resultSet.getString("post_name"),
                resultSet.getString("to_ping"),
                resultSet.getString("pinged"),
                resultSet.getTimestamp("post_modified"),
                resultSet.getTimestamp("post_modified_gmt"),
                resultSet.getString("post_content_filtered"),
                resultSet.getLong("post_parent"),
                new Guid(resultSet.getString("guid")),
                resultSet.getInt("menu_order"),
                resultSet.getString("post_type"),
                resultSet.getString("post_mime_type"),
                resultSet.getLong("comment_count")
        );
    }
}
