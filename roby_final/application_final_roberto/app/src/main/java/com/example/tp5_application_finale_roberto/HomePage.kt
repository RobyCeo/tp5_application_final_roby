import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.core.*
import androidx.compose.foundation.background

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(paddingValues: PaddingValues) {
    var isVisible by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition()
    val circleSize by infiniteTransition.animateFloat(
        initialValue = 300f,
        targetValue = 2000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )


    LaunchedEffect(Unit) {
        isVisible = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("RobyWorkout") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color(0xFF6200EE))
            )
        }
    ) { paddingValuesFromScaffold -> // Renommez le paramètre pour éviter l'utilisation de `_`
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValuesFromScaffold), // Utilisez ici le padding provenant de Scaffold
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(circleSize.dp)
                    .background(color = Color(0xFFBB86FC), shape = CircleShape)
            )

            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(animationSpec = tween(1000)) + scaleIn(initialScale = 0.5f),
                exit = fadeOut(animationSpec = tween(500))
            ) {
                Text(
                    text = "Welcome to RobyWorkout!",
                    fontSize = 28.sp,
                    color = Color.White,
                    modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally).wrapContentHeight()
                )
            }
        }
    }
}


