package com.xstudioo.appMunicipio;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Ecra_inicialActivity extends AppCompatActivity {

    private Button btnAnonimo, btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ecra_inicial);

        btnAnonimo = (Button) findViewById(R.id.anonimo);

        btnAnonimo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ecra_inicialActivity.this, MainActivity.class));
            }
        });

        btnEntrar = (Button) findViewById(R.id.entrar);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ecra_inicialActivity.this, LoginActivity.class));
            }
        });
    }
}
