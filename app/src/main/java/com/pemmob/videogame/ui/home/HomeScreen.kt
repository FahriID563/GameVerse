package com.pemmob.videogame.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.pemmob.videogame.data.model.GameDto
import com.pemmob.videogame.ui.components.ErrorView
import com.pemmob.videogame.ui.theme.CyberColors
import java.util.Locale

@Composable
fun HomeScreen(viewModel: HomeViewModel, onGameClick: (Int) -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    val query by viewModel.query.collectAsState()
    val filterState by viewModel.filterState.collectAsState()
    var cachedTotal by remember { mutableIntStateOf(0) }

    if (uiState is HomeUiState.Success) {
        cachedTotal = (uiState as HomeUiState.Success).games.size
    }
    val total = if (uiState is HomeUiState.Success) (uiState as HomeUiState.Success).games.size else cachedTotal

    Scaffold(containerColor = CyberColors.BackgroundDark) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(CyberColors.BackgroundGradient)
        ) {
            NeonOrb(Modifier.align(Alignment.TopEnd).offset(x = 100.dp, y = (-80).dp), CyberColors.NeonViolet, 360)
            NeonOrb(Modifier.align(Alignment.BottomStart).offset(x = (-80).dp, y = 80.dp), CyberColors.NeonCyan, 300)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, top = 20.dp)
            ) {
                HomeHeader(
                    query = query,
                    onQueryChange = viewModel::onQueryChange,
                    total = total,
                    filterState = filterState,
                    viewModel = viewModel
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    when (val state = uiState) {
                        is HomeUiState.Loading -> LoadingContent()
                        is HomeUiState.Error -> ErrorView(state.message, viewModel::loadGames, Modifier.fillMaxSize())
                        is HomeUiState.Success -> GameGrid(state.games, onGameClick)
                    }
                }
            }
        }
    }
}

@Composable
private fun LoadingContent() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(bottom = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(6) { LoadingGameCard() }
    }
}

@Composable
private fun LoadingGameCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(CyberColors.SurfaceCard)
            .border(1.dp, CyberColors.NeonCyan.copy(alpha = 0.25f), RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = CyberColors.NeonCyan,
            strokeWidth = 2.dp,
            modifier = Modifier.size(34.dp)
        )
    }
}

@Composable
private fun NeonOrb(modifier: Modifier, color: Color, size: Int) {
    Box(
        modifier = modifier
            .size(size.dp)
            .background(Brush.radialGradient(listOf(color.copy(alpha = 0.18f), Color.Transparent)), CircleShape)
    )
}

@Composable
private fun GameGrid(games: List<GameDto>, onGameClick: (Int) -> Unit) {
    if (games.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("NO SIGNAL: GAME NOT FOUND", color = CyberColors.TextSecondary, fontWeight = FontWeight.Bold)
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(games, key = { it.id }) { game -> GameCard(game, onClick = { onGameClick(game.id) }) }
        }
    }
}

