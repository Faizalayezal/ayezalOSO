package com.sanxingrenge.benben.uiComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.sanxingrenge.benben.ui.theme.QuillGrey
import com.sanxingrenge.benben.ui.theme.VioletBlue
import com.sanxingrenge.benben.ui.theme.poppinsRegular

data class InterestGridItem(
    val name: String,
    val imageResource: String,
    var isSelected: Boolean = false,
)

private const val TAG = "InterestGridLayout"
@Composable
fun InterestGridLayout(
    list: List<InterestGridItem>,
    spanCount: Int,
    clickable: Boolean,
    openPopupOnClick: ((List<InterestGridItem>) -> Unit)? = null
) {

    val horSpace = if (spanCount > 4) 10.dp else 20.dp

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
        LazyVerticalGrid(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .heightIn(0.dp, 1000.dp),
            columns = GridCells.Fixed(spanCount),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalArrangement = Arrangement.spacedBy(horSpace)
        ) {

            items(list.size) { pos ->

                val selectedGender = remember {
                    mutableStateOf(list[pos].isSelected)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(enabled = clickable, onClick = {

                        if (clickable && openPopupOnClick == null) {
                            list.filter { it.isSelected }.size.let {
                                if (it < 5 || selectedGender.value) {
                                    selectedGender.value = !selectedGender.value
                                    list.getOrNull(pos)?.isSelected = selectedGender.value
                                }
                            }
                        } else if (clickable && openPopupOnClick != null) {
                            list.filter { it.isSelected }.size.let {
                                if (it < 5 || selectedGender.value) {
                                    selectedGender.value = !selectedGender.value
                                    list.getOrNull(pos)?.isSelected = selectedGender.value
                                }
                            }

                            /*Log.d(TAG, "InterestGridLayout: testNEWDATA================")
                            list.forEach {
                                Log.d(TAG, "InterestGridLayout: ${it.name}  >>${it.isSelected}")
                            }
                            */
                            openPopupOnClick.invoke(list.filter {it.isSelected})
                        }

                    }) {

                        Image(
                            painter = rememberAsyncImagePainter(list.getOrNull(pos)?.imageResource),
                            contentDescription = "",
                            Modifier
                                .border(
                                    width = 1.dp,
                                    if (selectedGender.value) VioletBlue else QuillGrey,
                                    shape = CircleShape
                                )
                                .size(60.dp)
                                .padding(12.dp),
                        )
                    }
                    AddSpace(5.dp)
                    Text(
                        text = list.getOrNull(pos)?.name ?: "",
                        fontFamily = poppinsRegular,
                        fontSize = 10.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color =
                        if (clickable) {
                            if (selectedGender.value) VioletBlue else QuillGrey
                        } else {
                            Color.Black
                        }
                    )

                }
            }
        }
    }
}