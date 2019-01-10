package iitropar.advitiya;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


// JSON URL = https://script.googleusercontent.com/macros/echo?user_content_key=xAv03mdACNnqLhRMxIGywEYh86eRkfSbLxlfFj3NcX5bzLqX6y4IZE54Ya624Tgp8oWhEkOYZIBtMmEmn4QyI6Zn7u4yZt-uOJmA1Yb3SEsKFZqtv3DaNYcMrmhZHmUMWojr9NvTBuBLhyHCd5hHa1ZsYSbt7G4nMhEEDL32U4DxjO7V7yvmJPXJTBuCiTGh3rUPjpYM_V0PJJG7TIaKpxg3sqIEpekAzyihMj0D3XFJVzfu6jQdfU9jpet7gU6dQnzKBGNmosdqM6UAoBm0dMKiW3k6MDkf31SIMZH6H4k&lib=MbpKbbfePtAVndrs259dhPT7ROjQYJ8yx
public class Results extends AppCompatActivity {

    private static final String JSON_URL = "https://script.googleusercontent.com/macros/echo?user_content_key=3YML7Sr_GGPuWpTIjoMrj9z-nAQ1R_N05CXkw3AApCXxcEJqWHRjG7a15mPHVokAe6ncsBFZwS82L-EtGCzKVftuNvR1RKiEOJmA1Yb3SEsKFZqtv3DaNYcMrmhZHmUMWojr9NvTBuBLhyHCd5hHa1ZsYSbt7G4nMhEEDL32U4DxjO7V7yvmJPXJTBuCiTGh3rUPjpYM_V0PJJG7TIaKp3TA3SM5cVkTkHEkaKT9jpDfy_WdZZoJ24Bg-e54TOXsiHbngzp7RZdd3r3VBP8_YcKiW3k6MDkf31SIMZH6H4k&lib=MbpKbbfePtAVndrs259dhPT7ROjQYJ8yx";
    ArrayList<EventWinnerModel> winnerList;
    private RecyclerView recyclerView ;
    private ImageButton refreshButton ;
    private static LinearLayoutManager layoutManager ;
    private RelativeLayout layout ;
    private SpiralView mrunner ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_results);
        setBackgroundwithstars();
        winnerList = new ArrayList<>();
        getSupportActionBar().setTitle("Results");
        recyclerView = findViewById(R.id.recyclerView);


        ConnectivityManager connectivityManager = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to Results network

            loadData();
            refreshButton = findViewById(R.id.refresh);
            refreshButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ConnectivityManager connectivityManager = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                        //we are connected to Results network
                        loadData();

                    }

                }
            });
        }
        else {
            setBackgroundwithstarsInBlank();
        }

        refreshButton = findViewById(R.id.refresh);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connectivityManager = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to Results network
                    finish();
                    startActivity(getIntent());

                }

            }
        });
    }


    private void loadData( ) {
        //getting the progressbar
        final ProgressBar progressBar =  findViewById(R.id.progressBar);

        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);

        //creating Results string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        progressBar.setVisibility(View.INVISIBLE);


                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray collegeArray = obj.getJSONArray("Sheet1");
                            winnerList.clear();
                            //now looping through all the elements of the json array
                            for (int i = 0; i < collegeArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject eventObject = collegeArray.getJSONObject(i);

                                //creating Results hero object and giving them the values from json object
                                EventWinnerModel event = new EventWinnerModel(eventObject.getString("Name"), eventObject.getString("Winner"),eventObject.getString("Sec"),eventObject.getString("Third"));

                                //adding the hero to herolist
                                winnerList.add(event);
                            }
                            EventWinnerModel event = new EventWinnerModel("","","","");
                            winnerList.add(event);
                            EventWinnerAdapter adapter = new EventWinnerAdapter(getApplicationContext(), winnerList);
                            layoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(adapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //displaying the error in toast if occurrs
                        String message = null;
                        progressBar.setVisibility(View.GONE);
                        if (volleyError instanceof NetworkError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                            progressBar.setVisibility(View.GONE);
                        } else if (volleyError instanceof ServerError) {
                            message = "The server could not be found. Please try again after some time!!";
                        } else if (volleyError instanceof AuthFailureError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (volleyError instanceof NoConnectionError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (volleyError instanceof TimeoutError) {
                            message = "Connection TimeOut! Please check your internet connection.";
                        }
                        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();
                    }
                });



        //creating Results request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }

    private void setBackgroundwithstars() {
        //layout is the main enclosing layout of the xml file
        //activity_competition is the xml file in which it is present
        layout = (RelativeLayout) View.inflate(this, R.layout.activity_results, null);

        //Class object for running stars
        mrunner = new SpiralView(this);
        //adding view to layout
        layout.addView(mrunner);
        //Black colour of background
        layout.setBackgroundColor(0xF0000000);
        //setting height of the view
        mrunner.height = CoordinatorLayout.LayoutParams.MATCH_PARENT;
        setContentView(layout);



    }

    private void setBackgroundwithstarsInBlank() {
        //layout is the main enclosing layout of the xml file
        //activity_competition is the xml file in which it is present
        layout = (RelativeLayout) View.inflate(this, R.layout.blank_button, null);

        //Class object for running stars
        mrunner = new SpiralView(this);
        //adding view to layout
        layout.addView(mrunner);
        //Black colour of background
        layout.setBackgroundColor(0xF0000000);
        //setting height of the view
        mrunner.height = CoordinatorLayout.LayoutParams.MATCH_PARENT;
        setContentView(layout);



    }
    @Override
    protected void onPause() {
        super.onPause();
        mrunner.stopLooper();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mrunner.startLooper();
    }



}






