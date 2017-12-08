package qizepu.travelagency.adapter;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import qizepu.travelagency.R;
import qizepu.travelagency.activity.MainActivity;
import qizepu.travelagency.bean.ShopBean;
import qizepu.travelagency.utils.FirstEvent;


public class ShopListViewAdapter extends BaseAdapter {

    private MainActivity mainActivity;
    private FragmentActivity activity;
    private ArrayList<ShopBean> list;
    private Holder holder;
    private boolean ifchecked;
    private String msg;
    private Child_check child_check;


    public ShopListViewAdapter(FragmentActivity activity, ArrayList<ShopBean> list) {
        //注册EventBus
        EventBus.getDefault().register(this);
        mainActivity = new MainActivity();
        this.activity = activity;
        this.list = list;
    }

    public void setonChildclick(Child_check child_check) {
        this.child_check = child_check;
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            holder = new Holder();
            convertView = View.inflate(activity, R.layout.shop_listview_item, null);
            holder.tv1 = (TextView) convertView.findViewById(R.id.shop_listview_content);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.shop_listview_check_box);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }


        if (msg == null) {
            msg = "dsadsadsafsafasfsafa";
        }

        if (msg.equals("变成完成，并且勾选框出来吧")) {
            holder.checkBox.setVisibility(View.VISIBLE);
            Log.e("AAAAAAAAAAA", "勾选框出现");
        }
        if (msg.equals("变成编辑，并且勾选框消失吧")) {
            holder.checkBox.setVisibility(View.GONE);
            Log.e("BBBBBBBBBBBBB", "勾选框消失");
        }

        holder.tv1.setText(list.get(position).getContent());
        holder.checkBox.setChecked(list.get(position).isChild_check());

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                boolean checked = holder.checkBox.isChecked();
                if (child_check != null) {
                    child_check.onCheck(position, list.get(position).isChild_check());
                }
            }
        });
        return convertView;
    }

    //接受并处理消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(FirstEvent event) {
        msg = event.getMessage();


    }

    public interface Child_check {
        void onCheck(int position, boolean child_cx);
    }

    class Holder {
        TextView tv1;
        CheckBox checkBox;
    }
}
