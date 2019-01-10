package iitropar.advitiya;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EventBS extends BottomSheetDialogFragment {

    TextView name_bs;
    TextView rules ;
    Button register ;

    int initialheight;
    boolean isExpanded = false;
    public static EventBS newInstance(String eventTitle ,String eventRules , String registerLink) {
        EventBS f = new EventBS();
        Bundle args = new Bundle();
        args.putString("eventTitle", eventTitle);
        args.putString("eventRules", eventRules);
        args.putString("registerLink" , registerLink);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.event_view_bs, container, false);
        name_bs =  myView.findViewById(R.id.eventName_bs);
        rules = myView.findViewById(R.id.rules_bs);
        register = myView.findViewById(R.id.register_bs);
        RelativeLayout relativeLayout = myView.findViewById(R.id.design_bottom_sheet);
        RelativeLayout nameLayout = myView.findViewById(R.id.name_layout);
        relativeLayout.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.round_corner_shape));
        name_bs.setText(getArguments().getString("eventTitle"));
        rules.setText(getArguments().getString("eventRules"));
        //String colorTop = getArguments().getString("color") ;


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getArguments().getString("registerLink");
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });
        return myView;
    }


    public class ResizeAnimation extends Animation {
        final int targetHeight;
        View view;
        int startHeight;

        public ResizeAnimation(View view, int targetHeight, int startHeight) {
            this.view = view;
            this.targetHeight = targetHeight;
            this.startHeight = startHeight;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
//            int newHeight = (int) (startHeight + targetHeight * interpolatedTime);
            //to support decent animation, change new heigt as Nico S. recommended in comments
            int newHeight = (int) (startHeight+(targetHeight - startHeight) * interpolatedTime);
            view.getLayoutParams().height = newHeight;
            view.requestLayout();
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }
}
