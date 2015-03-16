package com.zaren.hometeachinghelper.data;

import android.util.Log;

import org.apache.commons.csv.CSVRecord;

import java.io.IOException;

import lombok.Getter;

/**
 * Created by John on 3/7/2015.
 */
class HomeTeachingRecord
{
    private static final String TAG = HomeTeachingRecord.class.getSimpleName();
    @Getter private String quorum = "";
    @Getter private int district = 0;
    @Getter private String supervisor = "";
    @Getter private String supervisorPhone = "";
    @Getter private String supervisorEmail = "";
    @Getter private String homeTeacher1 = "";
    @Getter private String homeTeacher1Phone = "";
    @Getter private String homeTeacher1Email = "";
    @Getter private String homeTeacher2 = "";
    @Getter private String homeTeacher2Phone = "";
    @Getter private String homeTeacher2Email = "";
    @Getter private String household = "";
    @Getter private String householdPhone = "";
    @Getter private String householdEmail = "";
    @Getter private String householdStreet1 = "";
    @Getter private String householdStreet2 = "";
    @Getter private String householdCity = "";
    @Getter private String householdPostal = "";
    @Getter private String householdState = "";
    @Getter private String householdCountry = "";

    public HomeTeachingRecord( CSVRecord aRecord ) throws IOException
    {
        //This is a pretty rudimentary check to see if the record is valid
        if( !aRecord.isMapped( HomeTeachingParser.QUORUM ) )
        {
            throw new IOException( "Quorum not in record" );
        }

        quorum = aRecord.get( HomeTeachingParser.QUORUM );

        String theDistrictString = aRecord.get( HomeTeachingParser.HT_DISTRICT );
        try
        {
            district = Integer.parseInt( theDistrictString );
        }
        catch( NumberFormatException e )
        {
            if( theDistrictString.isEmpty() )
            {
                Log.w( TAG, "Failed to parse district from empty string" );
            }
            else
            {
                Log.e( TAG, "Failed to parse district from " + aRecord.get( HomeTeachingParser.HT_DISTRICT ) );
            }
        }

        supervisor = aRecord.get( HomeTeachingParser.SUPERVISOR );
        supervisorPhone = aRecord.get( HomeTeachingParser.SUPERVISOR_PHONE );
        supervisorEmail = aRecord.get( HomeTeachingParser.SUPERVISOR_EMAIL );
        homeTeacher1 = aRecord.get( HomeTeachingParser.HT1 );
        homeTeacher1Phone = aRecord.get( HomeTeachingParser.HT1_PHONE );
        homeTeacher1Email = aRecord.get( HomeTeachingParser.HT1_EMAIL );
        homeTeacher2 = aRecord.get( HomeTeachingParser.HT2 );
        homeTeacher2Phone = aRecord.get( HomeTeachingParser.HT2_PHONE );
        homeTeacher2Email = aRecord.get( HomeTeachingParser.HT2_EMAIL );
        household = aRecord.get( HomeTeachingParser.HOUSEHOLD );
        householdPhone = aRecord.get( HomeTeachingParser.HOUSEHOLD_PHONE );
        householdEmail = aRecord.get( HomeTeachingParser.HOUSEHOLD_EMAIL );
        householdStreet1 = aRecord.get( HomeTeachingParser.HOUSEHOLD_STREET1 );
        householdStreet2 = aRecord.get( HomeTeachingParser.HOUSEHOLD_STREET2 );
        householdCity = aRecord.get( HomeTeachingParser.HOUSEHOLD_CITY );
        householdPostal = aRecord.get( HomeTeachingParser.HOUSEHOLD_POSTAL );
        householdState = aRecord.get( HomeTeachingParser.HOUSEHOLD_STATE );
        householdCountry = aRecord.get( HomeTeachingParser.HOUSEHOLD_COUNTRY );
    }
}
