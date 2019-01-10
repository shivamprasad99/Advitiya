package iitropar.advitiya;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class ContactUS extends AppCompatActivity {

    private RelativeLayout layout;
    private SpiralView mrunner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        setBackgroundwithstars();
        getSupportActionBar().setTitle("Contact Us");

        TextView mail_ids[] = {findViewById(R.id.email1), findViewById(R.id.email2), findViewById(R.id.email3), findViewById(R.id.email4), findViewById(R.id.email5), findViewById(R.id.email6), findViewById(R.id.email7), findViewById(R.id.email8), findViewById(R.id.email9), findViewById(R.id.email10), findViewById(R.id.email11), findViewById(R.id.email12)};

        View.OnClickListener EmailOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView text = (TextView) v;
                String[] sendTo = {text.getText().toString()};
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, sendTo);

                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        };
        for (int i = 0; i < mail_ids.length; i++)
            mail_ids[i].setOnClickListener(EmailOnClick);

        View.OnClickListener PhoneOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView text = (TextView) v;
                Intent phone = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + text.getText().toString()));
//                phone.putExtra(Intent.EXTRA_PHONE_NUMBER, text.getText().toString());
                startActivity(Intent.createChooser(phone, "Choose an Phone client :"));
            }
        };
        TextView PhoneNos[] = {findViewById(R.id.phone1), findViewById(R.id.phone2), findViewById(R.id.phone3), findViewById(R.id.phone4), findViewById(R.id.phone5), findViewById(R.id.phone6), findViewById(R.id.phone7), findViewById(R.id.phone8), findViewById(R.id.phone9), findViewById(R.id.phone10), findViewById(R.id.phone11), findViewById(R.id.phone12)};
        for (int i = 0; i < PhoneNos.length; i++)
            PhoneNos[i].setOnClickListener(PhoneOnClick);
    }

//    private void setBackgroundwithstars(){
//        //layout is the main enclosing layout of the xml file
//        //activity_competition is the xml file in which it is present
//        layout= (RelativeLayout) View.inflate(this, R.layout.activity_contact_us,null);
//
//        //Class object for running stars
//        mrunner=new MotionRunnerWithoutPlanets(this);
//        //adding view to layout
//        layout.addView(mrunner);
//        //Black colour of background
//        layout.setBackgroundColor(0xF0000000);
//        //setting height of the view
//        mrunner.height =ConstraintLayout.LayoutParams.MATCH_PARENT;
//        setContentView(layout);
//    }

    private void setBackgroundwithstars() {
        //layout is the main enclosing layout of the xml file
        //activity_competition is the xml file in which it is present
        layout = (RelativeLayout) View.inflate(this, R.layout.activity_contact_us, null);

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