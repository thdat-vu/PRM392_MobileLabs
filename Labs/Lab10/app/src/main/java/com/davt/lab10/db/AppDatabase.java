package com.davt.lab10.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.davt.lab10.dao.PersonDao;
import com.davt.lab10.model.Person;

@Database(entities = {Person.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PersonDao personDao();
}
