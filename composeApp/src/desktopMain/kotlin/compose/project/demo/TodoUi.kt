package compose.project.demo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.project.demo.data.TodoItem

@Composable
fun TodoApp(viewModel: TodoViewModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = viewModel.newTodoTitle.value,
            onValueChange = { viewModel.newTodoTitle.value = it },
            label = { Text("New Todo") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { viewModel.addTodo() }) {
            Text("Add Todo")
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(viewModel.todos.value) { todo ->
                TodoItems(
                    todo = todo,
                    onToggle = { viewModel.toggleTodo(
                        todo.id,
                        !todo.isCompleted
                    ) },
                    onEdit = { viewModel.startEditingTodo(todo)},
                    isEditing = viewModel.editingTodo.value?.id == todo.id,
                    onUpdate = { viewModel.editTodo(todo.id, todo.title)},
                    onCancelEdit = {viewModel.cancelEditing()}
                )
            }
        }
    }
}

@Composable
fun TodoItems(
    todo: TodoItem,
    onToggle: () -> Unit,
    onEdit: () -> Unit,
    isEditing: Boolean,
    onUpdate: (String) -> Unit,
    onCancelEdit: () -> Unit
) {
    var editTitle by remember { mutableStateOf(todo.title) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = todo.isCompleted,
            onCheckedChange = { onToggle() }
        )
        if (isEditing){
            TextField(
                value = editTitle,
                onValueChange = { editTitle = it },
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            )
            Button(onClick = {onUpdate(editTitle)}){
                Text("Save")
            }
            Button(onClick = {onCancelEdit()}){
                Text("Cancel")
            }

        }else {
            Text(
                text = todo.title,
                modifier = Modifier.padding(start = 8.dp)
            )
            Button(onClick = onEdit){
                Text("Edit")
            }
        }

    }
}