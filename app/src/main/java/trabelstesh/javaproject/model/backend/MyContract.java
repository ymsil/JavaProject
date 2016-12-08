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

    public static class User
    {
        public static final String USER_ID = "_id";
        public static final String USER_NAME = "name";
        public static final String USER_PASSWORD = "password";
        /**
         * The content:// style URI for this table
         */
        public static final Uri USER_URI = Uri.withAppendedPath(AUTHORITY_URI, "users");
    }

    public static class Business {
        public static final String BUSINESS_ID = "_id";
        public static final String BUSINESS_NAME = "name";
        public static final String BUSINESS_ADDRESS = "address";
        public static final String BUSINESS_PHONE = "phone";
        public static final String BUSINESS_EMAIL = "email";
        public static final String BUSINESS_WEBSITE = "website";

        /**
         * The content:// style URI for this table
         */
        public static final Uri BUSINESS_URI = Uri.withAppendedPath(AUTHORITY_URI, "businesses");
    }
    public static class Activity {
        public static final String ACTIVITY_ID = "id";
        public static final String ACTIVITY_DESCRIPTION = "description";
        public static final String ACTIVITY_COUNTRY = "country";
        public static final String ACTIVITY_START_DATE = "start_date";
        public static final String ACTIVITY_END_DATE = "end_date";
        public static final String ACTIVITY_COST = "cost";
        public static final String ACTIVITY_SHORT_DESCRIPTION = "short_description";
        public static final String ACTIVITY_BUSINESS_ID = "business_id";

        /**
         * The content:// style URI for this table
         */
        public static final Uri ACTIVITY_URI = Uri.withAppendedPath(AUTHORITY_URI, "activities");
    }
}
