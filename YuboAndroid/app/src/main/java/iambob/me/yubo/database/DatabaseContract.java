package iambob.me.yubo.database;

import android.provider.BaseColumns;

public final class DatabaseContract {
    public DatabaseContract() {}

    public static abstract class DatabaseEntry implements BaseColumns {
        public static final String TABLE_NAME = "spitlycontacts";
        public static final String COLUMN_NAME_CONTACT_ID = "contactid";
        public static final String COLUMN_NAME_ALLOWED_LOCATION = "allowedLocation";
        public static final String COLUMN_NAME_WANTS_LOCATION = "wantsLocation";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_NUMBER = "number";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + DatabaseEntry.TABLE_NAME + " (" +
            DatabaseEntry._ID + " INTEGER PRIMARY KEY," + DatabaseEntry.COLUMN_NAME_CONTACT_ID + TEXT_TYPE + COMMA_SEP +
            DatabaseEntry.COLUMN_NAME_ALLOWED_LOCATION + INTEGER_TYPE + COMMA_SEP +
            DatabaseEntry.COLUMN_NAME_WANTS_LOCATION+ INTEGER_TYPE + COMMA_SEP +
            DatabaseEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
            DatabaseEntry.COLUMN_NAME_NUMBER+ TEXT_TYPE + " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DatabaseEntry.TABLE_NAME;
}
