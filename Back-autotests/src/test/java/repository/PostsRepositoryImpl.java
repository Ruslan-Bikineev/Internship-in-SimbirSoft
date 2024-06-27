package repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import models.Post;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class PostsRepositoryImpl implements PostsRepository {
    private DataSource dataSource;

    public PostsRepositoryImpl() {
        HikariConfig config = new HikariConfig(
                System.getProperty("user.dir") + "\\src\\test\\resources\\db.properties");
        dataSource = new HikariDataSource(config);
    }

    @Override
    public Optional<Post> findById(long id) {
        String sqlQuery = "SELECT * FROM wp_posts WHERE id = " + id;
        Optional<Post> optionalPost = Optional.empty();
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(sqlQuery);
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                optionalPost = Optional.of(resultSetToPost(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return optionalPost;
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public void save(Object entity) {
    }

    @Override
    public void update(Object entity) {
    }

    @Override
    public void delete(long id) {
    }

    private Post resultSetToPost(ResultSet resultSet) throws SQLException {
        return new Post(resultSet.getLong("id"),
                resultSet.getLong("post_author"),
                resultSet.getDate("post_date"),
                resultSet.getDate("post_date_gmt"),
                resultSet.getString("post_content"),
                resultSet.getString("post_title"),
                resultSet.getString("post_excerpt"),
                resultSet.getString("post_status"),
                resultSet.getString("comment_status"),
                resultSet.getString("ping_status"),
                resultSet.getString("post_password"),
                resultSet.getString("post_name"),
                resultSet.getString("to_ping"),
                resultSet.getString("pinged"),
                resultSet.getDate("post_modified"),
                resultSet.getDate("post_modified_gmt"),
                resultSet.getString("post_content_filtered"),
                resultSet.getLong("post_parent"),
                resultSet.getString("guid"),
                resultSet.getInt("menu_order"),
                resultSet.getString("post_type"),
                resultSet.getString("post_mime_type"),
                resultSet.getLong("comment_count")
        );
    }
}
