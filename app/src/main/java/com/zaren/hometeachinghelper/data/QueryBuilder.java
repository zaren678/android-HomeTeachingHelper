package com.zaren.hometeachinghelper.data;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by John on 3/21/2015.
 */
public class QueryBuilder
{
    public static QueryBuilder withTable( String aTable )
    {
        return new QueryBuilder( aTable );
    }

    public static class QueryArgs
    {
        public String queryString;
        public String[] selectionArgs;
    }

    private StringBuilder theQueryBuilder = new StringBuilder();
    private List<String> mSelectionArgs = new ArrayList<>();
    private boolean isFirst = true;


    private QueryBuilder( final String aTable )
    {
        theQueryBuilder.append( "SELECT * FROM " ).append( aTable );
    }

    public QueryBuilder addItem( String aKey, String aValue, boolean isExact )
    {
        if( isFirst )
        {
            theQueryBuilder.append( " WHERE " );
            isFirst = false;
        }
        else
        {
            theQueryBuilder.append( " AND" );
        }

        theQueryBuilder.append( " " ).append( aKey );
        if( isExact )
        {
            theQueryBuilder.append( " = ?" );
            mSelectionArgs.add( aValue );
        }
        else
        {
            theQueryBuilder.append( " like %" ).append( aValue ).append( "%" );
        }

        return this;
    }

    public QueryBuilder addContentValues( ContentValues aContentValues, boolean isExact )
    {


        Set< String > theKeySet = aContentValues.keySet();
        for( final String theKey : theKeySet )
        {
            if( isFirst )
            {
                theQueryBuilder.append( " WHERE " );
                isFirst = false;
            }
            else
            {
                theQueryBuilder.append( " AND" );
            }

            theQueryBuilder.append( " " ).append( theKey );

            Object theValue = aContentValues.get( theKey );
            if( isExact )
            {
                theQueryBuilder.append( " = ? " );
                mSelectionArgs.add( String.valueOf( theValue ) );
            }
            else
            {
                theQueryBuilder.append( " like %" ).append( String.valueOf( theValue ) ).append( "% " );
            }
        }
        return this;
    }

    public QueryArgs build()
    {
        theQueryBuilder.append( ";" );
        QueryArgs theArgs = new QueryArgs();
        theArgs.queryString = theQueryBuilder.toString();
        theArgs.selectionArgs = mSelectionArgs.toArray( new String[ mSelectionArgs.size() ] );
        return theArgs;
    }

}
