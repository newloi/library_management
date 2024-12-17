package com.example.librarymanagement.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.example.librarymanagement.R
import com.example.librarymanagement.ui.borrow.BorrowRequestsUiState
//import com.example.librarymanagement.ui.borrow.BorrowRequestsDestination
import com.example.librarymanagement.ui.theme.Cancel
import com.example.librarymanagement.ui.theme.Delete
import com.example.librarymanagement.ui.theme.MainColor
import com.example.librarymanagement.ui.theme.MoreDarkGray
import com.example.librarymanagement.ui.theme.Roboto
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SearchTopBar(
    search: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    var searchText by rememberSaveable { mutableStateOf("") }
    var focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = searchText,
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        },

        trailingIcon = {
            if (searchText.isNotEmpty()) {
                Icon(
                    Icons.Rounded.Clear,
                    contentDescription = "Xóa",
                    tint = Color.Gray,
                    modifier = Modifier
                        .clickable() {
                            searchText = ""
                            search("")
                        }
                        .border(
                            width = 0.5.dp,
                            color = Color.Gray,
                            shape = CircleShape
                        )
                        .size(20.dp)
                )
            } else {
                Icon(
                    painter = painterResource(R.drawable.search),
                    contentDescription = "Tìm kiếm"
                )
            }

        },
        onValueChange = {
            searchText = it
            search(it)
        },
        singleLine = true,
        shape = RoundedCornerShape(99.dp),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        modifier = modifier
            .padding(horizontal = 16.dp)
            .height(52.dp)
            .fillMaxWidth()
    )
}

@Composable
fun FilterBar(
    isIncreasing: Boolean,
    onToggleSortOrder: () -> Unit,
    onSortByOpt0: () -> Unit,
    onSortByOpt1: () -> Unit,
    onSortByOpt2: () -> Unit,
    sortOptions: List<String>,
    modifier: Modifier = Modifier
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = {
                onToggleSortOrder()
            },
            modifier = Modifier.width(50.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.filter_options),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 4.dp)
                )
                Text(
                    text = if (isIncreasing) "A-Z" else "Z-A",
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
        IconButton(
            onClick = { isExpanded = true },
            modifier = Modifier.padding(end = 12.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.filter_alt),
                contentDescription = null
            )
            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                DropdownMenuItem(
                    onClick = {
                        isExpanded = false
                        onSortByOpt0()
                    },
                    text = {
                        Text(
                            text = sortOptions[0],
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    modifier = Modifier.height(40.dp)
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(0.5.dp)
                )
                DropdownMenuItem(
                    onClick = {
                        isExpanded = false
                        onSortByOpt1()
                    },
                    text = {
                        Text(
                            text = sortOptions[1],
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    modifier = Modifier.height(40.dp)
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(0.5.dp)
                )
                DropdownMenuItem(
                    onClick = {
                        isExpanded = false
                        onSortByOpt2()
                    },
                    text = {
                        Text(
                            text = sortOptions[2],
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    modifier = Modifier.height(40.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoAppBar(
    title: String,
    navigateToEdit: () -> Unit = {},
    onDelete: () -> Unit,
    navigateBack: () -> Unit,
    markReturned: () -> Unit = {},
    isBorrow: Boolean = false,
    isReturned: Boolean = false,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium
            )
        },
        navigationIcon = {
            IconButton(
                onClick = navigateBack
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Trở về"
                )
            }
        },
        actions = {
            var isExpanded by remember { mutableStateOf(false) }

            IconButton(onClick = { isExpanded = true }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Thêm"
                )
            }
            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
            ) {
                if (isBorrow && !isReturned) {
                    DropdownMenuItem(
                        onClick = {
                            isExpanded = false
                            markReturned()
                        },
                        text = {
                            Text(
                                text = "Đánh dấu đã trả",
                                style = MaterialTheme.typography.labelMedium,
//                                modifier = Modifier.padding(start = 20.dp),
                                color = MainColor
                            )
                        },
                        modifier = Modifier.height(40.dp)
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                    )
                } else if (!isBorrow) {
                    DropdownMenuItem(
                        onClick = {
                            isExpanded = false
                            navigateToEdit()
                        },
                        text = {
                            Text(
                                text = "Sửa",
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.padding(start = 20.dp)
                            )
                        },
                        trailingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.edit),
                                contentDescription = "Sửa thông tin"
                            )
                        },
                        modifier = Modifier.height(40.dp)
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                    )
                }
                DropdownMenuItem(
                    onClick = {
                        isExpanded = false
                        onDelete()
                    },
                    text = {
                        Text(
                            text = "Xóa",
                            style = MaterialTheme.typography.labelMedium,
                            color = Delete,
                            modifier = Modifier.padding(start = 20.dp)
                        )
                    },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.delete),
                            contentDescription = "Xóa",
                            tint = Delete
                        )
                    },
                    modifier = Modifier.height(40.dp)
                )
            }
        },
        modifier = modifier.shadow(4.dp)
    )
}

