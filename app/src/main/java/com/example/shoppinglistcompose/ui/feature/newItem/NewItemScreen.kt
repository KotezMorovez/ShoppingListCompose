package com.example.shoppinglistcompose.ui.feature.newItem

import android.content.Intent
import com.example.shoppinglistcompose.domain.model.Item
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.shoppinglistcompose.R
import com.example.shoppinglistcompose.ui.theme.Grey80
import com.example.shoppinglistcompose.ui.theme.PurpleGrey40
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewItemScreen(
    viewModel: NewItemViewModel = hiltViewModel(),
    onBackClickListener: () -> Unit
) {
    var nameFieldText by remember { mutableStateOf("") }
    var categoryFieldText by remember { mutableStateOf("") }
    var currencyFieldText by remember { mutableStateOf("₽") }
    var costFieldText by remember { mutableStateOf("") }
    var isExpanded by remember { mutableStateOf(false) }
    var imageData: Uri? by remember { mutableStateOf(null) }
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri == null) {
                return@rememberLauncherForActivityResult
            }

            context.contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            imageData = uri
        }
    )

    fun launchPhotoPicker() {
        singlePhotoPickerLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }

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
                        if (
                            nameFieldText.isNotBlank() &&
                            categoryFieldText.isNotBlank() &&
                            costFieldText.isNotBlank()
                        ) {
                            var newUri = ""
                            if (imageData != null) {
                                newUri = viewModel.addToCache(imageData!!)?.toString() ?: ""
                            }

                            val item = Item(
                                id = 0,
                                image = newUri,
                                name = nameFieldText,
                                category = categoryFieldText,
                                cost = "$currencyFieldText $costFieldText"

                            )
                            viewModel.addItem(item)
                            onBackClickListener.invoke()
                        } else {
                            scope.launch {
                                snackBarHostState.showSnackbar(
                                    message = context
                                        .resources
                                        .getString(R.string.new_item_error_toast)
                                )
                            }
                        }

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
        containerColor = Color.White,
        snackbarHost = {
            SnackbarHost(snackBarHostState) {
                Snackbar(
                    modifier = Modifier.padding(8.dp),
                    containerColor = Color.DarkGray,
                    contentColor = Color.LightGray,
                ) {
                    Text(
                        text = stringResource(id = R.string.new_item_error_toast),
                        fontSize = 16.sp
                    )
                }
            }
        }
    ) { padding ->
        val scaffoldPadding = PaddingValues(
            top = padding.calculateTopPadding() + 16.dp,
            bottom = 16.dp,
            start = padding.calculateStartPadding(LayoutDirection.Ltr) + 24.dp,
            end = padding.calculateEndPadding(LayoutDirection.Ltr) + 24.dp
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(scaffoldPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .height(204.dp)
                    .padding(vertical = 24.dp)
            ) {
                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context)
                        .data(imageData)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .memoryCacheKey(imageData.toString())
                        .diskCacheKey(imageData.toString())
                        .build(),
                    imageLoader = context.imageLoader,
                    placeholder = painterResource(id = R.drawable.ic_placeholder),
                    error = painterResource(id = R.drawable.ic_placeholder),
                    contentScale = ContentScale.Crop
                )

                Image(
                    painter = painter,
                    contentDescription = "Item image",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(8.dp)
                        .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                )

                IconButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .height(32.dp)
                        .width(32.dp)
                        .background(Grey80, shape = RoundedCornerShape(4.dp))
                        .padding(4.dp),
                    onClick = {
                        launchPhotoPicker()
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
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(8.dp)
                        ),
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
