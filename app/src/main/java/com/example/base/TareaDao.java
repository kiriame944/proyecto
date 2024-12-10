package com.example.base;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TareaDao {

    @Insert
    void insert(Tarea tarea);

    @Update
    void update(Tarea tarea);

    @Delete
    void delete(Tarea tarea);

    @Query("SELECT * FROM Tarea")
    List<Tarea> getAllTareas();

    @Query("SELECT * FROM Tarea WHERE id = :id")
    Tarea getTareaById(int id);
}