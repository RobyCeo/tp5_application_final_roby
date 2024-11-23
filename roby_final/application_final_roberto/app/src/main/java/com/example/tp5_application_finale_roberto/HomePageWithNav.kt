import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.Brush
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
fun DrawerContent(onItemClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(200.dp) // Réduction de la largeur du tiroir
            .background(Color.Blue, shape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)) // Bord arrondi
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
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 1.06f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

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
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = com.example.tp5_application_finale_roberto.R.drawable.download),
            contentDescription = "Silhouette d'une personne qui s'entraîne",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale
                )
        )

        Text(
            text = "B I E N V E N U E",
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            color = Color.Blue,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.White,
                    offset = androidx.compose.ui.geometry.Offset(2f, 2f),
                    blurRadius = 8f
                )
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp)
                .graphicsLayer(alpha = textAlpha)
        )
    }
}
