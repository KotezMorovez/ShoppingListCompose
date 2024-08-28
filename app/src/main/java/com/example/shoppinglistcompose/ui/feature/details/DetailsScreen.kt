package com.example.shoppinglistcompose.ui.feature.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.shoppinglistcompose.R
import com.example.shoppinglistcompose.domain.model.Item
import com.example.shoppinglistcompose.ui.theme.PurpleGrey40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    viewModel: DetailsScreenViewModel = hiltViewModel(),
    onBackClickListener: () -> Unit,
    itemId: Int?
) {
    val context = LocalContext.current
    val item: Item? by remember { mutableStateOf(itemId?.let { viewModel.getItem(it) })}
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.details_screen_title),
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
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Create,
                            contentDescription = "Edit",
                        )
                    }
                },
                windowInsets = TopAppBarDefaults.windowInsets,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    navigationIconContentColor = Color.Black,
                    titleContentColor = Color.Black,
                    actionIconContentColor = Color.Black
                )
            )
        },
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        containerColor = Color.White
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
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context)
                    .data(item?.image)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .memoryCacheKey(item?.image.toString())
                    .diskCacheKey(item?.image.toString())
                    .build(),
                imageLoader = context.imageLoader,
                placeholder = painterResource(id = R.drawable.ic_placeholder),
                error = painterResource(id = R.drawable.ic_placeholder),
            )

            Image(
                painter = painter,
                contentDescription = "Item image",
                modifier = Modifier
                    .widthIn(0.dp, 256.dp)
                    .aspectRatio(1f)
                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Fit
            )


            RowBlock(
                title = stringResource(id = R.string.details_screen_name_field),
                content = item?.name ?: "",
                Pair(0, 24)
            )

            RowBlock(
                title = stringResource(id = R.string.details_screen_category_field),
                content = item?.category ?: "",
                Pair(0, 16)
            )

            RowBlock(
                title = stringResource(id = R.string.details_screen_cost_field),
                content = item?.cost ?: "",
                rowPaddings = Pair(0, 16),
            )
        }
    }
}

@Composable
fun RowBlock(title: String, content: String, rowPaddings: Pair<Int, Int>) {
    Row(
        modifier = Modifier
            .padding(
                top = rowPaddings.second.dp,
                start = rowPaddings.first.dp,
                end = rowPaddings.first.dp
            )
            .widthIn(0.dp, 256.dp)
    ) {
        Text(
            modifier = Modifier
                .widthIn(0.dp, 120.dp)
                .padding(end = 16.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = title,
            fontSize = 16.sp,
            color = PurpleGrey40,
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = content,
            fontSize = 16.sp,
            color = PurpleGrey40,
        )
    }
}