package com.zaren.hometeachinghelper.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.squareup.sqlbrite.SqlBrite;
import com.zaren.hometeachinghelper.model.HomeTeacher;

import java.io.IOException;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import timber.log.Timber;

/**
 * Created by John on 3/21/2015.
 */
public class HomeTeachingSqlBrite
{
    public static final String ALL_HOMETEACHERS_QUERY = "SELECT * FROM "
            + HomeTeachingSqlDatabaseHelper.HOME_TEACHERS_TABLE
            + " ORDER BY "
            + HomeTeachingSqlDatabaseHelper.NAME + " ASC";

    SqlBrite mSqlBrite;

    public HomeTeachingSqlBrite( final SqlBrite aSqlBrite )
    {
        mSqlBrite = aSqlBrite;
    }

    public Observable< List< HomeTeacher > > getAllHomeTeachers()
    {
        return mSqlBrite.createQuery( HomeTeachingSqlDatabaseHelper.HOME_TEACHERS_TABLE, ALL_HOMETEACHERS_QUERY )
                        .map( t1 -> {
                            Cursor theCursor = t1.run();
                            try
                            {
                                List< HomeTeacher > theHomeTeachers = new ArrayList<>();
                                while( theCursor.moveToNext() )
                                {
                                    try
                                    {
                                        HomeTeacher theHomeTeacher = HomeTeacher.fromCursor( theCursor );
                                        theHomeTeachers.add( theHomeTeacher );
                                    }
                                    catch( SQLDataException e )
                                    {
                                        Timber.e( "Failed to get home teacher from cursor: " + theCursor );
                                    }
                                }
                                return theHomeTeachers;
                            }
                            finally
                            {
                                theCursor.close();
                            }
                        } );
    }

    public void beginTransaction()
    {
        mSqlBrite.beginTransaction();
    }

    public void setTransactionSuccessful()
    {
        mSqlBrite.setTransactionSuccessful();
    }

    public void endTransaction()
    {
        mSqlBrite.endTransaction();
    }

    public void close() throws IOException
    {
        mSqlBrite.close();
    }

    public long addOrUpdateHomeTeacher( final String aQuorum,
                                        final String aName,
                                        final String aPhone,
                                        final String aEmail )
    {
        ContentValues theValues = new ContentValues();
        theValues.put( HomeTeachingSqlDatabaseHelper.QUORUM, aQuorum );
        theValues.put( HomeTeachingSqlDatabaseHelper.NAME, aName );
        theValues.put( HomeTeachingSqlDatabaseHelper.PHONE, aPhone );
        theValues.put( HomeTeachingSqlDatabaseHelper.EMAIL, aEmail );

        QueryBuilder theBuilder = QueryBuilder.withTable( HomeTeachingSqlDatabaseHelper.HOME_TEACHERS_TABLE );
        theBuilder.addContentValues( theValues, true );
        QueryBuilder.QueryArgs theResult = theBuilder.build();

        Cursor theCursor = mSqlBrite.query( theResult.queryString, theResult.selectionArgs );
        try
        {
            if( theCursor.getCount() > 0 )
            {
                theCursor.moveToNext();
                int theIdIndex = theCursor.getColumnIndex( HomeTeachingSqlDatabaseHelper.ID );
                long theId = theCursor.getLong( theIdIndex );
                mSqlBrite.update( HomeTeachingSqlDatabaseHelper.HOME_TEACHERS_TABLE, theValues, HomeTeachingSqlDatabaseHelper.ID + "=?", String.valueOf( theId ) );
                return theId;
            }
            else
            {
                return mSqlBrite.insert( HomeTeachingSqlDatabaseHelper.HOME_TEACHERS_TABLE, theValues );
            }
        }
        finally
        {
            theCursor.close();
        }
    }
}
