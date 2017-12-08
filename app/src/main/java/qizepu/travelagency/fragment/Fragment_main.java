package qizepu.travelagency.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import qizepu.travelagency.activity.MainActivity;
import qizepu.travelagency.adapter.ShopListViewAdapter;
import qizepu.travelagency.adapter.StrategyAdapter;
import qizepu.travelagency.bean.ShopBean;
import qizepu.travelagency.utils.FirstEvent;
import qizepu.travelagency.R;


public class Fragment_main extends Fragment implements ShopListViewAdapter.Child_check {


    private View shop_view;
    private View strategy_view;
    private String name;
    private ListView shop_listview;
    private RelativeLayout check_layout;
    private ListView strategy_listview;
    private ShopListViewAdapter shop_adapter;
    private CheckBox shop_check_box_all;
    private ArrayList<ShopBean> list;
    private int child_index;
    private Button btn_delete;
    private static ArrayList<ShopBean> shop_list = new ArrayList<>();
    private int COUNT;


    public Fragment_main() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        name = bundle.getString("name");

        if (name.equals("0")) {
            shop_view = inflater.inflate(R.layout.shop_fragment, container, false);
            return shop_view;
        } else if (name.equals("1")) {
            strategy_view = inflater.inflate(R.layout.strategy_fragment, container, false);
            return strategy_view;
        }

        return null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //注册EventBus
        EventBus.getDefault().register(this);

        if (name.equals("0")) {
            //长城列表的操作
            shop_initView();
            shop_initData();
            shop_listener();

        } else if (name.equals("1")) {
            //海洋馆列表操作
            strategy_initView();
            strategy_initData();


        }

    }

    private void shop_listener() {
        //删除按钮点击事件
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new AlertDialog.Builder(getActivity())
                        .setMessage("确定删除吗？")
                        .setPositiveButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (shop_check_box_all.isChecked()) {
                                    list.clear();
                                    check_layout.setVisibility(View.GONE);
                                    shop_adapter.notifyDataSetChanged();
                                    MainActivity.setTextViewChange();
                                    Toast.makeText(getActivity(), "删除全部成功", Toast.LENGTH_SHORT).show();

                                } else {
                                    for (int i = 0; i < shop_list.size(); i++) {
                                        ShopBean shopBean = shop_list.get(i);
                                        list.remove(shopBean);
                                        shop_adapter.notifyDataSetChanged();

                                        Toast.makeText(getActivity(), "删除成功" , Toast.LENGTH_LONG).show();
                                    }

//                                    ck_count_list.clear();
                                }


                            }
                        }).show();


            }
        });


        //全选按钮联动字条目
        shop_check_box_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = shop_check_box_all.isChecked();
                if (checked) {
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setChild_check(true);
                        shop_adapter.notifyDataSetChanged();
                    }
                    shop_check_box_all.setText("取消全选");
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setChild_check(false);
                        shop_adapter.notifyDataSetChanged();
                    }
                    shop_check_box_all.setText("全选");
                }


            }
        });
    }


    //接受并处理消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(FirstEvent event) {

        String msg = event.getMessage();
        if (msg.equals("变成完成，并且勾选框出来吧")) {
            check_layout.setVisibility(View.VISIBLE);
            shop_adapter.notifyDataSetChanged();
        }
        if (msg.equals("变成编辑，并且勾选框消失吧")) {
            check_layout.setVisibility(View.GONE);
            shop_adapter.notifyDataSetChanged();
        }
        shop_adapter.notifyDataSetChanged();

    }

    private void strategy_initData() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add("北京海洋馆");
        }
        setStrategyAdapter(list);

    }

    private void setStrategyAdapter(ArrayList<String> list) {
        StrategyAdapter strategyAdapter = new StrategyAdapter(getActivity(), list);
        strategy_listview.setAdapter(strategyAdapter);
    }

    private void strategy_initView() {
        strategy_listview = strategy_view.findViewById(R.id.strategy_listview);
    }

    private void shop_initData() {

        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            qizepu.travelagency.bean.ShopBean shopBean = new qizepu.travelagency.bean.ShopBean();
            shopBean.setContent("八达岭长城是万里长城的重要隘口居高临下山北京旅行团八达岭长城" + i);
            list.add(shopBean);
        }
        setShop_List_Adapter(list);
    }

    private void setShop_List_Adapter(ArrayList<ShopBean> list) {
        shop_adapter = new ShopListViewAdapter(getActivity(), list);
        shop_listview.setAdapter(shop_adapter);
        shop_adapter.setonChildclick(this);
    }

    private void shop_initView() {
        btn_delete = shop_view.findViewById(R.id.shop_fragment_delete);
        shop_listview = shop_view.findViewById(R.id.shop_listview);
        check_layout = shop_view.findViewById(R.id.all_check_layout);
        shop_check_box_all = shop_view.findViewById(R.id.all_check);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //反注册
        EventBus.getDefault().unregister(this);
    }

    //字条目状态的接口回调
    @Override
    public void onCheck(int position, boolean child_cx) {
        if (child_cx) {
            list.get(position).setChild_check(false);
        } else {
            list.get(position).setChild_check(true);
        }

        if (list.get(position).isChild_check()){
            shop_list.add(list.get(position));
        }


//        list.get(position).setChild_check(child_cx);
        shop_adapter.notifyDataSetChanged();
        setZhuangTai(list);//所有子条目联动全选按钮

    }

    private void setZhuangTai(ArrayList<ShopBean> list) {
        ArrayList<Boolean> booleen_list = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            boolean b = list.get(i).isChild_check();
            if (b) {
                booleen_list.add(b);
            } else {
                shop_check_box_all.setChecked(false);
                shop_check_box_all.setText("全选");
            }


        }

        if (booleen_list.size() == list.size()) {
            shop_check_box_all.setChecked(true);
            shop_check_box_all.setText("取消全选");
            shop_adapter.notifyDataSetChanged();

        } else {
            shop_check_box_all.setChecked(false);
            shop_adapter.notifyDataSetChanged();

        }
    }
}
