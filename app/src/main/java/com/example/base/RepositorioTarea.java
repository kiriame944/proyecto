package com.example.base;


import android.content.Context;

import androidx.room.Room;

import java.util.List;

public class RepositorioTarea {

    private TareaDao tareaDao;

    public RepositorioTarea(Context context) {
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "app_database").build();
        tareaDao = db.tareaDao();
    }

    public void insert(Tarea tarea) {
        tareaDao.insert(tarea);
    }

    public void update(Tarea tarea) {
        tareaDao.update(tarea);
    }

    public void delete(Tarea tarea) {
        tareaDao.delete(tarea);
    }

    public List<Tarea> getAllTareas() {
        return tareaDao.getAllTareas();
    }

    public Tarea getTareaById(int id) {
        return tareaDao.getTareaById(id);
    }
}