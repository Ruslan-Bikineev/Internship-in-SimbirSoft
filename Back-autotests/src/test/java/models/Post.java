package models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class Post {
    private long id;
    private long postAuthor;
    private Date postDate;
    private Date postDateGmt;
    private String postContent;
    private String postTitle;
    private String postExcerpt;
    private String postStatus;
    private String commentStatus;
    private String pingStatus;
    private String postPassword;
    private String postName;
    private String toPing;
    private String pinged;
    private Date postModified;
    private Date postModifiedGmt;
    private String postContentFiltered;
    private long postParent;
    private String guid;
    private int menuOrder;
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

    public Boolean isEqualWithDefaultJsonBodyPost() {
        boolean result = true;
        if (!postStatus.equals("publish") || !postTitle.equals("Test title")
                || !commentStatus.equals("open") || !pingStatus.equals("open")
                || !postContent.equals("Test content") || !postExcerpt.equals("Test excerpt")) {
            result = false;
        }
        return result;
    }
}
