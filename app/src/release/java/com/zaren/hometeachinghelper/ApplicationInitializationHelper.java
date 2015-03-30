package com.zaren.hometeachinghelper;

import timber.log.Timber;

/**
 * Class used to help application initialization. This makes it easier to split up release and debug initialization.
 */
public class ApplicationInitializationHelper
{
    public static void initializeApp( Application aApplication )
    {
        //TODO should probably install crashlytics tree
        Timber.plant( new Timber.HollowTree() );
    }
}
