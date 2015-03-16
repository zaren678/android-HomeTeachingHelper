package com.zaren.hometeachinghelper.model;

import android.database.Cursor;
import android.support.annotation.NonNull;

import java.sql.SQLDataException;

import lombok.Value;

/**
 * Represents a household to be home taught
 */
@Value
public class Household
{
    private final long dbId;
    private final String name;
    private final String phone;
    private final String email;
    private final String street1;
    private final String street2;
    private final String city;
    private final String postal;
    private final String state;
    private final String country;

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
