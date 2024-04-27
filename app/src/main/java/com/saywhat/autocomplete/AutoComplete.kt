package com.saywhat.autocomplete

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoComplete() {
    val carBrands = listOf(
        "Toyota",
        "Honda",
        "Nissan",
        "Ford",
        "Chevrolet",
        "Volkswagen",
        "BMW",
        "Mercedes-Benz",
        "Audi",
        "Hyundai",
        "Kia",
        "Subaru",
        "Mazda",
        "Lexus",
        "Volvo",
        "Porsche",
        "Tesla",
        "Ferrari",
        "Lamborghini",
        "Maserati",
        "Jaguar",
        "Land Rover",
        "Jeep",
        "Chrysler",
        "Dodge",
        "Buick",
        "Cadillac",
        "Acura",
        "Infiniti",
        "Genesis"
    )
    var category by remember {
        mutableStateOf("")
    }
    val heightTextField by remember {
        mutableStateOf(55.dp)
    }
    var textFieldSize by remember {
        mutableStateOf(Size.Zero)
    }
    var expanded by remember {
        mutableStateOf(false)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Column(
        modifier = Modifier
            .padding(30.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    expanded = false
                }
            )
    ) {
        Text(
            text = "Cars",
            modifier = Modifier.padding(start = 3.dp, bottom = 2.dp),
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = category, onValueChange = {
                        expanded = true
                        category = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(heightTextField)
                        .border(
                            width = 1.8.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        }, placeholder = { Text("Enter an Cars Brand Name") },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black
                    ),
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                imageVector = Icons.Rounded.KeyboardArrowDown,
                                contentDescription = "Arrow",
                                tint = Color.Black,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                )
            }
            AnimatedVisibility(visible = expanded) {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .width(textFieldSize.width.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    LazyColumn(modifier = Modifier.heightIn(max = 150.dp)) {
                        if (category.isNotEmpty()) {
                            items(
                                carBrands.filter {
                                    it.lowercase().contains(category.lowercase()) || it.lowercase()
                                        .contains("others")
                                }.sorted()
                            ) {
                                ItemCategory(title = it) { title ->
                                    category = title
                                    expanded = false
                                }
                            }
                        } else {
                            items(carBrands.sorted()) {
                                ItemCategory(title = it) { title ->
                                    category = title
                                    expanded = false
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemCategory(
    title: String,
    onSelect: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect(title) }
            .padding(10.dp)
    ) {
        Text(text = title, fontSize = 16.sp)
    }
}