package compose.project.demo

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import compose.project.demo.data.TodoRepository
import compose.project.demo.data.database.DatabaseHelper

//fun main() = application {
//    Window(
//        onCloseRequest = ::exitApplication,
//        title = "ComposeDemo",
//    ) {
//        App()
//    }
//}
fun main() = application {
    val databaseHelper = DatabaseHelper()
    val database = databaseHelper.createDatabase()
    val repository = TodoRepository(database)
    val viewModel = TodoViewModel(repository)

    Window(onCloseRequest = ::exitApplication, title = "Todo App") {
        TodoApp(viewModel)
    }
}
