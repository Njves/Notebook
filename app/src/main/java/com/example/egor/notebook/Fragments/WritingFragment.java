package com.example.egor.notebook.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.egor.notebook.Databases.SQLiteFileListHandler;
import com.example.egor.notebook.Managers.FileManager;
import com.example.egor.notebook.R;

import java.io.*;
import java.util.Arrays;


public class WritingFragment extends Fragment {
    public static final String TAG = WritingFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private EditText mInputTitleFile;
    private EditText mInputFieldEditText;
    private TextView mFileContentTextView;
    private Button mWriteOnFileButton;
    private Button mOpenFileButton;
    private FileOutputStream mFileOutputStream;
    private FileInputStream mFileInputStream;
    private Context context;
    private Thread rxThread;
    private String mParamFileName;
    private String mParam2;
    private MenuFragmentInteraction mListener;
    private FileManager mFileManager;
    public static final String FILE_NAME = "text.txt";

    public WritingFragment() {
        // Required empty public constructor
    }


    //Fabric method
    public static WritingFragment newInstance(String param1) {
        WritingFragment fragment = new WritingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParamFileName = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_writing, container, false);
        mInputFieldEditText = view.findViewById(R.id.input_field);
        mOpenFileButton = view.findViewById(R.id.open);
        mWriteOnFileButton = view.findViewById(R.id.write);
        mFileContentTextView = view.findViewById(R.id.file_content);
        mInputTitleFile = view.findViewById(R.id.input_file_name);

        mInputTitleFile.setText(mParamFileName);
        mWriteOnFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        try {
//                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(mInputTitleFile.getText().toString() + ".txt", Context.MODE_PRIVATE)));
//                            writer.write(mInputFieldEditText.getText().toString());
//                            writer.flush();
                            FileManager fileManager = new FileManager(context);
                            fileManager.makeDocument(mInputTitleFile.getText().toString(), ".txt");
//                            TODO:Replace column in DB
                            Toast.makeText(context,  fileManager.writeInFile(mInputFieldEditText.getText().toString()), Toast.LENGTH_SHORT).show();
                            



                            Log.d(TAG, Arrays.toString(context.fileList()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }



                Toast.makeText(context, "Файл успешно записан!", Toast.LENGTH_SHORT).show();
            }
        });



        mOpenFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String fileName =  mInputTitleFile.getText().toString() + ".txt";
                    Log.d(TAG, fileName);
                    FileInputStream fis = context.openFileInput(fileName);
                    byte[] bytes = new byte[fis.available()];
                    fis.read(bytes);
                    String text = new String(bytes);
                    mFileContentTextView.setText(text);

                    Log.d(TAG, text);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof MenuFragmentInteraction) {
            mListener = (MenuFragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MenuFragmentInteraction");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface MenuFragmentInteraction {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

   

}
