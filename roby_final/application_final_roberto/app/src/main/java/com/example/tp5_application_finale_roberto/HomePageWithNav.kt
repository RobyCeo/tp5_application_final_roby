import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tp5_application_finale_roberto.ExerciseListScreen
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageWithNav() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedOption by remember { mutableStateOf("Home") }
    val context = LocalContext.current

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                onItemClick = { option ->
                    selectedOption = option
                    scope.launch { drawerState.close() }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Navigation",
                            color = Color.White // Titre en blanc
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Menu",
                                tint = Color.White // Icône en blanc
                            )
                        }
                    },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = Color.Blue // Fond bleu de la barre
                    )
                )
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
                when (selectedOption) {
                    "Home" -> AnimatedHomeScreen()
                    "Liste des exercices" -> ExerciseListScreen(context)
                    "Retour à l'accueil" -> AnimatedHomeScreen()
                }
            }
        }
    }
}

@Composable
fun ExerciseListScreen(context: android.content.Context) {
    // Exemple de contenu de la liste des exercices
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Liste des exercices",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        // Ajouter le contenu ou les fonctionnalités nécessaires ici
    }
}

@Composable
fun DrawerContent(onItemClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(200.dp) // Réduction de la largeur du tiroir
            .background(Color.Blue) // Fond violet du menu
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Menu",
            fontSize = 28.sp, // Taille légèrement augmentée
            color = Color.White,
            modifier = Modifier.padding(bottom = 24.dp),
            fontWeight = FontWeight.Bold // Titre en gras
        )
        // Option : Liste des exercices
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .clickable { onItemClick("Liste des exercices") },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Menu, // Icône alternative au lieu de List
                contentDescription = "Liste des exercices",
                tint = Color.White,
                modifier = Modifier.size(24.dp).padding(end = 12.dp)
            )
            Text(
                text = "Liste des exercices",
                fontSize = 20.sp, // Police plus grande
                fontWeight = FontWeight.Bold, // Texte en gras
                color = Color.White
            )
        }
        // Option : Retour à l'accueil
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .clickable { onItemClick("Retour à l'accueil") },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "Retour à l'accueil",
                tint = Color.White,
                modifier = Modifier.size(24.dp).padding(end = 12.dp)
            )
            Text(
                text = "Retour à l'accueil",
                fontSize = 20.sp, // Police plus grande
                fontWeight = FontWeight.Bold, // Texte en gras
                color = Color.White
            )
        }
    }
}

@Composable
fun AnimatedHomeScreen() {
    // Animation pour l'image (effet de respiration)
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 1.06f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Animation pour le texte (effet de scintillement)
    val textAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black), // Fond noir pour contraste
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = com.example.tp5_application_finale_roberto.R.drawable.download),
            contentDescription = "Silhouette d'une personne qui s'entraîne",
            contentScale = ContentScale.Crop, // Adapte l'image pour remplir tout l'espace
            modifier = Modifier
                .fillMaxSize() // Remplit toute la hauteur et largeur
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale // Animation d'effet de respiration
                )
        )

        // Texte animé (scintillement)
        Text(
            text = "B I E N V E N U E",
            fontSize = 30.sp,
            color = Color.Blue,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp)
                .graphicsLayer(alpha = textAlpha)
        )
    }
}
