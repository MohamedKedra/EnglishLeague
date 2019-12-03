package com.mohamed.englishleague.DB;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.mohamed.englishleague.Models.Team;

@Database(entities = {Team.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {

        private static AppDatabase instance;
        public abstract AppDao appDao();

        public static synchronized AppDatabase getInstance(Context context){

            if (instance == null){
                instance = Room.databaseBuilder(context,AppDatabase.class,"app_database")
                        .addCallback(callback).fallbackToDestructiveMigration().build();
            }
            return instance;
        }

        static RoomDatabase.Callback callback = new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                new OpenDBTask(instance).execute();
            }
        };

        private static class OpenDBTask extends AsyncTask<Void,Void,Void> {

            private AppDao appDao;

            public OpenDBTask(AppDatabase appDatabase){
                this.appDao = appDatabase.appDao();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                return null;
            }
        }
}
