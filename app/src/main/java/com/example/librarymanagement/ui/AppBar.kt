package com.example.librarymanagement.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.librarymanagement.R
import com.example.librarymanagement.ui.theme.Delete
import com.example.librarymanagement.ui.theme.LibraryManagementTheme
import com.example.librarymanagement.ui.theme.MainColor
import kotlinx.coroutines.selects.select

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAndFilterTopAppBar(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        var searchText by remember{ mutableStateOf("") }
        var active by remember { mutableStateOf(false) }

        SearchBar(
            query = searchText,
            onQueryChange = { searchText = it }, //Cap nhat noi dung tim kiem
            onSearch = { /* Xu ly tim kiem */ },
            active = active,
            onActiveChange = { active = it },
            placeholder = { Text(text = "Tìm kiếm...") },
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
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            //Lich su tim kiem
        }
        HorizontalDivider(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp))
        FilterBar()
    }
}

@Composable
private fun FilterBar(modifier: Modifier = Modifier) {
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
                HorizontalDivider(modifier = Modifier
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

@Preview(showBackground = true)
@Composable
fun SearchAndFilterTopAppBarPreview() {
    LibraryManagementTheme {
        SearchAndFilterTopAppBar()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Name Screen",
                style = MaterialTheme.typography.headlineMedium
            )
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Trở về"
            )
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
                        /* Sua thong tin */
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
                HorizontalDivider(modifier = Modifier
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

@Preview(showBackground = true)
@Composable
fun InfoAppBarPreview() {
    LibraryManagementTheme {
        Scaffold(
            topBar = { SearchAndFilterTopAppBar(modifier = Modifier.shadow(1.dp)) },
            bottomBar = { HomeBottomAppBar() }
        
        ) { innerPadding ->
            Text(
                text = "newloi",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun HomeBottomAppBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth().shadow(1.dp).padding(top = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TabIcon(
            selectedIcon = R.drawable.book_colored,
            unSelectedIcon = R.drawable.book,
            label = "Sách",
            modifier = Modifier.weight(1f)
        )
        TabIcon(
            selectedIcon = R.drawable.group_colored,
            unSelectedIcon = R.drawable.group,
            label = "Thành viên",
            modifier = Modifier.weight(1f)
        )
        TabIcon(
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
    @DrawableRes selectedIcon: Int,
    @DrawableRes unSelectedIcon: Int,
    label: String,
    modifier: Modifier = Modifier
) {
    Tab(
        selected = true,
        onClick = { },
        text = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall
            )
        },
        icon = {
            Icon(
                painter = if(true) painterResource(selectedIcon)
                else painterResource(unSelectedIcon),
                contentDescription = label,
                modifier = Modifier.size(40.dp)
            )
        },
        selectedContentColor = MainColor,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun BottomAppBarPreview() {
    LibraryManagementTheme {
        HomeBottomAppBar()
    }
}