package com.ilazar.myapp.hotel.ui.items

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilazar.myapp.hotel.data.Item

typealias OnItemFn = (id: String?) -> Unit

@Composable
fun ItemList(itemList: List<Item>, onItemClick: OnItemFn) {
    Log.d("ItemList", "recompose")
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        items(itemList) { item ->
            ItemDetail(item, onItemClick)
        }
    }
}

@Composable
fun ItemDetail(item: Item, onItemClick: OnItemFn) {
//    Log.d("ItemDetail", "recompose id = ${item._id}")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onItemClick(item._id) }
    ) {
        Column {
            Text(
                text = AnnotatedString(item.name),
                style = TextStyle(
                    fontSize = 24.sp,
                ),
            )
            Text(
                text = AnnotatedString("Price: " + item.capacity.toString()),
                style = TextStyle(
                    fontSize = 13.sp,
                ),
            )
            Text(
                text = AnnotatedString("Date bought: " + item.dateRegistered),
                style = TextStyle(
                    fontSize = 13.sp,
                ),
            )
            Text(
                text = AnnotatedString("On sale: " + item.isAvailable.toString()),
                style = TextStyle(
                    fontSize = 13.sp,
                ),
            )
        }
    }
}
