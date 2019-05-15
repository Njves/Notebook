package com.example.egor.notebook.Fragments.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.egor.notebook.Managers.FileManager;
import com.example.egor.notebook.R;

public class DeletingFileDialog extends DialogFragment {
    public static final String TAG = DeletingFileDialog.class.getSimpleName();
    private EditText mEditTextCount;
    private OnDeleteFileCallback callback;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        callback = (OnDeleteFileCallback) getTargetFragment();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.delete_files_dialog,null);
        mEditTextCount = dialogView.findViewById(R.id.edit_text_delete_dialog);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity()).setTitle("Удалить файлы").setView(dialogView).setPositiveButton(R.string.file_delete_placeholder, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int count = Integer.parseInt(mEditTextCount.getText().toString());
                int fileListLength = FileManager.getInstance(getContext()).getFilesNames().length;
                if(count>0) {
                    if(count<=fileListLength) {
                        FileManager.getInstance(getContext()).deleteFilesByCount(count);
                        callback.onDelete();
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Количество удаляемых файлов превышает общее количество файлов", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getContext(), "Введите коректнное число", Toast.LENGTH_SHORT).show();
                }

            }
        }).setIcon(R.drawable.ic_delete_black_24dp);
        return dialogBuilder.create();
    }
    public interface OnDeleteFileCallback
    {
        void onDelete();
    }
}
