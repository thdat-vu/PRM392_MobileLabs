package com.davt.lab10.db;

import androidx.room.RoomDatabase;

import com.davt.lab10.dao.PersonDao;

public abstract class AppDatabase extends RoomDatabase {
    public abstract PersonDao personDao();
}
