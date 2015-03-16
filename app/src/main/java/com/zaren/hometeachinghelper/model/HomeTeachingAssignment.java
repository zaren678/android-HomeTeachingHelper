package com.zaren.hometeachinghelper.model;

import android.database.Cursor;
import android.support.annotation.NonNull;

import java.sql.SQLDataException;
import java.util.List;

import lombok.Value;

/**
 * Created by John on 3/7/2015.
 */
@Value
public class HomeTeachingAssignment
{
    private final HomeTeachingSupervisor supervisor;
    private final HomeTeacher homeTeacher1;
    private final HomeTeacher homeTeacher2;
    private final List< Household > households;

    public static Household fromCursor( @NonNull Cursor aCursor ) throws SQLDataException
    {
        if( aCursor.getColumnCount() == 10 )
        {
            return new Household( aCursor.getLong( 0 ),
                                  aCursor.getString( 1 ),
                                  aCursor.getString( 2 ),
                                  aCursor.getString( 3 ),
                                  aCursor.getString( 4 ),
                                  aCursor.getString( 5 ),
                                  aCursor.getString( 6 ),
                                  aCursor.getString( 7 ),
                                  aCursor.getString( 8 ),
                                  aCursor.getString( 9 ) );
        }
        else
        {
            throw new SQLDataException( "Not enough values in cursor to create household" );
        }
    }
}