@Composable
fun HomeBottomAppBar(
    navigateToBooksScreen: () -> Unit = {},
    navigateToMembersScreen: () -> Unit = {},
    navigateToBorrowRequestsScreen: () -> Unit = {},
    navigateToSettingScreen: () -> Unit = {},
//    onTabSelected: (Int) -> Unit,
    currentTabIndex: Int,
    modifier: Modifier = Modifier
) {
    Row(
//        selectedTabIndex = currentTabIndex,
//        indicator = {},
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TabIcon(
            selected = (currentTabIndex == 0),
            navigateToAnother = navigateToBooksScreen,
            selectedIcon = R.drawable.book_colored,
            unSelectedIcon = R.drawable.book,
            label = "Sách",
            modifier = Modifier.weight(1f)
        )
        TabIcon(
            selected = (currentTabIndex == 1),
            navigateToAnother = navigateToMembersScreen,
            selectedIcon = R.drawable.group_colored,
            unSelectedIcon = R.drawable.group,
            label = "Thành viên",
            modifier = Modifier.weight(1f)
        )
        TabIcon(
            selected = (currentTabIndex == 2),
            navigateToAnother = navigateToBorrowRequestsScreen,
            selectedIcon = R.drawable.rent_book_colored,
            unSelectedIcon = R.drawable.rent,
            label = "Đơn mượn",
            modifier = Modifier.weight(1f)
        )
//        TabIcon(
//            selected = (currentTabIndex == 3),
//            navigateToAnother = navigateToSettingScreen,
//            selectedIcon = R.drawable.setting_line_light_colored,
//            unSelectedIcon = R.drawable.setting_line_light,
//            label = "Cài đặt",
//            modifier = Modifier.weight(1f)
//        )
    }
}

/**
 * Tạo tab với tiêu đề [label],
 * khi nhấn vào tab hiện icon [selectedIcon],
 * ngược lại hiện [unSelectedIcon]
 */
@Composable
private fun TabIcon(
    selected: Boolean,
    navigateToAnother: () -> Unit,
    @DrawableRes selectedIcon: Int,
    @DrawableRes unSelectedIcon: Int,
    label: String,
    modifier: Modifier = Modifier
) {
    Tab(
        selected = selected,
        onClick = navigateToAnother,
        text = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall
            )
        },
        icon = {
            Icon(
                painter = if (selected) painterResource(selectedIcon)
                else painterResource(unSelectedIcon),
                contentDescription = label,
                modifier = Modifier.size(28.dp)
            )
        },
        selectedContentColor = MainColor,
        unselectedContentColor = Color.DarkGray,
        modifier = modifier
    )
}

@Composable
fun AddButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        shape = RoundedCornerShape(999.dp),
        containerColor = MainColor,
        elevation = FloatingActionButtonDefaults.elevation(4.dp),
        modifier = modifier.size(56.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Thêm",
            tint = Color.White,
            modifier = Modifier.size(40.dp),
        )
    }
}

/**
 * Text field cho thuoc tinh [label] voi gia tri [value]
 */
@Composable
fun InfoAbout(
    label: String,
    value: String,
    onValueChange: (String) -> Unit = {},
    canEdit: Boolean = false,
    color: Color = Color.Black,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
        )
        OutlinedTextField(
            value = value,
            textStyle = TextStyle(
                fontFamily = Roboto,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = color
            ),
            singleLine = true,
            onValueChange = { onValueChange(it) },
            enabled = canEdit,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .height(52.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun AddInfo(
    onValueChange: (String) -> Unit,
    value: String,
//    bookDetail: BookDetail,
    modifier: Modifier = Modifier,
    label: String,
    canEdit: Boolean = true
) {
    val focusManager = LocalFocusManager.current
//    var text by remember { mutableStateOf("") }
    OutlinedTextField(
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium
            )
        },
        value = value,
        textStyle = TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = Color.Black
        ),
        onValueChange = { onValueChange(it) },
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        enabled = canEdit,
        modifier = modifier.height(60.dp)
    )
}

