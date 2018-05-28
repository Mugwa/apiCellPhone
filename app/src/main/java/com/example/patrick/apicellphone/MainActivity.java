package com.example.patrick.apicellphone;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.patrick.apicellphone.Requetes.GetLocation;
import com.example.patrick.apicellphone.Requetes.GetPosition;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//public class MainActivity extends AppCompatActivity {
//public class MainActivity extends Activity {
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    TextView ville = null;
    TextView pays = null;
    TextView result = null;
    Button calcul = null;

    boolean presser = true;
    boolean mapReady = false;
    final String EXTRA_ADDRESS = "les_adresses";
    int REQUEST_CODE = 3;

    ArrayList<GetPosition.indexAddress> posList = null;

    //API variables
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lier les variables aux widgets
        calcul = (Button) findViewById(R.id.Calculer);
        result = (TextView) findViewById(R.id.resultatTxt);
        ville = (TextView) findViewById(R.id.villeUnit);
        pays = (TextView) findViewById(R.id.paysUnit);

        //Creer l'evenement sur appuie du bouton
        calcul.setOnClickListener(calculListener);

        //API variables --> Appel des données
        apiInterface = APIClient.getClient().create(APIInterface.class);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.welcome_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.welcome_about:
                // Comportement du bouton "A Propos"
                Intent intentabout = new Intent(MainActivity.this, AboutActivity.class);
                MainActivity.this.startActivity(intentabout);
                break;
                //return true;

            case R.id.welcome_help:
                // Comportement du bouton "Aide"
                //return true;
                break;

            case R.id.welcome_settings:
                // Comportement du bouton "Aide"
                //return true;
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Map ready
        mapReady = true;

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            //Check s'il y a les bonnes variables
            if (extras.containsKey("newLat")) {
                //Recuperation du lieux
                String newLat = (String) intent.getStringExtra("newLat");
                String newLong = (String) intent.getStringExtra("newLong");
                String newCity = (String) intent.getStringExtra("newCity");
                String newState = (String) intent.getStringExtra("newState");
                String newCountry = (String) intent.getStringExtra("newCountry");

                // Update des infos
                ville.setText(newState );
                pays.setText(newCountry );
                result.setText(newCity);

                // Update du marker
                changeLieux(Double.parseDouble(newLat), Double.parseDouble(newLong));
            }
        }
        else {
            //Message de bienvenu
            Toast.makeText(getApplicationContext(),"Salut", Toast.LENGTH_SHORT).show();

            // Add a marker in the Map and move the camera
            //LatLng lieux = new LatLng(46.9972, 6.9377);
            LatLng lieux = new LatLng(0, 0);
            mMap.addMarker(new MarkerOptions().position(lieux).title("Nowhere"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(lieux));
        }

    }

    /*
     * Change marker on the map
     */
    protected void changeLieux(Double lat, Double lon )
    {
        if (mapReady)
        {
            //Effacer les marquers
            mMap.clear();

            //Nouvelle coordonnees
            //LatLng lieux = new LatLng(46.9972, 6.9377);
            LatLng lieux = new LatLng(lat, lon);
            mMap.addMarker(new MarkerOptions().position(lieux).title(result.getText().toString()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(lieux));
        }
    }

    //Controlleur des cellules
    private class tmpPosition{
        public String state;
        public String country;
    }

    /*
     * Manage the button "Calcul"
     */
    private View.OnClickListener calculListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (presser) {
                //presser = false;

                //Toast.makeText(getApplicationContext(),"Patientez SVP", Toast.LENGTH_SHORT).show();

                //Appel des infos par API
                String lieuChoise = result.getText().toString().trim();
                Call<GetPosition> call = apiInterface.getPos(lieuChoise);

                //Reception du message de l'API
                call.enqueue(new Callback<GetPosition>() {
                    @Override
                    public void onResponse(Call<GetPosition> call, Response<GetPosition> response) {
                        GetPosition resource = response.body();
                        posList = resource.address;


                        Intent intent = new Intent(MainActivity.this, ChoiceActivity.class);

                        int i=0;
                        for (GetPosition.indexAddress unite:  posList) {
                            intent.putExtra("value0"+String.valueOf(i), unite.city);
                            intent.putExtra("value1"+String.valueOf(i), unite.code);
                            intent.putExtra("value2"+String.valueOf(i), unite.state);
                            intent.putExtra("value3"+String.valueOf(i), unite.country);
                            intent.putExtra("value4"+String.valueOf(i), unite.latitude);
                            intent.putExtra("value5"+String.valueOf(i), unite.longiture);
                            i++;
                        }

                        intent.putExtra("nb", i);
                        MainActivity.this.startActivity(intent);

                        //Affichage du resultat
                        /*
                        for (GetPosition.indexAddress test:  posList) {
                            //MaJ des infos sur l'IHM
                            ville.setText(test.state );
                            pays.setText(test.country );

                            //Changer le marker
                            changeLieux(Double.parseDouble(test.latitude), Double.parseDouble(test.longiture));
                        }*/
                    }

                    @Override
                    public void onFailure(Call<GetPosition> call, Throwable t) {
                        call.cancel();

                        result.setText("Erreur de connexion");
                        ville.setText("Oups");
                        pays.setText("Oups");
                    }
                });
            }
            else {
                presser = true;
                result.setText("Nb de phones");
            }
        }
    };
}



