package iitropar.advitiya;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bluejamesbond.text.DocumentView;
import com.bumptech.glide.Glide;


public class Workshop extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout myView;
    ImageView[] img = new ImageView[10];
    RelativeLayout.LayoutParams[] params = new RelativeLayout.LayoutParams[10];
    TextView closePopupBtn, title;
    DocumentView rules;
    PopupWindow popupWindow;
    LinearLayout linearLayout1;
    Button more;
    private MotionRunnerWithoutPlanets mrunner;
    private RelativeLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // setContentView(R.layout.activity_workshop);
        myView = (RelativeLayout) View.inflate(this, R.layout.activity_workshop, null);

        setBackgroundwithstars();
        getSupportActionBar().setTitle("Workshop");
        int height = getResources().getDisplayMetrics().heightPixels;
        int width = getResources().getDisplayMetrics().widthPixels;
        int w = 0;
        int h = 0;
        int marginw;
        int marginh;
        if (width < 900) {
            w = width / 2 - 100;
            h = width / 2 - 100;
        } else {
            w = 400;
            h = 400;
        }
        marginw = (int) (width - 2 * w) / 3;
        marginh = marginw;
        for (int i = 0; i < 8; i++) {


            int j = i / 2;
            img[i] = new ImageView(this);
            if (i % 2 == 0) {
                params[i] = new RelativeLayout.LayoutParams(
                        w, h);
                params[i].leftMargin = marginw;
                params[i].topMargin = (j + 1) * marginh + (j) * h;
            } else {
                params[i] = new RelativeLayout.LayoutParams(
                        w, h);
                params[i].leftMargin = w + marginw * 2;
                params[i].topMargin = (j + 1) * marginh + (j) * h;


            }

            Log.d("width", "i = " + i + "left margin" + params[i].leftMargin + " top margin = " + params[i].topMargin + " marginw= " + marginw + " marginh= " + marginh);

        }
        Glide.with(this).load(R.drawable.dron).into(img[0]);
        Glide.with(this).load(R.drawable.ml).into(img[1]);
        Glide.with(this).load(R.drawable.robot).into(img[2]);


        for (int i = 0; i < 3; i++) {
            myView.addView(img[i], params[i]);
            img[i].setOnClickListener(this);
        }

        setContentView(myView);
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    }



    private void setBackgroundwithstars() {
        //layout is the main enclosing layout of the xml file
        //activity_competition is the xml file in which it is present

        //Class object for running stars
        mrunner = new MotionRunnerWithoutPlanets(this);
        //adding view to layout
        myView.addView(mrunner);
        //Black colour of background
        myView.setBackgroundColor(0xF0000000);
        //setting height of the view
        mrunner.height = CoordinatorLayout.LayoutParams.MATCH_PARENT;
//        setContentView(layout);

        mrunner.IncreaseSpeed(10,10);

        //mrunner.IncreaseSpeed(200,10);

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

    @Override
    public void onClick(View view) {

        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.popupwindow1_showasdropdown, null);
        closePopupBtn = customView.findViewById(R.id.closePopupBtn);
        linearLayout1 = customView.findViewById(R.id.linearLayout);
        popupWindow = new PopupWindow(customView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        popupWindow.showAtLocation(linearLayout1, Gravity.CENTER, 0, 0);

        rules = customView.findViewById(R.id.rules);
        more = customView.findViewById(R.id.more);
        title = customView.findViewById(R.id.title);

        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.update();

        closePopupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        int j = 0;
        for (int i = 0; i < 3; i++) {
            if (view.equals(img[i])) {
                j = i;
                break;
            }
        }

        switch (j) {
            case 0:
                title.setText("Quadcopter Drone");
                rules.setText(getString(R.string.drone));

                more.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse(getString(R.string.register_drone)));
                        startActivity(viewIntent);
                    }
                });
                break;
            case 1:
                title.setText("Machine Learning");
                rules.setText(getString(R.string.ml));

                more.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse(getString(R.string.register_ml)));
                        startActivity(viewIntent);
                    }
                });
                break;
            case 2:

                title.setText("Robotics");
                rules.setText(getString(R.string.robot));

                more.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse(getString(R.string.register_robot)));
                        startActivity(viewIntent);
                    }
                });
                break;


        }

    }

}


