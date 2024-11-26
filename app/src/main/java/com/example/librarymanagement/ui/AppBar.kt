package com.example.librarymanagement.ui

import android.graphics.Paint.Align
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.librarymanagement.R
import com.example.librarymanagement.ui.theme.Delete
import com.example.librarymanagement.ui.theme.LibraryManagementTheme
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
                        modifier = modifier.clickable {
                            if(searchText.isNotEmpty()) {
                                searchText = ""
                            } else active = false
                        }
                    )
                }
            },
            modifier = modifier.align(Alignment.CenterHorizontally)
        ) {
            //Lich su tim kiem
        }
        HorizontalDivider(modifier = modifier
            .fillMaxWidth()
            .padding(top = 12.dp))
        FilterBar()
    }
}

enum class OptionsFilter() {
    INCREASING, DECREASING
}

@Composable
private fun FilterBar(modifier: Modifier = Modifier) {
    var isIncreasing by remember { mutableStateOf(true) }
    var isExpanded by remember { mutableStateOf(false) }
    Row(
        modifier = modifier.fillMaxWidth().padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = { isIncreasing = !isIncreasing },
            modifier = modifier.width(50.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.filter_options),
                    contentDescription = null,
                    modifier = modifier.size(24.dp).padding(end = 4.dp)
                )
                Text(
                    text = if (isIncreasing) "A-Z" else "Z-A",
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
        IconButton(
            onClick = {isExpanded = true},
            modifier = modifier.padding(end = 12.dp)
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
                    modifier = modifier.height(40.dp)
                )
                HorizontalDivider(modifier = modifier
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
                    modifier = modifier.height(40.dp)
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
                            modifier = modifier.padding(start = 20.dp)
                        )
                    },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.edit),
                            contentDescription = "Sửa thông tin"
                        )
                    },
                    modifier = modifier.height(40.dp)
                )
                HorizontalDivider(modifier = modifier
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
                            modifier = modifier.padding(start = 20.dp)
                        )
                    },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.delete),
                            contentDescription = "Xóa",
                            tint = Delete
                        )
                    },
                    modifier = modifier.height(40.dp)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun InfoAppBarPreview() {
    LibraryManagementTheme {
        Scaffold(topBar = { InfoAppBar() }) { innerPadding ->
            Text(
                text = "newloi",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun BottomAppBar(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Tab(
            selected = true,
            onClick = { },
            text = {
                Text(text = "Sách")
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.book_duotone_line),
                    contentDescription = "Sách"
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomAppBarPreview() {
    LibraryManagementTheme {
        BottomAppBar()
    }
}