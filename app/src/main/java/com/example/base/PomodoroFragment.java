package com.example.base;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PomodoroFragment extends Fragment implements View.OnClickListener {

    private RadioButton rbTrabajo;
    private RadioButton rbDescanso;
    private EditText etTiempoPersonalizado;
    private TextView tvTemporizador;
    private Button btnIniciarTemporizador;
    private Button btnReiniciarTemporizador;
    private CountDownTimer countDownTimer;
    private long tiempoRestante;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pomodoro, container, false);

        rbTrabajo = view.findViewById(R.id.rbTrabajo);
        rbDescanso = view.findViewById(R.id.rbDescanso);
        etTiempoPersonalizado = view.findViewById(R.id.etTiempoPersonalizado);
        tvTemporizador = view.findViewById(R.id.tvTemporizador);
        btnIniciarTemporizador = view.findViewById(R.id.btnIniciarTemporizador);
        btnReiniciarTemporizador = view.findViewById(R.id.btnReiniciarTemporizador);

        btnIniciarTemporizador.setOnClickListener(this);
        btnReiniciarTemporizador.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnIniciarTemporizador) {
            iniciarTemporizador();
        } else if (v.getId() == R.id.btnReiniciarTemporizador) {
            reiniciarTemporizador();
        }
    }

    private void iniciarTemporizador() {
        long tiempo = 0;
        if (rbTrabajo.isChecked()) {
            tiempo = 25 * 60 * 1000;
        } else if (rbDescanso.isChecked()) {
            tiempo = 5 * 60 * 1000;
        } else {
            String tiempoPersonalizado = etTiempoPersonalizado.getText().toString();
            if (!tiempoPersonalizado.isEmpty()) {
                tiempo = Integer.parseInt(tiempoPersonalizado) * 60 * 1000;
            }
        }

        countDownTimer = new CountDownTimer(tiempo, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tiempoRestante = millisUntilFinished;
                int minutos = (int) (millisUntilFinished / 1000) / 60;
                int segundos = (int) (millisUntilFinished / 1000) % 60;
                tvTemporizador.setText(String.format("%02d:%02d", minutos, segundos));
            }

            @Override
            public void onFinish() {
                tvTemporizador.setText("00:00");
            }
        }.start();
    }

    private void reiniciarTemporizador() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            tvTemporizador.setText("00:00");
        }
    }
}