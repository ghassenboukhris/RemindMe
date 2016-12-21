package com.example.ghassen.myproject;

import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * A placeholder fragment containing a simple view.
 */
public class AllFragment extends Fragment {
    private CallbackManager mcallbackManager;


    private FacebookCallback<LoginResult> mCallback=new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {

            AccessToken accessToken =loginResult.getAccessToken();
            Profile profile= Profile.getCurrentProfile();
            String id=Profile.getCurrentProfile().getId();
            String firstName = profile.getFirstName();
            Uri profilepicture=profile.getProfilePictureUri(20, 20);
            Intent intent = new Intent(getActivity(), Menu_PrincipalActivity.class);
            intent.putExtra("EXTRA_SESSION_ID",profilepicture.toString());
            startActivity(intent);


        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };


    public AllFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.enableDefaults();

        FacebookSdk.sdkInitialize((getActivity().getApplicationContext()));
        mcallbackManager=CallbackManager.Factory.create();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_face, container, false);
    }
    @Override
    public void onViewCreated(View view,Bundle savedInstanceState)
    {super.onViewCreated(view, savedInstanceState);
        LoginButton loginButton=(LoginButton) view.findViewById(R.id.loginFacebookButton);
        loginButton.setReadPermissions("user_friends");
        loginButton.setFragment(this);
        loginButton.registerCallback(mcallbackManager,mCallback);

    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data ){
        super.onActivityResult(requestCode,resultCode,data);
        mcallbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
