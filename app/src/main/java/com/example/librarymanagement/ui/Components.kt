package com.example.librarymanagement.ui

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.librarymanagement.R
import com.example.librarymanagement.ui.book.BooksDestination
import com.example.librarymanagement.ui.borrow.BorrowRequestsDestination
import com.example.librarymanagement.ui.member.MembersDestination
import com.example.librarymanagement.ui.theme.Delete
import com.example.librarymanagement.ui.theme.MainColor
import com.example.librarymanagement.ui.theme.Roboto
import kotlinx.coroutines.selects.select

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    search: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
        var searchText by remember{ mutableStateOf("") }
        var active by remember { mutableStateOf(false) }

        SearchBar(
            query = searchText,
            onQueryChange = { searchText = it }, //Cap nhat noi dung tim kiem
            onSearch = { search(searchText) },
            active = active,
            onActiveChange = { active = it },
            placeholder = { Text(text = placeholder) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Tìm kiếm"
                )
            },
            trailingIcon = {
                if(active) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Hủy tìm kiếm",
                        modifier = Modifier.clickable {
                            if(searchText.isNotEmpty()) {
                                searchText = ""
                            } else active = false
                        }
                    )
                }
            },
            modifier = modifier
        ) {
            //Lich su tim kiem
        }
}

@Composable
fun FilterBar(modifier: Modifier = Modifier) {
    var isIncreasing by remember { mutableStateOf(true) }
    var isExpanded by remember { mutableStateOf(false) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = { isIncreasing = !isIncreasing },
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
            onClick = {isExpanded = true},
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
                        /* Sap xep theo the loai */
                    },
                    text = {
                        Text(
                            text = "Xếp theo thể loại",
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    modifier = Modifier.height(40.dp)
                )
                Divider(modifier = Modifier
                    .fillMaxWidth()
                    .height(0.5.dp))
                DropdownMenuItem(
                    onClick = {
                        isExpanded = false
                        /* Sap xep theo so luong */
                    },
                    text = {
                        Text(
                            text = "Xếp theo số lượng",
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
    navigateToEdit: () -> Unit,
    navigateBack: () -> Unit,
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
                Divider(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp))
                DropdownMenuItem(
                    onClick = {
                        isExpanded = false
                        /* Xoa */
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
            .height(84.dp),
        verticalAlignment = Alignment.Bottom
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
        TabIcon(
            selected = (currentTabIndex == 3),
            navigateToAnother = navigateToSettingScreen,
            selectedIcon = R.drawable.setting_line_light_colored,
            unSelectedIcon = R.drawable.setting_line_light,
            label = "Cài đặt",
            modifier = Modifier.weight(1f)
        )
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
                painter = if(selected) painterResource(selectedIcon)
                else painterResource(unSelectedIcon),
                contentDescription = label,
                modifier = Modifier.size(35.dp)
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
    canEdit: Boolean = false,
    modifier:Modifier = Modifier
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
                color = Color.Black
            ),
            singleLine = true,
            onValueChange = {},
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
    modifier: Modifier = Modifier,
    label: String
) {
    val focusManager = LocalFocusManager.current
    var text by remember { mutableStateOf("") }
    OutlinedTextField(
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium
            )
        },
        value = text,
        textStyle = TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = Color.Black
        ),
        onValueChange = {newText -> text = newText},
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
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
    modifier: Modifier = Modifier) {
    TextField(
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        value = "",
        onValueChange = {  },
        shape= RoundedCornerShape(16.dp),
        trailingIcon = {
            if(icon != null){
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
        modifier = modifier.padding(top= 8.dp, bottom = 8.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropList(
    label: String,
    items: List<Any>,
    modifier: Modifier = Modifier
) {
    var selected by remember { mutableStateOf("") }
    var isExpanded by remember { mutableStateOf(false) }
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
            value = selected,
            onValueChange = {},
            trailingIcon = {
                Icon(
                    imageVector = if(isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
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
            modifier = Modifier.height(200.dp)
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = item.toString()
                        )
                    },
                    onClick = {
                        selected = item.toString()
                        isExpanded = false
                    },
                    modifier = Modifier.height(32.dp)
                    )
            }
        }
    }
}

@Composable
fun ConfirmDialog(
    title: String,
    content: String,
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
        onDismissRequest = {  },
        confirmButton = {
            Button(
                onClick = { },
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
                onClick = { },
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

@Composable
fun FilterByDateBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 40.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        InputDate(placeholder = "Ngày")
        Text(text = "/", style = MaterialTheme.typography.bodyMedium)
        InputDate(placeholder = "Tháng")
        Text(text = "/", style = MaterialTheme.typography.bodyMedium)
        InputDate(placeholder = "Năm")
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
                style = MaterialTheme.typography.bodyMedium
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedPlaceholderColor = MainColor,
            unfocusedPlaceholderColor = Color.Gray
        ),
        modifier = modifier.width(75.dp)
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
    modifier: Modifier = Modifier
) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadowWithOffset(elevation = 2.dp, offsetY = (4).dp)
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = MainColor, // Thay đổi màu sắc cho gạch dưới (indicator)
                )
            },

            ) {
            listOf("Chưa trả", "Đã trả").forEachIndexed { index, state ->
                Tab(
                    selected = true,
                    text = {
                        Text(
                            text = state,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {},
                    selectedContentColor = MainColor,
                    modifier = Modifier.height(30.dp)
                )
            }
        }
    }
}

@Composable
fun InfoAboutTable(
    value: String,
    modifier:Modifier = Modifier
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


//@Preview(showBackground = true)
//@Composable
//fun ComponentPreview() {
//    HomeBottomAppBar()
//}