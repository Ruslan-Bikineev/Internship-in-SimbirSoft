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

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"postPassword", "format", "meta", "categories", "tags", "sticky", "template", "excerpt", "guid", "toPing", "pinged", "postParent", "menuOrder", "postMimeType", "commentCount", "link", "_links"})
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
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp postModified;
    @JsonProperty("modified_gmt")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
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
    }

    public Boolean isEqualWithDefaultJsonBodyPost() {
        boolean result = true;
        if (
                !postStatus.equals("publish") ||
                        !postTitle.getRendered().equals("Test title") ||
                        !commentStatus.equals("open") ||
                        !pingStatus.equals("open") ||
                        !postContent.getRendered().equals("Test content") ||
                        !postExcerpt.getRendered().equals("Test excerpt")
        ) {
            result = false;
        }
        return result;
    }
}
