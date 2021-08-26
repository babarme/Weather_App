package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    ImageView iv;
    TextView tv,city_name;
    Button go;
    RequestQueue requestQueue;
    JsonObjectRequest request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=findViewById(R.id.edit_text_place);
        iv=findViewById(R.id.imageofsun);
        tv=findViewById(R.id.temperature);
        city_name=findViewById(R.id.cityname_tv);
        go=findViewById(R.id.go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city=editText.getText().toString();
                String key = "9f0e789e3d296a7f41d156d1041771ae";
                String url = "http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=9f0e789e3d296a7f41d156d1041771ae";

                requestQueue = Volley.newRequestQueue(getApplicationContext());

                request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject=response.getJSONObject("main");
                            JSONObject jsonObjectclouds = response.getJSONObject("clouds");
                            String temp = jsonObject.getString("temp");
                            double temprature = Double.parseDouble(temp)-270;
                            tv.setText((int)temprature+"\u2103"+"");
                            city_name.setText(city );
                            String cloud= jsonObjectclouds.getString("all");
                            int clouds = Integer.parseInt(cloud);
                            if(clouds>10){
                                iv.setImageResource(R.drawable._clouds_over_sun);
                            }
                            else{
                                iv.setImageResource(R.drawable._sun);
                            }


                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        city_name.setText("No such city found");
                        tv.setText("");
                    }
                });

                requestQueue.add(request);
            }
        });


    }
}