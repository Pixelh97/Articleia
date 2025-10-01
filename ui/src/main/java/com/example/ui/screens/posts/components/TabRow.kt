package com.example.ui.screens.posts.components

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.AppTheme

@Composable
fun TabRow(
    modifier: Modifier = Modifier,
    containerColor: Color = AppTheme.color.tabContainer,
    selectedIndicatorColor: Color = AppTheme.color.tabIndicator,
    unselectedIndicatorColor: Color = AppTheme.color.tabContainer,
    containerShape: Shape = CircleShape,
    indicatorShape: Shape = CircleShape,
    paddingValues: PaddingValues = PaddingValues(4.dp),
    animationSpec: AnimationSpec<Dp> = tween(durationMillis = 250, easing = FastOutSlowInEasing),
    selectedTabPosition: Int = 0,
    tabItem: @Composable () -> Unit,
) {
    Surface(
        color = containerColor,
        shape = containerShape,
        modifier = modifier,
    ) {
        SubcomposeLayout(
            Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .selectableGroup(),
        ) { constraints ->
            val tabMeasurable: List<Placeable> =
                subcompose(SubComposeID.PRE_CALCULATE_ITEM, tabItem)
                    .map { it.measure(constraints) }

            val itemsCount = tabMeasurable.size
            val maxItemHeight = tabMeasurable.maxOf { it.height }

            val tabRowWidth = constraints.maxWidth
            val evenTabWidth = tabRowWidth / itemsCount

            val tabPlacables =
                subcompose(SubComposeID.ITEM, tabItem).map {
                    val c =
                        constraints.copy(
                            minWidth = evenTabWidth,
                            maxWidth = evenTabWidth,
                            minHeight = maxItemHeight,
                        )
                    it.measure(c)
                }

            val tabPositions =
                tabPlacables.mapIndexed { index, placeable ->
                    val itemWidth = evenTabWidth
                    val x = evenTabWidth * index
                    TabPosition(x.toDp(), itemWidth.toDp())
                }

            layout(tabRowWidth, maxItemHeight) {
                subcompose(SubComposeID.UNSELECTED_INDICATOR) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(maxItemHeight.toDp())
                            .background(color = unselectedIndicatorColor, containerShape),
                    )
                }.forEach {
                    it.measure(Constraints.fixed(tabRowWidth, maxItemHeight)).placeRelative(0, 0)
                }

                subcompose(SubComposeID.INDICATOR) {
                    Box(
                        Modifier
                            .tabIndicator(tabPositions[selectedTabPosition], animationSpec)
                            .fillMaxWidth()
                            .height(maxItemHeight.toDp())
                            .background(color = selectedIndicatorColor, indicatorShape),
                    )
                }.forEach {
                    it.measure(Constraints.fixed(tabRowWidth, maxItemHeight)).placeRelative(0, 0)
                }

                tabPlacables.forEachIndexed { index, placeable ->
                    val x = evenTabWidth * index
                    placeable.placeRelative(x, 0)
                }
            }
        }
    }
}

fun Modifier.tabIndicator(
    tabPosition: TabPosition,
    animationSpec: AnimationSpec<Dp>,
): Modifier =
    composed(
        inspectorInfo =
            debugInspectorInfo {
                name = "tabIndicatorOffset"
                value = tabPosition
            },
    ) {
        val currentTabWidth by animateDpAsState(
            targetValue = tabPosition.width,
            animationSpec = animationSpec,
        )
        val indicatorOffset by animateDpAsState(
            targetValue = tabPosition.left,
            animationSpec = animationSpec,
        )
        fillMaxWidth()
            .wrapContentSize(Alignment.BottomStart)
            .offset(x = indicatorOffset)
            .width(currentTabWidth)
            .fillMaxHeight()
    }

enum class SubComposeID {
    PRE_CALCULATE_ITEM,
    ITEM,
    UNSELECTED_INDICATOR,
    INDICATOR,
}

data class TabPosition(
    val left: Dp,
    val width: Dp,
)
