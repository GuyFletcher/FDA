package com.fletcherhart.fdaapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * Created by Fletcher on 6/20/2017.
 */

public class MainFragment extends Fragment {

    private EditText mSearch;
    private TextView mText;
    private static String urlFDA = "https://api.fda.gov/drug/event.json?limit=1";

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mSearch = (EditText) view.findViewById(R.id.search);
        mText = (TextView) view.findViewById(R.id.result);


        mSearch.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    try {
                        mText.setText(fetchData(mSearch.getText().toString()));
                    }
                    catch(IOException ex)
                    {
                        System.out.println(ex.getMessage());
                    }

                    //submit_btn.performClick();
                    return true;
                }
                return false;
            }
        });


        return view;
    }


    public static String fetchData(String search) throws IOException
    {

        String[] data = search.split(" ");

        URL url = new URL(urlFDA);
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        InputStream is = conn.getInputStream();

        BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }


        return sb.toString();
    }
}
