package com.example.morhealth.api;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResult;

import java.util.concurrent.TimeUnit;

public class FitnessApiManager {
    private static FitnessApiManager instance;
    private GoogleApiClient googleApiClient;

    private FitnessApiManager(Context context) {
        googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(Fitness.RECORDING_API)
                .addApi(Fitness.HISTORY_API)
                .addScope(Fitness.SCOPE_ACTIVITY_READ_WRITE)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        Log.d("FitnessApiManager", "Connected to Google Fit API");
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        Log.d("FitnessApiManager", "Connection to Google Fit API suspended");
                    }
                })
                .build();

        googleApiClient.connect();
    }

    public static synchronized FitnessApiManager getInstance(Context context) {
        if (instance == null) {
            instance = new FitnessApiManager(context);
        }
        return instance;
    }

    public float getStepCount(long startTime, long endTime) {
        DataReadRequest readRequest = new DataReadRequest.Builder()
                .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .bucketByTime(1, TimeUnit.DAYS)
                .build();

        DataReadResult result = Fitness.HistoryApi.readData(googleApiClient, readRequest).await(1, TimeUnit.MINUTES);

        if (result.getBuckets().size() > 0) {
            DataSet stepData = result.getBuckets().get(0).getDataSet(DataType.AGGREGATE_STEP_COUNT_DELTA);
            if (stepData != null && stepData.getDataPoints().size() > 0) {
                return stepData.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asFloat();
            }
        }

        return 0f;
    }
}
