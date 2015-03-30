package com.zaren.hometeachinghelper;

import com.zaren.hometeachinghelper.data.DbModule;
import com.zaren.hometeachinghelper.homeTeaching.HomeTeachersFragment;
import com.zaren.hometeachinghelper.homeTeaching.HomeTeachingActivity;
import com.zaren.hometeachinghelper.internal.dagger.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Created by John on 3/21/2015.
 */
@Singleton
@Component( modules = { ApplicationModule.class, DbModule.class } )
public interface HomeTeachingHelperComponent
{
    @NoArgsConstructor( access = AccessLevel.PRIVATE )
    public final static class Initializer
    {
        public static HomeTeachingHelperComponent init( Application aApp )
        {
            return Dagger_HomeTeachingHelperComponent.builder()
                                                     .applicationModule( new ApplicationModule( aApp ) )
                                                     .dbModule( new DbModule() )
                                                     .build();
        }
    }

    void inject( HomeTeachingActivity aHomeTeachingActivity );
    void inject( HomeTeachersFragment aHomeTeachersFragment );
}
