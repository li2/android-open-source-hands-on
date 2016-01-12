package com.paging.listview.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PullUpLoadListView listView;
    private MyPagingAdaper adapter;

    private List<String> firstList;
    private List<String> secondList;
    private List<String> thirdList;

    private int pager = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        adapter = new MyPagingAdaper(this);

        listView = (PullUpLoadListView) findViewById(R.id.pull_up_add_list_view);
        listView.setAdapter(adapter);
        listView.setOnPullUpLoadListener(new PullUpLoadListView.OnPullUpLoadListener() {
            @Override
            public void onPullUpLoading() {
                if (pager < 3) {
                    // Loading more data
                    new LoadDataAsyncTask().execute();
                } else {
                    // Already has no more data
                    listView.onPullUpLoadFinished(true);
                }
            }
        });
    }

    private void clearData() {
        pager = 0;
        adapter.removeAllItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                clearData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initData() {
        firstList = new ArrayList<String>();
        firstList.add("Afghanistan");
        firstList.add("Albania");
        firstList.add("Algeria");
        firstList.add("Andorra");
        firstList.add("Angola");
        firstList.add("Antigua and Barbuda");

        secondList = new ArrayList<String>();
        secondList.add("Bahamas, The");
        secondList.add("Bahrain");
        secondList.add("Bangladesh");
        secondList.add("Barbados");
        secondList.add("Belarus");
        secondList.add("Belgium");
        secondList.add("Belize");
        secondList.add("Benin");
        secondList.add("Burkina Faso");
        secondList.add("Burma");
        secondList.add("Burundi");

        thirdList = new ArrayList<String>();
        thirdList.add("Denmark");
        thirdList.add("Djibouti");
        thirdList.add("Dominica");
        thirdList.add("Dominican Republic");
    }

    private class LoadDataAsyncTask extends SafeAsyncTask<List<String>> {

        @Override
        public List<String> call() throws Exception {
            List<String> result = null;
            switch (pager) {
                case 0:
                    result = firstList;
                    break;
                case 1:
                    result = secondList;
                    break;
                case 2:
                    result = thirdList;
                    break;
            }
            Thread.sleep(1500);
            return result;
        }

        @Override
        protected void onSuccess(List<String> newItems) throws Exception {
            super.onSuccess(newItems);
            pager++;
            //
            listView.onPullUpLoadFinished(false);
            // Add more data to adapter and notify data set changed to update listView.
            adapter.addMoreItems(newItems);
        }
    }
}
