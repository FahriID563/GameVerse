package com.pemmob.videogame.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object CyberColors {
    val BackgroundDark = Color(0xFF090A10)
    val SurfaceCard = Color(0xCC131524)
    val SurfaceCardBorder = Color(0x4000F0FF)
    val NeonCyan = Color(0xFF00F0FF)
    val NeonViolet = Color(0xFFB026FF)
    val NeonPink = Color(0xFFFF007F)
    val NeonAmber = Color(0xFFFFB800)
    val TextPrimary = Color(0xFFF1F5F9)
    val TextSecondary = Color(0xFF94A3B8)
    val TextMuted = Color(0xFF64748B)

    val BackgroundGradient = Brush.verticalGradient(
        listOf(Color(0xFF0B0D17), Color(0xFF07080E), Color(0xFF040508))
    )
    val CardGlowOverlay = Brush.verticalGradient(
        listOf(Color.Transparent, Color(0x80090A10), Color(0xF0090A10))
    )
    val HeroOverlayGradient = Brush.verticalGradient(
        listOf(Color.Transparent, Color(0x60090A10), Color(0xFF090A10))
    )
}