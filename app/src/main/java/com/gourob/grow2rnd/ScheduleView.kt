package com.gourob.grow2rnd

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chihsuanwu.freescroll.freeScroll
import com.chihsuanwu.freescroll.rememberFreeScrollState
import com.gourob.grow2rnd.ui.theme.CeraPro
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale
import kotlin.random.Random


@Composable
fun ScheduleView(modifier: Modifier = Modifier) {
    val freeScrollState = rememberFreeScrollState()

    Column(modifier = modifier.fillMaxSize()) {
        //DateSection
        DateSection()

        // Header Row (Doctors' Names)
        Row {
            Box(
                Modifier
                    .width(tileWidth)
                    .height(tileHeight),
                contentAlignment = Alignment.Center
            ) {

            }
            HeaderSection(
                Modifier
                    .fillMaxWidth()
                    .freeScroll(state = freeScrollState)
            )
        }

        // Left Column (Time Slots)
        Row(modifier = Modifier.fillMaxSize()) {
            TimeSlotSection(
                modifier = Modifier
                    .width(tileWidth)
                    .freeScroll(state = freeScrollState)

            )

            // Main Content
            Box(
                modifier = Modifier
                    .weight(1f)
                    .freeScroll(state = freeScrollState)
            ) {
                MainSection(rows = timeSlots.size, cols = doctors.size)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    ScheduleView()
}


val tileHeight = 80.dp
val tileWidth = 40.dp
val mainTileHeight = 68.dp
val mainTileWidth = 124.dp

val headerTileWidth = mainTileWidth
val headerTileHeight = 100.dp

val borderColor = Color(0xFFF5F5F8)
val primaryTextColor = Color(0xFF201E5A)
val primaryButtonTextColor = Color(0xFF2143A4)

val timeSlots = listOf(
    "8:00 AM",
    "8:30 AM",
    "9:00 AM",
    "9:30 AM",
    "10:00 AM",
    "10:30 AM",
    "11:00 AM",
    "11:30 AM",
    "12:00 PM",
    "12:30 PM",
    "1:00 PM",
    "1:30 PM",
    "2:00 PM",
    "2:30 PM",
    "3:00 PM",
    "3:30 PM",
    "4:00 PM",
    "4:30 PM",
    "5:00 PM",
    "5:30 PM",
    "6:00 PM",
    "6:30 PM",
    "7:00 PM",
    "7:30 PM",
    "8:00 PM",
    "8:30 PM",
    "9:00 PM",
    "9:30 PM",
    "10:00 PM"
)

val doctors = listOf("Dr. A", "Dr. B", "Dr. C", "Dr. D", "", "", "", "", "")


@Composable
private fun MainSection(rows: Int, cols: Int) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row {
            repeat(cols) {
                Column {
                    repeat(rows) {
                        Box(
                            Modifier
                                .width(mainTileWidth)
                                .height(mainTileHeight)
                                .border(2.dp, borderColor),
                            contentAlignment = Alignment.TopStart
                        ) {
                            MainSectionContent()
                            if (Random.nextBoolean()) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .width(10.dp)
                                        .padding(2.dp)
                                        .clip(
                                            RoundedCornerShape(
                                                topStart = 15.dp,
                                                topEnd = 0.dp,
                                                bottomStart = 15.dp,
                                                bottomEnd = 0.dp
                                            )
                                        )
                                        .background(Color.Red)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TimeSlotSection(modifier: Modifier = Modifier) {
    Column(modifier) {
        timeSlots.forEach { time ->
            Box(
                modifier = Modifier
                    .width(tileWidth)
                    .height(mainTileHeight),
                contentAlignment = Alignment.TopCenter,
            ) {
                Text(
                    text = time,
                    modifier = Modifier.padding(top = 6.dp),
                    color = primaryTextColor,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    fontFamily = CeraPro
                )
            }
        }
    }
}


@Composable
private fun HeaderSection(modifier: Modifier) {
    Row(modifier) {
        doctors.forEach { doctor ->
            Box(
                Modifier
                    .width(headerTileWidth)
                    .height(headerTileHeight)
                    .border(2.dp, borderColor),
                contentAlignment = Alignment.Center,
            ) {
                HeaderSectionContent()
            }
        }
    }
}

@Composable
fun MainSectionContent() {
    val colors = listOf(Color(0xFFF8EFE1), Color(0xFFE7E5FF))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(15.dp))
            .background(colors.random())
            .padding(8.dp),
    ) {
        Column(Modifier.padding(4.dp)) {
            Text(
                "Cassidy Moore",
                color = primaryTextColor,
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp,
                fontFamily = CeraPro,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                "8:00-9:00 AM",
                color = primaryTextColor,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                fontFamily = CeraPro,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Composable
fun HeaderSectionContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Doctor 1",
            color = primaryTextColor,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            fontFamily = CeraPro,
        )

        OutlinedButton(
            onClick = {},
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(
                "Close",
                color = primaryButtonTextColor,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                fontFamily = CeraPro,
            )
        }
        Text(
            "$381.99",
            color = primaryTextColor,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            fontFamily = CeraPro,
        )
    }
}


@Composable
private fun DateCard(
    date: String,
    dayOfWeek: String,
    isSelected: Boolean,
    onDateSelected: () -> Unit
) {
    val color by animateColorAsState(if (isSelected) Color(0xFF201E5A) else Color.White)
    val textColor = if (isSelected) Color.White else Color(0xFF201E5A)
    //val scale by animateFloatAsState(if(isSelected) 1.2f else 1f, label = "Scale Animation")
    //val horizontalPadding by animateFloatAsState(if(isSelected) 4f else 0f, label = "padding animation")

    Box(
        Modifier
            .width(45.dp)
            .height(60.dp)
//            .scale(scale)
            //.padding(horizontalPadding.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color)
            .clickable { onDateSelected() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                date,
                fontWeight = FontWeight.Normal,
                fontFamily = CeraPro,
                fontSize = 13.sp,
                color = textColor,
            )
            Text(
                dayOfWeek,
                fontWeight = FontWeight.Bold,
                fontFamily = CeraPro,
                fontSize = 13.sp,
                color = textColor
            )
        }
    }
}

fun getAllDatesWithDays(today: LocalDate): List<Pair<String, String>> {
    val year = today.year
    val month = today.month
    val yearMonth = YearMonth.of(year, month)
    return (1..yearMonth.lengthOfMonth()).map { day ->
        val date = LocalDate.of(year, month, day)
        val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
        "$day" to dayOfWeek
    }
}

@Preview
@Composable
private fun DateCardPreview() {
    DateCard(
        "25",
        "Fri",
        true
    ) {}
}

@Composable
fun DateSection(
    modifier: Modifier = Modifier,
) {
    val dateWithDays = getAllDatesWithDays(LocalDate.now())
    var dayOfMonth by remember { mutableStateOf(LocalDate.now().dayOfMonth.toString()) }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val dayOfMonthIndex = dateWithDays.indexOfFirst { it.first == dayOfMonth }
        if (dayOfMonthIndex != -1) {
            coroutineScope.launch {
                listState.animateScrollToItem(dayOfMonthIndex)
            }
        }
    }

    LazyRow(
        state = listState,
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        items(dateWithDays) { (day, dayOfWeek) ->
            DateCard(day, dayOfWeek, day == dayOfMonth) {
                dayOfMonth = day
            }
        }
    }
}

@Composable
fun TopSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            "Schedule",
            fontWeight = FontWeight.ExtraBold,
            fontFamily = CeraPro,
            fontSize = 25.sp,
            color = primaryTextColor
        )


    }
}

@Preview(showBackground = true)
@Composable
private fun TopSectionPreview() {
    TopSection()
}