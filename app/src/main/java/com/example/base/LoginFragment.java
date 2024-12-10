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

public class LoginFragment extends Fragment implements View.OnClickListener {

    private EditText etCorreo;
    private EditText etContrasena;
    private Button btnIngresar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etCorreo = view.findViewById(R.id.etCorreo);
        etContrasena = view.findViewById(R.id.etContrasena);
        btnIngresar = view.findViewById(R.id.btnIngresar);

        btnIngresar.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        String correo = etCorreo.getText().toString().trim();
        String contrasena = etContrasena.getText().toString().trim();

        if (correo.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "app_database").allowMainThreadQueries().build();
        Usuario usuario = db.usuarioDao().login(correo, contrasena);

        if (usuario != null) {
            SharedPreferences preferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("usuarioId", usuario.getId());
            editor.putString("nombre", usuario.getNombre());
            editor.putString("correo", usuario.getCorreo());
            editor.apply();

            Toast.makeText(getContext(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new WelcomeFragment())
                    .commit();
        } else {
            Toast.makeText(getContext(), "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
        }
    }
}