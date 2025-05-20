package com.dkat.wordbook.ui.compose.reusable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun <EditableObjectType, DeletableObjectType> EditableDeletableItem(
    navController: NavController,
    editRoute: String,
    titleValue: String,

    editableObject: EditableObjectType,
    updateEditableObject: (editObject: EditableObjectType) -> Unit,
    editDescription: String,

    deletableObject: DeletableObjectType?,
    deleteObject: ((deleteObject: DeletableObjectType) -> Unit)?,
    deleteDescription: String,

    additionalContent: (@Composable () -> Unit)?,
    modifier: Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row {
            // word value
            Column(horizontalAlignment = Alignment.Start) {
                Text(text = titleValue, modifier.padding(8.dp),
                     style = MaterialTheme.typography.titleMedium,
                     fontWeight = FontWeight.Bold,
                     textAlign = TextAlign.Left)
            }
            Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                Row {
                    // edit button
                    Button(
                        onClick = {
                            updateEditableObject(editableObject)
                            navController.navigate(editRoute)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    ) {
                        Image(imageVector = Icons.Filled.Edit,
                              contentDescription = editDescription
                        )
                    }

                    if (deleteObject != null && deletableObject != null) {
                        // delete button
                        Button(
                            onClick = { deleteObject(deletableObject) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        ) {
                            Image(imageVector = Icons.Filled.Clear,
                                  contentDescription = deleteDescription
                            )
                        }
                    }
                }
            }
        }
        additionalContent?.let { additionalContent() }
    }
}
