package com.mbcoding.wcfinder.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.mbcoding.wcfinder.domain.model.WC


/**
 * Author: Mahmoud B.
 * Date: 01/11/2024
 * Description: WCList
 */

@Composable
fun WCList(
    pagingItems: LazyPagingItems<WC>,
    onClickItem: (wc: WC?) -> Unit
) {
    Text(
        modifier = Modifier.padding(start = 16.dp),
        text = if (pagingItems.itemSnapshotList.isNotEmpty())
            "Toilettes autour de moi" else
            "Aucune toilette trouvée dans un rayon de 5 km",
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium
    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(count = pagingItems.itemCount) { index ->
            val wc = pagingItems[index]
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onClickItem.invoke(wc) }
            ) {
                Text(
                    text = wc?.name ?: "",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = wc?.address ?: "",
                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp
                )
                Text(
                    modifier = Modifier.align(Alignment.End),
                    text = "${wc?.distance ?: ""}m",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = "Horaires: ${wc?.hours ?: ""}",
                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp
                )
            }
            HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
        }
    }
}