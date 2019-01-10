package iitropar.advitiya;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;

import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.android.volley.VolleyLog.TAG;


public class ScheduleDay1 extends Fragment {

    private RecyclerView recyclerView ;
    private static EventAdapter eventAdapter ;
    private ArrayList<Event> eventList ;
    private static Context context ;
    private static LinearLayoutManager layoutManager ;
    private DBHandler dba ;
    private static final String JSON_URL = "https://script.googleusercontent.com/macros/echo?user_content_key=24ZKRvVQuFxV9xuJaInAv8gzWTNFxrvoD08b4NjZKE8euqPhw8eqI4XMihTOA56oTs6vJSd77NQklWm65YzwDZjxY7Nc1sLtOJmA1Yb3SEsKFZqtv3DaNYcMrmhZHmUMWojr9NvTBuBLhyHCd5hHa1ZsYSbt7G4nMhEEDL32U4DxjO7V7yvmJPXJTBuCiTGh3rUPjpYM_V0PJJG7TIaKp0dLM60igx0qykB7lbhL-FlHu1JJ5LXhCv4wKhq9AhI3fkxwDmtEIHIU7hVVtHFbRMKiW3k6MDkf31SIMZH6H4k&lib=MbpKbbfePtAVndrs259dhPT7ROjQYJ8yx";
    private Button refreshButton ;
    private ProgressBar progressBar ;
    private int daynumber ;
    android.support.v4.app.FragmentManager manager;
    View myView;
    Context mainContext;
    Activity activity;
    private ImageView young_scientist, design_simulate, one_ohm , flight_show , water_rocket ;
    @Override
    public void onAttach(Context cont){
        super.onAttach(cont);
        mainContext=cont;
        activity=(Activity) cont;
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.scheduleday1, container, false);
        //mainContext = myView.getContext();
        manager=getActivity().getSupportFragmentManager();
        setHasOptionsMenu(true);
        progressBar = myView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        Bundle args = getArguments();
        daynumber = args.getInt("day");


        context = getContext();
        recyclerView = myView.findViewById(R.id.recyclerView);
        dba = new DBHandler(context, null, null, 1);
        eventList = new ArrayList<Event>();
        ConnectivityManager connectivityManager = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to Results network

            loadData();


            refreshButton = myView.findViewById(R.id.refresh);
            refreshButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ConnectivityManager connectivityManager = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                        //we are connected to Results network


                        loadData();


                    }

                }
            });
        }
        else {
            eventList = dba.getDataDay(daynumber);

            if (eventList.size() != 0){
                eventAdapter = new EventAdapter(context, eventList, manager);
                layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(eventAdapter);
            }
            else {
                myView = inflater.inflate(R.layout.blank_button, container, false);
            }
            refreshButton = myView.findViewById(R.id.refresh);

            refreshButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ConnectivityManager connectivityManager = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                        //we are connected to Results network
                        //Intent intent = new Intent(getApplicationContext(), ScheduleDay.class);
                        //startActivity(intent);
                       activity.finish();
                        startActivity(activity.getIntent());
                    }

                }
            });



        }

        return myView;
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    }


    private void loadData( ) {
        //getting the progressbar


        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);
        //progressBar.getProgressDrawable().setColorFilter(Color.WHITE);

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
                            JSONArray eventArray = obj.getJSONArray("Sheet1");
                            dba.clearDatabase();

                            //now looping through all the elements of the json array
                            for (int i = 0; i < eventArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject eventObject = eventArray.getJSONObject(i);

                                //creating Results hero object and giving them the values from json object
                                Event event = new Event(eventObject.getString("EventName"), eventObject.getString("EventVenue"),eventObject.getString("Time"),eventObject.getInt("EventDay"));

                                //adding the hero to herolist
                                dba.insertTableEvents(event);

                            }
                            eventList = dba.getDataDay(daynumber);
                            eventAdapter = new EventAdapter(context, eventList, manager);
                            layoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(eventAdapter);



                        } catch (JSONException e) {
                            Log.e(TAG, "Error ");
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.INVISIBLE);
                        //displaying the error in toast if occurrs
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });



        //creating Results request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }
}
