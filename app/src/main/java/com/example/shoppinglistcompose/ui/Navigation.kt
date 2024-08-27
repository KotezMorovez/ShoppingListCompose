package com.example.shoppinglistcompose.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.shoppinglistcompose.ui.feature.details.DetailsScreen
import com.example.shoppinglistcompose.ui.feature.newItem.NewItemScreen
import com.example.shoppinglistcompose.ui.feature.shoppinglist.MainScreen

enum class ShoppingListScreens {
    SHOPPING_LIST,
    EDIT,
    DETAILS,
    CREATE
}

@Composable
fun ShoppingListApp(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = ShoppingListScreens.SHOPPING_LIST.name,
        modifier = Modifier
            .fillMaxSize()
    ) {
        composable(route = ShoppingListScreens.SHOPPING_LIST.name) {
            MainScreen(
                onCreateButtonClicked = {
                    navController.navigate(ShoppingListScreens.CREATE.name)
                },
                onOptionMenuEditItemClicked = { item ->
//                    navController.navigate(
//                        route = ShoppingListScreens.EDIT.name
//                    )
                },
                onItemClicked = { item ->
                    navController.navigate(
                        route = ShoppingListScreens.DETAILS.name + "/${item.id}",
                    )
                }
            )
        }

//        composable(
//            route = ShoppingListScreens.EDIT.name,
//            arguments = listOf(navArgument("item") {
//                type = NavType.ReferenceType
//            })
//        ) {
//            val item: Item? = it.arguments?.getParcelable("item")
//        }

        composable(route = ShoppingListScreens.CREATE.name) {
            NewItemScreen(
                onBackClickListener = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = ShoppingListScreens.DETAILS.name + "/{itemId}",
            arguments = listOf(navArgument("itemId") {
                type = NavType.IntType
            })
        )
        {
            DetailsScreen(
                onBackClickListener = {
                    navController.popBackStack()
                },
                itemId = it.arguments?.getInt("itemId")
            )
        }
    }
}