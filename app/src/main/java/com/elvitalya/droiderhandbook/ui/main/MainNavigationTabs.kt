package com.elvitalya.droiderhandbook.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.navigationBarsPadding

@Composable
fun MainNavigationTabs(
    array: Array<MainNavigation>,
    selectedPosition: Int,
    onClick: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        repeat(array.size) { index ->
            val item = array[index]

            TabItem(
                modifier = Modifier.weight(1f),
                item = item,
                index = index,
                selected = selectedPosition == index,
                onClick = onClick,
            )
        }
    }
}

@Composable
private fun TabItem(
    modifier: Modifier = Modifier,
    item: MainNavigation,
    index: Int,
    selected: Boolean,
    onClick: (Int) -> Unit
) {
    val tintColor = if (selected) Color.Blue else Color.Yellow
    val textColor = if (selected) Color.Cyan else Color.Black

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick(index) }
            .padding(top = 8.dp, bottom = 6.dp)
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            imageVector = item.drawableRes,
            contentDescription = null,
            colorFilter = ColorFilter.tint(tintColor),
        )
        Text(
            text = stringResource(item.titleRes),
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            lineHeight = 14.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            color = textColor,
        )
    }
}

@Preview
@Composable
private fun TabBarPreview() {
    MainNavigationTabs(
        array = MainNavigation.values(),
        selectedPosition = MainNavigation.SECTIONS.ordinal,
        onClick = {},
    )
}