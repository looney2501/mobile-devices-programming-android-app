package com.ilazar.myapp.todo.ui

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ilazar.myapp.todo.data.Item

@Composable
fun ItemDetail(item: Item, onToggleDone: (itemId: Int) -> Unit) {
    Log.d("ItemDetail", "recompose text = ${item.text}")
    Row {
        Checkbox(checked = item.done, onCheckedChange = { onToggleDone(item.id) })
        Text(text = item.text)
    }
}

@Preview
@Composable
fun PreviewItemDetail() {
    ItemDetail(Item(id = 1, text = "Learn android", done = true), {})
}
