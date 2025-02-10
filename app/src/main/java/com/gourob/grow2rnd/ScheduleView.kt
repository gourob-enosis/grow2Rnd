package com.gourob.grow2rnd

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chihsuanwu.freescroll.freeScroll
import com.chihsuanwu.freescroll.rememberFreeScrollState
import com.gourob.grow2rnd.ui.theme.CeraPro
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.roundToInt
import kotlin.random.Random


@Composable
fun ScheduleView(modifier: Modifier = Modifier) {
    val freeScrollState = rememberFreeScrollState()

    Column(modifier = modifier.fillMaxSize()) {
        //DateSection
        DateSection()

        Spacer(Modifier.height(16.dp))
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
        Row{
            TimeSlotSection(
                modifier = Modifier
                    .width(tileWidth)
                    .freeScroll(freeScrollState)
            )

            // Main Content
            Box(modifier = Modifier.freeScroll(freeScrollState)) {
                MainSection(timeSlots = timeSlots, doctors = doctors)
            }

        }

    }
}


@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    ScheduleView()
}


val tileHeight = 90.dp
val tileWidth = 40.dp
val mainTileHeight = tileHeight
val mainTileWidth = 200.dp

val headerTileWidth = mainTileWidth
val headerTileHeight = 100.dp

val borderColor = Color(0xFFF5F6F8)
val primaryTextColor = Color(0xFF201E5A)
val primaryButtonTextColor = Color(0xFF2143A4)


val timeSlots = listOf(
    "8:00 AM",
    "9:00 AM",
    "10:00 AM",
    "11:00 AM",
    "12:00 PM",
    "1:00 PM",
    "2:00 PM",
    "3:00 PM",
    "4:00 PM",
    "5:00 PM",
    "6:00 PM",
    "7:00 PM",
    "8:00 PM",
    "9:00 PM",
    "10:00 PM"
)

val doctors = listOf("Dr. A", "Dr. B", "Dr. C", "Dr. D", "Dr. E")
//val doctors = listOf("Dr. A", "Dr. B", "Dr. C",)
//val doctors = listOf("Dr. A")

val schedules = listOf(
    ScheduleBlock("John Doe", "8:00 AM", "9:00 AM", "Dr. A"),
    ScheduleBlock("Emma Smith", "9:00 AM", "10:00 AM", "Dr. B"),
    ScheduleBlock("Michael Johnson", "10:00 AM", "11:00 AM", "Dr. C"),
    ScheduleBlock("Sophia Brown", "3:00 PM", "4:00 PM", "Dr. D"),
    ScheduleBlock("David Wilson", "4:00 PM", "5:00 PM", "Dr. E")
)


data class ScheduleBlock(
    val name: String,
    val startTime: String,
    val endTime: String,
    val doctorName: String,
) {

    companion object {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("h:mm a")
    }

    private fun getTimeSpanInHour(): Double {
        val start = LocalTime.parse(startTime, formatter)
        val end = LocalTime.parse(endTime, formatter)
        return (end.toSecondOfDay() - start.toSecondOfDay()) / 3600.0
    }

    fun getHeightOfBlock(): Int {
        return (getTimeSpanInHour() * mainTileHeight.value.toDouble()).roundToInt() - 2
    }


    fun getGridTopOffset(): Int {
        val start = LocalTime.parse(startTime, formatter)
        val minutes = start.minute
        val minuteFraction = minutes / 60.0
        return (minuteFraction * mainTileHeight.value.toDouble()).roundToInt()
    }
}


fun startTimeInThisRange(startTime: String, timeSlotStarting: String): Boolean {
    val formatter = DateTimeFormatter.ofPattern("h:mm a")
    val start = LocalTime.parse(startTime, formatter)
    println("Start $start")
    val slotStart = LocalTime.parse(timeSlotStarting, formatter)
    println("Slot Start $slotStart")
    val slotEnd = slotStart.plusMinutes(59)
    println("Slot End $slotEnd")
    return start >= slotStart && start < slotEnd
}


fun getScheduleToPlaceIn(timeRangeIndex: Int, doctorColumnIndex: Int): ScheduleBlock? {
    return schedules.firstOrNull { schedule ->
        val timeSlot = timeSlots[timeRangeIndex]
        val doctorName = doctors[doctorColumnIndex]
        schedule.doctorName == doctorName && startTimeInThisRange(schedule.startTime, timeSlot)
    }
}


