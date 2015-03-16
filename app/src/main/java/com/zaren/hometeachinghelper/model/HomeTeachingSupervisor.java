package com.zaren.hometeachinghelper.model;

import android.database.Cursor;
import android.support.annotation.NonNull;

import java.sql.SQLDataException;

import lombok.Value;

/**
 * Represents a home teaching supervisor
 */
@Value
public class HomeTeachingSupervisor
{
    private final long dbId;
    private final int district;
    private final String name;
    private final String phone;
    private final String email;

    public static HomeTeachingSupervisor fromCursor( @NonNull Cursor aCursor ) throws SQLDataException
    {
        if( aCursor.getColumnCount() == 5 )
        {
            return new HomeTeachingSupervisor( aCursor.getLong( 0 ),
                                               aCursor.getInt( 1 ),
                                               aCursor.getString( 2 ),
                                               aCursor.getString( 3 ),
                                               aCursor.getString( 4 ) );
        }
        else
        {
            throw new SQLDataException( "Not enough values in cursor to create home teaching supervisor" );
        }
    }
}