@Composable
fun AddNumInfo(
    onValueChange: (String) -> Unit,
    value: String,
//    bookDetail: BookDetail,
    modifier: Modifier = Modifier,
    label: String
) {
    val focusManager = LocalFocusManager.current
//    var text by remember { mutableStateOf("") }
    OutlinedTextField(
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium
            )
        },
        value = value,
        textStyle = TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = Color.Black
        ),
        onValueChange = { onValueChange(it) },
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Default),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        ),
        modifier = modifier.height(60.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAppBar(
    navigateBack: () -> Unit,
//    showDialog: Boolean,
    modifier: Modifier = Modifier,
    title: String
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium
            )
        },
        navigationIcon = {
            IconButton(
                onClick = navigateBack
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Trở về"
                )
            }
        },
        modifier = modifier.shadow(4.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldAbout(
    @DrawableRes icon: Int? = null,
    label: String,
    modifier: Modifier = Modifier
) {
    TextField(
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        value = "",
        onValueChange = { },
        shape = RoundedCornerShape(16.dp),
        trailingIcon = {
            if (icon != null) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent, // Tắt đường gạch dưới khi focus
            unfocusedIndicatorColor = Color.Transparent // Tắt đường gạch dưới khi không focus
        ),
        modifier = modifier.padding(top = 8.dp, bottom = 8.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropList(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    items: List<String>,
    modifier: Modifier = Modifier
) {
    var selected by rememberSaveable { mutableStateOf("") }
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            label = {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium
                )
            },
            value = value,
            onValueChange = {},
            trailingIcon = {
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            },
            readOnly = true,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .height(60.dp)
                .menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
//            modifier = Modifier.height(200.dp)
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = item
                        )
                    },
                    onClick = {
//                        selected = item
                        onValueChange(item)
                        isExpanded = false
                    },
                    modifier = Modifier.height(32.dp)
                )
            }
        }
    }
}

