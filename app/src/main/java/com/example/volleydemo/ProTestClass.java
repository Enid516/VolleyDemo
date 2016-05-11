package com.example.volleydemo;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by enid on 2016/3/28.
 */
public class ProTestClass extends HttpDataTranUtils{
    private Context mContext;
    public ProTestClass(Context context){
        this.mContext = context;
    }

    public void test(String url,Map<String,String> params){
        super.postJsonData(mContext, url, params);
//        super.requestJsonData(mContext,url);
    }

    @Override
    public void proJSONDataOnSuc(JSONObject response) {
        Log.i("ProTestClass","success---->" + response.toString());
    }

    @Override
    public void proJSONDataOnErr(VolleyError error) {
        Log.i("ProTestClass","error---->" + error.toString());
    }
}
