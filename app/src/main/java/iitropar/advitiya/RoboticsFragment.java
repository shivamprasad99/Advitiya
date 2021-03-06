package iitropar.advitiya;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;

import android.os.Bundle;
import android.support.v4.app.Fragment;

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





public class RoboticsFragment extends Fragment implements ImageView.OnClickListener {

    RelativeLayout myView;
    Context mainContext;
    ImageView[] img = new ImageView[10];
    RelativeLayout.LayoutParams[] params = new RelativeLayout.LayoutParams[10];
    TextView closePopupBtn, title;
    DocumentView rules;
    PopupWindow popupWindow;
    LinearLayout linearLayout1;
    Button more;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = (RelativeLayout) inflater.inflate(R.layout.fragment_engineering, container, false);
        mainContext = myView.getContext();
        setHasOptionsMenu(true);


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
        for (int i = 0; i < 4; i++) {


            int j = i / 2;
            img[i] = new ImageView(mainContext);
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
        Glide.with(mainContext).load(R.drawable.robo_wars).into(img[0]);
        Glide.with(mainContext).load(R.drawable.robo_soccer).into(img[1]);
        Glide.with(mainContext).load(R.drawable.line_imitator).into(img[2]);
        Glide.with(mainContext).load(R.drawable.fury_road).into(img[3]);



        for (int i = 0; i < 4; i++) {
            myView.addView(img[i], params[i]);
            img[i].setOnClickListener(this);
        }


        return myView;
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    }

    @Override
    public void onClick(View view) {

        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        for (int i = 0; i < 4; i++) {
            if (view.equals(img[i])) {
                j = i;
                break;
            }
        }

        switch (j) {
            case 0:
                title.setText("Robowars");
                rules.setText(getString(R.string.robowars));

                more.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse(getString(R.string.register_robowars)));
                        startActivity(viewIntent);
                    }
                });
                break;
            case 1:
                title.setText("Robosoccer");
                rules.setText(getString(R.string.robosoccer));
                more.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse(getString(R.string.register_robosoccer)));
                        startActivity(viewIntent);
                    }
                });

                closePopupBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                break;
            case 2:

                title.setText("Line Imitator");
                rules.setText(getString(R.string.line_imitator));

                more.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse(getString(R.string.register_line_imitator)));
                        startActivity(viewIntent);
                    }
                });
                break;

            case 3:

            title.setText("Fury Road");
            rules.setText(getString(R.string.fury_road));

            more.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    Intent viewIntent =
                            new Intent("android.intent.action.VIEW",
                                    Uri.parse(getString(R.string.register_fury_road)));
                    startActivity(viewIntent);
                }
            });
            break;

        }

    }

}



