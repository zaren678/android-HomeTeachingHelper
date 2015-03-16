package com.zaren.hometeachinghelper.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 3/8/2015.
 */
public class BaseDbAdapter
{
    protected SQLiteDatabase mDb;
    private SQLiteOpenHelper mDbHelper;

    public BaseDbAdapter( SQLiteOpenHelper aHelper )
    {
        mDbHelper = aHelper;
    }

    /**
     * Opens the database. This must be done before any transaction can occur.
     *
     * @throws android.database.SQLException
     */
    public void open() throws SQLException
    {
        mDb = mDbHelper.getWritableDatabase();
    }

    /**
     * Closes the database until open is called again.
     */
    public void close()
    {
        if( mDb != null )
        {
            mDb.close();
            mDb = null;
        }

        if( mDbHelper != null )
        {
            mDbHelper.close();
        }
    }

    /**
     * Begins a transaction on the database.
     *
     * @throws java.io.IOException if the database is not open
     */
    public void beginTransaction() throws IOException
    {
        if( mDb != null )
        {
            mDb.beginTransaction();
        }
        else
        {
            throw new IllegalStateException( "Database Not Open" );
        }
    }

    /**
     * Ends a transaction on the database.
     *
     * @throws java.io.IOException if the database is not open
     */
    public void endTransaction() throws IOException
    {
        if( mDb != null )
        {
            mDb.endTransaction();
        }
        else
        {
            throw new IllegalStateException( "Database Not Open" );
        }
    }

    /**
     * Marks a transaction as successful and commits it to the database.
     *
     * @throws java.io.IOException if the database is not open
     */
    public void setTransactionSuccessful() throws IOException
    {
        if( mDb != null )
        {
            mDb.setTransactionSuccessful();
        }
        else
        {
            throw new IllegalStateException( "Database Not Open" );
        }
    }

    public Cursor runBasicQuery( String aTable,
                                 String[] aSelectColumns,
                                 List< QueryEntry > aQueryEntries ) throws IOException
    {
        if( mDb != null )
        {
            if( aQueryEntries != null && !aQueryEntries.isEmpty() )
            {
                StringBuilder theWhereClause = new StringBuilder();
                List< String > theWhereValues = new ArrayList<>();
                for( QueryEntry theEntry : aQueryEntries )
                {
                    String theColumnValue = theEntry.getColumnValue();
                    if( theColumnValue != null )
                    {
                        appendAndIfNecessary( theWhereClause );
                        theWhereClause.append( theEntry.getColumnId() );
                        theWhereClause.append( " " );

                        boolean isExact = theEntry.isExactMatch();
                        theWhereClause.append( isExact ? "= ?" : "like '?'" );

                        if( !isExact )
                        {
                            theColumnValue = theColumnValue + "%";
                        }
                        theWhereValues.add( theColumnValue );
                    }
                }

                return mDb.query( aTable,
                                  aSelectColumns,
                                  theWhereClause.toString(),
                                  theWhereValues.toArray( new String[ theWhereValues.size() ] ),
                                  null, null, null );
            }
            else
            {
                return mDb.query( aTable,
                                  aSelectColumns,
                                  null, null, null, null, null );
            }
        }
        else
        {
            throw new IllegalStateException( "Database Not Open" );
        }
    }

    private void appendAndIfNecessary( final StringBuilder aWhereClause )
    {
        if( aWhereClause.length() > 0 )
        {
            aWhereClause.append( " AND " );
        }
    }

    public static class QueryEntry
    {
        private final String mColumnId;
        private final String mColumnValue;
        private final boolean mExactMatch;

        public <T> QueryEntry( final String aColumnId,
                           final T aColumnValue,
                           final boolean aExactMatch )
        {
            mColumnId = aColumnId;
            if( aColumnValue != null )
            {
                mColumnValue = String.valueOf( aColumnValue );
            }
            else
            {
                mColumnValue = null;
            }
            mExactMatch = aExactMatch;
        }

        public String getColumnId()
        {
            return mColumnId;
        }

        public String getColumnValue()
        {
            return mColumnValue;
        }

        public boolean isExactMatch()
        {
            return mExactMatch;
        }
    }
}
