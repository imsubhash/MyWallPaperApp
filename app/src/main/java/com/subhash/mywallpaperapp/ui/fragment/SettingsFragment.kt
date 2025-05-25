package com.subhash.mywallpaperapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import coil.compose.rememberAsyncImagePainter

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    imageDetailsSetup()
                }
            }
        }

    }
}

@Composable
fun imageDetailsSetup(modifier: Modifier = Modifier) {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /* handle on click here */ },
                text = { Text(text = "Options") },
                icon = { Icon(imageVector = Icons.Filled.Add, contentDescription = "Add") },
                modifier = modifier.offset(y = (-50).dp)
            )
        }
    ) { paddingValues ->

        Column(
            modifier = modifier
                .padding(paddingValues)
                .padding(horizontal = 4.dp)
                .verticalScroll(rememberScrollState())
        ) {
            loadImage(url = "https://i.redd.it/jprg32wygg061.jpg")

            Spacer(modifier = Modifier.size(30.dp))

            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xff347484)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                    modifier = modifier
                        .clickable(onClick = {})
                        .padding(start = 24.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        detailText(text = "text Line 1 - 1")
                        detailText(text = "text Line 2 - 2")
                        detailText(text = "text Line 3 - 3")
                        detailText(text = "text Line 4 - 4")
                        detailText(text = "text Line 5 - 5")
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    smallButton(text = "Jetpack Compose", onClick = {})
                    Spacer(modifier = Modifier.size(4.dp))
                    smallButton(text = "Jetpack Compose", onClick = {})
                    Spacer(modifier = Modifier.size(4.dp))
                    smallButton(text = "Jetpack Compose", onClick = {})
                    Spacer(modifier = Modifier.size(4.dp))
                    smallButton(text = "Jetpack Compose", onClick = {})
                }
            }

            Spacer(modifier = Modifier.size(40.dp))
        }
    }
}

@Composable
fun loadImage(url: String, modifier: Modifier = Modifier) {
    Image(
        painter = rememberAsyncImagePainter(url),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.LightGray)
    )
}

@Composable
fun detailText(text: String) {
    Text(
        text = text,
        color = Color.White,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(vertical = 2.dp)
    )
}

@Composable
fun smallButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = text)
    }
}
