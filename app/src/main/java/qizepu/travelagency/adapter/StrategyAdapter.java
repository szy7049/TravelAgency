package qizepu.travelagency.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import qizepu.travelagency.R;



public class StrategyAdapter extends BaseAdapter{
    private  ArrayList<String> list;
    private  FragmentActivity activity;

    public StrategyAdapter(FragmentActivity activity, ArrayList<String> list) {
        this.activity=activity;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView==null){
            holder=new Holder();
            convertView= View.inflate(activity, R.layout.strategy_listview_item,null);
            holder.tv1= convertView.findViewById(R.id.strategy_listview_content);
            convertView.setTag(holder);
        }else {
            holder= (Holder) convertView.getTag();
        }

        holder.tv1.setText(list.get(position).toString());


        return convertView;
    }
    class Holder{
        TextView tv1;

    }
}
