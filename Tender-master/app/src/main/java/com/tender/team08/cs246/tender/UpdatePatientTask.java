package com.tender.team08.cs246.tender;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Michael on 3/15/2018.
 */

public class UpdatePatientTask extends AsyncTask <Void, String, Void> {

    private Context context;
    private Patient patient;
    private AppDatabase db;

    public UpdatePatientTask(Context context, Patient patient) {
        this.context = context;
        this.patient = patient;
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
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        db = AppDatabase.getInstance(context);
        db.patientDao().updatePatients(patient);
        return null;
    }

}
