package com.example.lucas.accapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setTitle(getResources().getText(R.string.full_list_title));

        ArrayList<Activity> items = new ArrayList<Activity>();

        ArrayAdapter<Activity> itemsAdapter = new ArrayAdapter<Activity>(this, R.layout.activities_list_item, items);
        ListView listView = (ListView) findViewById(R.id.lv_items);
        View header = getLayoutInflater().inflate(R.layout.list_header, null);
        listView.addHeaderView(header);
        listView.setAdapter(itemsAdapter);

        final ActivitiesAdapter adapter = new ActivitiesAdapter(this, items);
        listView.setAdapter(adapter);

        this.fillList(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Activity selectedItem = (Activity) adapter.getItem(position-1);

                Intent intent = new Intent(ListActivity.this, FinalActivity.class);
                intent.putExtra("selectedActivity", selectedItem);
                startActivity(intent);
            }
        });
    }

    private void fillList(ActivitiesAdapter adapter) {

        Activity item0 = new Activity(0, getResources().getString(R.string.activity0), 60);
        adapter.add(item0);

        Activity item1 = new Activity(1, getResources().getString(R.string.activity1), 60);
        adapter.add(item1);

        Activity item2 = new Activity(2, getResources().getString(R.string.activity2), 60);
        adapter.add(item2);

        Activity item3 = new Activity(3, getResources().getString(R.string.activity3), 60);
        adapter.add(item3);

        Activity item4 = new Activity(4, getResources().getString(R.string.activity4), 60);
        adapter.add(item4);

        Activity item5 = new Activity(5, getResources().getString(R.string.activity5), 60);
        adapter.add(item5);

        Activity item6 = new Activity(6, getResources().getString(R.string.activity6), 60);
        adapter.add(item6);

        Activity item7 = new Activity(7, getResources().getString(R.string.activity7), 60);
        adapter.add(item7);

        Activity item9 = new Activity(9, getResources().getString(R.string.activity9), 60);
        adapter.add(item9);

        Activity item10 = new Activity(10, getResources().getString(R.string.activity10), 60);
        adapter.add(item10);

        Activity item11 = new Activity(11, getResources().getString(R.string.activity11), 60);
        adapter.add(item11);

        Activity item12 = new Activity(12, getResources().getString(R.string.activity12), 10);
        adapter.add(item12);

        Activity item13 = new Activity(13, getResources().getString(R.string.activity13), 10);
        adapter.add(item13);

        Activity item16 = new Activity(16, getResources().getString(R.string.activity16), 60);
        adapter.add(item16);

        Activity item17 = new Activity(17, getResources().getString(R.string.activity17), 60);
        adapter.add(item17);

        Activity item18 = new Activity(18, getResources().getString(R.string.activity18), 30);
        adapter.add(item18);

        Activity item19 = new Activity(19, getResources().getString(R.string.activity19), 60);
        adapter.add(item19);

        Activity item20 = new Activity(20, getResources().getString(R.string.activity20), 60);
        adapter.add(item20);

        Activity item24 = new Activity(24, getResources().getString(R.string.activity24), 30);
        adapter.add(item24);

        Activity item25 = new Activity(25, getResources().getString(R.string.activity25), 60);
        adapter.add(item25);

        Activity item26 = new Activity(26, getResources().getString(R.string.activity26), 10);
        adapter.add(item26);

        Activity item27 = new Activity(27, getResources().getString(R.string.activity27), 15);
        adapter.add(item27);

        Activity item28 = new Activity(28, getResources().getString(R.string.activity28), 15);
        adapter.add(item28);

        Activity item29 = new Activity(29, getResources().getString(R.string.activity29), 60);
        adapter.add(item29);

        Activity item30 = new Activity(30, getResources().getString(R.string.activity30), 15);
        adapter.add(item30);

        Activity item31 = new Activity(31, getResources().getString(R.string.activity31), 15);
        adapter.add(item31);

        Activity item32 = new Activity(32, getResources().getString(R.string.activity32), 60);
        adapter.add(item32);

        Activity item33 = new Activity(33, getResources().getString(R.string.activity33), 10);
        adapter.add(item33);

        Activity item34 = new Activity(34, getResources().getString(R.string.activity34), 15);
        adapter.add(item34);

        Activity item35 = new Activity(35, getResources().getString(R.string.activity35), 15);
        adapter.add(item35);

        Activity item36 = new Activity(36, getResources().getString(R.string.activity36), 30);
        adapter.add(item36);

        Activity item37 = new Activity(37, getResources().getString(R.string.activity37), 15);
        adapter.add(item37);

        Activity item38 = new Activity(38, getResources().getString(R.string.activity38), 15);
        adapter.add(item38);

        Activity item39 = new Activity(39, getResources().getString(R.string.activity39), 30);
        adapter.add(item39);

        Activity item40 = new Activity(40, getResources().getString(R.string.activity40), 10);
        adapter.add(item40);

        Activity item41 = new Activity(41, getResources().getString(R.string.activity41), 15);
        adapter.add(item41);

        Activity item42 = new Activity(42, getResources().getString(R.string.activity42), 30);
        adapter.add(item42);

        Activity item43 = new Activity(43, getResources().getString(R.string.activity43), 30);
        adapter.add(item43);

        Activity item44 = new Activity(44, getResources().getString(R.string.activity44), 60);
        adapter.add(item44);

        Activity item45 = new Activity(45, getResources().getString(R.string.activity45), 60);
        adapter.add(item45);

        Activity item46 = new Activity(46, getResources().getString(R.string.activity46), 15);
        adapter.add(item46);

        Activity item47 = new Activity(47, getResources().getString(R.string.activity47), 60);
        adapter.add(item47);

        Activity item48 = new Activity(48, getResources().getString(R.string.activity48), 60);
        adapter.add(item48);

        Activity item50 = new Activity(50, getResources().getString(R.string.activity50), 60);
        adapter.add(item50);

        Activity item51 = new Activity(51, getResources().getString(R.string.activity51), 60);
        adapter.add(item51);

        Activity item52 = new Activity(52, getResources().getString(R.string.activity52), 60);
        adapter.add(item52);

        Activity item53 = new Activity(53, getResources().getString(R.string.activity53), 60);
        adapter.add(item53);

        Activity item55 = new Activity(55, getResources().getString(R.string.activity55), 10);
        adapter.add(item55);

        Activity item56 = new Activity(56, getResources().getString(R.string.activity56), 60);
        adapter.add(item56);
    }
}
