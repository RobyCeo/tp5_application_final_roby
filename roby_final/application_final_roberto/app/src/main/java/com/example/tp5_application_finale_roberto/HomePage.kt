import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(paddingValues: PaddingValues) {
    // Animation pour la respiration (agrandissement/rétrécissement)
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("RobyWorkout") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color(0xFF6200EE))
            )
        }
    ) { paddingValuesFromScaffold ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValuesFromScaffold),
            contentAlignment = Alignment.Center
        ) {
            // Image animée (VectorDrawable)
            Image(
                painter = painterResource(id = R.drawable.download), // Remplacez "download" par le nom de votre VectorDrawable
                contentDescription = "Silhouette d'une personne qui s'entraîne",
                modifier = Modifier
                    .size(300.dp) // Taille de l'image
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale // Appliquez l'animation de respiration
                    )
            )

            // Texte sous l'image
            Text(
                text = "Welcome to RobyWorkout!",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 50.dp)
            )
        }
    }
}
