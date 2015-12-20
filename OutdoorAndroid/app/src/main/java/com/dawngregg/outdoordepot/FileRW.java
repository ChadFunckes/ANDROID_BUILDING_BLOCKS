package com.dawngregg.outdoordepot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import android.app.Activity;
import android.util.Log;

public class FileRW {

    private final static String fileName = "TestFile.txt";
    private String TAG = "InternalFileWriteReadActivity";

    public void writeFile(String jdata, Activity curActivity) throws FileNotFoundException {

        FileOutputStream fos = curActivity.openFileOutput(fileName, android.content.Context.MODE_PRIVATE);

        PrintWriter pw = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(fos)));

        pw.println(jdata);

        pw.close();

    }

    public String readFile(Activity curActivity) throws IOException {

        FileInputStream fis = curActivity.openFileInput(fileName);
        BufferedReader br = null;
        StringBuffer data = new StringBuffer("");
        //try {
            br = new BufferedReader(new InputStreamReader(fis));
            String line = "";
            while ((line = br.readLine()) != null) {
                data.append(line);
            }
        //} catch (IOException e) {
        //    Log.e(TAG, "IOException");
        //} finally {
            br.close();
        //}
        return data.toString();
    }

}