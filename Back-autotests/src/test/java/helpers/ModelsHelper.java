package helpers;

import models.Comment;
import models.Post;
import models.User;
import models.sub.models.Content;
import models.sub.models.Excerpt;
import models.sub.models.Guid;
import models.sub.models.Title;

import java.sql.Timestamp;

public class ModelsHelper {
    public static Comment getDefaultComment() {
        Comment comment = new Comment(
                0,
                1,
                "test_comment_author",
                "test_author_email",
                "test_author_url",
                "test_author_ip",
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                new Content("test_comment_content"),
                0,
                "comment_approved",
                "test_comment_agent",
                "test_comment_type",
                0,
                0);
        comment.getCommentDate().setNanos(0);
        comment.getCommentDateGmt().setNanos(0);
        return comment;
    }

    public static Post getDefaultPost() {
        Post post = new Post(
                0,
                1,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                new Content("test_post_content", false),
                new Title("test_post_title"),
                new Excerpt("test_post_excerpt", false),
                "publish",
                "open",
                "open",
                "",
                "test_post_name",
                "",
                "",
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                "0",
                0,
                new Guid("http://localhost:8000/?p=34"),
                0,
                "post",
                "",
                0);
        post.getPostDate().setNanos(0);
        post.getPostDateGmt().setNanos(0);
        post.getPostModified().setNanos(0);
        post.getPostModifiedGmt().setNanos(0);
        return post;
    }

    public static User getDefaultUser() {
        User user = new User(
                0,
                "test_user_login",
                "test_user_pass",
                "test_use_nicename",
                "test_user_email",
                "test_user_url",
                new Timestamp(System.currentTimeMillis()),
                "test_user_activation_key",
                0,
                "test_display_name");
        user.getUserRegistered().setNanos(0);
        return user;
    }
}
