package com.pemmob.videogame.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pemmob.videogame.ui.theme.CyberColors

@Composable
fun LoadingView(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = CyberColors.NeonCyan, strokeWidth = 3.dp, modifier = Modifier.size(54.dp))
            Spacer(Modifier.height(18.dp))
            Text(
                text = "ACCESSING THE MAINFRAME...",
                color = CyberColors.NeonCyan,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
fun ErrorView(message: String, onRetry: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier.padding(24.dp), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(CyberColors.SurfaceCard)
                .border(1.dp, CyberColors.NeonPink.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(Icons.Default.WarningAmber, contentDescription = "Error", tint = CyberColors.NeonPink, modifier = Modifier.size(48.dp))
                Spacer(Modifier.height(12.dp))
                Text("TRANSMISSION DISRUPTED", color = CyberColors.NeonPink, fontWeight = FontWeight.Black, fontSize = 15.sp, letterSpacing = 1.sp)
                Spacer(Modifier.height(8.dp))
                Text(message, color = CyberColors.TextSecondary, textAlign = TextAlign.Center, fontSize = 13.sp, lineHeight = 18.sp)
                Spacer(Modifier.height(20.dp))
                Button(
                    onClick = onRetry,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CyberColors.NeonPink)
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Retry", modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("RECONNECT", fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                }
            }
        }
    }
}