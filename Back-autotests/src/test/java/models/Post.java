package models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.sub.models.Content;
import models.sub.models.Excerpt;
import models.sub.models.Guid;
import models.sub.models.Title;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static data.TestData.CURRENT_TIMEZONE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"format", "meta", "categories", "tags", "sticky", "template", "link", "_links"})
public class Post {
    private long id;
    @JsonProperty("author")
    private long postAuthor;
    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = CURRENT_TIMEZONE)
    private Timestamp postDate;
    @JsonProperty("date_gmt")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = CURRENT_TIMEZONE)
    private Timestamp postDateGmt;
    @JsonProperty("content")
    private Content postContent;
    @JsonProperty("title")
    private Title postTitle;
    @JsonProperty("excerpt")
    private Excerpt postExcerpt;
    @JsonProperty("status")
    private String postStatus;
    @JsonProperty("comment_status")
    private String commentStatus;
    @JsonProperty("ping_status")
    private String pingStatus;
    private String postPassword;
    @JsonProperty("slug")
    private String postName;
    private String toPing;
    private String pinged;
    @JsonProperty("modified")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = CURRENT_TIMEZONE)
    private Timestamp postModified;
    @JsonProperty("modified_gmt")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = CURRENT_TIMEZONE)
    private Timestamp postModifiedGmt;
    @JsonProperty("featured_media")
    private String postContentFiltered;
    private long postParent;
    @JsonProperty("guid")
    private Guid guid;
    private int menuOrder;
    @JsonProperty("type")
    private String postType;
    private String postMimeType;
    private long commentCount;

    public static Post getDefaultPost() {
        Post post = new Post(0,
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

    public static Map<String, String> getDefaultJsonBodyPost() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("status", "publish");
        requestBody.put("title", "Test title");
        requestBody.put("content", "Test content");
        requestBody.put("excerpt", "Test excerpt");
        requestBody.put("comment_status", "open");
        requestBody.put("ping_status", "open");
        return requestBody;
    }

    public boolean isEqual(Post post) {
        boolean result = false;
        if (id == post.getId() &&
                postAuthor == post.getPostAuthor() &&
                postDate.compareTo(post.getPostDate()) == 0 &&
                postDateGmt.compareTo(post.getPostDateGmt()) == 0 &&
                postContent.isEqual(post.getPostContent()) &&
                postTitle.isEqual(post.getPostTitle()) &&
                postExcerpt.isEqual(post.getPostExcerpt()) &&
                postStatus.equals(post.getPostStatus()) &&
                commentStatus.equals(post.getCommentStatus()) &&
                pingStatus.equals(post.getPingStatus()) &&
                postName.equals(post.getPostName()) &&
                postModified.compareTo(post.getPostModified()) == 0 &&
                postModifiedGmt.compareTo(post.getPostModifiedGmt()) == 0 &&
                postContentFiltered.equals(post.getPostContentFiltered()) &&
                postParent == post.getPostParent() &&
                guid.isEqual(post.getGuid()) &&
                menuOrder == post.getMenuOrder() &&
                postType.equals(post.getPostType()) &&
                commentCount == post.getCommentCount()) {
            result = true;
        }
        return result;
    }

    public Boolean isEqualWithDefaultJsonBodyPost() {
        boolean result = false;
        if (
                postStatus.equals("publish") &&
                        postTitle.getRendered().equals("Test title") &&
                        commentStatus.equals("open") &&
                        pingStatus.equals("open") &&
                        postContent.getRendered().equals("Test content") &&
                        postExcerpt.getRendered().equals("Test excerpt")
        ) {
            result = true;
        }
        return result;
    }
}
