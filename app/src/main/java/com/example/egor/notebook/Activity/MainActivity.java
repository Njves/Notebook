package com.example.egor.notebook.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.example.egor.notebook.Adapters.FileListAdapter;
import com.example.egor.notebook.Databases.SQLiteFileListHandler;
import com.example.egor.notebook.Fragments.MenuFragment;
import com.example.egor.notebook.Fragments.WritingFragment;
import com.example.egor.notebook.R;

public class MainActivity extends AppCompatActivity implements WritingFragment.MenuFragmentInteraction, MenuFragment.OnMenuFragmentDataListener, FileListAdapter.FileListListener, FragmentManager.OnBackStackChangedListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(R.string.current_action_list);
        fragment = new MenuFragment();
        fragmentManager.beginTransaction().add(R.id.menu_frame,fragment).commit();
        Log.d(TAG,getApplicationInfo().dataDir);
        SQLiteFileListHandler sql = new SQLiteFileListHandler(this);
        sql.getTable();



    }

    @Override
    public void updateTitle() {
        getSupportActionBar().setTitle(R.string.current_action_list);
    }

    //Implemented from
    @Override
    public void onCreateFragment(Fragment fragment) {

        getSupportActionBar().setTitle(R.string.current_action_list);
        fragmentManager.beginTransaction().hide(this.fragment).commit();
        if(fragment!=null) {
            this.fragment = fragment;
            fragmentManager.beginTransaction().add(R.id.menu_frame, fragment).addToBackStack(null).commit();
            if (fragment.getTag() != null) {
                Log.d(TAG, "Запустился фрагмент " + fragment.getTag());
            }
        }
    }
    // Implemented from FileListAdapter
    @Override
    public void showWritingFragment(String fileName) {

        this.fragment = WritingFragment.newInstance(fileName);
        fragmentManager.addOnBackStackChangedListener(this);
        fragmentManager.beginTransaction().replace(R.id.menu_frame,this.fragment).addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
        if(this.fragment.getTag()!=null)
        {
            Log.d(TAG, "Запустился фрагмент " + this.fragment.getTag());
        }
    }

    @Override
    public void onBackStackChanged() {
//        getSupportActionBar().setTitle(R.string.current_action_list);
    }
}
