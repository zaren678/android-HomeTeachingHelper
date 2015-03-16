package com.zaren.hometeachinghelper.internal.dagger;

import com.zaren.hometeachinghelper.Application;
import com.zaren.hometeachinghelper.HomeTeachingActivity;

import javax.inject.Singleton;

import dagger.Component;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Component for things dealing with the application
 */
@Singleton
@Component( modules = ApplicationModule.class )
public interface AppComponent
{
    @NoArgsConstructor( access = AccessLevel.PRIVATE )
    public final static class Initializer
    {
        public static AppComponent init( Application aApp )
        {
            return Dagger_AppComponent.builder()
                                      .applicationModule( new ApplicationModule( aApp ) )
                                      .build();
        }
    }

    void inject( HomeTeachingActivity aHomeTeachingActivity );
}

