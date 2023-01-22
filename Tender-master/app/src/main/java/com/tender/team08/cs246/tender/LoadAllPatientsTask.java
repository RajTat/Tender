package com.tender.team08.cs246.tender;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.net.ConnectException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Michael on 3/17/2018.
 */

public class LoadAllPatientsTask extends AsyncTask<Void, String, Void>  {

    private AppDatabase db;
    private Context context;
    private ListView listView;
    private List<Patient> patientList;
    private ArrayAdapter<String> patientNamesAdapter;

    public LoadAllPatientsTask(Context context, ListView listView, ArrayAdapter<String> patientNamesAdapter) {
        this.context = context;
        this.listView = listView;
        this.patientNamesAdapter = patientNamesAdapter;
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
        listView.setAdapter(patientNamesAdapter);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        db = AppDatabase.getInstance(context);
        patientList = db.patientDao().getAll();
        if(patientList.size() > 0) {
            Collections.sort(patientList);
            for (Patient patient : patientList) {
                patientNamesAdapter.add(patient.getLastName() + ", " + patient.getFirstName());
            }
        }
        else{
            patientNamesAdapter.add("Please add a patient");
        }
        publishProgress("");
        return null;
    }
}
