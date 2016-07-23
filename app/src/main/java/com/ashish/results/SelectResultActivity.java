package com.ashish.results;

/*
* Copyright 2016 Ashish Shekar (codekidX)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
* */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ashish.results.adapters.StreamListAdapter;
import com.ashish.results.fragments.InfoBottomSheet;
import com.ashish.results.services.ResultsNotificationService;

import java.util.ArrayList;

public class SelectResultActivity extends AppCompatActivity {


    ListView listView;
    TextView textView;

    FloatingActionButton fab;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor ed;

    private static final String MU_URL = "http://mu.ac.in/portal/results/";

    ArrayList<String> arrayList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ed = sharedPreferences.edit();
        ed.putString("mu", MU_URL);
        ed.apply();

        listView = (ListView) findViewById(R.id.streams_list);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        textView = (TextView) findViewById(R.id.header);

        if(sharedPreferences.contains("which-stream")) {
            switch (sharedPreferences.getInt("which-stream",0)) {
                case 0:
                    textView.setText("Notification set for BE COMPS Engg");
                    break;
                case 1:
                    textView.setText("Notification set for BE ETRX Engg");
                    break;
                case 2:
                    textView.setText("Notification set for BE EXTC Engg");
                    break;
                case 3:
                    textView.setText("Notification set for BE IT Engg");
                    break;
                case 4:
                    textView.setText("Notification set for BE ETRX Engg Sem-VII");
                    break;
                case 5:
                    textView.setText("Notification set for BE COMPS Engg (OLD)");
                    break;
                case 6:
                    textView.setText("Notification set for BE ETRX Engg (OLD)");
                    break;
                case 7:
                    textView.setText("Notification set for BE EXTC Engg (OLD)");
                    break;
                case 8:
                    textView.setText("Notification set for BE IT Engg (OLD)" );
                    break;
                case 99:
                    textView.setText("DISABLED" );
                    break;
            }
        }

        populateStreams();
        listView.setAdapter(new StreamListAdapter(arrayList, getApplicationContext()));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ed = sharedPreferences.edit();
                ed.putInt("which-stream",i);
                ed.apply();

                switch (i) {
                    case 0:
                        textView.setText("Notification set for BE COMPS Engg (CBGS)");
                        break;
                    case 1:
                        textView.setText("Notification set for BE ETRX Engg (CBGS)");
                        break;
                    case 2:
                        textView.setText("Notification set for BE EXTC Engg (CBGS)");
                        break;
                    case 3:
                        textView.setText("Notification set for BE IT Engg (CBGS)" );
                        break;
                    case 4:
                        textView.setText("Notification set for BE ETRX Engg Sem-VII (REV)");
                        break;
                    case 5:
                        textView.setText("Notification set for BE COMPS Engg (OLD)");
                        break;
                    case 6:
                        textView.setText("Notification set for BE ETRX Engg (OLD)");
                        break;
                    case 7:
                        textView.setText("Notification set for BE EXTC Engg (OLD)");
                        break;
                    case 8:
                        textView.setText("Notification set for BE IT Engg (OLD)" );
                        break;
                }

                Intent intent = new Intent(SelectResultActivity.this,ResultsNotificationService.class);
                startService(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialogFragment bottomSheetDialogFragment = new InfoBottomSheet();
                bottomSheetDialogFragment.show(getSupportFragmentManager(),"info-sheet");
            }
        });

    }



    private void populateStreams() {
        arrayList.add("- BE Computer Engg Sem VIII (CBGS)");
        arrayList.add("- BE Electronics Engg Sem VIII (CBGS)");
        arrayList.add("- BE Electronics and Telecommunication Engg Sem VIII (CBGS)");
        arrayList.add("- BE Information Technology Engg Sem VIII (CBGS)");
        arrayList.add("- BE Electronics Engg Sem VII (REV)");
        //old syllabus
        arrayList.add("- BE Computer Engg Sem VIII (OLD)");
        arrayList.add("- BE Electronics Engg Sem VIII (OLD)");
        arrayList.add("- BE Electronics and Telecommunication Engg Sem VIII (OLD)");
        arrayList.add("- BE Information Technology Engg Sem VIII (OLD)");
    }
}