@Composable
private fun MainSection(timeSlots: List<String>, doctors: List<String>) {
    Column {
        Row {
            repeat(doctors.size) { _ ->
                Column {
                    repeat(timeSlots.size) { _ ->
                        Box(
                            Modifier
                                .width(mainTileWidth)
                                .height(mainTileHeight)
                                .border(1.dp, Color.Gray),
                            contentAlignment = Alignment.Center
                        ) {}
                    }
                }
            }
        }
    }
    Column {
        Row {
            repeat(doctors.size) { doctorIndex ->
                Column {
                    repeat(timeSlots.size) { timeSlotIndex ->
                        val schedule = getScheduleToPlaceIn(timeSlotIndex, doctorIndex)

                        if (schedule != null) {
                            Box(
                                Modifier
                                    .width(mainTileWidth)
                                    .height(schedule.getHeightOfBlock().dp)
                                    .padding(horizontal = 4.dp, vertical = 2.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                MainSectionContent(schedule)
                            }
                        } else {
                            Box(
                                Modifier
                                    .width(mainTileWidth)
                                    .height(mainTileHeight),
                                //.border(1.dp, Color.Gray),
                                contentAlignment = Alignment.Center
                            ) {}
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
                    .height(tileHeight),
                contentAlignment = Alignment.TopCenter,
            ) {
                Text(
                    text = time,
                    // modifier = Modifier.padding(top = 6.dp),
                    color = primaryTextColor,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    fontFamily = CeraPro,
                    textAlign = TextAlign.Center
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
                    .border(1.dp, Color.Gray),
                contentAlignment = Alignment.Center,
            ) {
                HeaderSectionContent(doctorName = doctor)
            }
        }
    }
}

@Composable
fun MainSectionContent(scheduleBlock: ScheduleBlock) {
    Column(
        modifier = Modifier
            .padding(3.dp)
            .clip(RoundedCornerShape(10.dp)),
    ) {
        Slot(scheduleBlock)
    }
}

@Composable
fun Slot(
    scheduleBlock: ScheduleBlock,
    showMarker: Boolean = Random.nextBoolean()
) {
    val colors = listOf(Color(0xFFF8EFE1), Color(0xFFE7E5FF))
    val markerColors = listOf(Color.Red, Color.Green)

    Box(
        modifier = Modifier
            .offset(y = scheduleBlock.getGridTopOffset().dp, x = 0.dp)
            .height(scheduleBlock.getHeightOfBlock().dp)
            .fillMaxWidth()
            .background(colors.random())
    ) {
        Column(
            Modifier
                .padding(4.dp)
                .padding(horizontal = if (showMarker) 4.dp else 0.dp)
                .padding(horizontal = 4.dp)
        ) {
            Text(
                scheduleBlock.name,
                color = primaryTextColor,
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp,
                fontFamily = CeraPro,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                "${scheduleBlock.startTime} - ${scheduleBlock.endTime}",
                color = primaryTextColor,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                fontFamily = CeraPro,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }


        if (showMarker) {
            Box(
                Modifier
                    .width(5.dp)
                    .height(scheduleBlock.getHeightOfBlock().dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 10.dp,
                            topEnd = 0.dp,
                            bottomStart = 10.dp,
                            bottomEnd = 0.dp
                        )
                    )
                    .background(markerColors.random())
            )
        }
    }

}


@Composable
fun HeaderSectionContent(modifier: Modifier = Modifier, doctorName: String) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            doctorName,
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

    Box(
        Modifier
            .width(45.dp)
            .height(60.dp)
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


fun main() {
    println(timeSlots)
    println(doctors)
    repeat(doctors.size) { doctorIndex ->
        repeat(timeSlots.size) { timeSlotIndex ->
            println("${doctors[doctorIndex]}  ${timeSlots[timeSlotIndex]}")
            val schedule = getScheduleToPlaceIn(timeSlotIndex, doctorIndex)
            if (schedule != null) {
                println("Place schedule $schedule in time slot ${timeSlots[timeSlotIndex]} for doctor ${doctors[doctorIndex]}")
            }
        }
    }
}