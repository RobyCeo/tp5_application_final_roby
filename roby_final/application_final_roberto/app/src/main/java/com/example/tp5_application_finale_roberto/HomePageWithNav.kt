import androidx.compose.animation.core.*
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageWithNav() {
    var drawerState by remember { mutableStateOf(false) } // État pour ouvrir/fermer le menu

    ModalNavigationDrawer(
        drawerState = rememberDrawerState(if (drawerState) DrawerValue.Open else DrawerValue.Closed),
        drawerContent = {
            DrawerContent { drawerState = false } // Contenu du menu latéral
        }
    ) {
        Scaffold(
            topBar = {
                AnimatedTopAppBar(
                    onMenuClick = { drawerState = true } // Ouvre le menu
                )
            }
        ) { paddingValues ->
            // Contenu principal de la page
            HomePage(paddingValues)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedTopAppBar(onMenuClick: () -> Unit) {
    // Animation pour le titre
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    TopAppBar(
        title = {
            Text(
                text = "N A V I G A T I O N",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.graphicsLayer(scaleX = scale, scaleY = scale) // Animation
            )
        },
        navigationIcon = {
            IconButton(onClick = { onMenuClick() }) {
                Icon(Icons.Filled.Menu, contentDescription = "Menu")
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color(0xFF6200EE))
    )
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
            text = AnnotatedString("Liste des exercices"),
            onClick = {
                onItemClick() // Appelle la navigation
            },
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}