@Composable
fun GenderDropList(
    label: String,
    modifier: Modifier = Modifier
) {
    var selectedGender by remember { mutableStateOf(label) }
    var expanded by remember { mutableStateOf(false) }
    val genders = listOf("Nam", "Nữ", "Khác")

    Column(modifier = modifier) {
        Text(
            text = "Giới tính",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
        )

        // Dropdown menu với TextField
        Box {
            OutlinedTextField(
                value = selectedGender,
                onValueChange = { },
                textStyle = TextStyle(
                    fontFamily = Roboto,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clickable { expanded = !expanded }
                    .border(
                        width = 1.dp,
                        color = MoreDarkGray,
                        shape = RoundedCornerShape(10.dp)
                    ),
                enabled = false,
                readOnly = true,
                shape = RoundedCornerShape(10.dp),
                trailingIcon = {
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Dropdown icon"
                    )
                }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                genders.forEach { gender ->
                    DropdownMenuItem(
                        text = { Text(text = gender) },
                        onClick = {
                            selectedGender = gender.toString()
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun ConfirmDialog(
    title: String,
    content: String,
    onDelete: () -> Unit,
    onCancel: () -> Unit,
    cancelLabel: String,
    confirmLabel: String,
    cancelColor: Color,
    confirmColor: Color,
    alpha: Float = 1f,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium
            )
        },
        text = {
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        onDismissRequest = onCancel,
        confirmButton = {
            Button(
                onClick = onDelete,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = confirmColor),
                modifier = Modifier
                    .size(100.dp, 44.dp)
                    .alpha(alpha)
            ) {
                Text(
                    text = confirmLabel,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },
        dismissButton = {
            Button(
                onClick = onCancel,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = cancelColor),
                modifier = Modifier.size(100.dp, 44.dp)
            ) {
                Text(
                    text = cancelLabel,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black
                )
            }
        },
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterByDateBar(
//    day: String,
//    month: String,
//    year: String,
//    updateDate: (String, String, String) -> Unit,
    search: (String, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var day by rememberSaveable { mutableStateOf("") }
    var month by rememberSaveable { mutableStateOf("") }
    var year by rememberSaveable { mutableStateOf("") }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 40.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            value = day,
            onValueChange = {
                day = it
                search(day, month, year)
            },
            placeholder = {
                Text(
                    text = "DD",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedPlaceholderColor = MainColor,
                unfocusedPlaceholderColor = Color.Gray
            ),
            singleLine = true,
            modifier = Modifier.width(72.dp)
        )
        Text(text = "/", style = MaterialTheme.typography.bodyMedium)
        TextField(
            value = month,
            onValueChange = {
                month = it
                search(day, month, year)
            },
            placeholder = {
                Text(
                    text = "MM",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedPlaceholderColor = MainColor,
                unfocusedPlaceholderColor = Color.Gray
            ),
            singleLine = true,
            modifier = Modifier.width(72.dp)
        )
        Text(text = "/", style = MaterialTheme.typography.bodyMedium)
        TextField(
            value = year,
            onValueChange = {
                year = it
                search(day, month, year)
            },
            placeholder = {
                Text(
                    text = "YYYY",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedPlaceholderColor = MainColor,
                unfocusedPlaceholderColor = Color.Gray
            ),
            singleLine = true,
            modifier = Modifier.width(72.dp)
        )
        IconButton(
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Tìm kiếm",
                tint = MainColor
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputDate(
    modifier: Modifier = Modifier,
    placeholder: String,

    ) {
    TextField(
        value = "",
        onValueChange = {},
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedPlaceholderColor = MainColor,
            unfocusedPlaceholderColor = Color.Gray
        ),
        modifier = modifier.width(72.dp)
    )
}

fun Modifier.shadowWithOffset(
    elevation: Dp,
    shape: RoundedCornerShape = RoundedCornerShape(0.dp),
    clip: Boolean = false,
    offsetY: Dp = 0.dp
): Modifier {
    return this
        .shadow(elevation = elevation, shape = shape, clip = clip) // Áp dụng shadow gốc
        .offset(y = offsetY) // Thêm offset Y
}

@Composable
fun BorrowStateBottomBar(
    onSwitch: () -> Unit,
    selectedTab: Int,
    modifier: Modifier = Modifier
) {
//    var selectedTabIndex by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadowWithOffset(elevation = 2.dp, offsetY = (4).dp)
    ) {
        TabRow(
            selectedTabIndex = selectedTab,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = MainColor, // Thay đổi màu sắc cho gạch dưới (indicator)
                )
            },

            ) {
            listOf("Chưa trả", "Đã trả").forEachIndexed { index, state ->
                Tab(
                    selected = selectedTab == index,
                    text = {
                        Text(
                            text = state,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = onSwitch,
                    selectedContentColor = MainColor,
                    unselectedContentColor = Color.DarkGray,
                    modifier = Modifier.height(30.dp)
                )
            }
        }
    }
}

@Composable
fun InfoAboutTable(
    value: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        textStyle = TextStyle(
            fontFamily = Roboto,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black
        ),
        singleLine = true,
        onValueChange = {},
        enabled = false,
        shape = RoundedCornerShape(5.dp),
        modifier = modifier
            .height(52.dp)
            .fillMaxWidth()
    )
}

@Composable
fun ConfirmDelete(
    title: String,
    content: String,
    onDelete: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    ConfirmDialog(
        title = title,
        content = content,
        onDelete = onDelete,
        onCancel = onCancel,
        cancelLabel = "Không",
        confirmLabel = "Xóa",
        cancelColor = Cancel,
        confirmColor = Delete,
        alpha = 0.66f,
        modifier = modifier
    )
}

@Composable
fun ConfirmCancel(
    onDelete: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    ConfirmDialog(
        title = "Hủy thay đổi",
        content = "Các dữ liệu đã hủy sẽ không được lưu. Xác nhận hủy?",
        cancelLabel = "Ở lại",
        confirmLabel = "Hủy",
        cancelColor = Cancel,
        confirmColor = MainColor,
        onDelete = onDelete,
        onCancel = onCancel,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerWithLabel(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Trạng thái hiển thị của DatePickerDialog
    var showDialog by remember { mutableStateOf(false) }

    // Trạng thái lưu trữ ngày đã chọn
    var selectedDate by remember { mutableStateOf<String?>(null) }
    val datePickerState = rememberDatePickerState()

    OutlinedTextField(
        value = value,
        onValueChange = {},
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium
            )
        },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { showDialog = !showDialog }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Select date"
                )
            }
        },
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .height(60.dp)
            .fillMaxWidth()
    )
//    if (showDialog) {
//        Popup(
//            onDismissRequest = { showDialog = false },
//            alignment = Alignment.TopStart
//        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .offset(y = 64.dp)
//                    .shadow(elevation = 4.dp)
//                    .background(MaterialTheme.colorScheme.surface)
//                    .padding(16.dp)
//            ) {
//                DatePicker(
//                    state = datePickerState,
//                    showModeToggle = false
//                )
//            }
//        }
//    }
    // Hiển thị DatePickerDialog nếu `showDialog` là true
    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            selectedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                .format(Date(it))
                        }
                        showDialog = false
                        onValueChange(selectedDate.toString())
                    }
                ) {
                    Text("Xác nhận")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Hủy")
                }
            }
        ) {
            // DatePicker
            DatePicker(
                state = datePickerState,
                showModeToggle = true
            )
        }
    }
}

