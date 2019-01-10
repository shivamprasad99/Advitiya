package iitropar.advitiya;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.Manifest;

import static iitropar.advitiya.R.*;


public class SpaceActivity extends AppCompatActivity implements View.OnClickListener , DrawerLayout.DrawerListener ,NavigationView.OnNavigationItemSelectedListener {

    Context contex;
    MotionRunner2 mrunner;
    private RelativeLayout layout=null;
    private boolean ScreenSet=false;
    private Button[] buttons;
    private boolean ButtonSet=false;
    private int RadiusLength;
    private boolean called;
    private String[] events={"COMPETITION","WORKSHOP","TALKS/STARS","SCHEDULE","MUN"};
    private NavigationView navigationView;
    private DrawerLayout draw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startAnimation();
        setContentView(layout);


        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.CALL_PHONE};

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        navigationView = findViewById(id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        addImageButton();

    }

    @Override
    public void onPause() {
        super.onPause();
        // Stop animation if going into background
        mrunner.stopLooper();
    }


    @Override
    public void onResume() {
        super.onResume();
        // Resume animation
        mrunner.startLooper();
    }

    // Method to start the animation


    private void startAnimation(){
        // Instantiate the class MotionRunner to define the entry screen display
        mrunner = new MotionRunner2(SpaceActivity.this, this);
        layout = (RelativeLayout) View.inflate(this, R.layout.activity_space, null);
        if (layout == null)
            Log.d("Animator", "something is null");
        contex=this;
        layout.setBackgroundColor(0xF0000000);
        mrunner.height = RelativeLayout.LayoutParams.MATCH_PARENT;
        Log.d("height","mrunner"+mrunner.height);
        float[] R0 = mrunner.getR0(500);
        ScreenSet = true;
        called = true;

        int height = getResources().getDisplayMetrics().heightPixels;
        int width = getResources().getDisplayMetrics().widthPixels;
        int resId = getResources().getIdentifier("navigation_bar_height","dimen","android");
        int result=0;
        if (resId > 0) {
            result = getResources().getDimensionPixelSize(resId);
        }
        Log.d("size","height is "+height+"result is "+result);
        if(mrunner.height!=-1){
            height=mrunner.height;
            Log.d("size","going here");
        }
        setViews(mrunner.getR0((height)),this,width);
        Log.d("size","R0"+R0[0]+" "+R0[1]+" "+R0[2]);
        //setContentView(layout);


    }

    public void setViews(float[] R0, Context a, int width){
        a=contex;
        if(layout==null) {
            startAnimation();
        }
        //layout.removeView(mrunner);
        buttons=new Button[R0.length];
        /*Button b=new Button(Results);
        b.setText("Hello");
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(500,RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.leftMargin=width/2-250;
                params.topMargin=0;

        Log.d("Space", "setViews");
        layout.addView(b,params);*/
        //mrunner.setVisibility(View.GONE);
        //mrunner.bringToFront();

        draw=layout.findViewById(id.drawer_layout);
        //ViewCompat.setTranslationZ(draw,100);
        draw.addDrawerListener(this);
        for(int i=0;i<R0.length;i++){
            buttons[i]=new Button(a);
            RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(
                    500,RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin=width/2-250;
            params.topMargin=(int) R0[i];
            buttons[i].setText(events[i]);
            Log.d("size","R0 is "+R0[i]);
            //Log.d("Array length","i= "+i+"R0="+(int) R0[i] + " " + mrunner.height);
            buttons[i].setTextColor(getResources().getColor(color.white));
            buttons[i].setBackgroundColor(Color.TRANSPARENT);
            buttons[i].setTextSize(20);
            ViewCompat.setTranslationZ(buttons[i],20);
            //   buttons[i].bringToFront();
            buttons[i].setOnClickListener(SpaceActivity.this);
            layout.addView(buttons[i],params);

            //buttons[i].setTranslationZ(0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                buttons[i].setBackground(getResources().getDrawable(drawable.roundbutton));
            }
            else{
                buttons[i].setBackgroundDrawable(getResources().getDrawable(drawable.roundbutton));
            }
        }

        //layout.setVisibility(View.VISIBLE);
        //setContentView(layout);
        ButtonSet=true;
        RadiusLength=R0.length;

        layout.addView(mrunner);

    }
    public void deleteViews(){
        if(ButtonSet){
            for(int i=0;i<RadiusLength;i++){
                layout.removeView(buttons[i]);
                buttons[i]=null;
            }
            Log.d("Array Length","Delete Views called");
            ButtonSet=false;

        }
        Log.d("Array Length","Delete Views called without");
    }

    @Override
    public void onClick(View view) {
        Button b=(Button) view;
        String buttonText=b.getText().toString();
        int j=-1;
        for(int i=0;i<events.length;i++){
            if(events[i].equals(buttonText)){
                j=i;
            }
        }
        switch(j){
            case 0:
                Log.d("size","first");
                Intent intent = new Intent(this, Competition.class);
                this.startActivity(intent);

                break;
            case 1:
                Intent intent1 = new Intent(this, Workshop.class);
                this.startActivity(intent1);
                break;
            case 2:
                Intent intent2 = new Intent(this, Talks.class);
                this.startActivity(intent2);
                break;
            case 3:
                Intent intent3 = new Intent(this, ScheduleDay.class);
                this.startActivity(intent3);
                break;
            case 4:
                String url = "http://www.advitiya.in/mun/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
            default:
                break;
        }
    }

    public void addImageButton(){
        ImageButton img=new ImageButton(this);
        img.setImageResource(drawable.ic_icon_nav);
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(72,72);
        params.leftMargin=50;
        params.topMargin=50;
        img.setBackgroundColor(Color.TRANSPARENT);
        layout.addView(img,params);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                draw.openDrawer(Gravity.LEFT);
            }
        });

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.home) {
            Intent intent = new Intent(getApplicationContext(), SpaceActivity.class);
            startActivity(intent);

        } else if (id == R.id.results) {
            Intent intent = new Intent(getApplicationContext(), Results.class);
            startActivity(intent);

        } else if (id == R.id.about) {
            Intent intent = new Intent(getApplicationContext(), AboutAdivitya.class);
            startActivity(intent);

        } else if (id == R.id.contact) {
            Intent intent = new Intent(this, ContactUS.class);
            startActivity(intent);


        } else if (id == R.id.developers) {
            String url = "https://software-community.github.io/";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        } else if (id == R.id.sponsors) {
            Intent intent = new Intent(this, Sponsors.class);
            startActivity(intent);


        } else if (id == R.id.campus) {

            String url = "http://www.advitiya.in/ca.php";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        } else if (id == R.id.accom){
            Intent intent = new Intent(this,Accomodation.class);
            startActivity(intent);
        }


        draw.closeDrawer(GravityCompat.START);
        return true;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);

        }
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        ViewCompat.setTranslationZ(draw,100);
    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {
        ViewCompat.setTranslationZ(draw,0);
    }

    @Override
    public void onDrawerStateChanged(int newState) {


    }
}