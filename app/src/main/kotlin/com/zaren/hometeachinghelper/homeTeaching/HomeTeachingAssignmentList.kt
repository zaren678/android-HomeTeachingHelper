package com.zaren.hometeachinghelper.homeTeaching

import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import com.zaren.hometeachinghelper.R
import android.support.v7.widget.RecyclerView
import com.zaren.hometeachinghelper.widget.CursorRecyclerViewAdapter
import android.content.Context
import android.database.Cursor

/**
 * Created by John on 3/15/2015.
 */
public class HomeTeachingAssignmentList : Fragment()
{
    var mRecyclerView: View? = null;

    override fun onCreateView(aInflater: LayoutInflater?, aContainer: ViewGroup?, aSavedInstanceState: Bundle?): View?
    {
        if( aInflater != null )
        {
            var theView = aInflater.inflate( R.layout.fragment_home_teaching_assignments, aContainer, false );
            mRecyclerView = theView.findViewById( R.id.recycler_view );
            //mAdapter = HomeTeachingAssignmentAdapter()

            return theView;
        }
        return null;
    }
}

class HomeTeachingAssignmentAdapter( aContext: Context, aCursor: Cursor ) : CursorRecyclerViewAdapter< HomeTeachingAssignmentAdapter.ViewHolder >( aContext, aCursor )
{
    override fun onBindViewHolder(viewHolder: ViewHolder?, cursor: Cursor?)
    {
        throw UnsupportedOperationException()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder?
    {
        throw UnsupportedOperationException()
    }

    class ViewHolder( aView: View ) : RecyclerView.ViewHolder( aView )
    {

    }

}
