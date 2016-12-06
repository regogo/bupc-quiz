package com.bupc.bupc_quiz.data.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;


public class Question implements Parcelable {

    public static final String TAG = Question.class.getSimpleName();
    private static final String PARCEL_STATE_KEY = TAG + "_Parcel_Key";

    private int id;
    private String difficulty, question, option1, option2, option3, option4, answer;

    private Question(int id, String difficulty, String question, String option1, String option2, String option3, String option4, String answer) {
        this.id = id;
        this.difficulty = difficulty;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
    }

    public Question(Parcel source) {
        this.id = source.readInt();
        this.difficulty = source.readString();
        this.question = source.readString();
        this.option1 = source.readString();
        this.option2 = source.readString();
        this.option3 = source.readString();
        this.option4 = source.readString();
        this.answer = source.readString();
    }

    public int getId() {
        return id;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public String getAnswer() {
        return answer;
    }

    public String toString() {
        return "Connection{" +
                "difficulty= " + difficulty +
                "question= " + question +
                ", option1= " + option1 +
                ", option2= " + option2 +
                ", option3= " + option3 +
                ", option4= " + option4 +
                ", answer= " + answer +
                '}';
    }

    public static class Builder {
        private int id;
        private String difficulty, question, option1, option2, option3, option4, answer;

        public Question build() {
            return new Question(id, difficulty, question, option1, option2, option3, option4, answer);
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setDifficulty(String difficulty) {
            this.difficulty = difficulty;
            return this;
        }

        public Builder setQuestion(String question) {
            this.question = question;
            return this;
        }

        public Builder setOption1(String option1) {
            this.option1 = option1;
            return this;
        }

        public Builder setOption2(String option2) {
            this.option2 = option2;
            return this;
        }

        public Builder setOption3(String option3) {
            this.option3 = option3;
            return this;
        }

        public Builder setOption4(String option4) {
            this.option4 = option4;
            return this;
        }

        public Builder setAnswer(String answer) {
            this.answer = answer;
            return this;
        }
    }


    @Override
    public int describeContents() {
        Log.d(TAG, "describeContents()");
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Log.d(TAG, "writeToParcel(): " + this.toString());

        dest.writeInt(id);
        dest.writeString(difficulty);
        dest.writeString(question);
        dest.writeString(option1);
        dest.writeString(option2);
        dest.writeString(option3);
        dest.writeString(option4);
        dest.writeString(answer);
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {

        @Override
        public Question createFromParcel(Parcel source) {
            Question Connection = new Question(source);
            Log.d(TAG, "createFromParcel(): " + Connection.toString());
            return Connection;
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public static boolean saveParcelableArrayList(Bundle bundle, ArrayList<Question> Connections) {
        try {
            bundle.putParcelableArrayList(PARCEL_STATE_KEY, Connections);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static ArrayList<Question> getParcelableArrayList(Bundle bundle) {
        return bundle.getParcelableArrayList(PARCEL_STATE_KEY);
    }

}
