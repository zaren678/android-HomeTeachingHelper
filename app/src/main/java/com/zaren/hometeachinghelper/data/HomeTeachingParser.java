package com.zaren.hometeachinghelper.data;

import android.support.annotation.Nullable;
import android.util.Log;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Parses home teaching csv files exported from MLS.
 * You can iterate over the parser after it is constructed but next calls on the iterator may return null;
 */
@SuppressWarnings( "TryFinallyCanBeTryWithResources" )
public class HomeTeachingParser implements Iterable< HomeTeachingRecord >, Closeable
{
    //Constants for headers from CSV File
    static final String QUORUM = "Quorum";
    static final String HT_DISTRICT = "HT District";
    static final String SUPERVISOR = "Supervisor";
    static final String SUPERVISOR_PHONE = "Supv Phone";
    static final String SUPERVISOR_EMAIL = "Supv E-mail";
    static final String COMP_ID = "Comp ID";
    static final String HT1 = "Home Teacher 1";
    static final String HT1_PHONE = "HT 1 Phone";
    static final String HT1_EMAIL = "HT 1 E-mail";
    static final String HT2 = "Home Teacher 2";
    static final String HT2_PHONE = "HT 2 Phone";
    static final String HT2_EMAIL = "HT 2 E-mail";
    static final String HOUSEHOLD = "Household";
    static final String HOUSEHOLD_PHONE = "Household Phone";
    static final String HOUSEHOLD_EMAIL = "E-mail Address";
    static final String HOUSEHOLD_STREET1 = "Street 1";
    static final String HOUSEHOLD_STREET2 = "Street 2";
    static final String HOUSEHOLD_DP = "D/P";
    static final String HOUSEHOLD_CITY = "City";
    static final String HOUSEHOLD_POSTAL = "Postal";
    static final String HOUSEHOLD_STATE = "State/Prov";
    static final String HOUSEHOLD_COUNTRY = "Country";

    private static final String TAG = HomeTeachingParser.class.getSimpleName();

    private final CSVParser mParser;

    public HomeTeachingParser( final File aFile ) throws IOException
    {
        mParser = CSVParser.parse( aFile, Charset.defaultCharset(), CSVFormat.DEFAULT.withHeader() );

        if( !mParser.getHeaderMap().containsKey( QUORUM ) )
        {
            throw new IOException( "Not a valid Home teaching csv file" );
        }
    }

    @Override
    public Iterator< HomeTeachingRecord > iterator()
    {
        return new HomeTeachingParserIterator( mParser.iterator() );
    }

    @Override
    public void close() throws IOException
    {
        mParser.close();
    }

    public static class HomeTeachingParserIterator implements Iterator< HomeTeachingRecord >
    {
        private final Iterator< CSVRecord > mIterator;

        public HomeTeachingParserIterator( final Iterator< CSVRecord > aIterator )
        {
            mIterator = aIterator;
        }

        @Override
        public boolean hasNext()
        {
            return mIterator.hasNext();
        }

        @Override
        public @Nullable HomeTeachingRecord next()
        {
            while( hasNext() )
            {
                try
                {
                    return new HomeTeachingRecord( mIterator.next() );
                }
                catch( IOException e )
                {
                    Log.e( TAG, "Invalid Record: " + e );
                }
                catch( NoSuchElementException e )
                {
                    return null;
                }
            }

            return null;
        }

        @Override
        public void remove()
        {
            throw new IllegalArgumentException( "Remove not supported" );
        }
    }
}
