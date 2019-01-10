package iitropar.advitiya;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class Talks extends AppCompatActivity {

    private ViewPager mSlideViewPager ;
    private LinearLayout mDotLayout ;
    private SliderAdapter sliderAdapter ;
    private TextView[] mDots ;
    private RelativeLayout layout ;
    private SpiralView mrunner ;
    boolean addDot = true ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talks);
        setBackgroundwithstars();
        mSlideViewPager =  findViewById(R.id.slideViewPager);
        mDotLayout = findViewById(R.id.dots);
        getSupportActionBar().setTitle("Talks and Stars");
        sliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        addDot = false ;
        mSlideViewPager.addOnPageChangeListener(viewListener);

    }

    public void addDotsIndicator(int position){
        if (addDot) {
            mDots = new TextView[6];
            for (int i = 0; i < mDots.length; i++) {
                mDots[i] = new TextView(this);
                mDots[i].setText(Html.fromHtml("&#8226;"));
                mDots[i].setTextSize(35);
                mDots[i].setTextColor(getResources().getColor(R.color.grey));

                mDotLayout.addView(mDots[i]);

            }
        }
        if (mDots.length > 0){
            for (int i = 0 ; i < mDots.length ; i++){
                if (i == position){
                    mDots[position].setTextColor(getResources().getColor(R.color.white));
                }
                else {
                    mDots[i].setTextColor(getResources().getColor(R.color.grey));
                }
            }

        }
    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void setBackgroundwithstars() {
        //layout is the main enclosing layout of the xml file
        //activity_competition is the xml file in which it is present
        layout = (RelativeLayout) View.inflate(this, R.layout.activity_talks, null);

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

}
