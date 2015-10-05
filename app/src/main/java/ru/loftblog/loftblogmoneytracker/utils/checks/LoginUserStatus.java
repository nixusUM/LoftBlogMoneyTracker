package ru.loftblog.loftblogmoneytracker.utils.checks;

public class LoginUserStatus {

    public static final String STATUS_OK = "success";
    public static final String WRONG_PASSWORD = "Wrong password";
    public static final String WRONG_LOGIN = "Wrong login";
    public static final String ANOTHER_ERROR = "Error";
    public static final String WRONG_TOKEN = "wrong token";

    private final static String G_PLUS_SCOPE =
            "oauth2:https://www.googleapis.com/auth/plus.me";
    private final static String USERINFO_SCOPE =
            "https://www.googleapis.com/auth/userinfo.profile";
    private final static String EMAIL_SCOPE =
            "https://www.googleapis.com/auth/userinfo.email";
    public final static String SCOPES = G_PLUS_SCOPE + " " + USERINFO_SCOPE + " " + EMAIL_SCOPE;

}
