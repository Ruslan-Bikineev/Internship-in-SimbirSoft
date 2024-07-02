package models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"postPassword", "format", "meta", "categories", "tags", "title", "sticky", "template", "excerpt", "guid", "toPing", "pinged", "postParent", "menuOrder", "postMimeType", "commentCount", "link", "_links"})
public class Post {
    private long id;
    @JsonProperty("author")
    private long postAuthor;
    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp postDate;
    @JsonProperty("date_gmt")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp postDateGmt;
    @JsonUnwrapped(prefix = "content.")
    private Content postContent;
    //    @JsonProperty("tittle")
    private String postTitle;
    //    @JsonProperty("excerpt")
    private String postExcerpt;
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
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp postModified;
    @JsonProperty("modified_gmt")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp postModifiedGmt;
    @JsonProperty("featured_media")
    private String postContentFiltered;
    private long postParent;
    //    @JsonProperty("guid")
    private String guid;
    private int menuOrder;
    @JsonProperty("type")
    private String postType;
    private String postMimeType;
    private long commentCount;

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

    public static Post getDefaultPost() {
        return new Post(0,
                1,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                new Content("test_post_content", false),
                "test_post_title",
                "test_post_excerpt",
                "publish",
                "open",
                "open",
                "test_post_password",
                "test_post_name",
                "",
                "",
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                "",
                0,
                "http://localhost:8000/?p=34",
                0,
                "post",
                "",
                0);
    }

    public Boolean isEqualWithDefaultJsonBodyPost() {
        boolean result = true;
        if (
                !postStatus.equals("publish") ||
                        !postTitle.equals("Test title") ||
                        !commentStatus.equals("open") ||
                        !pingStatus.equals("open") ||
                        !postContent.getRendered().equals("Test content") ||
                        !postExcerpt.equals("Test excerpt")
        ) {
            result = false;
        }
        return result;
    }
}
