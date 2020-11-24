package id.refactory.androidmaterial.day17.repositories.local.entities

class TodoEntity(val id: Long, val task: String) {
    companion object {
        const val TABLE_NAME = "todo_table"
        const val COLUMN_ID = "id"
        const val COLUMN_TASK = "task"
        const val COLUMN_STATUS = "status"
        const val SQL_CREATE_TODO = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_TASK TEXT)"
        const val SQL_DELETE_TODO = "DROP TABLE IF EXISTS $TABLE_NAME"
        const val SQL_MIGRATE_TODO = "ALTER TABLE $TABLE_NAME ADD COLUMN $COLUMN_STATUS INTEGER"
    }
}