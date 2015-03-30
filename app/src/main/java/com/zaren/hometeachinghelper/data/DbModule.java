package com.zaren.hometeachinghelper.data;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.squareup.sqlbrite.SqlBrite;
import com.zaren.hometeachinghelper.Application;
import com.zaren.hometeachinghelper.BuildConfig;
import com.zaren.hometeachinghelper.internal.dagger.ApplicationModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

/**
 * Created by John on 3/21/2015.
 */
@Module( includes={ ApplicationModule.class } )
public class DbModule
{
    @Provides @Singleton
    HomeTeachingSqlDatabaseHelper provideHomeTeachingOpenHelper( Context aContext )
    {
        return new HomeTeachingSqlDatabaseHelper( aContext );
    }

    @Provides @Singleton
    HomeTeachingSqlBrite provideHomeTeachingSqlBrite( HomeTeachingSqlDatabaseHelper aHelper )
    {
        SqlBrite theDb = SqlBrite.create( aHelper );
        if( BuildConfig.DEBUG )
        {
            theDb.setLogger( s -> Timber.tag( "HomeTeachingDb" ).v( s ) );
            theDb.setLoggingEnabled( true );
        }

        return new HomeTeachingSqlBrite( theDb );
    }
}
