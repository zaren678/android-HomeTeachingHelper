package com.zaren.hometeachinghelper;

import android.content.Context;

import com.facebook.stetho.Stetho;
import com.zaren.hometeachinghelper.internal.dagger.AppComponent;

/**
 * Created by John on 3/7/2015.
 */
public class Application extends android.app.Application
{
    private AppComponent mAppComponent;

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
        }

        buildComponentAndInject();
    }

    private void buildComponentAndInject()
    {
        mAppComponent = AppComponent.Initializer.init( this );
    }

    public static AppComponent getAppComponent( Context aContext )
    {
        return ((Application)aContext.getApplicationContext()).mAppComponent;
    }
}
