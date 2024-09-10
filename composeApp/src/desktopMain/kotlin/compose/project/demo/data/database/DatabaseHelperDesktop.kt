package compose.project.demo.data.database

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver



actual class DatabaseHelper{
    actual fun createDatabase(): AppDatabase {
        val driver =
            JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY) // Use "jdbc:sqlite:sample.db" for persistent storage
        AppDatabase.Schema.create(driver)
        return AppDatabase(driver)
    }
}
