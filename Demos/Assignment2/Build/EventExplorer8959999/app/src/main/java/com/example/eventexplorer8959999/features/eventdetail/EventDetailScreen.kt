package com.example.eventexplorer8959999.features.eventdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eventexplorer8959999.R
import com.example.eventexplorer8959999.features.eventlist.EventListViewModel

@Composable
fun EventDetailScreen(viewModel: EventListViewModel, onBackClick: () -> Unit, modifier: Modifier = Modifier){

    val event = viewModel.selectedEvent
    var scrollState = rememberScrollState()

    // Event Detail page
    Surface (
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {
        Column (
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Event Logo
            Image(
                painter = painterResource(event.imageRes),
                contentDescription = event.name,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .size(200.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            // Event Details
            Column (
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Title
                Text(event.name, style = MaterialTheme.typography.headlineMedium)

                // Category, Date, Time, Location
                Spacer(modifier.height(8.dp))
                Text(
                    "${viewModel.selectedEventCategoryIcon} " + event.category,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text("📅 " + event.date, style = MaterialTheme.typography.bodyMedium)
                Text("🕐 " + event.time, style = MaterialTheme.typography.bodyMedium)
                Text("📍 " + event.location, style = MaterialTheme.typography.bodyMedium)

                // Description
                Spacer(modifier.height(16.dp))
                Text(event.description, style = MaterialTheme.typography.bodyLarge)


                // Ticket Price
                Spacer(modifier.height(16.dp))
                Text(stringResource(R.string.event_ticket_price_title))
                Text("$ ${event.ticketPrice}", style = MaterialTheme.typography.headlineSmall)
            }

            // Buy Tickets Button
            Button(
                onClick = {},
                modifier = modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(stringResource(R.string.event_buy_button), style = MaterialTheme.typography.headlineSmall)
            }
        }
    }
}