package com.zaren.hometeachinghelper.internal.dagger;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.zaren.hometeachinghelper.data.HomeTeachingDbAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provides basic things for the application as a whole
 */
@Module
public class ApplicationModule
{
    private final Application mApplication;

    public ApplicationModule( final Application aApplication )
    {
        mApplication = aApplication;
    }

    @Provides
    Context provideContext()
    {
        return mApplication;
    }

    @Provides
    @Singleton
    SharedPreferences providePreferenceManager()
    {
        return PreferenceManager.getDefaultSharedPreferences( mApplication );
    }
}
