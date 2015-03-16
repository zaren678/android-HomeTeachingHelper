package com.zaren.hometeachinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.zaren.hometeachinghelper.data.HomeTeachingParser;

import java.io.File;


public class MainActivity extends ActionBarActivity
{

    private static final int PICKFILE_RESULT_CODE = 1;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        if( savedInstanceState == null )
        {
            getSupportFragmentManager().beginTransaction()
                                       .add( R.id.container, new PlaceholderFragment() )
                                       .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.menu_main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem aItem )
    {
        int theId = aItem.getItemId();

        switch( theId )
        {
            case R.id.import_from_mls:
                Intent theIntent = new Intent( Intent.ACTION_GET_CONTENT );
                theIntent.setType( "file/*" );
                startActivityForResult( theIntent, PICKFILE_RESULT_CODE );
                return true;
        }

        return super.onOptionsItemSelected( aItem );
    }

    @Override
    protected void onActivityResult( final int aRequestCode, final int aResultCode, final Intent aData )
    {
        switch( aRequestCode )
        {
            case PICKFILE_RESULT_CODE:
                if( aResultCode == RESULT_OK )
                {
                    String theFilePath = aData.getData().getPath();
                    //HomeTeachingParser.parse( new File( theFilePath ) );
                }
                break;

        }
        super.onActivityResult( aRequestCode, aResultCode, aData );
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment
    {

        public PlaceholderFragment()
        {
        }

        @Override
        public View onCreateView( LayoutInflater inflater, ViewGroup container,
                                  Bundle savedInstanceState )
        {
            View rootView = inflater.inflate( R.layout.fragment_main, container, false );
            return rootView;
        }
    }
}
