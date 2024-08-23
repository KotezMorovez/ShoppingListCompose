package com.example.shoppinglistcompose.ui.feature.newItem

import android.net.Uri
import android.view.View.OnClickListener
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.shoppinglistcompose.R
import com.example.shoppinglistcompose.ui.theme.Grey80
import com.example.shoppinglistcompose.ui.theme.PurpleGrey40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewItemScreen(
    onBackClickListener: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.new_item_title),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onBackClickListener.invoke()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        // add item to itemList and navigate back
                        onBackClickListener.invoke()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done",
                            tint = Color.Black
                        )
                    }
                },
                windowInsets = TopAppBarDefaults.windowInsets,
                colors = TopAppBarDefaults.topAppBarColors()
            )
        },
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        containerColor = Grey80
    ) { padding ->
        var nameFieldText by remember { mutableStateOf("") }
        var categoryFieldText by remember { mutableStateOf("") }
        var currencyFieldText by remember { mutableStateOf("₽") }
        var costFieldText by remember { mutableStateOf("") }
        var isExpanded by remember { mutableStateOf(false) }
        val cardPadding = PaddingValues(
            top = padding.calculateTopPadding() + 16.dp,
            bottom = 16.dp,
            start = padding.calculateStartPadding(LayoutDirection.Ltr) + 24.dp,
            end = padding.calculateEndPadding(LayoutDirection.Ltr) + 24.dp
        )

        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(Color.White),
            modifier = Modifier
                .padding(cardPadding)
                .fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .height(204.dp)
                        .padding(vertical = 24.dp)
                ) {
                    var data: Uri? by remember { mutableStateOf(null) }
                    val painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(data)
                            .placeholder(R.drawable.ic_placeholder)
                            .build()
                    )

                    Image(
                        painter =
                        if (data != null) {
                            painter
                        } else {
                            painterResource(id = R.drawable.ic_placeholder)
                        },
                        contentDescription = "Item image",
                        modifier = Modifier
                            .aspectRatio(1f)
                            .padding(8.dp)
                            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                    )

                    IconButton(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .height(28.dp)
                            .width(28.dp)
                            .background(Grey80, shape = RoundedCornerShape(4.dp))
                            .padding(4.dp),
                        onClick = {
                            /*TODO: Open gallery, don't forget to give permissions for this*/
                        }
                    ) {
                        Icon(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .height(24.dp)
                                .width(24.dp),
                            imageVector = Icons.Filled.Create,
                            contentDescription = "Add image",
                            tint = Color.Black,
                        )
                    }
                }

                TextField(
                    value = nameFieldText,
                    onValueChange = { nameFieldText = it },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 24.dp)
                        .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp)),
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Grey80,
                        focusedContainerColor = Grey80,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                    ),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.enter_item_name),
                            fontSize = 14.sp,
                            color = PurpleGrey40
                        )
                    }
                )

                TextField(
                    value = categoryFieldText,
                    onValueChange = { categoryFieldText = it },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 24.dp)
                        .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp)),
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Grey80,
                        focusedContainerColor = Grey80,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                    ),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.enter_item_category),
                            fontSize = 14.sp,
                            color = PurpleGrey40
                        )
                    }
                )

                Row(
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    ExposedDropdownMenuBox(
                        expanded = isExpanded,
                        onExpandedChange = { isExpanded = it }
                    ) {
                        TextField(
                            value = currencyFieldText,
                            onValueChange = { currencyFieldText = it },
                            readOnly = true,
                            modifier = Modifier
                                .widthIn(1.dp, 80.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color.Black,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .menuAnchor(),
                            shape = RoundedCornerShape(8.dp),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Grey80,
                                focusedContainerColor = Grey80,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                            ),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                            }
                        )
                        ExposedDropdownMenu(
                            expanded = isExpanded,
                            onDismissRequest = { isExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text(text = "€") },
                                onClick = {
                                    currencyFieldText = "€"
                                    isExpanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text(text = "\$") },
                                onClick = {
                                    currencyFieldText = "\$"
                                    isExpanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text(text = "₽") },
                                onClick = {
                                    currencyFieldText = "₽"
                                    isExpanded = false
                                }
                            )
                        }
                    }

                    TextField(
                        value = costFieldText,
                        onValueChange = { costFieldText = it },
                        singleLine = true,
                        modifier = Modifier
                            .padding(start = 8.dp, bottom = 24.dp)
                            .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp)),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Grey80,
                            focusedContainerColor = Grey80,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                        ),
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.enter_item_cost),
                                fontSize = 14.sp,
                                color = PurpleGrey40
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        )
                    )
                }
            }
        }
    }
}