package com.example.shoppinglistcompose.ui.feature.shoppinglist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.shoppinglistcompose.R
import com.example.shoppinglistcompose.domain.model.Item
import com.example.shoppinglistcompose.ui.theme.Grey80

@Composable
fun MainScreen(viewModel: ShoppingListViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Grey80)
    ) {
        LaunchedEffect(Unit) {
            viewModel.getItems()
        }

        MyAppBar(title = stringResource(id = R.string.toolbar_title))
        MyRecyclerView(viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyAppBar(title: String, fontSize: Int = 20) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontSize = fontSize.sp,
                fontWeight = FontWeight.Bold
            )
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Create",
                    tint = Color.Black
                )
            }
        },
        windowInsets = TopAppBarDefaults.windowInsets,
        colors = TopAppBarDefaults.topAppBarColors()
    )
}

@Composable
private fun MyRecyclerView(viewModel: ShoppingListViewModel) {
    val itemsList by viewModel.itemsList.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        userScrollEnabled = true
    ) {
        items(
            items = itemsList,
            key = { item ->
                item.id
            },
            contentType = { null },
            itemContent = { it ->
                ItemView(item = it) { id ->
                    viewModel.deleteItem(id)
                }
            }
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun ItemView(item: Item, deleteItemAction: ((id: Int) -> Unit)) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.image)
                    .placeholder(R.drawable.ic_placeholder)
                    .build()
            )

            Image(
                painter = painter,
                contentDescription = "item image",
                modifier = Modifier
                    .aspectRatio(1f)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Grey80),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Text(
                    text = item.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Text(
                    text = item.category,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Text(
                    text = item.cost,
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1.0f))

            Box(
                modifier = Modifier.wrapContentSize(Alignment.TopStart)
            ) {
                Image(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "menu",
                    Modifier
                        .padding(horizontal = 8.dp)
                        .clickable {
                            expanded = true
                        }
                )

                this@Row.AnimatedVisibility(
                    visible = expanded
                ) {
                    ItemOptionMenu(
                        expanded = expanded,
                        modifier = Modifier.animateEnterExit(
                            enter = fadeIn() + expandIn(spring()),
                            exit = fadeOut() + shrinkOut(spring())
                        ),
                        closeAction = {
                            expanded = false
                        },
                        deleteAction = {
                            deleteItemAction.invoke(item.id)
                        },
                        editAction = {
//                            viewModel.editItem(item)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ItemOptionMenu(
    expanded: Boolean,
    modifier: Modifier,
    closeAction: () -> Unit,
    deleteAction: () -> Unit,
    editAction: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = closeAction,
        modifier = modifier
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(id = R.string.shopping_list_menu_edit),
                    fontSize = 16.sp
                )
            },
            onClick = {
                // Show edit screen with crossfade animation
                editAction.invoke()
                closeAction.invoke()
            }
        )
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(id = R.string.shopping_list_menu_delete),
                    fontSize = 16.sp
                )
            },
            onClick = {
                deleteAction.invoke()
                closeAction.invoke()
            }
        )
    }
}