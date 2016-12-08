package trabelstesh.javaproject.model.backend;

import android.net.Uri;

/**
 * Created by ymsil on 12/8/2016.
 */

public class MyContract
{
    /**
     * The authority for the contacts provider
     */
    public static final String AUTHORITY = "trabelstesh.javaproject";
    /**
     * A content:// style uri to the authority for the contacts provider
     */
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    public static class User {
        public static final String USER_ID = "_id";
        public static final String USER_NAME = "name";
        public static final String USER_PASSWORD = "password";
        /**
         * The content:// style URI for this table
         */
        public static final Uri USER_URI = Uri.withAppendedPath(AUTHORITY_URI, "users");
    }

    public static class Business {
        public static final String COURSE_ID = "_id";
        public static final String COURSE_NAME = "name";

        /**
         * The content:// style URI for this table
         */
        public static final Uri COURSE_URI = Uri.withAppendedPath(AUTHORITY_URI, "courses");
}
