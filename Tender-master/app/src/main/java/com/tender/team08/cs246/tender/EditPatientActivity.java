package com.tender.team08.cs246.tender;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class EditPatientActivity extends AppCompatActivity implements AsyncResponse{

    private String _firstName;
    private String _lastName;
    private Map<String, EditText> _views;
    private Patient _patient;
    private EditText _editFirstName;
    private EditText _editLastName;
    private EditText _editEmail;
    private EditText _editPhone;
    private EditText _editStreet;
    private EditText _editCity;
    private EditText _editState;
    private EditText _editZip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_patient);
        Intent intent = getIntent();
        _patient = null;
        _firstName = intent.getStringExtra("firstName");
        _lastName = intent.getStringExtra("lastName");
        _editFirstName = (EditText)findViewById(R.id.editFirstName);
        _editLastName = (EditText)findViewById(R.id.editLastName);
        _editEmail = (EditText)findViewById(R.id.editEmail);
        _editPhone = (EditText)findViewById(R.id.editPhone);
        _editStreet = (EditText)findViewById(R.id.editStreet);
        _editCity = (EditText)findViewById(R.id.editCity);
        _editState = (EditText)findViewById(R.id.editState);
        _editZip = (EditText)findViewById(R.id.editZip);

        _views = new HashMap<>();
        _views.put("firstName", _editFirstName);
        _views.put("lastName", _editLastName);
        _views.put("email", _editEmail);
        _views.put("phoneNumber", _editPhone);
        _views.put("street", _editStreet);
        _views.put("city",_editCity);
        _views.put("state",_editState);
        _views.put("zip",_editZip);
        LoadPatientTask loadPatientTask = new LoadPatientTask(this, null, _views, _firstName, _lastName);
        loadPatientTask.delegate = this;
        loadPatientTask.execute();
    }

    public void saveEdits(View view){
        if(_patient != null){
            _patient.setFirstName(_editFirstName.getText().toString());
            _patient.setLastName(_editLastName.getText().toString());
            _patient.setEmail(_editEmail.getText().toString());
            _patient.setPhone(_editPhone.getText().toString());
            _patient.setStreet(_editStreet.getText().toString());
            _patient.setCity(_editCity.getText().toString());
            _patient.setState(_editState.getText().toString());
            _patient.setZip(_editZip.getText().toString());
            UpdatePatientTask updatePatientTask = new UpdatePatientTask(this,_patient);
            updatePatientTask.execute();

            Intent intent = new Intent(this, DisplayPatientActivity.class);
            intent.putExtra("firstName", _patient.getFirstName());
            intent.putExtra("lastName", _patient.getLastName());
            startActivity(intent);
        }
    }

    public void deleteEntry(View view){
        if(_patient != null){
            DeletePatientTask deletePatientTask = new DeletePatientTask(this,_patient);
            deletePatientTask.execute();
            Intent intent = new Intent(this, PatientActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void processFinish(boolean uniqueEntity) {

    }

    @Override
    public void processFinish(Patient patient) {
        this._patient = patient;
    }
}
