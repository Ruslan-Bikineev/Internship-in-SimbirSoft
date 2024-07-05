package models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.sub.models.Content;

import java.sql.Timestamp;

import static data.TestData.CURRENT_TIMEZONE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"parent", "author", "link", "status", "type", "author_avatar_urls", "meta", "_links"})
public class Comment {
    @JsonProperty("id")
    private long id;
    @JsonProperty("post")
    private long commentPostId;
    @JsonProperty("author_name")
    private String commentAuthor;
    private String commentAuthorEmail;
    @JsonProperty("author_url")
    private String commentAuthorUrl;
    private String commentAuthorIp;
    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = CURRENT_TIMEZONE)
    private Timestamp commentDate;
    @JsonProperty("date_gmt")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = CURRENT_TIMEZONE)
    private Timestamp commentDateGmt;
    @JsonProperty("content")
    private Content commentContent;
    private int commentKarma;
    private String commentApproved;
    private String commentAgent;
    private String commentType;
    private int commentParent;
    private long userId;

    public boolean isEqual(Comment comment) {
        boolean result = false;
        if (commentPostId == comment.getCommentPostId() &&
                commentAuthor.equals(comment.commentAuthor) &&
                commentAuthorUrl.equals(comment.getCommentAuthorUrl()) &&
                commentDate.compareTo(comment.getCommentDate()) == 0 &&
                commentDateGmt.compareTo(comment.getCommentDateGmt()) == 0 &&
                commentContent.isEqual(comment.getCommentContent())) {
            result = true;
        }
        return result;
    }
}
