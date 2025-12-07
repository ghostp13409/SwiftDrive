package com.example.swiftdrive.features.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.swiftdrive.R
import com.example.swiftdrive.components.customer.DetailRow
import com.example.swiftdrive.components.profile.StatCard

// Profile Screen for Profile Page
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel, onEditProfile: () -> Unit, onLogout: () -> Unit) {
    // gets the current user
    val user = viewModel.currentUser
    val scrollState = rememberScrollState()
    // what this this does is it will show the loading screen while the data is being fetched
    if (viewModel.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (user == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                    stringResource(R.string.user_not_found),
                    style = MaterialTheme.typography.headlineMedium
            )
        }
    } else {
        Column(
                modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Header
            Card(
                    modifier = Modifier.fillMaxWidth().shadow(8.dp, RoundedCornerShape(20.dp)),
                    shape = RoundedCornerShape(20.dp),
                    colors =
                            CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface
                            )
            ) {
                Row(
                        modifier = Modifier.padding(24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Profile Icon
                    Box(
                            modifier =
                                    Modifier.size(80.dp)
                                            .background(
                                                    color = MaterialTheme.colorScheme.primary,
                                                    shape = RoundedCornerShape(40.dp)
                                            ),
                            contentAlignment = Alignment.Center
                    ) {
                        Text(
                                text =
                                        "${user.firstName.firstOrNull() ?: ""}${user.lastName.firstOrNull() ?: ""}".uppercase(),
                                style =
                                        MaterialTheme.typography.headlineLarge.copy(
                                                color = MaterialTheme.colorScheme.onPrimary,
                                                fontWeight = FontWeight.Bold
                                        )
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                                text = "${user.firstName} ${user.lastName}",
                                style =
                                        MaterialTheme.typography.headlineMedium.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onSurface
                                        )
                        )

                        Text(
                                text = user.email,
                                style =
                                        MaterialTheme.typography.bodyLarge.copy(
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Stats Section
            Row(
                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatCard(title = "Total Rentals", value = viewModel.totalRentals.toString())
                StatCard(
                        title = "Total Revenue",
                        value = "$${"%.2f".format(viewModel.totalRevenue)}"
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            // User Details Card
            Card(
                    modifier =
                            Modifier.fillMaxWidth()
                                    .padding(bottom = 20.dp)
                                    .shadow(6.dp, RoundedCornerShape(20.dp)),
                    shape = RoundedCornerShape(20.dp),
                    colors =
                            CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface
                            )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                            text = stringResource(R.string.personal_information),
                            style =
                                    MaterialTheme.typography.titleLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                    ),
                            modifier = Modifier.padding(bottom = 16.dp)
                    )
                    // This is where the user details are displayed
                    DetailRow("Role", user.roles.name)
                    DetailRow("Age", user.age.toString())
                    DetailRow("Phone", user.phoneNumber)
                    DetailRow(
                            "Driving Licence",
                            user.drivingLicence ?: stringResource(R.string.not_provided)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Action Buttons
                    Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                                onClick = onEditProfile,
                                modifier = Modifier.weight(1f),
                                colors =
                                        ButtonDefaults.outlinedButtonColors(
                                                contentColor =
                                                        MaterialTheme.colorScheme.secondaryContainer
                                        )
                        ) {
                            Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit Profile",
                                    modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                    stringResource(R.string.edit),
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }

                        Button(
                                onClick = onLogout,
                                modifier = Modifier.weight(1f),
                                colors =
                                        ButtonDefaults.buttonColors(
                                                containerColor =
                                                        MaterialTheme.colorScheme.errorContainer
                                        )
                        ) {
                            Icon(
                                    imageVector = Icons.Default.Logout,
                                    contentDescription = "Logout",
                                    modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                    stringResource(R.string.logout),
                                    color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                    }
                }
            }
        }
    }
}
