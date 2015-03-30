package com.zaren.hometeachinghelper.homeTeaching;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zaren.hometeachinghelper.R;
import com.zaren.hometeachinghelper.data.HomeTeachingSqlBrite;
import com.zaren.hometeachinghelper.model.HomeTeacher;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by John on 3/29/2015.
 */
public class HomeTeachersAdapter extends RecyclerView.Adapter< HomeTeachersAdapter.HomeTeachersViewHolder > implements Closeable
{
    private final Subscription mSubscription;
    private List< HomeTeacher > mData = new ArrayList<>();

    public HomeTeachersAdapter( HomeTeachingSqlBrite aDb )
    {
        mSubscription = aDb.getAllHomeTeachers()
                           .subscribeOn( Schedulers.computation() )

                           .map( aHomeTeacherList ->
                                 {
                                     return aHomeTeacherList;
                                 } )
                           .observeOn( AndroidSchedulers.mainThread() )
                           .subscribe( aHomeTeachers ->
                                       {
                                           mData = aHomeTeachers;
                                           notifyDataSetChanged();
                                       } );
    }

    @Override
    public HomeTeachersViewHolder onCreateViewHolder( final ViewGroup aParent, final int aViewType )
    {
        LayoutInflater theInflater = LayoutInflater.from( aParent.getContext() );
        View theView = theInflater.inflate( R.layout.home_teacher_item, aParent, false );
        return new HomeTeachersViewHolder( theView );
    }

    @Override
    public void onBindViewHolder( final HomeTeachersViewHolder aHolder, final int aPosition )
    {
        HomeTeacher theItem = mData.get( aPosition );
        aHolder.mName.setText( theItem.getName() );
    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }

    @Override
    public void close() throws IOException
    {
        if( mSubscription != null )
        {
            mSubscription.unsubscribe();
        }
    }

    public static class HomeTeachersViewHolder extends RecyclerView.ViewHolder
    {
        @InjectView( R.id.home_teacher_name ) TextView mName;

        public HomeTeachersViewHolder( final View aItemView )
        {
            super( aItemView );
            ButterKnife.inject( this, aItemView );
        }
    }
}
