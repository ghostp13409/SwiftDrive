package com.example.eventexplorer8959999.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.eventexplorer8959999.R
import com.example.eventexplorer8959999.data.Event
import com.example.eventexplorer8959999.data.EventCategories
import com.example.eventexplorer8959999.ui.theme.EventExplorer8959999Theme

@Composable
fun EventCard(event: Event, modifier: Modifier = Modifier, onEventClick: (Event) -> Unit) {

    // Event Card Component
    Card (
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable{
                onEventClick(event)
            },
        elevation = CardDefaults.cardElevation(6.dp),
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Event Image
            Image(
                painter = painterResource(id = event.imageRes),
                contentDescription = event.name,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Event Info: Name, Category, Date, Location
            Column (
                modifier = Modifier.weight(1f)
            ) {
                Text(text = event.name, style = MaterialTheme.typography.titleLarge)
                Text(text = event.category.toString(), style = MaterialTheme.typography.bodyLarge)
                Text(text = "${event.date}, ${event.time}", style = MaterialTheme.typography.titleSmall)
                Text(text = event.location, style = MaterialTheme.typography.titleMedium)

            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun EventCardPreview(){
    val sampleEvent = Event(
        name = "Jazz Under the Stars",
        imageRes = R.drawable.music_event,
        date = "2024-12-15",
        time = "20:00",
        location = "Blue Note Theater",
        description = "An enchanting evening of smooth jazz featuring local and international artists under the open sky.",
        ticketPrice = 45.00f,
        category = EventCategories.Music
    )

    EventExplorer8959999Theme {
        EventCard(event = sampleEvent, onEventClick = {})
    }
}