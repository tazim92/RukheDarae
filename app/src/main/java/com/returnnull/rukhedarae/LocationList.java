package com.returnnull.rukhedarae;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class LocationList extends AppCompatActivity implements
        ConnectionCallbacks,
        OnConnectionFailedListener,
        LocationListener {


    //Left Side Bar
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;





    //Shared Preferences
    public static final String MY_PREFS_NAME = "LocationType";
    //Server URL
    String URL;
    //Initializing View
    Button buttonHome ;
    Button buttonRefresh;
    ListView listViewLocation;
    SwipeRefreshLayout mSwipeRefreshLayout;
    //Initializing Variables
    boolean sendDataStatus ;
    String type;
    String user_latitude;
    String user_longitude;
    String user_latitude_final;
    String user_longitude_final;
    //Define a request code to send to Google Play services
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;

    List<List<String>> locationInformations= new ArrayList<List<String>>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);


        getSupportActionBar().setTitle("    রুখে দাঁড়াই");
        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();


        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }catch (Exception ex){
            Toast.makeText(this,ex.toString(),Toast.LENGTH_SHORT).show();
        }



        //Start Google Api for latitude and longitude
        startGoolgeApi();
        //From this method, all the necessary functions are called
        gettingStarted();

    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.finishAffinity();
        }
        else {
            LocationList.this.finish();
        }
    }


    public void startGoolgeApi(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                        //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 500)        // 5 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds
    }


    @Override
    protected void onResume() {
        super.onResume();
        //Now lets connect to the API
        mGoogleApiClient.connect();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.v(this.getClass().getSimpleName(), "onPause()");

        //Disconnect from API onPause()
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }







    public void gettingStarted(){
        //Setting Variables
        setVariables();
        //Setting Buttons and ListView
        setViews();
        //Setting Type, User Latitude and Longitude
        getUserRequest();
        //Receiving User requested Data
        getInfoFromServer();
    }
    public void setVariables(){
        URL ="http://googleservice.austcse.org";
        sendDataStatus = false;
        type="";
        user_latitude="0.0";
        user_longitude="0.0";
        user_latitude_final = "0.0";
        user_longitude_final = "0.0";
    }
    public void setViews(){
        buttonHome = (Button)findViewById(R.id.buttonHome);
        buttonRefresh = (Button)findViewById(R.id.buttonRefresh);
        listViewLocation = (ListView)findViewById(R.id.listViewLocation);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
    }
    public void getUserRequest(){
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        type = prefs.getString("type", "");

        //First Attempt from Google Api
        if(isLatLngNull(user_latitude, user_longitude)){
            GPSTracker gpsTracker = new GPSTracker(LocationList.this);
            user_latitude = gpsTracker.getGpsLatitude();
            user_longitude = gpsTracker.getGpsLongitude();
            //Second Attempt from GPS
            if(isLatLngNull(user_latitude,user_longitude)){
                user_latitude=gpsTracker.getNetworkLatitude();
                user_longitude=gpsTracker.getNetworkLongitude();
                //Third check from Network
                if(isLatLngNull(user_latitude,user_longitude)){
                    Toast.makeText(this, "Sorry, your current location can't detect!",Toast.LENGTH_LONG).show();
                    startMainActivity();
                }else {
                    setUser_latitude_final(user_latitude);
                    setUser_longitude_final(user_longitude);
                }
            } else{
                setUser_latitude_final(user_latitude);
                setUser_longitude_final(user_longitude);
            }
        } else{
            setUser_latitude_final(user_latitude);
            setUser_longitude_final(user_longitude);
        }

    }

    public boolean isLatLngNull(String lat, String lng){
        if(lat.equalsIgnoreCase("0.0") || lat.isEmpty() || lat.equals("") || lat.equals(null) || lng.equalsIgnoreCase("0.0") || lng.isEmpty() || lng.equals("") || lng.equals(null)){
            return true;
        }
        else {
            return false;
        }
    }




    //Get Data From Server if network connection is available
    public void getInfoFromServer(){
        if(isConnected()){
            new HttpAsyncTask().execute(URL);
        }
        else{
            Toast.makeText(this, "No internet connection!", Toast.LENGTH_SHORT).show();
            startMainActivity();
        }
    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... arg) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("lat", getUser_latitude_final()));
            params.add(new BasicNameValuePair("lng", getUser_longitude_final()));
            params.add(new BasicNameValuePair("type", type));

            ServiceHandler serviceClient = new ServiceHandler();
            String json = serviceClient.makeServiceCall(URL,ServiceHandler.GET, params);

            if (json != null) {
                parseJSONData(json);
            } else {
                sendDataStatus=false;
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            if(sendDataStatus==true) {
                buttonHome.setText("Home");
                buttonRefresh.setText("Refresh");
                setListView();
                mSwipeRefreshLayout.setRefreshing(false);
                sendDataStatus=false;
            }
            else{
                Toast.makeText(getBaseContext(), "Sorry, can't receive locations at this moment!", Toast.LENGTH_SHORT).show();
                startMainActivity();
            }
        }
    }
    public void parseJSONData(String json){
        try {

            String name;
            String address;
            String contact;
            String distance;
            String lat2;
            String lng2;
            String front_url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=100&maxheight=100&photoreference=";
            String url;

            JSONArray array = new JSONArray(json);
            locationInformations.clear();

            if(array.length()==0){
                Toast.makeText(this, "Sorry, no data found!", Toast.LENGTH_SHORT).show();
                startMainActivity();
            }


            for(int i = 0; i < array.length(); i++){
                JSONObject object = array.getJSONObject(i);
                locationInformations.add(new ArrayList<String>());

                name=object.getString("name");
                address=object.getString("address");
                contact=object.getString("contact");
                distance=object.getString("distance");
                lat2 = object.getString("lat");
                lng2 = object.getString("lng");
                url = object.getString("link");
                if(url.equalsIgnoreCase("null"))
                    url="null";
                else
                    url = front_url+url;


                locationInformations.get(i).add(name);
                locationInformations.get(i).add(address);
                locationInformations.get(i).add(contact);
                locationInformations.get(i).add(distance);
                locationInformations.get(i).add(lat2);
                locationInformations.get(i).add(lng2);
                locationInformations.get(i).add(url);
            }

            sendDataStatus=true;

        } catch (JSONException e) {
            sendDataStatus=false;
        }
    }



    //After PostExecution (getting data from web server), start custom ListView
    public void setListView(){

        String[][] str = new String[locationInformations.size()][7];
        int i=0;
        int j=0;
        for (i=0; i<locationInformations.size(); i++){
            for(j=0; j<7; j++){
                str[i][j]=locationInformations.get(i).get(j);
            }
        }

        ListAdapter listAdapter = new CustomListview(this,str,type);
        listViewLocation.setAdapter(listAdapter);


        listViewLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dlat = locationInformations.get(position).get(4);
                String dlng = locationInformations.get(position).get(5);
                displayGoogleMap(dlat, dlng);
            }
        });



        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

            @Override
            public void onRefresh() {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        refreshPage();
                    }
                });
            }
        });



    }




    //Display Google Map Intent onClick List Item from custom ListView
    public void displayGoogleMap(String dlat, String dlng){
        // String uri = "http://maps.google.com/maps?saddr="+getUser_latitude_final()+","+getUser_longitude_final()+"&daddr="+dlat+","+dlng;
        String uri = "http://maps.google.com/maps?f=d&daddr="+dlat+","+dlng;
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
        try {
            startActivity(intent);
        }catch(ActivityNotFoundException ex)
        {
            Toast.makeText(this, "Please install Google Map Application", Toast.LENGTH_SHORT).show();
        }
    }









    //Goggle Api to receive latitude & longitude
    /**
     * If connected get lat and long
     *
     */
    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            //If everything went fine lets get latitude and longitude
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
            user_latitude=String.valueOf(currentLatitude);
            user_longitude=String.valueOf(currentLongitude);
            //  Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onConnectionSuspended(int i) {}
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
            /*
             * Google Play services can resolve some errors it detects.
             * If the error has a resolution, try sending an Intent to
             * start a Google Play services activity that can resolve
             * error.
             */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                    /*
                     * Thrown if Google Play services canceled the original
                     * PendingIntent
                     */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
                /*
                 * If no resolution is available, display a dialog to the
                 * user with the error.
                 */
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }
    /**
     * If locationChanges change lat and long
     *
     *
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
        user_latitude=String.valueOf(currentLatitude);
        user_longitude=String.valueOf(currentLongitude);
        // Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
    }
    // End of Goggle Api to receive latitude & longitude








    //Checking Network Connection
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
    //Refreshing the page
    public void refreshPage(){
        if(isConnected())
            gettingStarted();
        else
            startMainActivity();
    }
    //Start Home Page
    public void startMainActivity(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }








    //onClick Home Button
    public void onClickHome(View view){
        String btnText = buttonHome.getText().toString();
        if(btnText.equalsIgnoreCase("Home")) {
            startMainActivity();
        }
    }
    //onClick Refresh Button
    public void onClickRefresh(View view){
        String btnText = buttonRefresh.getText().toString();
        if(isConnected()) {
            if (btnText.equalsIgnoreCase("Refresh")) {
                mSwipeRefreshLayout.setRefreshing(true);
                buttonHome.setText("Please Wait . . .");
                buttonRefresh.setText("");
                refreshPage();
            }
        }else{
            Toast.makeText(this,"No internet connection!",Toast.LENGTH_SHORT).show();
            startMainActivity();
        }
    }




    public String getUser_latitude_final() {
        return user_latitude_final;
    }

    public void setUser_latitude_final(String user_latitude_final) {
        this.user_latitude_final = user_latitude_final;
    }

    public String getUser_longitude_final() {
        return user_longitude_final;
    }

    public void setUser_longitude_final(String user_longitude_final) {
        this.user_longitude_final = user_longitude_final;
    }
















    //Left Side Bar

    private void addDrawerItems() {
        String[] osArray = getResources().getStringArray(R.array.place_name);
        final String[] placeValue = getResources().getStringArray(R.array.place_value);

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, placeValue[position], Toast.LENGTH_SHORT).show();
                String locType = placeValue[position];
                setLocationInfo(locType);
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("নিকটবর্তী জরুরী সেবা সমূহ");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle("    রুখে দাঁড়াই");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }


    /*   @Override
       public boolean onCreateOptionsMenu(Menu menu) {
           // Inflate the menu; this adds items to the action bar if it is present.
           getMenuInflater().inflate(R.menu.menu_main, menu);
           return true;
       }
   */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       // if (id == R.id.action_settings) {
        //    return true;
        //}

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Setting Location Info
    public void setLocationInfo(String type){
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("type", type);
        editor.commit();

        if(isConnected()) {
            mSwipeRefreshLayout.setRefreshing(true);
            mDrawerLayout.closeDrawers();
            buttonHome.setText("Please Wait . . .");
            buttonRefresh.setText("");
            refreshPage();
        }
        else{
            Toast.makeText(this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
        }
    }




}