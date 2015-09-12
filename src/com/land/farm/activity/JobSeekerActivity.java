package com.land.farm.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.land.farm.Constants.UrlConstants;
import com.land.farm.R;
import com.land.farm.adapter.JobSeekerAdapter;
import com.land.farm.helper.RemoteJson;
import com.land.farm.interfaces.JsonCallback;
import com.land.farm.models.JobSeeker;

import java.util.ArrayList;
import java.util.Locale;


public class JobSeekerActivity extends Activity {

    private final int REQ_CODE_SPEECH_INPUT = 100;
    private ListView _jobSeekerListView;
    private TextView _jobSeekerLoaderView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobseeker);

        _jobSeekerListView = (ListView) this.findViewById(R.id.jobSeekerListView);
        _jobSeekerLoaderView = (TextView) this.findViewById(R.id.jobSeekerLoaderView);

        RemoteJson remoteJson = new RemoteJson(this.getApplicationContext());
        remoteJson.SendRequest(new JsonCallback() {

            @Override
            public void onSuccess(ArrayList<JobSeeker> result) {
                JobSeekerAdapter jobSeekerAdapter = new JobSeekerAdapter(JobSeekerActivity.this, R.id.jobSeekerListView, result);
                _jobSeekerListView.setAdapter(jobSeekerAdapter);

                _jobSeekerLoaderView.setWidth(0);
                _jobSeekerLoaderView.setHeight(0);
                _jobSeekerLoaderView.setVisibility(View.INVISIBLE);
            }
        }, UrlConstants.TICKERS);

        _jobSeekerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                TextView linkView = (TextView) view.findViewById(R.id.linkView);
//
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkView.getText().toString()));
//                startActivity(intent);
            }

        });
    }

    public void promptSpeechInput(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speechPrompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speechError),
                    Toast.LENGTH_SHORT).show();
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    //result.get(0));
                }
                break;
            }

        }
    }
}