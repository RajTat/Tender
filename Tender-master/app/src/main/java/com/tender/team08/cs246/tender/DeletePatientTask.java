package com.tender.team08.cs246.tender;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Michael on 3/15/2018.
 */

public class DeletePatientTask extends AsyncTask<Void, String, Void> {
    Context context;
    Patient patient;
    AppDatabase db;

    public DeletePatientTask(Context context, Patient patient) {
        this.context = context;
        this.patient = patient;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        AppDatabase db = AppDatabase.getInstance(context);
        db.patientDao().delete(patient);
        return null;
    }
}
