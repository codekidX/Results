package com.ashish.results.fragments;

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
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ashish.results.R;
import com.ashish.results.services.ResultsNotificationService;

/**
 * Created by snowpuppet on 22/07/16.
 */
public class InfoBottomSheet extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.info_bottom_sheet_layout,container,false);
        Button gotoSource = (Button) v.findViewById(R.id.goto_source);
        Button disableButton = (Button) v.findViewById(R.id.disable_notification);

        gotoSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://github.com/codekidX/Results"));
                startActivity(intent);
            }
        });

        disableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("which-stream",99);
                editor.apply();
                Intent intent = new Intent(getActivity(),ResultsNotificationService.class);
                getActivity().startService(intent);

                InfoBottomSheet.this.dismiss();
                getActivity().recreate();
            }
        });

        return v;
    }

}
