package com.ruicomp.downloader.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ruicomp.downloader.core.ui.R


@Composable
fun AdIcon(onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .width(30.dp)
            .height(30.dp),

//            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Ad",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier = Modifier
                .height(26.dp)
                .width(26.dp)
                .padding(3.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White),
            textAlign = TextAlign.Center
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_block),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onClick()
                },
            tint = Color(0xFFA60000)
        )
    }
}

@Composable
fun AdIcon2() {
    Box(
        modifier = Modifier
            .width(26.dp)
            .height(26.dp),

//            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Ad",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(20.dp)
                .height(20 .dp)
//                .padding(3.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White),
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_block),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
//                .clickable {
//                    Toast
//                        .makeText(App.appcontext, "Ac block click", Toast.LENGTH_SHORT)
//                        .show()
//                },
            tint = Color(0xFFA60000)
        )
    }
}