@Composable
private fun HomeHeader(
    query: String,
    onQueryChange: (String) -> Unit,
    total: Int,
    filterState: FilterState,
    viewModel: HomeViewModel
) {
    var isFilterExpanded by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("GAME", color = CyberColors.TextPrimary, fontWeight = FontWeight.Black, fontSize = 30.sp, letterSpacing = 1.5.sp)
                    Text("VERSE", color = CyberColors.NeonCyan, fontWeight = FontWeight.Black, fontSize = 30.sp, letterSpacing = 1.5.sp)
                }
                Text("NEXT-GEN RAWG CATALOG", color = CyberColors.NeonPink, fontWeight = FontWeight.Bold, fontSize = 11.sp, letterSpacing = 2.sp)
            }
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(CyberColors.SurfaceCard)
                    .border(1.dp, CyberColors.NeonCyan.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(Modifier.size(7.dp).clip(CircleShape).background(CyberColors.NeonCyan))
                Spacer(Modifier.width(6.dp))
                Text("$total TITLES", color = CyberColors.TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(Modifier.height(18.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(28.dp))
                    .background(CyberColors.SurfaceCard)
                    .border(1.dp, CyberColors.NeonCyan.copy(alpha = 0.45f), RoundedCornerShape(28.dp))
            ) {
                TextField(
                    value = query,
                    onValueChange = onQueryChange,
                    placeholder = { Text("Search gameverse...", color = CyberColors.TextMuted) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = CyberColors.NeonCyan) },
                    trailingIcon = {
                        if (query.isNotEmpty()) IconButton(onClick = { onQueryChange("") }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear", tint = CyberColors.TextSecondary)
                        }
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = CyberColors.TextPrimary,
                        unfocusedTextColor = CyberColors.TextPrimary,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = CyberColors.NeonCyan
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (filterState.isActive) CyberColors.NeonCyan.copy(alpha = 0.2f) else CyberColors.SurfaceCard)
                    .border(
                        1.dp,
                        if (filterState.isActive) CyberColors.NeonCyan else CyberColors.NeonCyan.copy(alpha = 0.45f),
                        RoundedCornerShape(20.dp)
                    )
                    .clickable { isFilterExpanded = !isFilterExpanded },
                contentAlignment = Alignment.Center
            ) {
                Box {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = "Filter Games",
                        tint = if (filterState.isActive) CyberColors.NeonCyan else CyberColors.TextSecondary,
                        modifier = Modifier.size(24.dp)
                    )
                    if (filterState.isActive) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .align(Alignment.TopEnd)
                                .clip(CircleShape)
                                .background(CyberColors.NeonPink)
                        )
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = isFilterExpanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(CyberColors.SurfaceCard)
                    .border(1.dp, CyberColors.NeonCyan.copy(alpha = 0.35f), RoundedCornerShape(20.dp))
                    .padding(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Search by filters", color = CyberColors.NeonCyan, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                    if (filterState.isActive) {
                        Text(
                            "RESET ALL",
                            color = CyberColors.NeonPink,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable { viewModel.clearFilters() }
                        )
                    }
                }

                Spacer(Modifier.height(10.dp))

                // Genre Filter
                Text("GENRE", color = CyberColors.TextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(6.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    val genres = listOf("ALL", "ACTION", "RPG", "SHOOTER", "ADVENTURE", "INDIE", "STRATEGY", "RACING", "SPORTS")
                    items(genres) { genre ->
                        val isSelected = (genre == "ALL" && filterState.selectedGenre == null) ||
                                (filterState.selectedGenre.equals(genre, ignoreCase = true))
                        FilterChip(
                            text = genre,
                            isSelected = isSelected,
                            onClick = {
                                viewModel.setGenreFilter(if (genre == "ALL") null else genre)
                            }
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Min Rating Filter
                Text("MIN RATING", color = CyberColors.TextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(6.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    val ratings = listOf(
                        "ALL" to 0.0,
                        "★ 4.5+" to 4.5,
                        "★ 4.0+" to 4.0,
                        "★ 3.5+" to 3.5
                    )
                    items(ratings) { (label, value) ->
                        val isSelected = filterState.minRating == value
                        FilterChip(
                            text = label,
                            isSelected = isSelected,
                            onClick = {
                                viewModel.setMinRatingFilter(value)
                            }
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Release Year Filter
                Text("RELEASE YEAR", color = CyberColors.TextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(6.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    val years = listOf("ALL", "2024", "2023", "2022", "2021", "2020", "2019")
                    items(years) { year ->
                        val isSelected = (year == "ALL" && filterState.selectedYear == null) ||
                                (filterState.selectedYear == year)
                        FilterChip(
                            text = year,
                            isSelected = isSelected,
                            onClick = {
                                viewModel.setYearFilter(if (year == "ALL") null else year)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .background(if (isSelected) CyberColors.NeonCyan.copy(alpha = 0.25f) else Color(0x33121420))
            .border(
                1.dp,
                if (isSelected) CyberColors.NeonCyan else Color(0x442A2E45),
                RoundedCornerShape(14.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            color = if (isSelected) CyberColors.NeonCyan else CyberColors.TextSecondary,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium
        )
    }
}

@Composable
private fun GameCard(game: GameDto, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CyberColors.SurfaceCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .border(1.dp, CyberColors.NeonCyan.copy(alpha = 0.32f), RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
    ) {
        Box(Modifier.fillMaxSize()) {
            AsyncImage(game.backgroundImage, contentDescription = game.name, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
            Box(Modifier.fillMaxSize().background(CyberColors.CardGlowOverlay))
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xE6090A10))
                    .border(1.dp, CyberColors.NeonAmber.copy(alpha = 0.7f), RoundedCornerShape(12.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Star, contentDescription = "Rating", tint = CyberColors.NeonAmber, modifier = Modifier.size(13.dp))
                Spacer(Modifier.width(3.dp))
                Text(String.format(Locale.US, "%.1f", game.rating), color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
            Column(Modifier.align(Alignment.BottomStart).fillMaxWidth().padding(12.dp)) {
                game.genres.firstOrNull()?.let {
                    Text(it.name.uppercase(), color = CyberColors.NeonCyan, fontSize = 9.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp, maxLines = 1)
                    Spacer(Modifier.height(2.dp))
                }
                Text(game.name, color = CyberColors.TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold, maxLines = 2, overflow = TextOverflow.Ellipsis, lineHeight = 18.sp)
                Spacer(Modifier.height(4.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(game.released?.take(4) ?: "TBA", color = CyberColors.TextMuted, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                    Box(Modifier.size(6.dp).clip(CircleShape).background(CyberColors.NeonPink))
                }
            }
        }
    }
}