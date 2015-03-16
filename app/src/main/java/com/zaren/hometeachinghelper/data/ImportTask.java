package com.zaren.hometeachinghelper.data;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.File;
import java.io.IOException;

/**
 * Task for importing data from a csv file into our database
 */
public class ImportTask extends AsyncTask< File, Void, Boolean >
{
    private static final String TAG = ImportTask.class.getSimpleName();
    private final HomeTeachingDbAdapter mDbAdapter;
    private final Context mContext;
    private MaterialDialog mDialog;

    public ImportTask( final Context aContext, final HomeTeachingDbAdapter aDb )
    {
        mContext = aContext;
        mDbAdapter = aDb;
    }

    @Override
    protected void onPreExecute()
    {
        mDialog = new MaterialDialog.Builder( mContext )
                .progress( true, 0 )
                        //TODO strings.xml
                .content( "Importing data..." )
                .cancelable( false )
                .show();
    }

    @Override
    protected void onPostExecute( final Boolean aResult )
    {
        if( aResult )
        {
            if( mDialog != null && mDialog.isShowing() )
            {
                mDialog.dismiss();
            }
        }
        else
        {
            mDialog.setContent( "FAILED" );
        }
    }

    @Override
    protected Boolean doInBackground( final File... aParams )
    {
        File theFile = aParams[ 0 ];
        try
        {
            try
            {
                mDbAdapter.open();
                mDbAdapter.beginTransaction();
                //TODO detect what type we are parsing, VT or HT
                //TODO clear db?

                HomeTeachingParser theParser = new HomeTeachingParser( theFile );
                for( HomeTeachingRecord theRecord : theParser )
                {
                    if( !isCancelled() )
                    {
                        if( theRecord != null )
                        {
                            try
                            {
                                processRecord( theRecord );
                            }
                            catch( IOException | HomeTeachingDatabaseException e )
                            {
                                Log.e( TAG, "Failed to process record: " + e );
                            }
                        }
                    }
                    else
                    {
                        Log.d( TAG, "Import task was cancelled" );
                        break;
                    }
                }
                mDbAdapter.setTransactionSuccessful();
            }
            finally
            {
                mDbAdapter.endTransaction();
                mDbAdapter.close();
            }
        }
        catch( IOException e )
        {
            Log.e( TAG, "Failed to parse file " + theFile + ":" + e );
            return false;
        }

        return true;
    }

    private void processRecord( @NonNull final HomeTeachingRecord aRecord ) throws IOException, HomeTeachingDatabaseException
    {
        long theHt1Id = mDbAdapter.addOrUpdateHomeTeacher( aRecord.getQuorum(),
                                                           aRecord.getHomeTeacher1(),
                                                           aRecord.getHomeTeacher1Phone(),
                                                           aRecord.getHomeTeacher1Email() );

        long theHt2Id = mDbAdapter.addOrUpdateHomeTeacher( aRecord.getQuorum(),
                                                           aRecord.getHomeTeacher2(),
                                                           aRecord.getHomeTeacher2Phone(),
                                                           aRecord.getHomeTeacher2Email() );

        long theHouseholdId = mDbAdapter.addOrUpdateHtHousehold( aRecord.getHousehold(),
                                                                 aRecord.getHouseholdPhone(),
                                                                 aRecord.getHouseholdEmail(),
                                                                 aRecord.getHouseholdStreet1(),
                                                                 aRecord.getHouseholdStreet2(),
                                                                 aRecord.getHouseholdCity(),
                                                                 aRecord.getHouseholdPostal(),
                                                                 aRecord.getHouseholdState(),
                                                                 aRecord.getHouseholdCountry() );

        long theSupervisorId = mDbAdapter.addOrUpdateHtSupervisor( aRecord.getDistrict(),
                                                                   aRecord.getSupervisor(),
                                                                   aRecord.getSupervisorPhone(),
                                                                   aRecord.getSupervisorEmail() );

        mDbAdapter.addOrReplaceHtAssignment( theSupervisorId, theHt1Id, theHt2Id, theHouseholdId );
    }
}
