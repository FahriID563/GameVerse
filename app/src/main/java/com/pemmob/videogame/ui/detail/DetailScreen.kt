package com.pemmob.videogame.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import java.util.Locale
import com.pemmob.videogame.data.model.GameDetailDto
import com.pemmob.videogame.ui.components.ErrorView
import com.pemmob.videogame.ui.components.LoadingView
import com.pemmob.videogame.ui.theme.CyberColors

@Composable
fun DetailScreen(gameId: Int, viewModel: DetailViewModel, onBack: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(gameId) { viewModel.loadGame(gameId) }

    Scaffold(containerColor = CyberColors.BackgroundDark) { padding ->
        Box(Modifier.fillMaxSize().padding(padding).background(CyberColors.BackgroundGradient)) {
            when (val state = uiState) {
                DetailUiState.Loading -> LoadingView(Modifier.fillMaxSize())
                is DetailUiState.Error -> ErrorView(state.message, onRetry = { viewModel.loadGame(gameId) }, modifier = Modifier.fillMaxSize())
                is DetailUiState.Success -> DetailContent(state.game, onBack)
            }
        }
    }
}

@Composable
private fun DetailContent(game: GameDetailDto, onBack: () -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Box(Modifier.fillMaxWidth().height(340.dp)) {
            AsyncImage(game.backgroundImage, contentDescription = game.name, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
            Box(Modifier.fillMaxSize().background(CyberColors.HeroOverlayGradient))
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(16.dp)
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color(0x99090A10))
                    .border(1.dp, CyberColors.NeonCyan.copy(alpha = 0.5f), CircleShape)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = CyberColors.TextPrimary)
            }
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 20.dp, bottom = 14.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xD9090A10))
                    .border(1.dp, CyberColors.NeonAmber, RoundedCornerShape(14.dp))
                    .padding(horizontal = 14.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Star, contentDescription = "Rating", tint = CyberColors.NeonAmber, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(6.dp))
                Text(String.format(Locale.US, "%.1f", game.rating), color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 15.sp)
            }
        }

        Column(Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(bottom = 36.dp)) {
            Text(
                game.name,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Black,
                    color = CyberColors.TextPrimary,
                    letterSpacing = 0.5.sp
                )
            )
            Spacer(Modifier.height(14.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                CyberInfoChip(label = game.released ?: "TBA", accentColor = CyberColors.NeonCyan) {
                    Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = CyberColors.NeonCyan, modifier = Modifier.size(14.dp))
                }
                game.metacritic?.let { CyberInfoChip(label = "META: $it", accentColor = CyberColors.NeonPink) }
            }
            Spacer(Modifier.height(18.dp))
            if (game.genres.isNotEmpty()) {
                Text("GENRES", color = CyberColors.NeonCyan, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp, style = MaterialTheme.typography.labelMedium)
                Spacer(Modifier.height(8.dp))
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    game.genres.forEach { genre ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(CyberColors.SurfaceCard)
                                .border(1.dp, CyberColors.NeonViolet.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Text(genre.name, color = CyberColors.TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(CyberColors.SurfaceCard)
                    .border(1.dp, CyberColors.SurfaceCardBorder.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
                    .padding(18.dp)
            ) {
                Column {
                    Text("TRANSMISSION / BRIEFING", color = CyberColors.NeonPink, fontWeight = FontWeight.Bold, letterSpacing = 1.8.sp, style = MaterialTheme.typography.labelSmall)
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = game.description?.takeIf { it.isNotBlank() } ?: "No data stream available for this title.",
                        color = CyberColors.TextSecondary,
                        lineHeight = 22.sp,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun CyberInfoChip(
    label: String,
    accentColor: Color,
    icon: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(CyberColors.SurfaceCard)
            .border(1.dp, accentColor.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.invoke()
        if (icon != null) Spacer(Modifier.width(6.dp))
        Text(label, color = CyberColors.TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}