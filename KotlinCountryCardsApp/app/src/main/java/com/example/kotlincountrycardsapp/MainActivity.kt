package com.example.kotlincountrycardsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kotlincountrycardsapp.ui.theme.KotlinCountryCardsAppTheme

// Use the Geo font (make sure res/font/geo_regular.ttf exists)
val GeoFontFamily = FontFamily(
    Font(R.font.geo_regular)
)

// Simple model for a country
data class Country(
    val name: String,
    val capital: String,
    val flagRes: Int
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinCountryCardsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CountryCardsScreen()
                }
            }
        }
    }
}

@Composable
fun CountryCardsScreen() {
    // 4 countries with their flags
    val countries = listOf(
        Country("Finland", "Capital: Helsinki", R.drawable.flag_finland),
        Country("Bangladesh", "Capital: Dhaka", R.drawable.flag_bangladesh),
        Country("Japan", "Capital: Tokyo", R.drawable.flag_japan),
        Country("Italy", "Capital: Rome", R.drawable.flag_italy)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Countries and Flags",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            fontFamily = GeoFontFamily
        )

        // Four cards stacked in a Column (NOT LazyColumn)
        countries.forEachIndexed { index, country ->
            val bgColor: Color = if (index % 2 == 0) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.secondaryContainer
            }
            CountryCard(country = country, containerColor = bgColor)
        }
    }
}

@Composable
fun CountryCard(
    country: Country,
    containerColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        shape = RoundedCornerShape(24.dp), // customize shape
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Flag image
            Image(
                painter = painterResource(id = country.flagRes),
                contentDescription = "${country.name} flag",
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop
            )

            // Country name + capital
            Column(
                modifier = Modifier.weight(1.3f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = country.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontFamily = GeoFontFamily
                )
                Text(
                    text = country.capital,
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = GeoFontFamily
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CountryCardsPreview() {
    KotlinCountryCardsAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            CountryCardsScreen()
        }
    }
}
