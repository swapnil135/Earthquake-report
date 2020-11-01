/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.earthquake;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getName();

    TextView emptyView;
    ArrayList<Row> earthquakes;
    ListView earthquakeListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String requesturl="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=1&limit=100";
        earthquakeListView = (ListView) findViewById(R.id.list);
        emptyView=(TextView) findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(emptyView);
        EarthquakeAsyncTask task=new EarthquakeAsyncTask();
        task.execute(requesturl);



    }
    private class EarthquakeAsyncTask extends AsyncTask<String,Void,ArrayList<Row>>
    {

        @Override
        protected ArrayList<Row> doInBackground(String... urls) {
            ArrayList<Row> result=NetworkUtils.fetchEarthquakeData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<Row> rows) {
            emptyView.setText("No earthquakes found");
            ProgressBar p=(ProgressBar) findViewById(R.id.loading_indicator);
            p.setVisibility(p.GONE);
            RowAdapter adapter= new RowAdapter(MainActivity.this,rows);
            earthquakeListView.setAdapter(adapter);
            earthquakes=rows;
            final ArrayList<Row> finalEarthquakes = earthquakes;
            earthquakeListView.setOnItemClickListener(new ListView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String url= finalEarthquakes.get(position).getURL();
                    Uri webpage= Uri.parse(url);
                    Intent i= new Intent(Intent.ACTION_VIEW,webpage);
                    startActivity(i);
                }
            });
        }
    }


}
