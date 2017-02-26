package com.example.mrides;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Objects;

public class BackgroundWork extends AsyncTask<boolean[], Void, String> {

    Context context;
    AlertDialog mAlertDialog;

    public BackgroundWork(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(boolean[]... params) {
        String save_url = "http://successdrivingschool.ca/new_test_android.php";
        //String save_url = "https://httpbin.org/post";

        boolean [] array1 = params[0];
        String wantsPet = String.valueOf(array1[0]);
        String wantsSmoking = String.valueOf(array1[1]);
        String wantsMale = String.valueOf(array1[3]);
        String wantsFemale = String.valueOf(array1[2]);
        String email = "test2@app.com";//temporary hardcoded email.

        try {
            URL url = new URL(save_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data =
                    URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(email, "UTF-8")+"&"
                    +URLEncoder.encode("wantsPet", "UTF-8")+"="+URLEncoder.encode(wantsPet, "UTF-8")+"&"
                    +URLEncoder.encode("wantsSmoking", "UTF-8")+"="+URLEncoder.encode(wantsSmoking, "UTF-8")+"&"
                    +URLEncoder.encode("wantsMale", "UTF-8")+"="+URLEncoder.encode(wantsMale, "UTF-8")+"&"
                    +URLEncoder.encode("wantsFemale", "UTF-8")+"="+URLEncoder.encode(wantsFemale, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream =  httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result ="Initial Result\n";
            String line = "";
            while ((line = bufferedReader.readLine()) != null){
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return result;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    protected void onPreExecute() {
        mAlertDialog = new AlertDialog.Builder(context).create();
        mAlertDialog.setTitle("Adding Preferences");
    }

    @Override
    protected void onPostExecute(String result) {
        mAlertDialog.setMessage(result);
        mAlertDialog.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
