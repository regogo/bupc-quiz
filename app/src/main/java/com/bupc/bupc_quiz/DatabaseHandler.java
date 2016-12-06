package com.bupc.bupc_quiz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.bupc.bupc_quiz.data.models.Question;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {

    private final String TAG = DatabaseHandler.class.getSimpleName();

    private static DatabaseHandler instance = new DatabaseHandler(AppClass.getInstance().getAppContext());

    public static final String DATABASE_NAME = "bupcquiz.sqlite";
    public static final String DATABASE_LOCATION = "/data/data/com.bupc.bupc_quiz/databases/";
    private static final int DATABASE_VERSION = 1;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    private boolean mNewDatabase = false;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    public static synchronized DatabaseHandler getHandler(Context context) {
        return instance;
    }

    public void initializeDatabase() {
        if (mDatabase == null || !mDatabase.isOpen()) {
            getWritableDatabase();

            if (mNewDatabase) {
                try {
                    copyDatabase();
                } catch (IOException e) {
                    throw new Error("Error copying preloaded database");
                }
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        mNewDatabase = true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        mNewDatabase = true;
    }

    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(DATABASE_NAME).getPath();
        if (mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
//        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    public void copyDatabase() throws IOException {
        try {
            InputStream inputStream = mContext.getAssets().open(DATABASE_NAME);
            String outFilename = DATABASE_LOCATION + DATABASE_NAME;
            OutputStream outputStream = new FileOutputStream(outFilename);
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.i(TAG, "DB Copied");
            Toast.makeText(mContext, "INITIALIZATION SUCCESS", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(mContext, "INITIALIZATION FAILED", Toast.LENGTH_SHORT).show();
        }
    }

    public List<Question> getQuestions() {
        Question question = null;
        List<Question> questions = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM questions", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            question = new Question.Builder()
                    .setId(cursor.getInt(cursor.getColumnIndex("id")))
                    .setDifficulty(cursor.getString(cursor.getColumnIndex("difficulty")))
                    .setQuestion(cursor.getString(cursor.getColumnIndex("question")))
                    .setOption1(cursor.getString(cursor.getColumnIndex("option1")))
                    .setOption2(cursor.getString(cursor.getColumnIndex("option2")))
                    .setOption3(cursor.getString(cursor.getColumnIndex("option3")))
                    .setOption4(cursor.getString(cursor.getColumnIndex("option4")))
                    .setAnswer(cursor.getString(cursor.getColumnIndex("answer")))
                    .build();
            questions.add(question);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();

        return questions;
    }
}
