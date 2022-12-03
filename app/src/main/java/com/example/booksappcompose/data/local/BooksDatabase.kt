package com.example.booksappcompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [Top15MostPopularBooksItemEntity::class, BookDetailEntity::class],
    version = 2
)
@TypeConverters(ListConverter::class)
abstract class BooksDatabase : RoomDatabase() {

    abstract val dao: BooksDao

    companion object {
        val migration1To2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS bookDetail(" +
                            " `authors` TEXT NOT NULL," +
                            " `book_id` INTEGER NOT NULL," +
                            " `cover` TEXT NOT NULL," +
                            " `name` TEXT NOT NULL," +
                            " `pages` INTEGER NOT NULL," +
                            " `published_date` TEXT NOT NULL," +
                            " `rating` INTEGER NOT NULL," +
                            " `synopsis` TEXT NOT NULL," +
                            " `id` INTEGER, PRIMARY KEY(`id`))"
                )
            }
        }
    }
}