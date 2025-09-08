package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    LemonadeApp()
                }
            }
        }
    }
}

@Preview()
@Composable
fun LemonadeApp() {
    LemonadeImageAndDescription(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}

@Composable
fun LemonadeImageAndDescription(modifier: Modifier = Modifier) {
    var currentStep by remember { mutableStateOf(1) }
    // Ajout d'un état pour compter les pressions sur le citron.
    var squeezeCount by remember { mutableStateOf(0) }

    val imageResource = when (currentStep) {
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }
    val textResource = when (currentStep) {
        1 -> R.string.libel1
        2 -> R.string.libel2
        3 -> R.string.libel3
        else -> R.string.libel4
    }
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = { // Utilise un "when" pour gérer la logique de chaque étape.
                when (currentStep) {
                    1 -> {
                        // Passe à l'étape 2 et définit un nombre de pressions aléatoire.
                        currentStep = 2
                        squeezeCount =
                            (2..4).random() // L'utilisateur devra appuyer entre 2 et 4 fois.
                    }

                    2 -> {
                        // Décrémente le compteur de pressions.
                        squeezeCount--
                        // Si le compteur atteint 0, passe à l'étape suivante.
                        if (squeezeCount == 0) {
                            currentStep = 3
                        }
                    }

                    3 -> currentStep = 4 // Passe à l'étape 4.
                    4 -> currentStep = 1 // Recommence.
                }
            },
            shape = RoundedCornerShape(40.dp), // Angles arrondis
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC8E6C9)) //Couleur d'accentuation du thème
        ) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = currentStep.toString(),
                modifier = Modifier.padding(16.dp), // Padding interne à l'image dans le bouton
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = textResource),
            fontSize = 18.sp
        )
    }
}
