package com.xstudioo.appMunicipio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    // Creating EditText.
    EditText Nome,Email, Password,ConfirmPassword;

    // Create string variable to hold the EditText Value.
    String NomeHolder, EmailHolder1, PasswordHolder1, ConfirmPasswordHolder;

    // Creating button;
    Button ButtonCancel, RegisterButton1;

    // Boolean
    Boolean CheckEditText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Assigning ID's to EditText.
        Nome = (EditText) findViewById(R.id.editText_nome1);

        Email = (EditText) findViewById(R.id.editText_Email1);

        Password = (EditText) findViewById(R.id.editText_Password1);

        ConfirmPassword = (EditText) findViewById(R.id.editText_ConfirmPassword);



        // Register Button
        RegisterButton1 = (Button) findViewById(R.id.button_register1);

        // Cancel Register Button
        ButtonCancel = (Button) findViewById(R.id.button_Cancelregister);

        RegisterButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Storing server url into String variable.
                CheckEditTextIsEmptyOrNot();
                Register();

            }
        });

        ButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Storing server url into String variable.
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));

            }
        });

    }

    public void Register() {
        if (CheckEditText) {
            String HttpUrl = "https://appmunicipios.000webhostapp.com/Myslim/api/registo";
            Map<String, String> jsonParams = new HashMap<String, String>();

            jsonParams.put("name",NomeHolder);
            jsonParams.put("email",EmailHolder1);
            jsonParams.put("password",PasswordHolder1);

            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, HttpUrl,
                    new JSONObject(jsonParams),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getBoolean("status" )==true ) {
                                    startActivity(new Intent(RegisterActivity.this, Mapa_activity.class));
                                    Toast.makeText(RegisterActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(RegisterActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException ex) {
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    headers.put("User-agent", System.getProperty("http.agent"));
                    return headers;
                }
            };
            MySingleton.getInstance(this).addToRequestQueue(postRequest);
        }else{
            Toast.makeText(RegisterActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();
        }
    }

    public void CheckEditTextIsEmptyOrNot() {

        // Getting values from EditText.
        NomeHolder = Nome.getText().toString().trim();
        EmailHolder1 = Email.getText().toString().trim();
        PasswordHolder1 = Password.getText().toString().trim();
        ConfirmPasswordHolder = ConfirmPassword.getText().toString().trim();

        // Checking whether EditText value is empty or not.
        if (TextUtils.isEmpty(NomeHolder) || TextUtils.isEmpty(EmailHolder1) || TextUtils.isEmpty(PasswordHolder1) || TextUtils.isEmpty(ConfirmPasswordHolder) || !PasswordHolder1.equals(ConfirmPasswordHolder)) {

            // If any of EditText is empty then set variable value as False.
            CheckEditText = false;

        } else {

            // If any of EditText is filled then set variable value as True.
            CheckEditText = true;
        }
    }


}
