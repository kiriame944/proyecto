package com.example.base;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UsuarioDao {
    @Insert
    void insert(Usuario usuario);

    @Update
    void update(Usuario usuario);

    @Delete
    void delete(Usuario usuario);

    @Query("SELECT * FROM Usuario")
    List<Usuario> getAllUsuarios();

    @Query("SELECT * FROM Usuario WHERE id = :id")
    Usuario getUsuarioById(int id);

    @Query("SELECT * FROM Usuario WHERE correo = :correo AND contrasena = :contrasena")
    Usuario login(String correo, String contrasena);
}