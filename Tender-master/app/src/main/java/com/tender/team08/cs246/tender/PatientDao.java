package com.tender.team08.cs246.tender;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PatientDao {
    @Query("SELECT * FROM patient")
    List<Patient> getAll();

    @Query("SELECT * FROM patient WHERE uid IN (:userIds)")
    List<Patient> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM patient WHERE first_name LIKE :firstName AND " + "last_name LIKE :lastName LIMIT 1")
    Patient findByName(String firstName, String lastName);

    @Update
    void updatePatients(Patient... patients);

    @Insert
    void insertAll(Patient... patients);

    @Delete
    void delete(Patient patient);
}
