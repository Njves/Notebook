package com.example.egor.notebook.Managers;

import android.content.Context;
import com.example.egor.notebook.Databases.SQLiteFileListHandler;

import java.io.*;
import java.util.ArrayList;

public class FileManager {

    private File file;
    private SQLiteFileListHandler fileListDB;
    private ArrayList<File> mFileArrayList = new ArrayList<>();
    private Context context;
    private BufferedWriter bufferedFileOutput;
    private File currentFile;

    public FileManager(Context context)
    {
        this.context = context;
    }
    public void addFile(File file)
    {
        mFileArrayList.add(file);
    }
    public File makeDocument(String title,String extension) throws IOException {

        File file = new File(title+extension);
        bufferedFileOutput = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(file.getName(), Context.MODE_PRIVATE)));
        addFile(file);
        currentFile = file;
        return file;

    }

    public String writeInFile(String text) throws IOException {
        bufferedFileOutput.write(text);
        bufferedFileOutput.flush();
        bufferedFileOutput.close();
        return "Запись в файл окончена!";
    }
    public String writeInFile(File file, String text) throws IOException {
        bufferedFileOutput = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(file.getName(), Context.MODE_PRIVATE)));
        bufferedFileOutput.write(text);
        bufferedFileOutput.flush();
        bufferedFileOutput.close();
        return "Запись в файл окончена!";
    }

    public File getCurrentDocument() {
        return currentFile;
    }

    private void setCurrentDocument(File currentDocument) {
        this.currentFile = currentDocument;
    }
    public boolean isFileExistsOnSdCard()
    {

        return false;
    }
}
