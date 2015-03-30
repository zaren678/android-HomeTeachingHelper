package com.zaren.hometeachinghelper;

import android.content.Context;

import com.facebook.stetho.Stetho;

import timber.log.Timber;

/**
 * Created by John on 3/7/2015.
 */
public class Application extends android.app.Application
{
    private HomeTeachingHelperComponent mHomeTeachingHelperComponent;

    @Override
    public void onCreate()
    {
        super.onCreate();
        if( BuildConfig.DEBUG )
        {
            Stetho.initialize(
                Stetho.newInitializerBuilder( this )
                    .enableDumpapp( Stetho.defaultDumperPluginsProvider( this ) )
                    .enableWebKitInspector( Stetho.defaultInspectorModulesProvider( this ) )
                    .build() );

            Timber.plant( new Timber.DebugTree() );
        }

        //TODO should probably install crashlytics tree
        Timber.plant( new Timber.HollowTree() );

        buildComponentAndInject();
    }

    private void buildComponentAndInject()
    {
        mHomeTeachingHelperComponent = HomeTeachingHelperComponent.Initializer.init( this );
    }

    public static HomeTeachingHelperComponent getAppComponent( Context aContext )
    {
        return ((Application)aContext.getApplicationContext()).mHomeTeachingHelperComponent;
    }
}
