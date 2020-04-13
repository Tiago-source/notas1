package com.xstudioo.appMunicipio;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Mapa_activity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    Toolbar toolbar;
    GoogleMap mMap;

    String latHolder, lngHolder, descricaoHolder;

    Button btnadd, btncnl;
    ArrayList<Pontos> mapa_pontos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);


        toolbar = (Toolbar) findViewById(R.id.toolbar_mapa);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mapa");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mapa, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:
                finish();
                Toast.makeText(this, "Logout", Toast.LENGTH_LONG).show();
                break;
            case R.id.normal:

                if (item.isChecked()) {
                    // If item already checked then unchecked it
                    item.setChecked(false);
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    Toast.makeText(this, "Normal", Toast.LENGTH_LONG).show();
                } else {
                    // If item is unchecked then checked it
                    item.setChecked(true);
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    Toast.makeText(this, "Normal", Toast.LENGTH_LONG).show();
                }
                // Update the text view text style
                return true;

            case R.id.satelite:
                if (item.isChecked()) {
                    // If item already checked then unchecked it
                    item.setChecked(false);
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    Toast.makeText(this, "Normal", Toast.LENGTH_LONG).show();
                } else {
                    // If item is unchecked then checked it
                    item.setChecked(true);
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    Toast.makeText(this, "Satélite", Toast.LENGTH_LONG).show();
                }
                // Update the text view text style
                return true;

            case R.id.terreno:
                if (item.isChecked()) {
                    // If item already checked then unchecked it
                    item.setChecked(false);
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    Toast.makeText(this, "Normal", Toast.LENGTH_LONG).show();
                } else {
                    // If item is unchecked then checked it
                    item.setChecked(true);
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    Toast.makeText(this, "Terreno", Toast.LENGTH_LONG).show();
                }
                // Update the text view text style
                return true;

            case R.id.hibrido:
                if (item.isChecked()) {
                    // If item already checked then unchecked it
                    item.setChecked(false);
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    Toast.makeText(this, "Normal", Toast.LENGTH_LONG).show();
                } else {
                    // If item is unchecked then checked it
                    item.setChecked(true);
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    Toast.makeText(this, "Hibrido", Toast.LENGTH_LONG).show();
                }
                // Update the text view text style
                return true;

            case R.id.markers:
                if (item.isChecked()) {
                    // If item already checked then unchecked it
                    item.setChecked(false);
                    mMap.clear();
                } else {
                    // If item is unchecked then checked it
                    item.setChecked(true);
                    LerPontos();
                }
                // Update the text view text style
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        LatLng viana = new LatLng(41.686628, -8.657874);

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setOnMapClickListener(this);

        //posição da camera
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(viana)
                .zoom(10)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }


    @Override
    public void onMapClick(final LatLng latLng) {
        Toast.makeText(this, "Latitude :" + String.valueOf(latLng.latitude) + "\n" +
                "Longitude :" + String.valueOf(latLng.longitude), Toast.LENGTH_SHORT).show();
        mMap.addMarker(new MarkerOptions().position(latLng));


        setContentView(R.layout.popup_mapa);
        TextView latitude = (TextView) findViewById(R.id.Text_latitude);
        latitude.setText(String.valueOf(latLng.latitude));
        TextView longitude = (TextView) findViewById(R.id.Text_longitude);
        longitude.setText(String.valueOf(latLng.longitude));
        final EditText descricao = (EditText) findViewById(R.id.editText_descricao);


        btncnl = (Button) findViewById(R.id.button_Canceladd);
        btncnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), Mapa_activity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        btnadd = (Button) findViewById(R.id.button_addponto);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latHolder = String.valueOf(latLng.latitude);
                lngHolder = String.valueOf(latLng.longitude);
                descricaoHolder = descricao.getText().toString().trim();
                RegisterPonto();
            }
        });

    }

    public void RegisterPonto() {


        String HttpUrl = "https://appmunicipios.000webhostapp.com/Myslim/api/pontos";
        Map<String, String> jsonParams = new HashMap<String, String>();

        jsonParams.put("lat", latHolder);
        jsonParams.put("lng", lngHolder);
        jsonParams.put("descricao", descricaoHolder);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, HttpUrl,
                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("status") == true) {
                                startActivity(new Intent(Mapa_activity.this, Mapa_activity.class));
                                Toast.makeText(Mapa_activity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Mapa_activity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException ex) {
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Mapa_activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
    }

    public void LerPontos () {
        String HttpUrl = "https://appmunicipios.000webhostapp.com/Myslim/api/getPontos";

        // ArrayList<Pontos> mapa_pontos = new ArrayList<>();

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.GET, HttpUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if (response.getBoolean("status") == true) {
                                JSONArray pontos = response.getJSONArray("message");
                                for (int i = 0; i < pontos.length(); i++) {
                                    JSONObject jsonObject = pontos.getJSONObject(i);
                                    LatLng ponto = new LatLng(jsonObject.getDouble("lat"), jsonObject.getDouble("lng"));
                                    Pontos p = new Pontos(jsonObject.getInt("id"), ponto.latitude, ponto.longitude, jsonObject.getString("descricao"));
                                    mapa_pontos.add(p);
                                    mMap.addMarker(new MarkerOptions()
                                            .position(ponto)
                                            .title("Descrição: " + jsonObject.getString("descricao")));


                                }

                            } else {
                                Toast.makeText(Mapa_activity.this, response.getString("message"+"aqui"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException ex) {
                            Log.d("alerta","catch");
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("alerta","onErrorResponse");
                        Toast.makeText(Mapa_activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.d("alerta","getHeaders");
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(postRequest);

    }



}






