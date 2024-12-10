package com.example.base;


import android.content.Context;

import androidx.room.Room;

import java.util.List;

public class RepositorioUsuario {

    private UsuarioDao usuarioDao;

    public RepositorioUsuario(Context context) {
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "app_database").build();
        usuarioDao = db.usuarioDao();
    }

    public void insert(Usuario usuario) {
        usuarioDao.insert(usuario);
    }

    public void update(Usuario usuario) {
        usuarioDao.update(usuario);
    }

    public void delete(Usuario usuario) {
        usuarioDao.delete(usuario);
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioDao.getAllUsuarios();
    }
}