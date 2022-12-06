package com.example.booksappcompose

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.booksappcompose.data.local.BooksDatabase
import org.junit.Test
import org.junit.runner.RunWith


private const val DB_NAME = "test"

@RunWith(AndroidJUnit4::class)
class UserMigrationTest {

    @Test
    fun testAllMigrations() {
        Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            BooksDatabase::class.java,
            DB_NAME
        ).addMigrations(BooksDatabase.migration1To2).build().apply {
            openHelper.writableDatabase.close()
        }
    }
}