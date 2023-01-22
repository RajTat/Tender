package com.tender.team08.cs246.tender;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;




@Database(entities = {Patient.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract PatientDao patientDao();

    //Singleton pattern;
    public static AppDatabase getInstance(Context context){
        if(instance == null){
            instance =  Room.databaseBuilder(context , AppDatabase.class, "tenderdb").fallbackToDestructiveMigration().build();
        }
        return instance;
    }


}
