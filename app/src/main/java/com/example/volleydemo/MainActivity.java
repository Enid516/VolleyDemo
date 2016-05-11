package com.example.volleydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RequestQueue mQueue;
//    String url = "http://www.baidu.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        initRequestQueue();
//        mQueue.add(xmlRequest);

        String url = "http://api.map.baidu.com/telematics/v3/weather?";
//        String url = "http://api.map.baidu.com/telematics/v3/weather?location=%E5%8C%97%E4%BA%AC&output=json&ak=yourkey";


        Map<String,String> params = new HashMap<>();
        params.put("location","成都");
        params.put("output","json");
        new ProTestClass(this).test(url,params);

    }


    private void initRequestQueue() {
        mQueue = Volley.newRequestQueue(getApplicationContext());
    }

    StringRequest strReq = new StringRequest(Request.Method.GET, url, new StrListener(), new StrErrorListener());

    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new StrListener(), new StrErrorListener()) {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> map = new HashMap<String, String>();
            map.put("params1", "value1");
            map.put("params2", "value2");
            return map;
        }
    };

    XMLRequest xmlRequest = new XMLRequest("http://flash.weather.com.cn/wmaps/xml/china.xml", new Listener<XmlPullParser>() {
        @Override
        public void onResponse(XmlPullParser response) {
            try {
                int eventType = response.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            String nodeName = response.getName();
                            if ("city".equals(nodeName)) {
                                String pName = response.getAttributeValue(0);
                                Log.d("TAG", "pName is " + pName);
                            }
                            break;
                    }
                    eventType = response.next();
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }, new ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Log.e("TAG", volleyError.getMessage(), volleyError);
        }
    });

    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://m.weather.com.cn/data/101010100.html", null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("TAG", response.toString());
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("TAG", error.getMessage(), error);
        }
    });


    //Str 请求成功回调
    private class StrListener implements Listener<String> {

        @Override
        public void onResponse(String response) {
            Log.d("TAG", response);
        }
    }

    //公用请求失败回调
    private class StrErrorListener implements ErrorListener {

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Log.e("TAG", volleyError.getMessage(), volleyError);
        }
    }


}
