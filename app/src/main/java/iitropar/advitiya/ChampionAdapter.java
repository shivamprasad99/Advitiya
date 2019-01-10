package iitropar.advitiya;

import java.util.ArrayList;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



public class ChampionAdapter extends BaseAdapter {
    public ArrayList<ChampionClass> collegeList;
    Activity activity;

    public ChampionAdapter(Activity activity, ArrayList<ChampionClass> collegeList) {
        super();
        this.activity = activity;
        this.collegeList = collegeList;
    }

    @Override
    public int getCount() {
        return collegeList.size();
    }

    @Override
    public Object getItem(int position) {
        return collegeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView sNo;
        TextView college;
        TextView points;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_champion_card, null);
            holder = new ViewHolder();
            holder.sNo =  convertView.findViewById(R.id.sNo);
            holder.college =  convertView.findViewById(R.id.college);
            holder.points = convertView.findViewById(R.id.points);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ChampionClass item = collegeList.get(position);
        int count = position + 1 ;
        holder.sNo.setText( Integer.toString(count));
        holder.college.setText(item.getCollegeName());
        holder.points.setText(item.getPoints());

        return convertView;
    }

}