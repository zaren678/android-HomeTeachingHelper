package com.zaren.hometeachinghelper.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.jetbrains.annotations.NotNull;

/**
 * Helper for initializing and upgrading HomeTeachingDatabase
 */
class HomeTeachingSqlDatabaseHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "HomeTeachingDb";

    static final String HOME_TEACHERS_TABLE = "HomeTeachers";
    static final String ID = "_id";
    static final String QUORUM = "quorum";
    static final String NAME = "name";
    static final String PHONE = "phone";
    static final String EMAIL = "email";

    static final String HT_SUPERVISORS_TABLE = "HTSupervisors";
    static final String DISTRICT = "district";

    static final String HT_HOUSEHOLDS_TABLE = "HTHouseholds";
    static final String STREET1 = "street1";
    static final String STREET2 = "street2";
    static final String CITY = "city";
    static final String POSTAL = "postal";
    static final String STATE = "state";
    static final String COUNTRY = "country";

    static final String HT_ASSIGNMENTS_TABLE = "HTAssignments";
    static final String SUPERVISOR_ID = "supervisor_id";
    static final String HOMETEACHER1_ID = "homeTeacher1_id";
    static final String HOMETEACHER2_ID = "homeTeacher2_id";
    static final String HOUSEHOLD_ID = "household_id";
    static final String ASSIGNMENTS_VIEW = "assignmentsView";

    private static String makeHomeTeachersTableCreateString()
    {
        return "create table " + HOME_TEACHERS_TABLE +
                " (" +
                ID + " integer primary key autoincrement," +
                QUORUM + " text," +
                NAME + " text unique," +
                PHONE + " text," +
                EMAIL + " text" +
                ");";
    }

    private static String makeHomeTeacherSupervisorsTableCreateString()
    {
        return "create table " + HT_SUPERVISORS_TABLE +
                " (" +
                ID + " integer primary key autoincrement," +
                DISTRICT + " integer," +
                NAME + " text," +
                PHONE + " text," +
                EMAIL + " text," +
                "unique(" + DISTRICT + ", " + NAME + ")" +
                ");";
    }

    private static String makeHomeTeacherHouseholdsTableCreateString()
    {
        return "create table " + HT_HOUSEHOLDS_TABLE +
                " (" +
                ID + " integer primary key autoincrement," +
                NAME + " text," +
                PHONE + " text," +
                EMAIL + " text," +
                STREET1 + " text," +
                STREET2 + " text," +
                CITY + " text," +
                POSTAL + " text," +
                STATE + " text," +
                COUNTRY + " text" +
                ");";
    }

    private static String makeHomeTeacherAssignmentsTableCreateString()
    {
        return "create table " + HT_ASSIGNMENTS_TABLE +
                " (" +
                SUPERVISOR_ID + " int," +
                HOMETEACHER1_ID + " int," +
                HOMETEACHER2_ID + " int," +
                HOUSEHOLD_ID + " int unique," +
                "foreign key (" + SUPERVISOR_ID + ") references " + HT_SUPERVISORS_TABLE + "(_id)," +
                "foreign key (" + HOMETEACHER1_ID + ") references " + HOME_TEACHERS_TABLE + "(_id)," +
                "foreign key (" + HOMETEACHER2_ID + ") references " + HOME_TEACHERS_TABLE + "(_id)," +
                "foreign key (" + HOUSEHOLD_ID + ") references " + HT_HOUSEHOLDS_TABLE + "(_id)" +
                ");";
    }

//    private static String makeHomeTeachersAssignmentViewCreateString()
//    {
//        return "create view " + ASSIGNMENTS_VIEW + "as" +
//               "select " +
//    }

    public HomeTeachingSqlDatabaseHelper( final Context context )
    {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate( @NotNull final SQLiteDatabase aDb )
    {
        aDb.execSQL( makeHomeTeachersTableCreateString() );
        aDb.execSQL( makeHomeTeacherSupervisorsTableCreateString() );
        aDb.execSQL( makeHomeTeacherHouseholdsTableCreateString() );
        aDb.execSQL( makeHomeTeacherAssignmentsTableCreateString() );
    }

    @Override
    public void onUpgrade( @NotNull final SQLiteDatabase aDb, final int aOldVersion, final int aNewVersion )
    {
        //Make sure to implement this when we change the db version from 1
    }
}
