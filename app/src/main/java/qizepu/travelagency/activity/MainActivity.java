package qizepu.travelagency.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import qizepu.travelagency.R;


public class MainActivity extends AppCompatActivity {
    private String[] title = {"商品", "路线/旅游攻略"};
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView im_cancel;
    private static TextView tv_edit;
    private boolean ifchecked = true;
    private qizepu.travelagency.fragment.Fragment_main fragment_main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        EventBus.getDefault().register(this);

        initView();
        setTablayout();
        setListener();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:

               showDilog();
                break;
            /*
            * Home键是系统事件，不能通过KeyDown监听
            * 此处log不会打印
            */
            case KeyEvent.KEYCODE_HOME:

                break;
            case KeyEvent.KEYCODE_MENU:

                break;
TextView
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showDilog() {
        new AlertDialog.Builder(MainActivity.this)
            .setMessage("确定退出吗？")
            .setPositiveButton("否", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            })
            .setNegativeButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).show();
    }

    private void setListener() {
        //提示框
        im_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDilog();

            }
        });

        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Event完成的逻辑操作Bus.getDefault().post(new FirstEvent("传值"));
                if (tv_edit.getText().toString().equals("编辑")) {
                    qizepu.travelagency.utils.FirstEvent event = new qizepu.travelagency.utils.FirstEvent();
                    event.setMessage("变成完成，并且勾选框出来吧");

                    EventBus.getDefault().post(event);  //发送消息
                    tv_edit.setText("完成");
                } else if (tv_edit.getText().toString().equals("完成")) {

                    qizepu.travelagency.utils.FirstEvent event = new qizepu.travelagency.utils.FirstEvent();
                    event.setMessage("变成编辑，并且勾选框消失吧");
                    EventBus.getDefault().post(event);  //发送消息
                    tv_edit.setText("编辑");
                }
            }
        });

    }

    private void setTablayout() {
        setViewPagerAdapter();

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setViewPagerAdapter() {

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            //获取当前页面
            public Fragment getItem(int position) {
                Fragment fragment = new qizepu.travelagency.fragment.Fragment_main();
                Bundle bundle = new Bundle();
                bundle.putString("name", "" + position);
                fragment.setArguments(bundle);
                return fragment;
            }

            @Override
            public int getCount() {
                return title.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                //ViewPager的页面和tablayout的tab对应
                return title[position];
            }
        });
    }

    private void initView() {
        fragment_main = new qizepu.travelagency.fragment.Fragment_main();
        tv_edit = (TextView) findViewById(R.id.edit);
        im_cancel = (ImageView) findViewById(R.id.cancel);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册事件
//        EventBus.getDefault().unregister(this);
    }

    public static void setTextViewChange() {
        tv_edit.setVisibility(View.GONE);
    }


//    //接口回调
//    public interface  onCheck{
//        void onChecked (boolean ifchecked);
//    }


//    public void setonchecked(onCheck check){
//        this.check=check;
//    }
}
