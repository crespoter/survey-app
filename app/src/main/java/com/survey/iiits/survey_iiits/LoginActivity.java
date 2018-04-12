package com.survey.iiits.survey_iiits;

/*
 * Authors : [David Christie,Shyam Sunder]
 * Last Edited : 4/10/2018
 * Project : Pen Survey
 * Description : Survey application for IIIT Sricity
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
public class LoginActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "http://www.mocky.io/v2/597c41390f0000d002f4dbd1";
    private EditText mEmailView;
    private EditText mPasswordView;
    private String TAG = "Crespoter";
    private CheckBox checkBoxRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        if (!new PrefManager(this).isUserLogedOut()) {
            startHomeActivity();
        }

    }
    private void attemptLogin() {
        mEmailView.setError(null);
        mPasswordView.setError(null);
        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();
        final String[] userid = {new String()};
        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError("Username Cant be Empty");
            focusView = mEmailView;
            cancel = true;

        }
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError("Password Cant be Empty");
            focusView = mPasswordView;
            cancel = true;
        }
        RequestQueue MyRequestQueue = null;
        if (cancel) {
            focusView.requestFocus();
        } else {
            try {
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                String URL = ipclass.url + "/api/login";
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("username", email);
                jsonBody.put("password", password);
                final String requestBody = jsonBody.toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseJSON = new JSONObject(response);
                            boolean usernameStatus = responseJSON.getBoolean("usernameValid");
                            boolean passwordStatus = responseJSON.getBoolean("passwordValid");
                            if (!usernameStatus)
                            {
                                Toast.makeText(LoginActivity.this, "Invalid Username", Toast.LENGTH_SHORT).show();
                                mEmailView.setError("Invalid Username");
                            }
                            else if(!passwordStatus)
                            {
                                Toast.makeText(LoginActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                                mPasswordView.setError("Invalid Password");
                            }
                            else
                            {
                                int userId = responseJSON.getInt("id");
                                saveLoginDetails(email, password,Integer.toString(userId));
                                startHomeActivity();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY ERROR", error.toString());
                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                            return null;
                        }
                    }


                };

                requestQueue.add(stringRequest);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    private void startHomeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveLoginDetails(String email, String password,String userid) {
        new PrefManager(this).saveLoginDetails(email, password,userid);
    }
}