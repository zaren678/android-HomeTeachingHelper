package com.zaren.hometeachinghelper.homeTeaching;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zaren.hometeachinghelper.Application;
import com.zaren.hometeachinghelper.R;
import com.zaren.hometeachinghelper.data.HomeTeachingSqlBrite;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HomeTeachersFragment extends Fragment
{

    @Inject HomeTeachingSqlBrite mDb;
    @InjectView( R.id.home_teachers_list ) RecyclerView mHomeTeachersList;
    private HomeTeachersAdapter mAdapter;

    public static HomeTeachersFragment newInstance()
    {
        return new HomeTeachersFragment();
    }

    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        Application.getAppComponent( getActivity() ).inject( this );
    }

    @Override
    public View onCreateView( @NotNull LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState )
    {
        View theView = inflater.inflate( R.layout.fragment_home_teachers, container, false );
        ButterKnife.inject( this, theView );
        return theView;
    }

    @Override
    public void onViewCreated( final View aView, final Bundle aSavedInstanceState )
    {
        super.onViewCreated( aView, aSavedInstanceState );

        mAdapter = new HomeTeachersAdapter( mDb );
        mHomeTeachersList.setAdapter( mAdapter );

        LinearLayoutManager theLayoutManager = new LinearLayoutManager( getActivity() );
        theLayoutManager.setOrientation( LinearLayoutManager.VERTICAL );
        mHomeTeachersList.setLayoutManager( theLayoutManager );
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        ButterKnife.reset( this );
        try
        {
            mAdapter.close();
        }
        catch( IOException ignored )
        {
        }
    }
}
