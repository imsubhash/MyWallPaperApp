package com.subhash.mywallpaperapp.ui.util

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.subhash.mywallpaperapp.R

import fontFamilyBungee
import searchBarStyle
import titleStyle

@Composable
fun TopBar() {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = "Wallportal.",
            fontSize = 32.sp,
            color = colorResource(R.color.titleColor),
            style = titleStyle,
            fontFamily = fontFamilyBungee,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalComposeUiApi
@Composable
fun TopBarNew(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    search: () -> Unit,
    searchProgress: Boolean
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    Row {
        TextField(
            placeholder = {
                Text(
                    text = "Search ...",
                    style = searchBarStyle,
                    color = Color.White
                )
            },
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .height(50.dp)
                .border(1.dp, Color.White, RoundedCornerShape(4.dp)),
            textStyle = searchBarStyle,
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.White,
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    search.invoke()
                    keyboardController?.hide()
                }),
            trailingIcon = {
                if (searchProgress) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(CenterVertically)
                            .size(20.dp),
                        strokeWidth = 3.dp,
                        color = colorResource(R.color.titleColor)
                    )
                }
            }

        )
    }

}

@Composable
fun LoadingBox() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = colorResource(R.color.titleColor),
            modifier = Modifier.size(40.dp)
        )
    }
}