package iitropar.advitiya;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class AnimationTesting extends AppCompatActivity {

    RelativeLayout layout;
    SpiralView mrunner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    setBackgroundwithstars();
    }
    private void setBackgroundwithstars(){
        //layout is the main enclosing layout of the xml file
        //activity_competition is the xml file in which it is present
        layout= (RelativeLayout) View.inflate(this, R.layout.activity_talks,null);

        //Class object for running stars
        mrunner=new SpiralView(this);
        //adding view to layout
        layout.addView(mrunner);
        //Black colour of background
        layout.setBackgroundColor(0xF0000000);
        //setting height of the view
        mrunner.height = CoordinatorLayout.LayoutParams.MATCH_PARENT;
        setContentView(layout);
        // mrunner.IncreaseSpeed(200,10);
    }

}
