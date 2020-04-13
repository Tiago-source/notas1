package com.xstudioo.appMunicipio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    // Creating EditText.
    EditText Email, Password;

    // Creating button;
    Button LoginButton, RegisterButton;

    // Creating Volley RequestQueue.
    RequestQueue requestQueue;

    // Create string variable to hold the EditText Value.
    String EmailHolder, PasswordHolder;

    // Creating Progress dialog.
    ProgressDialog progressDialog;



    Boolean CheckEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Assigning ID's to EditText.
        Email = (EditText) findViewById(R.id.editText_Email);

        Password = (EditText) findViewById(R.id.editText_Password);

        // Assigning ID's to Button.
        LoginButton = (Button) findViewById(R.id.button_login);

        // Register Button
        RegisterButton = (Button) findViewById(R.id.button_register);
        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(LoginActivity.this);

        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(LoginActivity.this);

        // Adding click listener to button.
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Storing server url into String variable.

                CheckEditTextIsEmptyOrNot();
                login();
            }
        });

        // Adding click listener to button.
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Storing server url into String variable.
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

    }




    public void login() {
        if (CheckEditText) {
            String HttpUrl = "https://appmunicipios.000webhostapp.com/Myslim/api/login";
            Map<String, String> jsonParams = new HashMap<String, String>();


            jsonParams.put("email",EmailHolder);
            jsonParams.put("password",PasswordHolder);

            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, HttpUrl,
                    new JSONObject(jsonParams),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getInt("cod" )==6 ) {
                                    startActivity(new Intent(LoginActivity.this, Mapa_activity.class));
                                    Toast.makeText(LoginActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(LoginActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException ex) {
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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



        } else {

            Toast.makeText(LoginActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

        }
    }



    public void CheckEditTextIsEmptyOrNot() {

        // Getting values from EditText.
        EmailHolder = Email.getText().toString().trim();
        PasswordHolder = Password.getText().toString().trim();

        // Checking whether EditText value is empty or not.
        if (TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)) {

            // If any of EditText is empty then set variable value as False.
            CheckEditText = false;

        } else {

            // If any of EditText is filled then set variable value as True.
            CheckEditText = true;
        }
    }


}