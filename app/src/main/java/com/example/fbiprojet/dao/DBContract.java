package com.example.fbiprojet.dao;

public class DBContract {

    /**
     * Deux table User et liked
     */
    public static class Form {
            public static final String TABLE_NAME_USER = "User";
            public static final String USER_ID = "id";
            public static final String USER_COLUMN_NAME = "nom";
            public static final String USER_COLUMN_PASSWORD = "password";

            public static final String  TABLE_NAME_LIKED = "liked";
            public static final String  LIKE_ID = "id";
            public static final String  LIKED_COLUMN_UID = "uid";
            public static final String  LIKED_COLUMN_USER = "User";

        }

}
