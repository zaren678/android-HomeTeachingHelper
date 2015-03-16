package com.zaren.hometeachinghelper.model;

import android.database.Cursor;
import android.support.annotation.NonNull;

import java.sql.SQLDataException;

import lombok.Value;

/**
 * Created by John on 3/7/2015.
 */
@Value
public class HomeTeacher
{
    private final long dbId;
    private final String quorum;
    private final String name;
    private final String phone;
    private final String email;

    public static HomeTeacher fromCursor( @NonNull Cursor aCursor ) throws SQLDataException
    {
        if( aCursor.getColumnCount() == 5 )
        {
            return new HomeTeacher( aCursor.getLong( 0 ),
                                    aCursor.getString( 1 ),
                                    aCursor.getString( 2 ),
                                    aCursor.getString( 3 ),
                                    aCursor.getString( 4 ) );
        }
        else
        {
            throw new SQLDataException( "Not enough values in cursor to create home teacher" );
        }
    }
}
