package com.tender.team08.cs246.tender;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

public class AddPatientTask extends AsyncTask<Void, String, Void> {

    private Context context;
    private Patient patient;
    private AppDatabase db;
    private boolean uniqueEntry;
    public AsyncResponse delegate = null;

    AddPatientTask(Context context, Patient patient){
        this.context = context;
        this.patient = patient;
        this.uniqueEntry = true;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        db = AppDatabase.getInstance(context);
        //does this entry already exist?
        Patient testPatient = db.patientDao().findByName(patient.getFirstName(),patient.getLastName());
        if(testPatient == null){
            db.patientDao().insertAll(patient);
            publishProgress("Added: " + patient.getFirstName() + " " + patient.getLastName());
        }
        else {
            publishProgress("Entry Already Exists");
            uniqueEntry = false;
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        delegate.processFinish(uniqueEntry );
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Toast toast = Toast.makeText(context, values[0], Toast.LENGTH_SHORT);
        toast.show();
    }

}
