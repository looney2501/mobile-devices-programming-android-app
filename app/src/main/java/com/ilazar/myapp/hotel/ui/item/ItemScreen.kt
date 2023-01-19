package com.ilazar.myapp.hotel.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ilazar.myapp.R
import com.ilazar.myapp.hotel.ui.item.ItemViewModel
import com.ilazar.myapp.utils.createNotificationChannel
import com.ilazar.myapp.utils.showSimpleNotification

@Composable
fun ItemScreen(itemId: String?, onClose: () -> Unit) {
    val itemViewModel = viewModel<ItemViewModel>(factory = ItemViewModel.Factory(itemId))
    val itemUiState = itemViewModel.uiState
    var name by rememberSaveable { mutableStateOf(itemUiState.item?.name ?: "") }
    var capacity by rememberSaveable { mutableStateOf(itemUiState.item?.capacity ?: 0) }
    var dateRegistered by rememberSaveable {
        mutableStateOf(
            itemUiState.item?.dateRegistered ?: ""
        )
    }
    var isAvailable by rememberSaveable { mutableStateOf(itemUiState.item?.isAvailable ?: false) }
    val context = LocalContext.current
    val channelId = "MyTestChannel"
    val notificationId = 0
    Log.d("ItemScreen", "recompose, text = $name")

    LaunchedEffect(Unit) {
        createNotificationChannel(channelId, context)
    }

    LaunchedEffect(itemUiState.savingCompleted) {
        Log.d("ItemScreen", "Saving completed = ${itemUiState.savingCompleted}");
        if (itemUiState.savingCompleted) {
            showSimpleNotification(
                context,
                channelId,
                notificationId,
                "New hotel",
                "You have just created a new hotel: $name"
            )
            onClose();
        }
    }

    var textInitialized by remember { mutableStateOf(itemId == null) }
    LaunchedEffect(itemId, itemUiState.isLoading) {
        Log.d("ItemScreen", "Saving completed = ${itemUiState.savingCompleted}");
        if (textInitialized) {
            return@LaunchedEffect
        }
        if (itemUiState.item != null && !itemUiState.isLoading) {
            name = itemUiState.item.name
            capacity = itemUiState.item.capacity
            dateRegistered = itemUiState.item.dateRegistered
            isAvailable = itemUiState.item.isAvailable
            textInitialized = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.item)) },
                actions = {
                    Button(onClick = {
                        Log.d("ItemScreen", "save item name = $name");
                        itemViewModel.saveOrUpdateItem(name, capacity, dateRegistered, isAvailable)
                    }) { Text("Save") }
                }
            )
        }
    ) {
        if (itemUiState.isLoading) {
            CircularProgressIndicator()
            return@Scaffold
        }
        if (itemUiState.loadingError != null) {
            Text(text = "Failed to load item - ${itemUiState.loadingError.message}")
        }
        Column() {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = capacity.toString(),
                onValueChange = { if (it.toIntOrNull() != null) capacity = it.toInt() },
                label = { Text("Capacity") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = dateRegistered,
                onValueChange = { dateRegistered = it },
                label = { Text("Date Registered") },
                modifier = Modifier.fillMaxWidth()
            )
            Row(modifier = Modifier.padding(8.dp)) {
                Text(text = "Available", modifier = Modifier.padding(top = 11.dp))
                Switch(
                    checked = isAvailable,
                    onCheckedChange = { isAvailable = !isAvailable }
                )
            }
        }
        if (itemUiState.isSaving) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) { LinearProgressIndicator() }
        }
        if (itemUiState.savingError != null) {
            Text(text = "Failed to save hotel - ${itemUiState.savingError.message}")
        }
    }
}


@Preview
@Composable
fun PreviewItemScreen() {
    ItemScreen(itemId = "0", onClose = {})
}
