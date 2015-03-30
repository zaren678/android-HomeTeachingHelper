package com.zaren.hometeachinghelper.data;

import android.content.Context;

import javax.inject.Inject;

/**
 * Created by John on 3/15/2015.
 */
public class HomeTeachingDbAdapter extends BaseDbAdapter
{
    @Inject
    public HomeTeachingDbAdapter( final Context aContext )
    {
        super( new HomeTeachingSqlDatabaseHelper( aContext ) );
    }


    public long addOrUpdateHomeTeacher( final String aQuorum,
                                        final String aHomeTeacher,
                                        final String aHomeTeacherPhone,
                                        final String aHomeTeacherEmail )
    {

        return 0;
    }

    public long addOrUpdateHtHousehold( final String aHousehold,
                                        final String aHouseholdPhone,
                                        final String aHouseholdEmail,
                                        final String aHouseholdStreet1,
                                        final String aHouseholdStreet2,
                                        final String aHouseholdCity,
                                        final String aHouseholdPostal,
                                        final String aHouseholdState,
                                        final String aHouseholdCountry )
    {

        return 0;
    }

    public long addOrUpdateHtSupervisor( final int aDistrict,
                                         final String aSupervisor,
                                         final String aSupervisorPhone,
                                         final String aSupervisorEmail )
    {

        return 0;
    }

    public void addOrReplaceHtAssignment( final long aSupervisorId,
                                          final long aHt1Id,
                                          final long aHt2Id,
                                          final long aHouseholdId )
    {
        return;
    }
}
