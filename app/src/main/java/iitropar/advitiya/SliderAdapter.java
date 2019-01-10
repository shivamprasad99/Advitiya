package iitropar.advitiya;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class SliderAdapter extends PagerAdapter {

    Context context ;
    LayoutInflater layoutInflater ;

    public SliderAdapter(Context context){
        this.context = context ;
    }
    public int[] slider_images = {
            R.drawable.web_harrdy, R.drawable.biswa , R.drawable.samar ,R.drawable.rj_naved  ,R.drawable.divyanshu_damani ,R.drawable.djray

    };


    @Override
    public int getCount() {
        return slider_images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout)object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout ,container,false);
        ImageView slideImageView = view.findViewById(R.id.image);
        slideImageView.setImageResource(slider_images[position]);
        container.addView(view);



        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
