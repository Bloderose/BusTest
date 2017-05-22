package com.avtobus.projekt.avtobus;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jaka on 19.5.2017.
 */

public class Drugizaslon extends AppCompatActivity implements ItemClickListener{

    public Context mContext = null;
    public RecyclerView recView, recView2;
    public MainAdapter mAdapter;
    public String mSearchString;
    private ListView lv;
    private Button btnhit;
    JSONArray contacts = null;
    private static final String TAG = "MyActivity";

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext=this;
//            btnhit.setOnClickListener(
//                    new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                        }
//                    }
//            );
        // new JsonTask().execute();

        initD();
//        Arrivals();


    }
//    public void Arrivals(){
//        recView2 = (RecyclerView) findViewById(R.id.textSize);
//        mAdapter = new MainAdapter(Drugizaslon.this);
//        recView2.setLayoutManager(new LinearLayoutManager(Drugizaslon.this));
//        recView2.setItemAnimator(new DefaultItemAnimator());
//        recView2.setAdapter(mAdapter);
//        mAdapter.setClickListener(this);
//
//    }

    public void initD(){
        recView = (RecyclerView) findViewById(R.id.fishPriceList);
        mAdapter = new MainAdapter(Drugizaslon.this);
        recView.setLayoutManager(new LinearLayoutManager(Drugizaslon.this));
        recView.setItemAnimator(new DefaultItemAnimator());
        recView.setAdapter(mAdapter);
        //recView.addOnItemTouchListener(this);
        mAdapter.setClickListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    //Metoda za delo z searchbarom ko se vse pojavi
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);

        //Dodamo Search bar v app
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        final Toast toast = new Toast(this);

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                //Here u can get the value "query" which is entered in the search box.
                mSearchString = query;
                //new JsonTask().doInBackground(mSearchString);
                Log.d("ImePostaje", mSearchString);

                new JsonTask(JsonTask.ACTION_GET_STATIONS).execute(mSearchString);
                //doFilterAsync(mSearchString);
                toast.makeText(getApplicationContext(), mSearchString, Toast.LENGTH_LONG).show();
                // new JsonTask().execute(mSearchString);
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onCreateOptionsMenu(menu);
    }




    // kaj se bo zgodilo z gumbom
    public boolean onOptionsItemSelected(MenuItem item) {
        // izbira določenega elemnta
        switch (item.getItemId()) {
            //gum za iskanje
            case R.id.action_search:
                //  Searching();

                return true;
            //lokacija najdena
            case R.id.action_location_found:
                //LocationFound();
                return true;
            //gumb za posodbitev
            case R.id.action_refresh:
                return true;
            //za pomoč
            case R.id.action_help:
                return true;
            // za preverjanje posobitev
            case R.id.action_check_updates:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*private void LocationFound() {
        Intent itent = new Intent(Drugizaslon.this, LocationFound.class);
        startActivity(itent);
    }*/




    @Override
    public void onClick(View view, int position) {
        Log.i(TAG,"OnItemClick pos: "+position);

        //new JsonTask(JsonTask.ACTION_GET_ARRIVALS).execute("IME POSTAJE");
        //final Maindata sez = GlavniPodatki.get(position);
        //Intent i = new Intent(this);
        //startActivity(i);
    }


    //    private void Searching(){
//        Intent itent = new Intent(Drugizaslon.this, SearchResultsActivity.class);
//        startActivity(itent);
//    }
    public class JsonTask extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        private String Urlstr = "https://www.trola.si/";
        private JSONObject rawData;
        private String koncni;
        private int action = 0;

        public static final int ACTION_GET_STATIONS = 0;
        public static final int ACTION_GET_ARRIVALS = 1;

            /*JSONArray dataJsonArr = new JSONArray();
            JSONObject json = new JSONObject();*/

        public JsonTask(int action){
            this.action = action;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Drugizaslon.this);
            pDialog.setMessage("Prosimo počakajte...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            HtmlWithJson htmlData = new HtmlWithJson();
            switch (action){
                case JsonTask.ACTION_GET_STATIONS:
                    koncni = Urlstr.trim() + params[0].trim();
                    break;
                case JsonTask.ACTION_GET_ARRIVALS:
                    //prihod = Urlstr.trim() + params[0].trim() + "/postaje".trim(); //NISM ZIHR DA JE PRAV
                    break;
            }
            Log.d("String", koncni);
            rawData = htmlData.getJSONFromUrl(koncni);
            return rawData;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
//              seznam = rezultat.getStringArray(rawData);
//              Log.i("doInBackground", String.valueOf(seznam));
            JsonParser rezultat = new JsonParser();

            Log.d("StringRaw", String.valueOf(rawData));
            pDialog.dismiss();
            List<Maindata> podatki , prihodi = new ArrayList<>();
           // List<Maindata> GlavniPodatki, postaje = new ArrayList<>();
            pDialog.dismiss();


            prihodi = rezultat.getArrivals(rawData); //[1,2,56]
           // mAdapter.setData(prihodi);

            podatki = rezultat.getStations(rawData); //[bavarski,krsgzj]
            Log.d("Ime Postaje", String.valueOf(podatki));
            //mAdapter.setData(podatki);

            mAdapter.setData(podatki, prihodi);







//                lv = (ListView) findViewById(R.id.listView);
//                ListAdapter adapter = new ArrayAdapter<String>(Drugizaslon.this, R.layout.activity_main,R.id.listView);
//                lv.setAdapter(adapter);


        }
    }


}