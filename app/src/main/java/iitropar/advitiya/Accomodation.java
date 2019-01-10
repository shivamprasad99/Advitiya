package iitropar.advitiya;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class Accomodation extends AppCompatActivity {

    private ListView text;
    private RelativeLayout layout;
    private SpiralView mrunner;
    private Button register ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accomodation);
        setBackgroundwithstars();
        String []rules={"Production of College ID card is mandatory at the Registration Desk.","After successful registration, each registered student would be given an Accommodation Pass. It is mandatory for all registered students to carry this pass throughout their stay during the fest.",
                "Accommodation would be strictly on first-come-first-serve and shared basis.","Mattresses, blankets and bed sheets will be provided during your accommodation.",
                "If any student is found responsible for causing damage to the property of IIT Ropar, the concerned student will be liable to the full cost of replacement/repair.",
                "-Charges(INR) : 1 Day (300), 2 Days (400) and 3 Days (500).\n-Charges for online payment(INR) : 1 Day (250), 2 Days (350) and 3 Days (400).\n-Breakfast is included in accommodation."};

        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setTitle("Accomodation Rules");
        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://www.advitiya.in/accommodation.php";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        ArrayAdapter adapter = new ListAdapte(this,new ArrayList<String>(Arrays.asList(rules)));

        ListView listView = (ListView) findViewById(R.id.accomodation);
        listView.setAdapter(adapter);
    }

    private void setBackgroundwithstars() {
        //layout is the main enclosing layout of the xml file
        //activity_competition is the xml file in which it is present
        layout = (RelativeLayout) View.inflate(this, R.layout.activity_accomodation, null);

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
class ListAdapte extends ArrayAdapter<String> {
    public ListAdapte(Context context, ArrayList<String> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_accomodation, parent, false);
        }
        // Lookup view for data population
        TextView rule= (TextView) convertView.findViewById(R.id.rules_acc);
        // Populate the data into the template view using the data object
        rule.setText(user);
        // Return the completed view to render on screen
        return convertView;
    }
}