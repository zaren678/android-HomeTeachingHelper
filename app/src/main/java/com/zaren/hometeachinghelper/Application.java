package com.zaren.hometeachinghelper;

import android.content.Context;

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
        ApplicationInitializationHelper.initializeApp( this );
        buildComponentAndInject();
    }

    private void buildComponentAndInject()
    {
        mHomeTeachingHelperComponent = HomeTeachingHelperComponent.Initializer.init( this );
    }

    public static HomeTeachingHelperComponent getAppComponent( Context aContext )
    {
        return ( (Application) aContext.getApplicationContext() ).mHomeTeachingHelperComponent;
    }
}
