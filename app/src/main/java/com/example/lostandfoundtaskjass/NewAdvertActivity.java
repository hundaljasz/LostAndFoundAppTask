package com.example.lostandfoundtaskjass;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class NewAdvertActivity extends AppCompatActivity {
    RadioButton radioButton;
    private static final String API_KEY = "AIzaSyB65LpbHTkKKJ4EE7gV--oCVD4ryOaYjrI";
    EditText name,phone,desc,date,locationET;
    Button SaveItem,crntLocation;
    private FusedLocationProviderClient fusedLocationClient;
    private PlacesClient placesClient;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_advert);
        RadioGroup radioGroup = findViewById(R.id.postTypeRadioGroup);
        name = findViewById(R.id.NameItem);
        phone = findViewById(R.id.PhoneItem);
        desc = findViewById(R.id.DescItem);
        date = findViewById(R.id.DateItem);
        locationET = findViewById(R.id.LocationItem);
        SaveItem = findViewById(R.id.SaveItem);
        crntLocation = findViewById(R.id.currentLocation);
        // Initialize location services
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // Initialize Google Places API
        Places.initialize(getApplicationContext(), API_KEY);
        placesClient = Places.createClient(this);
        DB db = new DB(this);
//        locationET.setOnClickListener(v -> AutocompleteIntent());

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setTypeFilter(TypeFilter.ESTABLISHMENT);
        autocompleteFragment.setCountries("AU");
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ADDRESS, Place.Field.NAME));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener( ) {
            @Override
            public void onPlaceSelected (Place place) {
                locationET.setText(place.getAddress());
            };
            @Override
            public void onError (Status status) {
                // TODO: Handle the error
              Log. i (TAG, "An error occurred: " + status) ;
            }
            });
        crntLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(NewAdvertActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                } else {
                    ActivityCompat.requestPermissions(NewAdvertActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 0);
                }
            }
        });
        SaveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                if(TextUtils.isEmpty(radioButton.getText().toString().trim())) {
                    Toast.makeText(NewAdvertActivity.this, "Please Select Post Type!..", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(name.getText().toString().trim())) {
                    Toast.makeText(NewAdvertActivity.this, "Please Enter Name!..", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(phone.getText().toString().trim())) {
                    Toast.makeText(NewAdvertActivity.this, "Please Enter Phone!..", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(desc.getText().toString().trim())) {
                    Toast.makeText(NewAdvertActivity.this, "Please Enter Description!..", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(date.getText().toString().trim())) {
                    Toast.makeText(NewAdvertActivity.this, "Please Enter Date!..", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(locationET.getText().toString().trim())) {
                    Toast.makeText(NewAdvertActivity.this, "Please Enter Location!..", Toast.LENGTH_SHORT).show();
                    return;
                }
                Items i = new Items(
                        name.getText().toString(),
                        phone.getText().toString(),
                        desc.getText().toString(),
                        date.getText().toString(),
                        locationET.getText().toString(),
                        radioButton.getText().toString().trim().equals("Lost") ? 1 : 0
                );
                db.insertItem(i);
                Toast.makeText(NewAdvertActivity.this, "Advertisement Added Successfully!...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void getCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Double latitude = location.getLatitude();
                            Double longitude = location.getLongitude();
                            try {
                                Geocoder geocoder;
                                List<Address> addresses;
                                geocoder = new Geocoder(NewAdvertActivity.this, Locale.getDefault());
                                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                                String addressx = addresses.get(0).getAddressLine(0);
                                Toast.makeText(NewAdvertActivity.this, addressx, Toast.LENGTH_SHORT).show();
                                locationET.setText(addressx);
                            }catch (IOException e){
                                Log.d("reached",e.getMessage());
                            }
                        }
                    }
                });
    }

}