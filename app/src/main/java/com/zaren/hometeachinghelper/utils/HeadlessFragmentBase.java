package com.zaren.hometeachinghelper.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Base class for a basic headless fragment
 */
public class HeadlessFragmentBase extends Fragment
{
    @Override
    public void onCreate( final Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setRetainInstance( true );
    }

    @Override
    public View onCreateView( final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState )
    {
        return null;
    }
}
