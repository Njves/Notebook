package com.example.egor.notebook.Fragments.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.EditText;
import com.example.egor.notebook.Fragments.MenuFragment;
import com.example.egor.notebook.R;


public class CreateFileDialog extends DialogFragment {
    private Context context;
    private OnCreateFileDialogListener dialogListener;
    private EditText mFileNameEditText;
    private EditText mFileExtensionEditText;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if(context instanceof OnCreateFileDialogListener)
        {
            dialogListener = (OnCreateFileDialogListener) context;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.create_file_dialog, null);
        mFileNameEditText = view.findViewById(R.id.file_name_input_dialog);
        mFileExtensionEditText = view.findViewById(R.id.file_extension_input_dialog);
        dialogBuilder.setView(view).setPositiveButton(R.string.file_create_text_create_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mFileNameEditText.getText().toString() != " " || mFileExtensionEditText.getText().toString() != " ") {
                    dialogListener.createFile(mFileNameEditText.getText().toString(), mFileExtensionEditText.getText().toString());
                }
                else
                {
                    dialogListener.errorFileCreate("Заполните все");
                }
            }
        }).setNegativeButton(R.string.file_create_text_canel_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return dialogBuilder.create();
    }
    public interface OnCreateFileDialogListener
    {
        void createFile(String name, String extension);
        void errorFileCreate(String error);
    }
}
