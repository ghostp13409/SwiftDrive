package com.example.swiftdrive.components.customer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.swiftdrive.R
import com.example.swiftdrive.data.models.UserRoles

// Role Dropdown Component for selecting user roles
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoleDropdown(selectedRole: UserRoles, onRoleSelected: (UserRoles) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val roles = UserRoles.values() // Gets all possible enum values.

    // This box handles the state of the dropdown menu (expanded or collapsed).
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        // This is the visible text field part of the dropdown.
        OutlinedTextField(
                value = selectedRole.name,
                onValueChange = {}, // readOnly, so no change is handled here.
                readOnly = true,
                label = { Text(stringResource(id = R.string.user_role)) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier =
                        Modifier.menuAnchor() // Anchors the dropdown menu to this text field.
                                .fillMaxWidth()
        )
        // This is the actual dropdown that appears when expanded.
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            roles.forEach { role ->
                DropdownMenuItem(
                        text = { Text(role.name) },
                        onClick = {
                            onRoleSelected(role)
                            expanded = false
                        }
                )
            }
        }
    }
}
