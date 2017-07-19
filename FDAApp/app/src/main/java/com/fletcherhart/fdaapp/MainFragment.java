package com.fletcherhart.fdaapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.json.*;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Fletcher on 6/20/2017.
 */

public class MainFragment extends Fragment {


    private static String urlFDA = "https://api.fda.gov/drug/event.json?limit=1";
    private String mJSON;
    public ProgressDialog pd;
    private RecyclerView mRecycle;
    private AdapterFDA mAdapter;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        System.out.println("Start");

        mRecycle = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private String parseAndAdd() throws JSONException {

        JSONObject obj = new JSONObject(mJSON);

        JSONArray arr = obj.getJSONArray("results");

        String genericName = "";
        String brand;
        String manufacturer;
        String useage;
        String EPC;

        for (int i = 0; i < arr.length(); i++)
        {
            genericName = arr.getJSONObject(i).getString("generic_name");
            System.out.println(genericName);
        }

        return genericName;
    }

    private void updateUI() {
        DrugLab drugLab = DrugLab.get(getActivity());
        List<Drug> drugs = drugLab.getDrugs();

        System.out.println("UpdateUI");

        if (mAdapter == null) {
            mAdapter = new AdapterFDA(drugs);
            mRecycle.setAdapter(mAdapter);
        } else {
            mAdapter.setDrugs(drugs);
            mAdapter.notifyDataSetChanged();
        }
    }

    //https://stackoverflow.com/questions/33229869/get-json-data-from-url-using-android
    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(getActivity());
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }
            mJSON = result;
        }
    }

    private class ResultHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Drug mDrug;
        private EditText mSearch;
        private TextView mGeneric;

        public ResultHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mSearch = (EditText) itemView.findViewById(R.id.list_search);
            mGeneric = (TextView) itemView.findViewById(R.id.list_drug);

          /*  mSearch.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {

                        new JsonTask().execute("https://api.fda.gov/drug/event.json?limit=1&search=fatigue");

                        try {
                            parseAndAdd();
                        }
                        catch(JSONException e)
                        {
                            System.out.println(e);
                        }

                        updateUI();
                        //submit_btn.performClick();
                        return true;
                    }
                    return false;
                }
            });*/

        }

        public void bindDrug(Drug drug)
        {
            mDrug = drug;
            mGeneric.setText(mDrug.getGenericName());
        }

        @Override
        public void onClick(View v) {
            Intent intent = DrugPageActivity.newIntent(getActivity(), mDrug.getId());
            startActivity(intent);
        }
    }


    private class AdapterFDA extends RecyclerView.Adapter<ResultHolder> {

        private List<Drug> mDrugs;

        public AdapterFDA(List<Drug> drugs) {
            mDrugs = drugs;
        }

        @Override
        public ResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_drug, parent, false);
            return new ResultHolder(view);
        }

        @Override
        public void onBindViewHolder(ResultHolder holder, int position) {
            Drug drug = mDrugs.get(position);
            holder.bindDrug(drug);
        }

        @Override
        public int getItemCount() {
            return mDrugs.size();
        }

        public void setDrugs(List<Drug> drugs) {
            mDrugs = drugs;
        }
    }
}
