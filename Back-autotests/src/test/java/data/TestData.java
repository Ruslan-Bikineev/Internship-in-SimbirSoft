package data;

public class TestData {
    public static final String URL = "http://localhost:8000";
    public static final String GET = "/index.php?rest_route=/wp/v2/users/me";
    public static final String POST = "/index.php?rest_route=/wp/v2/posts";
    public static final String DELETE = "/index.php?rest_route=/wp/v2/posts/";
    public static final String PUT = "/index.php?rest_route=/wp/v2/posts/";
    public static final String VALID_LOGIN = "Name";
    public static final String VALID_PASSWORD = "123-Test";
    public static final String NON_EXISTENT_LOGIN = "NonExistentLogin";
    public static final String NON_EXISTENT_PASSWORD = "NonExistentPassword";
}
