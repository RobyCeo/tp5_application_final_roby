import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageWithNav() {
    var drawerState by remember { mutableStateOf(false) } // État pour le menu

    ModalNavigationDrawer(
        drawerState = rememberDrawerState(if (drawerState) DrawerValue.Open else DrawerValue.Closed),
        drawerContent = {
            DrawerContent { drawerState = false } // Fermer le menu au clic
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("RobyWorkout") },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color(0xFF6200EE)),
                    navigationIcon = {
                        IconButton(onClick = { drawerState = true }) { // Ouvrir le menu
                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { paddingValues ->
            // Inclure le HomePage existant ici
            HomePage(paddingValues)
        }
    }
}

@Composable
fun DrawerContent(onItemClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Menu",
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        ClickableText(
            text = AnnotatedString("Accueil"),
            onClick = { onItemClick() }, // Ferme le menu
            modifier = Modifier.padding(vertical = 8.dp)
        )
        ClickableText(
            text = AnnotatedString("Entraînement"),
            onClick = { onItemClick() },
            modifier = Modifier.padding(vertical = 8.dp)
        )
        ClickableText(
            text = AnnotatedString("Profil"),
            onClick = { onItemClick() },
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}
