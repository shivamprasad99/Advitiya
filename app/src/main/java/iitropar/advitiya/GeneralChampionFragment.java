package iitropar.advitiya;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;


import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
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

import static android.content.ContentValues.TAG;


public class GeneralChampionFragment extends  Fragment{

    private static final String JSON_URL = "https://script.googleusercontent.com/macros/echo?user_content_key=xAv03mdACNnqLhRMxIGywEYh86eRkfSbLxlfFj3NcX5bzLqX6y4IZE54Ya624Tgp8oWhEkOYZIBtMmEmn4QyI6Zn7u4yZt-uOJmA1Yb3SEsKFZqtv3DaNYcMrmhZHmUMWojr9NvTBuBLhyHCd5hHa1ZsYSbt7G4nMhEEDL32U4DxjO7V7yvmJPXJTBuCiTGh3rUPjpYM_V0PJJG7TIaKpxg3sqIEpekAzyihMj0D3XFJVzfu6jQdfU9jpet7gU6dQnzKBGNmosdqM6UAoBm0dMKiW3k6MDkf31SIMZH6H4k&lib=MbpKbbfePtAVndrs259dhPT7ROjQYJ8yx";

    public GeneralChampionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View myView;
    ArrayList<ChampionClass> collegeList;
    ListView listview ;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ChampionAdapter adapter ;
    private Button refreshButton ;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_result_championship, container, false);
        collegeList = new ArrayList<>();
        listview = myView.findViewById(R.id.listview);


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
            myView = inflater.inflate(R.layout.blank_button, container, false);
            TextView text = myView.findViewById(R.id.blankText);
            text.setText("Connect With Internet and then Refresh the Page");
            setHasOptionsMenu(true);
            refreshButton = myView.findViewById(R.id.refresh);

            refreshButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ConnectivityManager connectivityManager = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                        //we are connected to Results network
                        //Intent intent = new Intent(getContext(), Results.class);
                        //startActivity(intent);
                        String Tag_name = getTag();
                        Fragment frg = null;
                        frg = getFragmentManager().findFragmentByTag(Tag_name);
                        final FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.detach(frg);
                        ft.attach(frg);
                        ft.commit();
                    }

                }
            });
        }



        return myView;
    }


    private void loadData( ) {
        //getting the progressbar
        final ProgressBar progressBar =  myView.findViewById(R.id.progressBar);

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
                            collegeList.clear();
                            //now looping through all the elements of the json array
                            for (int i = 0; i < collegeArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject collegeObject = collegeArray.getJSONObject(i);

                                //creating Results hero object and giving them the values from json object
                                ChampionClass college = new ChampionClass(collegeObject.getString("College"), collegeObject.getString("Points"));

                                //adding the hero to herolist
                                collegeList.add(college);
                            }
                            ChampionAdapter adapter = new ChampionAdapter(getActivity(), collegeList);
                            listview.setAdapter(adapter);



                        } catch (JSONException e) {
                            Log.e(TAG, "Error ");
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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