package com.tender.team08.cs246.tender;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;

public class LoadPatientTask extends AsyncTask<Void, Patient, Void> {

    private Context context;
    private Patient patient;
    private Map<String, TextView> textViews;
    private Map<String, EditText> editTexts;
    private String firstName;
    private String lastName;
    private AppDatabase db;
    public AsyncResponse delegate = null;


    public LoadPatientTask(Context context, Map<String, TextView> textViews, Map<String, EditText> editTexts, String firstName, String lastName) {
        this.context = context;
        this.textViews = textViews;
        this.editTexts = editTexts;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(delegate != null){
            delegate.processFinish(patient);
        }
    }

    @Override
    protected void onProgressUpdate(Patient... values) {
        super.onProgressUpdate(values);
        if(textViews != null) {
            textViews.get("firstName").setText(values[0].getFirstName());
            textViews.get("lastName").setText(values[0].getLastName());
            textViews.get("phoneNumber").setText(values[0].getPhone());
            textViews.get("email").setText(values[0].getEmail());
            textViews.get("street").setText(values[0].getStreet());
            textViews.get("city").setText(values[0].getCity());
            textViews.get("state").setText(values[0].getState());
            textViews.get("zip").setText(values[0].getZip());
        }
        else if (editTexts != null){
            editTexts.get("firstName").setText(values[0].getFirstName());
            editTexts.get("lastName").setText(values[0].getLastName());
            editTexts.get("phoneNumber").setText(values[0].getPhone());
            editTexts.get("email").setText(values[0].getEmail());
            editTexts.get("street").setText(values[0].getStreet());
            editTexts.get("city").setText(values[0].getCity());
            editTexts.get("state").setText(values[0].getState());
            editTexts.get("zip").setText(values[0].getZip());
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        db = AppDatabase.getInstance(context);
        patient = db.patientDao().findByName(firstName, lastName);
        publishProgress(patient);
        return null;
    }
}
