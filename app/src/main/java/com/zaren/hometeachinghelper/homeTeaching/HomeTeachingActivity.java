package com.zaren.hometeachinghelper.homeTeaching;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.zaren.hometeachinghelper.Application;
import com.zaren.hometeachinghelper.R;
import com.zaren.hometeachinghelper.data.HomeTeachingSqlBrite;
import com.zaren.hometeachinghelper.data.ImportTask;
import com.zaren.hometeachinghelper.utils.HeadlessFragmentBase;

import java.io.File;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class HomeTeachingActivity extends ActionBarActivity
{
    private static final String IMPORT_FRAGMENT = "import_fragment";
    private static final int PICKFILE_RESULT_CODE = 1;

    /**
     * Fragment to hold onto the import async task
     */
    public static class ImportFragment extends HeadlessFragmentBase
    {
        private ImportTask mTask;

        public void startImport( HomeTeachingSqlBrite aDb, File aCsvFile )
        {
            Activity theActivity = getActivity();
            if( theActivity != null )
            {
                mTask = new ImportTask( theActivity, aDb );
                mTask.execute( aCsvFile );
            }
        }
    }

    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    @InjectView(R.id.pager) ViewPager mViewPager;
    @InjectView(R.id.tabs) PagerSlidingTabStrip mTabs;

    @Inject HomeTeachingSqlBrite mDb;

    private ImportFragment mImportFragment;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home_teaching );
        ButterKnife.inject( this );
        Application.getAppComponent( this ).inject( this );

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter( getSupportFragmentManager() );

        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter( mSectionsPagerAdapter );
        mTabs.setViewPager( mViewPager );

        FragmentManager theFragmentManager = getSupportFragmentManager();
        mImportFragment = (ImportFragment) theFragmentManager.findFragmentByTag( IMPORT_FRAGMENT );
        if( mImportFragment == null )
        {
            mImportFragment = new ImportFragment();
            getSupportFragmentManager().beginTransaction().add( mImportFragment,
                                                                IMPORT_FRAGMENT ).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.menu_home_teaching, menu );
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
                theIntent.setType( "*/*" );
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
                    mImportFragment.startImport( mDb, new File( theFilePath ) );
                }
                break;

        }
        super.onActivityResult( aRequestCode, aResultCode, aData );
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {

        private SparseArray<String> mTitles = new SparseArray<>();
        public SectionsPagerAdapter( FragmentManager fm )
        {
            super( fm );
            mTitles.put( 0, "Home Teachers" );
            mTitles.put( 1, "Section 1" );
            mTitles.put( 2, "Section 2" );
        }

        @Override
        public Fragment getItem( int position )
        {
            switch( position )
            {
                case 0:
                    return HomeTeachersFragment.newInstance();
                default:
                    return PlaceholderFragment.newInstance( position + 1 );
            }
        }

        @Override
        public int getCount()
        {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle( int position )
        {
            return mTitles.get( position );
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment
    {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance( int sectionNumber )
        {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt( ARG_SECTION_NUMBER, sectionNumber );
            fragment.setArguments( args );
            return fragment;
        }

        public PlaceholderFragment()
        {
        }

        @Override
        public View onCreateView( LayoutInflater inflater, ViewGroup container,
                                  Bundle savedInstanceState )
        {
            return inflater.inflate( R.layout.fragment_home_teaching, container, false );
        }
    }

}
