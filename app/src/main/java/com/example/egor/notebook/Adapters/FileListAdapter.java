package com.example.egor.notebook.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.egor.notebook.Activity.MainActivity;
import com.example.egor.notebook.Managers.FileManager;
import com.example.egor.notebook.R;

public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.FileListHolder>
{
    private Context context;
    private String[] fileNames;
    private View.OnClickListener mOnItemClickListener;
    private FileListListener mListener;

    private int count;

    public FileListAdapter(Context context, String[] fileNames)
    {
        this.context = context;
        this.fileNames = fileNames;

    }
    @NonNull
    @Override
    public FileListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.file_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(layoutIdForListItem,viewGroup, false);

        return new FileListHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FileListHolder fileListHolder, int i) {
        fileListHolder.bindFileNames(i);
    }


    @Override
    public int getItemCount() {

        count = fileNames.length;
        return count;
    }



    public interface FileListListener
    {
        void showWritingFragment(String fileName);
    }
    class FileListHolder extends RecyclerView.ViewHolder
    {
        private TextView mTextViewFileName;
        private TextView mTextViwFileDate;
        private TextView mTextViewExtension;
        public FileListHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewFileName = itemView.findViewById(R.id.file_list_item_name);
            mTextViwFileDate = itemView.findViewById(R.id.file_list_item_creation_date);
            mTextViewExtension = itemView.findViewById(R.id.file_list_item_extension);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(context instanceof MainActivity) {
                        mListener = (MainActivity) context;
                        String[] fileNameArray = FileManager.getInstance(context).getFilesNames();
                        mListener.showWritingFragment(fileNameArray[getAdapterPosition()]);
                    }
                    else
                    {
                        throw new RuntimeException("Must implements FileListListener on MainActivity");
                    }
                }
            });
        }
        public void bindFileNames(int i)
        {
            if(i>=0)
            {
                Resources res = context.getResources();
                String placeholderName = res.getString(R.string.file_create_placeholder_name_dialog);
                String placeholderExtension = res.getString(R.string.file_list_holder_extension);
                String placeHolderDate = res.getString(R.string.file_list_holder_date);
                mTextViewFileName.setText(placeholderName + " " +  fileNames[i] + " #" + i);
                mTextViewExtension.setText(placeholderExtension+FileManager.getInstance(context).getFileExtension(fileNames[i]));
                mTextViwFileDate.setText(placeHolderDate);
            }

        }

    }
}
