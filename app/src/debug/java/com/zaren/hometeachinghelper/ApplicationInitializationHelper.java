package com.zaren.hometeachinghelper;

import com.facebook.stetho.Stetho;

import timber.log.Timber;

/**
 * Class used to help application initialization. This makes it easier to split up release and debug initialization.
 */
public class ApplicationInitializationHelper
{
    public static void initializeApp( Application aApplication )
    {
        Stetho.initialize(
                Stetho.newInitializerBuilder( aApplication )
                      .enableDumpapp( Stetho.defaultDumperPluginsProvider( aApplication ) )
                      .enableWebKitInspector( Stetho.defaultInspectorModulesProvider( aApplication ) )
                      .build() );

        Timber.plant( new Timber.DebugTree() );
    }
}
