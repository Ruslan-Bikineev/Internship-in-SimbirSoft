package models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

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
}
