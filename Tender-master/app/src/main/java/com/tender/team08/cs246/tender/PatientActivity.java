package com.tender.team08.cs246.tender;

import android.content.Intent;
import android.preference.PreferenceActivity;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Class displaying the current list of Patients
 * User can create a new patient or pull up an existing one
 */
public class PatientActivity extends AppCompatActivity {

    ListView patientsListView;
    ArrayAdapter<String> patientsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        patientsListView = (ListView)findViewById(R.id.patientsListView);
        patientsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fullName = (String)parent.getItemAtPosition(position);
                if(!fullName.equals("Please add a patient")) {
                    Intent intent = new Intent(PatientActivity.this, DisplayPatientActivity.class);
                    intent.putExtra("firstName", fullName.split(", ")[1]);
                    intent.putExtra("lastName", fullName.split(", ")[0]);
                    startActivity(intent);
                }
            }
        });
        patientsListAdapter =  new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        LoadAllPatientsTask loadAllPatientsTask =  new LoadAllPatientsTask(this,patientsListView, patientsListAdapter);
        loadAllPatientsTask.execute();
    }

    /**
     * Method sends user to AddPatientActivity
     *
     * @param view
     */
    public void goToAddPatient(View view){
        Intent intent = new Intent(this, AddPatientActivity.class);
        startActivity(intent);

    }

    /**
     * Method sends user to DisplayPatientActivity
     *
     * @param view
     */
    public void goToPatientDisplay(View view) {
        Intent intent = new Intent( this, DisplayPatientActivity.class);
        startActivity(intent);
    }

    /**
     * Method ends the class instance
     * When used, should send user back to MainActivity
     *
     * @param view
     */
    public void goBack(View view) {
        finish();
    }

    /**
     * Method sends the user to the CalendarActivity
     *
     * @param view
     */
    public void goToCalendar(View view) {
        Intent intent = new Intent( this, CalendarActivity.class);
        startActivity(intent);
    }
}
