package com.xstudioo.appMunicipio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ecra_inicialActivity extends AppCompatActivity {

    private Button btnAnonimo, btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ecra_inicial);

        btnAnonimo = (Button) findViewById(R.id.anonimo);
        btnEntrar = (Button) findViewById(R.id.login);

        btnAnonimo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ecra_inicialActivity.this, MainActivity.class));
            }
        });

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ecra_inicialActivity.this, LoginActivity.class));
            }
        });
    }
}
