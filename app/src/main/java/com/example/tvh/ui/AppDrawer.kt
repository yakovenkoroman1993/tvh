package com.example.tvh.ui

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.*
import androidx.ui.material.Divider
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.material.TextButton
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Home
import androidx.ui.material.icons.filled.Info
import androidx.ui.text.TextStyle
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import com.example.tvh.services.Navigator

@Composable
fun AppDrawer(
    currentScreen: Navigator.Screen,
    onNavigateTo: (Navigator.Screen) -> Unit,
    onClose: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(Modifier.preferredHeight(24.dp))
        Text(
            text = "Меню",
            style = TextStyle(fontSize = 20.sp),
            modifier = Modifier.padding(start = 8.dp)
        )
        Divider(color = MaterialTheme.colors.onSurface.copy(alpha = .2f))
        DrawerButton(
            icon = { Icon(Icons.Filled.Home) },
            label = "Главная",
            isSelected = currentScreen == Navigator.Screen.HomeScreen,
            action = {
                onNavigateTo(Navigator.Screen.HomeScreen)
                onClose()
            }
        )
        DrawerButton(
            icon = { Icon(Icons.Filled.Info) },
            label = "Audit Logs",
            isSelected = currentScreen == Navigator.Screen.AuditInfoScreen,
            action = {
                onNavigateTo(Navigator.Screen.AuditInfoScreen)
                onClose()
            }
        )
    }
}

@Composable
private fun DrawerButton(
    icon: @Composable() () -> Unit,
    label: String,
    isSelected: Boolean,
    action: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colors
    val textIconColor = if (isSelected) {
        colors.primary
    } else {
        colors.onSurface.copy(alpha = 0.6f)
    }
    val backgroundColor = if (isSelected) {
        colors.primary.copy(alpha = 0.12f)
    } else {
        colors.surface
    }

    val surfaceModifier = modifier
        .padding(start = 8.dp, top = 8.dp, end = 8.dp)
        .fillMaxWidth()

    Surface(
        modifier = surfaceModifier,
        color = backgroundColor,
        shape = RoundedCornerShape(4.dp)
    ) {
        TextButton(onClick = action, modifier = Modifier.fillMaxWidth()) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalGravity = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                icon()
                Spacer(Modifier.preferredWidth(16.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.body2.copy(color = textIconColor),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
