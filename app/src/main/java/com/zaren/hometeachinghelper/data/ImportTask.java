package com.zaren.hometeachinghelper.data;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.MaterialDialog;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import timber.log.Timber;

/**
 * Task for importing data from a csv file into our database
 */
public class ImportTask extends AsyncTask< File, Void, Boolean >
{
    private final HomeTeachingSqlBrite mDb;
    private final Context mContext;
    private MaterialDialog mDialog;

    public ImportTask( final Context aContext, final HomeTeachingSqlBrite aDb )
    {
        mContext = aContext;
        mDb = aDb;
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
    protected void onPostExecute( @NotNull final Boolean aResult )
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
    protected Boolean doInBackground( @NotNull final File... aParams )
    {
        File theFile = aParams[ 0 ];
        try
        {
            try
            {
                mDb.beginTransaction();
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
                                Timber.e( "Failed to process record: " + e );
                            }
                        }
                    }
                    else
                    {
                        Timber.d( "Import task was cancelled" );
                        break;
                    }
                }
                mDb.setTransactionSuccessful();
            }
            finally
            {
                mDb.endTransaction();
                mDb.close();
            }
        }
        catch( IOException e )
        {
            Timber.e( "Failed to parse file " + theFile + ":" + e );
            return false;
        }

        return true;
    }

    private void processRecord( @NonNull final HomeTeachingRecord aRecord ) throws IOException, HomeTeachingDatabaseException
    {
        long theHt1Id = -1;
        if( !aRecord.getHomeTeacher1().isEmpty() )
        {
            theHt1Id = mDb.addOrUpdateHomeTeacher( aRecord.getQuorum(),
                                                   aRecord.getHomeTeacher1(),
                                                   aRecord.getHomeTeacher1Phone(),
                                                   aRecord.getHomeTeacher1Email() );
        }

        long theHt2Id = -1;
        if( !aRecord.getHomeTeacher2().isEmpty() )
        {
            theHt2Id = mDb.addOrUpdateHomeTeacher( aRecord.getQuorum(),
                                                   aRecord.getHomeTeacher2(),
                                                   aRecord.getHomeTeacher2Phone(),
                                                   aRecord.getHomeTeacher2Email() );
        }

//        long theHouseholdId = mDb.addOrUpdateHtHousehold( aRecord.getHousehold(),
//                                                                 aRecord.getHouseholdPhone(),
//                                                                 aRecord.getHouseholdEmail(),
//                                                                 aRecord.getHouseholdStreet1(),
//                                                                 aRecord.getHouseholdStreet2(),
//                                                                 aRecord.getHouseholdCity(),
//                                                                 aRecord.getHouseholdPostal(),
//                                                                 aRecord.getHouseholdState(),
//                                                                 aRecord.getHouseholdCountry() );
//
//        long theSupervisorId = mDb.addOrUpdateHtSupervisor( aRecord.getDistrict(),
//                                                                   aRecord.getSupervisor(),
//                                                                   aRecord.getSupervisorPhone(),
//                                                                   aRecord.getSupervisorEmail() );
//
//        mDb.addOrReplaceHtAssignment( theSupervisorId, theHt1Id, theHt2Id, theHouseholdId );
    }
}
