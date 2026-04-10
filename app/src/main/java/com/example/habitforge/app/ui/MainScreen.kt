package com.example.habitforge.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitforge.app.session.AppSession
import com.example.habitforge.app.ui.gym.GymScreen
import com.example.habitforge.app.ui.home.HomeScreen
import com.example.habitforge.app.ui.profile.ProfileScreen
import com.example.habitforge.app.ui.theme.*

data class BottomNavItem(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)

val bottomNavItems = listOf(
    BottomNavItem("Home",   Icons.Filled.Home,          Icons.Outlined.Home,          "home"),
    BottomNavItem("Gym",    Icons.Filled.FitnessCenter,  Icons.Outlined.FitnessCenter,  "gym"),
    BottomNavItem("Perfil", Icons.Filled.Person,         Icons.Outlined.Person,         "profile"),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onLogout: () -> Unit = {}) {
    var selectedRoute by remember { mutableStateOf("home") }

    val displayName = AppSession.displayName()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (selectedRoute) {
                            "home"    -> "HabitForge"
                            "gym"     -> "Gym"
                            "profile" -> "Mi Perfil"
                            else      -> "HabitForge"
                        },
                        style = MaterialTheme.typography.titleLarge,
                        color = CreamWhite,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.5.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Brown80,
                    titleContentColor = CreamWhite
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Brown80,
                contentColor = CreamWhite,
                tonalElevation = 0.dp
            ) {
                bottomNavItems.forEach { item ->
                    val isSelected = selectedRoute == item.route
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { selectedRoute = item.route },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) item.selectedIcon
                                else item.unselectedIcon,
                                contentDescription = item.label
                            )
                        },
                        label = {
                            Text(
                                text = item.label,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = if (isSelected) FontWeight.SemiBold
                                else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = CreamWhite,
                            selectedTextColor = CreamWhite,
                            unselectedIconColor = Brown20,
                            unselectedTextColor = Brown20,
                            indicatorColor = Brown60
                        )
                    )
                }
            }
        },
        containerColor = BeigeBase
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(BeigeBase)
        ) {
            when (selectedRoute) {
                "home" -> HomeScreen(
                    username = displayName,
                    onGymClick = { selectedRoute = "gym" }
                )
                "gym" -> GymScreen()
                "profile" -> ProfileScreen(
                    username = displayName,
                    onLogout = {
                        AppSession.logout()
                        onLogout()
                    }
                )
            }
        }
    }
}