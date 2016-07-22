package com.ashish.results.adapters;

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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ashish.results.R;

import java.util.ArrayList;

/**
 * Created by snowpuppet on 22/07/16.
 */
public class StreamListAdapter extends BaseAdapter {

    ArrayList<String> streams;
    Context context;

    TextView streamTitle;

    public StreamListAdapter(ArrayList<String> streams, Context context) {
        this.streams = streams;
        this.context = context;
    }

    @Override
    public int getCount() {
        return streams.size();
    }

    @Override
    public Object getItem(int i) {
        return streams.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.row_streams_list,viewGroup,false);
        streamTitle = (TextView) v.findViewById(R.id.stream_title);

        streamTitle.setText(streams.get(i));

        return v;
    }
}
