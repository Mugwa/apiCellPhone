package com.example.patrick.apicellphone;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.patrick.apicellphone.Requetes.GetPosition;

import java.util.ArrayList;
import java.util.List;

public class ChoiceActivity extends AppCompatActivity {

    ListView mListView = null;
    Button retour = null;
    List<GetPosition.indexAddress> posList;
    final String EXTRA_ADDRESS = "les_adresses";


    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice_menu);

        //Lier les events
        mListView = (ListView) findViewById(R.id.listView);
        retour = (Button) findViewById(R.id.Retour);

        //Creer l'evenement sur appuie du bouton
        retour.setOnClickListener(retourListener);

        //Rechercher les infos recu
        List<Position> positions = new ArrayList<Position>();

        Intent intent = getIntent();
        if (intent  != null) {
            Integer nbPos = (Integer) intent.getIntExtra("nb", 0);

            for (int i=0; i < nbPos; i++) {
                String citytmp = (String) intent.getStringExtra("value0"+String.valueOf(i));
                String codetmp = (String) intent.getStringExtra("value1"+String.valueOf(i));
                String statetmp = (String) intent.getStringExtra("value2"+String.valueOf(i));
                String countrytmp = (String) intent.getStringExtra("value3"+String.valueOf(i));
                String lattmp = (String) intent.getStringExtra("value4"+String.valueOf(i));
                String longtmp = (String) intent.getStringExtra("value5"+String.valueOf(i));

                positions.add(new Position(citytmp, codetmp, statetmp, countrytmp, lattmp, longtmp));
            }
        }
        else {
            //Remplir la list
           positions = testPosition();
        }

        // Creation de la listView avec l'Adapter
        PositionAdapter adapter = new PositionAdapter(ChoiceActivity.this, positions);
        mListView.setAdapter(adapter);

        // Configuration de listview
        mListView.setClickable(true);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Recupere le lieu selectionne
                Position selectedItem = (Position) parent.getItemAtPosition(position);

                //TEST: affiche le lieu selectionne
                //Toast.makeText(getApplicationContext(),"clicked on " + selectedItem.getLatitude(), Toast.LENGTH_SHORT).show();

                //Retour dans l'autre context
                Intent intent = new Intent(ChoiceActivity.this, MainActivity.class);
                intent.putExtra("newLat", selectedItem.getLatitude());
                intent.putExtra("newLong", selectedItem.getLongitude());
                intent.putExtra("newCity", selectedItem.getCity());
                intent.putExtra("newState", selectedItem.getState());
                intent.putExtra("newCountry", selectedItem.getCountry());
                ChoiceActivity.this.startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.choice_menu, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.choice_about:
                // Comportement du bouton "A Propos"
                Intent intentabout = new Intent(ChoiceActivity.this, AboutActivity.class);
                ChoiceActivity.this.startActivity(intentabout);
                return true;

            case R.id.choice_help:
                // Comportement du bouton "Aide"
                return true;

            case android.R.id.home:
                Intent intent = new Intent(ChoiceActivity.this, MainActivity.class);
                ChoiceActivity.this.startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private List<Position> testPosition() {
        List<Position> positions = new ArrayList<Position>();

        positions.add(new Position("Neuch", "2000", "Neuchatel", "Suisse"));
        positions.add(new Position("Lausanne", "1000", "Vaud", "Suisse"));
        positions.add(new Position("Bienne", "2500", "Bern", "Suisse"));
        positions.add(new Position("Paris", "70000", "Paris", "France"));
        positions.add(new Position("Tumba", "12563", "Butare", "Rwanda"));
        positions.add(new Position("Fribourg", "2500", "Bern", "Suisse"));
        positions.add(new Position("Sion", "70000", "Paris", "France"));
        positions.add(new Position("Cossonay", "12563", "Butare", "Rwanda"));
        positions.add(new Position("Lugano", "70000", "Paris", "France"));
        positions.add(new Position("Milan", "12563", "Butare", "Rwanda"));

        return positions;
    }

    /*
     * Manage the button
     */
    private View.OnClickListener retourListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //final Intent intent = new Intent(ChoiceActivity.this, MainActivity.class);
            final Intent intent = new Intent(ChoiceActivity.this, MainActivity.class);
            ChoiceActivity.this.startActivity(intent);
        }
    };
}