//@SuppressLint("RestrictedApi")
//@Composable
//fun HomeBottomAppBar(
//    navController: NavHostController,
//    modifier: Modifier = Modifier
//) {
//
////    if(currentDestiantion in listOf(BooksDestination.route, MembersDestination.route, BorrowRequestsDestination.route)) {
//        BottomNavigation(
//            modifier = modifier.shadow(8.dp),
//            backgroundColor = Color.White
//        ) {
//            val navBackStackEntry by navController.currentBackStackEntryAsState()
//            val currentDestination = navBackStackEntry?.destination
//            BottomNavigationItem(
//                selected = currentDestination?.hierarchy?.any { it.hasRoute(
//                    BooksDestination.route,
//                    arguments = TODO()
//                ) } == true,
//                icon = {
//                    Icon(
//                        painter = if(currentDestination?.route == BooksDestination.route) painterResource(R.drawable.book_colored)
//                        else painterResource(R.drawable.book),
//                        contentDescription = "Sách",
//                        modifier = Modifier.size(35.dp)
//                    )
//                },
//                label = {
//                    Text(
//                        text = "Sách",
//                        style = MaterialTheme.typography.bodySmall
//                    )
//                },
//                onClick = {
//                    navController.navigate(BooksDestination.route) {
//                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
//                        launchSingleTop = true
//                        restoreState = true
//                    }
//                },
//                selectedContentColor = MainColor
//            )
//            BottomNavigationItem(
//                selected = currentDestination?.hierarchy?.any { it.hasRoute(
//                    MembersDestination.route,
//                    arguments = TODO()
//                ) } == true,
//                icon = {
//                    Icon(
//                        painter = if(currentDestination?.route == MembersDestination.route) painterResource(R.drawable.group_colored)
//                        else painterResource(R.drawable.group),
//                        contentDescription = "Thành viên",
//                        modifier = Modifier.size(35.dp)
//                    )
//                },
//                label = {
//                    Text(
//                        text = "Thành viên",
//                        style = MaterialTheme.typography.bodySmall
//                    )
//                },
//                onClick = {
//                    navController.navigate(MembersDestination.route) {
//                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
//                        launchSingleTop = true
//                        restoreState = true
//                    }
//                },
//                selectedContentColor = MainColor
//            )
//            BottomNavigationItem(
//                selected = currentDestination?.hierarchy?.any { it.hasRoute(
//                    BorrowRequestsDestination.route,
//                    arguments = TODO()
//                ) } == true,
//                icon = {
//                    Icon(
//                        painter = if(currentDestination?.route == BorrowRequestsDestination.route) painterResource(R.drawable.rent_book_colored)
//                        else painterResource(R.drawable.rent),
//                        contentDescription = "Đơn mượn",
//                        modifier = Modifier.size(35.dp)
//                    )
//                },
//                label = {
//                    Text(
//                        text = "Đơn mượn",
//                        style = MaterialTheme.typography.bodySmall
//                    )
//                },
//                onClick = {
//                    navController.navigate(BorrowRequestsDestination.route) {
//                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
//                        launchSingleTop = true
//                        restoreState = true
//                    }
//                },
//                selectedContentColor = MainColor
//            )
//        }
////    }
//}

//@Composable
//private fun BottomNavItem(
//    @DrawableRes selectedIcon: Int,
//    @DrawableRes unSelectedIcon: Int,
//    label: String,
//    selected: Boolean,
//
//) {
//    BottomNavigationItem(
//        selected = (currentDestiantion == BooksDestination.route),
//        icon = {
//            Icon(
//                painter = painterResource(R.drawable.book),
//                contentDescription = "Sách"
//            )
//        },
//        label = {
//            Text(
//                text = "Sách",
//                style = MaterialTheme.typography.bodySmall
//            )
//        },
//        onClick = {
//            navController.navigate(BooksDestination.route) {
//                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
//                launchSingleTop = true
//                restoreState = true
//            }
//        },
//        selectedContentColor = MainColor
//    )
//}

//
//@Preview(showBackground = true)
//@Composable
//fun ComponentPreview() {
//    DatePickerWithLabel("chon ngay", onValueChange = {}, modifier = Modifier)
//}