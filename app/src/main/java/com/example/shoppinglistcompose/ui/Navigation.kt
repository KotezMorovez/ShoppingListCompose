package com.example.shoppinglistcompose.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.shoppinglistcompose.ui.feature.newItem.NewItemScreen
import com.example.shoppinglistcompose.ui.feature.shoppinglist.MainScreen

enum class ShoppingListScreens {
    SHOPPING_LIST,
    EDIT,
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
                onOptionMenuEditItemClicked = {
                    navController.navigate(
                        route = ShoppingListScreens.EDIT.name
                    )
                }
            )
        }
        composable(
            route = ShoppingListScreens.EDIT.name,
            arguments = listOf(navArgument("item") {
                type = NavType.ReferenceType
            })
        ) {
//            val item: Item? = it.arguments?.getParcelable("item")
//            TODO: add function call for edit item screen
        }
        composable(route = ShoppingListScreens.CREATE.name) {
            NewItemScreen(
                onBackClickListener = {
                    navController.navigateUp()
                }
            )
        }
    }
}