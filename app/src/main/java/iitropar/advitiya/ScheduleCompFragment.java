package iitropar.advitiya;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;


public class ScheduleCompFragment extends Fragment{
    View myView ;
    private RecyclerView recyclerView ;
    private static EventAdapter eventAdapter ;
    private ArrayList<Event> eventList ;
    private static Context context ;
    private static LinearLayoutManager layoutManager ;
    private CheckBox checkBox ;
    private Button register ;
    private DBHandler dba ;
    private int day ;
    private int type ;
    public ScheduleCompFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_schedule_day, container, false);
        recyclerView = myView.findViewById(R.id.recyclerView);

        context = getContext() ;
        dba = new DBHandler(getContext(), null, null, 1);
        eventList = new ArrayList<Event>();
        day = getArguments().getInt("eventDay");
        type = getArguments().getInt("eventType");

        eventList = dba.getDataDayType(day,type);


        eventAdapter = new EventAdapter(context, eventList,getActivity().getSupportFragmentManager());

        layoutManager = new LinearLayoutManager( getActivity());
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(
//                getActivity()
//        ));

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(eventAdapter);



        return myView ;
    }

}
