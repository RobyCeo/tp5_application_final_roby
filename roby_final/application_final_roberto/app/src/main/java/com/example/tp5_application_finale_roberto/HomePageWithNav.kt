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
import com.example.tp5_application_finale_roberto.ExerciseListScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageWithNav() {
    val drawerState = rememberDrawerState(DrawerValue.Closed) // État du menu latéral
    val scope = rememberCoroutineScope() // Pour gérer l'ouverture/fermeture du menu
    var selectedOption by remember { mutableStateOf("Home") } // Option sélectionnée

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                onItemClick = { option ->
                    selectedOption = option // Met à jour l'option sélectionnée
                    scope.launch { drawerState.close() } // Ferme le menu
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Navigation") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { paddingValues ->
            when (selectedOption) {
                "Home" -> HomePage(paddingValues)
                "Liste des exercices" -> ExerciseListScreen()
            }
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
fun DrawerContent(onItemClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Menu",
            fontSize = 30.sp, // Texte plus grand pour le titre du menu
            modifier = Modifier.padding(bottom = 24.dp)
        )
        ClickableText(
            text = AnnotatedString("Liste des exercices"),
            onClick = { onItemClick("Liste des exercices") },
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
        )
    }
}

