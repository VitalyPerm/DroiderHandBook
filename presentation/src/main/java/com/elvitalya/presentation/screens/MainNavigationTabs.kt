package com.elvitalya.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elvitalya.presentation.theme.accent
import com.elvitalya.presentation.theme.black
import com.elvitalya.presentation.theme.white
import com.elvitalya.presentation.core.MainNavigation
import com.elvitalya.presentation.core.rippleClickable
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
            .background(accent),
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
    val tintColor = if (selected) black else white
    val textColor = if (selected) black else white

    Column(
        modifier = modifier
            .padding(top = 4.dp, bottom = 3.dp)
            .navigationBarsPadding()
            .clip(CircleShape)
            .rippleClickable(onClick = { onClick(index) })
            .padding(top = 4.dp, bottom = 3.dp),
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