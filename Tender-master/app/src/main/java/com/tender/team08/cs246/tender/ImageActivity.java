package com.tender.team08.cs246.tender;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Contains Image and Notes for Therapist Reference
 *
 * Notes are Patient specific
 *
 */
public class ImageActivity extends AppCompatActivity {

    private EditText notesTxt;
    private Patient _patient;

    private RelativeLayout rl_Main;

    /**
     * stores the points of interest for this patient
     * draws them with semitransparent red circles
     */
    private List<Point> myPoints;
    private ImageView body;
    private List<Point> points;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        rl_Main = findViewById(R.id.myRL);
        rl_Main.addView(new MyView(this));
        Bundle bundle = getIntent().getExtras();
        _patient = (Patient) bundle.getSerializable("Patient");

        /*
        points = new ArrayList<>();

        List<Point> testPoints = new ArrayList<>();
        testPoints.add(new Point(1,1));
        testPoints.add(new Point(1,2));

        _patient.setPoints(testPoints);
        points = _patient.getPointsList();
        */

        notesTxt = findViewById(R.id.NotesTxt);
        notesTxt.setText(_patient.getNotes());
        body = findViewById(R.id.BodyImgV);
        myPoints = _patient.getPointsList();
        rl_Main.addView(new MyView(this));

        body.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                int newX = (int) (e.getX() - 100) * 2;
                int newY = (int) (e.getY() * 2.045);
                boolean doit = true;
                for (int i = 0; i < myPoints.size(); i++) {
                    double oldX = myPoints.get(i).x;
                    double oldY = myPoints.get(i).y;
                    double distance = Math.sqrt(Math.pow(oldX - newX, 2)) + (Math.pow(oldY - newY, 2));
                    Log.i("Distance", String.valueOf(i) + ":" + String.valueOf(distance));
                    if (distance < 250) {
                        doit = false;
                        myPoints.remove(i);
                    }
                }
                if (doit){
                    myPoints.add(new Point(newX, newY));
                }
                rl_Main.addView(new MyView(v.getContext()));
                return false;
            }
        });
        body.setEnabled(false);

        ToggleButton signTog = (ToggleButton) findViewById(R.id.EditToggle);
        signTog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                toggler(compoundButton);
            }});
    }

    /**
     * Method to save work if user moves away
     * Necessary because the program could get terminated
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EditText txt = findViewById(R.id.NotesTxt);
        _patient.setNotes(txt.getText().toString());
        _patient.setPoints(myPoints);
        savePoints();
    }

    /**
     * Another Method to save work
     * This should occur anytime the user goes back to the DisplayPatientActivity
     * Also helps in case of termination
     */
    @Override
    protected void onStop() {
        super.onStop();
        EditText txt = findViewById(R.id.NotesTxt);
        _patient.setNotes(txt.getText().toString());
        _patient.setPoints(myPoints);
        savePoints();
    }

    class MyView extends View {

        Paint paint = new Paint();

        public MyView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            Log.i("onDraw", "called");
            super.onDraw(canvas);

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.patient);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.RED);
            paint.setAlpha(150);

            Bitmap workingBitmap = Bitmap.createBitmap(bitmap);
            Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);

            //draw all the points on the canvas
            Canvas oCanvas = new Canvas(mutableBitmap);
            for (int i=0; i<myPoints.size(); i++) {
                oCanvas.drawCircle(myPoints.get(i).x, myPoints.get(i).y, 20, paint);
            }
            body.setAdjustViewBounds(true);
            body.setImageBitmap(mutableBitmap);
            body.bringToFront();
        }
    }


    /**
     * This will save your _patient, so run this after you are done writing to the list of points
     * **/
    private void savePoints(){
        UpdatePatientTask updatePatientTask = new UpdatePatientTask(this,_patient);
        updatePatientTask.execute();
    }

    public void toggler(CompoundButton compoundButton) {
        body.setEnabled(compoundButton.isEnabled());
    }

    public void clearer(View view) {
        myPoints = new ArrayList<Point>();
        rl_Main.addView(new MyView(view.getContext()));
    }

    public void backer(View view) {
        EditText txt = findViewById(R.id.NotesTxt);
        _patient.setNotes(txt.getText().toString());
        _patient.setPoints(myPoints);
        savePoints();
        finish();
    }
}
