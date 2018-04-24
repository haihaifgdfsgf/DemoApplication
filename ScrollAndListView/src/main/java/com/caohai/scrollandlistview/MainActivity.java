package com.caohai.scrollandlistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;

public class MainActivity extends AppCompatActivity {
    private MyListView mMyListView;
    private int direction = 0;
    private boolean isScroll = true;
    private String datas[] = new String[]{"一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
    private MyParent mMyScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMyListView = (MyListView) findViewById(R.id.list_view);
        mMyScrollView = (MyParent) findViewById(R.id.scrollView);
        mMyListView.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, datas));
        mMyListView.setListViewScroll(new MyListView.ListViewScroll() {
            @Override
            public void listViewScrolling(int mderection) {
                direction = mderection;
//                mMyScrollView.setIntercept(isScroll);
//                Log.i("caohaidemo", "isScroll:" + isScroll);
            }
        });
        mMyListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0) {
                    if (direction == 1) {
                        isScroll = true;
                    } else if (direction == 2) {
                        isScroll = false;
                    } else {
                        isScroll = false;
                    }
                } else if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
                    if (direction == 1) {
                        isScroll = false;
                    } else if (direction == 2) {
                        isScroll = true;
                    } else {
                        // isScroll = true;
                    }
                } else {
                    isScroll = false;
                }
            }
        });
    }
}
