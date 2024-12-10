package com.example.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

public class RegistroFragment extends Fragment implements View.OnClickListener {

    private EditText etNombre;
    private EditText etCorreo;
    private EditText etContrasena;
    private Button btnCrearCuenta;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registro, container, false);

        etNombre = view.findViewById(R.id.etNombre);
        etCorreo = view.findViewById(R.id.etCorreo);
        etContrasena = view.findViewById(R.id.etContrasena);
        btnCrearCuenta = view.findViewById(R.id.btnCrearCuenta);

        btnCrearCuenta.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        String nombre = etNombre.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();
        String contrasena = etContrasena.getText().toString().trim();

        if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(getActivity(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "app_database").allowMainThreadQueries().build();
            Usuario usuario = new Usuario(nombre, correo, contrasena);
            db.usuarioDao().insert(usuario);

            // Obtener el ID del usuario insertado
            Usuario usuarioInsertado = db.usuarioDao().login(correo, contrasena);
            int usuarioId = usuarioInsertado.getId();

            SharedPreferences preferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("usuarioId", usuarioId);
            editor.putString("nombre", nombre);
            editor.putString("correo", correo);
            editor.apply();

            Toast.makeText(getActivity(), "Cuenta creada exitosamente", Toast.LENGTH_SHORT).show();
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new WelcomeFragment())
                    .commit();
        }
    }
}