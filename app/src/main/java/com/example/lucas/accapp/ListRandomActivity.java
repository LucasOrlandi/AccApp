package com.example.lucas.accapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListRandomActivity extends AppCompatActivity {

    private ArrayList<Integer> finalIndexes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_random);

        setTitle(getResources().getText(R.string.random_list_title));

        int indexes[] = {0, 1, 2, 3, 4, 5, 6, 7, 9, 10, 11, 12, 13, 16, 17, 18, 19, 20, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36,
                            37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 50, 51, 52, 53, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66};


        finalIndexes = selectActivities(indexes, 4);

        for(int i = 0; i < finalIndexes.size(); i++) {
            Log.e("finalIndexes", ""+finalIndexes.get(i).intValue());
        }

        ArrayList<Activity> items = new ArrayList<Activity>();

        ArrayAdapter<Activity> itemsAdapter = new ArrayAdapter<Activity>(this, R.layout.activities_list_item, items);
        ListView listView = (ListView) findViewById(R.id.lv_random_items);
        View header = getLayoutInflater().inflate(R.layout.list_header, null);
        listView.addHeaderView(header);
        listView.setAdapter(itemsAdapter);

        final ActivitiesAdapter adapter = new ActivitiesAdapter(this, items);
        listView.setAdapter(adapter);

        fillList(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Activity selectedItem = (Activity) adapter.getItem(position-1);

                Intent intent = new Intent(ListRandomActivity.this, FinalActivity.class);
                intent.putExtra("selectedActivity", selectedItem);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void fillList(ActivitiesAdapter adapter) {

        int element;

        for(int i = 0; i < finalIndexes.size(); i++) {

            element = finalIndexes.get(i).intValue();

            if(element == 0) {
                Activity item0 = new Activity(0, getResources().getString(R.string.activity0), 60);
                adapter.add(item0);

            } else if(element == 1) {
                Activity item1 = new Activity(1, getResources().getString(R.string.activity1), 60);
                adapter.add(item1);

            } else if(element == 2) {
                Activity item2 = new Activity(2, getResources().getString(R.string.activity2), 60);
                adapter.add(item2);

            } else if(element == 3) {
                Activity item3 = new Activity(3, getResources().getString(R.string.activity3), 60);
                adapter.add(item3);

            } else if(element == 4) {
                Activity item4 = new Activity(4, getResources().getString(R.string.activity4), 60);
                adapter.add(item4);

            } else if(element == 5) {
                Activity item5 = new Activity(5, getResources().getString(R.string.activity5), 60);
                adapter.add(item5);

            } else if(element == 6) {
                Activity item6 = new Activity(6, getResources().getString(R.string.activity6), 60);
                adapter.add(item6);

            } else if(element == 7) {
                Activity item7 = new Activity(7, getResources().getString(R.string.activity7), 60);
                adapter.add(item7);

            } else if(element == 9) {
                Activity item9 = new Activity(9, getResources().getString(R.string.activity9), 60);
                adapter.add(item9);

            } else if(element == 10) {
                Activity item10 = new Activity(10, getResources().getString(R.string.activity10), 60);
                adapter.add(item10);

            } else if(element == 11) {
                Activity item11 = new Activity(11, getResources().getString(R.string.activity11), 60);
                adapter.add(item11);

            } else if(element == 12) {
                Activity item12 = new Activity(12, getResources().getString(R.string.activity12), 10);
                adapter.add(item12);

            } else if(element == 13) {
                Activity item13 = new Activity(13, getResources().getString(R.string.activity13), 10);
                adapter.add(item13);

            } else if(element == 16) {
                Activity item16 = new Activity(16, getResources().getString(R.string.activity16), 60);
                adapter.add(item16);

            } else if(element == 17) {
                Activity item17 = new Activity(17, getResources().getString(R.string.activity17), 60);
                adapter.add(item17);

            } else if(element == 18) {
                Activity item18 = new Activity(18, getResources().getString(R.string.activity18), 30);
                adapter.add(item18);

            } else if(element == 19) {
                Activity item19 = new Activity(19, getResources().getString(R.string.activity19), 60);
                adapter.add(item19);

            } else if(element == 20) {
                Activity item20 = new Activity(20, getResources().getString(R.string.activity20), 60);
                adapter.add(item20);

            } else if(element == 24) {
                Activity item24 = new Activity(24, getResources().getString(R.string.activity24), 30);
                adapter.add(item24);

            } else if(element == 25) {
                Activity item25 = new Activity(25, getResources().getString(R.string.activity25), 60);
                adapter.add(item25);

            } else if(element == 26) {
                Activity item26 = new Activity(26, getResources().getString(R.string.activity26), 10);
                adapter.add(item26);

            } else if(element == 27) {
                Activity item27 = new Activity(27, getResources().getString(R.string.activity27), 15);
                adapter.add(item27);

            } else if(element == 28) {
                Activity item28 = new Activity(28, getResources().getString(R.string.activity28), 15);
                adapter.add(item28);

            } else if(element == 29) {
                Activity item29 = new Activity(29, getResources().getString(R.string.activity29), 60);
                adapter.add(item29);

            } else if(element == 30) {
                Activity item30 = new Activity(30, getResources().getString(R.string.activity30), 15);
                adapter.add(item30);

            } else if(element == 31) {
                Activity item31 = new Activity(31, getResources().getString(R.string.activity31), 15);
                adapter.add(item31);

            } else if(element == 32) {
                Activity item32 = new Activity(32, getResources().getString(R.string.activity32), 60);
                adapter.add(item32);

            } else if(element == 33) {
                Activity item33 = new Activity(33, getResources().getString(R.string.activity33), 10);
                adapter.add(item33);

            } else if(element == 34) {
                Activity item34 = new Activity(34, getResources().getString(R.string.activity34), 15);
                adapter.add(item34);

            } else if(element == 35) {
                Activity item35 = new Activity(35, getResources().getString(R.string.activity35), 15);
                adapter.add(item35);

            } else if(element == 36) {
                Activity item36 = new Activity(36, getResources().getString(R.string.activity36), 30);
                adapter.add(item36);

            } else if(element == 37) {
                Activity item37 = new Activity(37, getResources().getString(R.string.activity37), 15);
                adapter.add(item37);

            } else if(element == 38) {
                Activity item38 = new Activity(38, getResources().getString(R.string.activity38), 15);
                adapter.add(item38);

            } else if(element == 39) {
                Activity item39 = new Activity(39, getResources().getString(R.string.activity39), 30);
                adapter.add(item39);

            } else if(element == 40) {
                Activity item40 = new Activity(40, getResources().getString(R.string.activity40), 10);
                adapter.add(item40);

            } else if(element == 41) {
                Activity item41 = new Activity(41, getResources().getString(R.string.activity41), 15);
                adapter.add(item41);

            } else if(element == 42) {
                Activity item42 = new Activity(42, getResources().getString(R.string.activity42), 30);
                adapter.add(item42);

            } else if(element == 43) {
                Activity item43 = new Activity(43, getResources().getString(R.string.activity43), 30);
                adapter.add(item43);

            } else if(element == 44) {
                Activity item44 = new Activity(44, getResources().getString(R.string.activity44), 60);
                adapter.add(item44);

            } else if(element == 45) {
                Activity item45 = new Activity(45, getResources().getString(R.string.activity45), 60);
                adapter.add(item45);

            } else if(element == 46) {
                Activity item46 = new Activity(46, getResources().getString(R.string.activity46), 15);
                adapter.add(item46);

            } else if(element == 47) {
                Activity item47 = new Activity(47, getResources().getString(R.string.activity47), 60);
                adapter.add(item47);

            } else if(element == 48) {
                Activity item48 = new Activity(48, getResources().getString(R.string.activity48), 60);
                adapter.add(item48);

            } else if(element == 50) {
                Activity item50 = new Activity(50, getResources().getString(R.string.activity50), 60);
                adapter.add(item50);

            } else if(element == 51) {
                Activity item51 = new Activity(51, getResources().getString(R.string.activity51), 60);
                adapter.add(item51);

            } else if(element == 52) {
                Activity item52 = new Activity(52, getResources().getString(R.string.activity52), 60);
                adapter.add(item52);

            } else if(element == 53) {
                Activity item53 = new Activity(53, getResources().getString(R.string.activity53), 60);
                adapter.add(item53);

            } else if(element == 55) {
                Activity item55 = new Activity(55, getResources().getString(R.string.activity55), 10);
                adapter.add(item55);

            } else if(element == 56) {
                Activity item56 = new Activity(56, getResources().getString(R.string.activity56), 60);
                adapter.add(item56);

            }
        }
    }

    private ArrayList<Integer> selectActivities(int[] vector, int nActivities) {

        ArrayList<Integer> list = new ArrayList<Integer>();
        Random random = new Random();
        boolean put;
        int element;

        while(list.size() < nActivities) {

            put = true;
            element = random.nextInt(vector.length);

            if(!list.isEmpty()) {

                for(int i = 0; i < list.size() && put; i++) {

                    if(list.get(i).intValue() == vector[element])
                        put = false;
                }
            }

            if(put) {
                list.add(new Integer(vector[element]));
            }
        }

        return(list);
    }
}
