package com.example.egor.notebook.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.example.egor.notebook.Fragments.WritingFragment;
import com.example.egor.notebook.R;

public class MainActivity extends AppCompatActivity implements WritingFragment.MenuFragmentInteraction {
    public static final String TAG = MainActivity.class.getSimpleName();
    FragmentManager fragmentManager;
    Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        fragment = new WritingFragment();
        fragmentManager.beginTransaction().add(R.id.menu_frame,fragment).commit();
        Log.d(TAG,getApplicationInfo().dataDir);



    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
