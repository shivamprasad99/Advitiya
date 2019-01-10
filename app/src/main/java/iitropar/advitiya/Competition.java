package iitropar.advitiya;

import android.graphics.Color;

import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;


// JSON URL = https://script.googleusercontent.com/macros/echo?user_content_key=xAv03mdACNnqLhRMxIGywEYh86eRkfSbLxlfFj3NcX5bzLqX6y4IZE54Ya624Tgp8oWhEkOYZIBtMmEmn4QyI6Zn7u4yZt-uOJmA1Yb3SEsKFZqtv3DaNYcMrmhZHmUMWojr9NvTBuBLhyHCd5hHa1ZsYSbt7G4nMhEEDL32U4DxjO7V7yvmJPXJTBuCiTGh3rUPjpYM_V0PJJG7TIaKpxg3sqIEpekAzyihMj0D3XFJVzfu6jQdfU9jpet7gU6dQnzKBGNmosdqM6UAoBm0dMKiW3k6MDkf31SIMZH6H4k&lib=MbpKbbfePtAVndrs259dhPT7ROjQYJ8yx
public class Competition extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CoordinatorLayout layout;
    private MotionRunnerWithoutPlanets mrunner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackgroundwithstars();

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        getSupportActionBar().setTitle("Competitions");
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


    private void setBackgroundwithstars(){
        //layout is the main enclosing layout of the xml file
        //activity_competition is the xml file in which it is present
        layout= (CoordinatorLayout) View.inflate(this, R.layout.activity_competition,null);

        //Class object for running stars
        mrunner=new MotionRunnerWithoutPlanets(this);
        //adding view to layout
        layout.addView(mrunner);
        //Black colour of background
        layout.setBackgroundColor(0xF0000000);
        //setting height of the view
        mrunner.height =CoordinatorLayout.LayoutParams.MATCH_PARENT;
        setContentView(layout);
        mrunner.IncreaseSpeed(10,10);
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


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new EngineeringFragment(), "Engineering");
        adapter.addFragment(new ManagementFragment(), "Management");
        adapter.addFragment(new RoboticsFragment(), "Robotics");
        adapter.addFragment(new CodingFragment(), "Coding");
        adapter.addFragment(new OthersFragment(), "Others");


        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}


