package com.example.librarymanagement.ui.borrow

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.librarymanagement.R
import com.example.librarymanagement.data.book.Book
import com.example.librarymanagement.data.borrow.BorrowRequest
import com.example.librarymanagement.data.borrow.BorrowRequestDetail
import com.example.librarymanagement.ui.AppViewModelProvider
import com.example.librarymanagement.ui.ConfirmDelete
import com.example.librarymanagement.ui.InfoAbout
import com.example.librarymanagement.ui.InfoAboutTable
import com.example.librarymanagement.ui.InfoAppBar
import com.example.librarymanagement.ui.book.BookDetailDestination
import com.example.librarymanagement.ui.book.BookDetailDestination.bookIdArg
import com.example.librarymanagement.ui.navigation.NavigationDestination
import com.example.librarymanagement.ui.theme.Delete
import com.example.librarymanagement.ui.theme.MainColor
import kotlinx.coroutines.launch

object BorrowRequestDetailDestination : NavigationDestination {
    override val route = "borrow_request_detail"
    const val borrowRequestIdArg = "borrowRequestId"
    val routeWithArgs = "${route}/{$borrowRequestIdArg}"
}

@Composable
fun BorrowRequestDetailScreen(
    navigateToEditBorrowRequest: (Int) -> Unit,
    navigateBack: () -> Unit,
    navigateDone: () -> Unit,
    viewModel: BorrowRequestDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
//    borrowRequest : BorrowRequestDetail = BorrowRequestDetail(
//        borrowId = 1,
//        listBookIds = "",
//        listBooks = "Quyen nay de test",
//        memberName = "LNL",
//        memberId = 1,
//        borrowDate = "01/01/2023",
//        exceptDate = "01/01/2023",
//        returnDate = "01/01/2024",
//        state = true
//    )
) {
    val uiState by viewModel.uiState.collectAsState()
    var isShowDialog by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
       topBar = {
           InfoAppBar(
//               navigateToEdit = { navigateToEditBorrowRequest(uiState.currentBorrowRequest.id) },
               navigateBack = navigateBack,
               isBorrow = true,
               isReturned = uiState.currentBorrowRequest.state,
               markReturned = { viewModel.markReturned(uiState.currentBorrowRequest.id) },
               onDelete = { isShowDialog = true },
               title = stringResource( R.string.don_muon, uiState.currentBorrowRequest.id)
           )
       }
    ) { innerPadding ->
        BorrowRequestDetail(
            listBookIds = uiState.listBooks,
            getMemberName = {
                viewModel.getMemberName(it)
            },
            getBookName = { bookId ->
                viewModel.getBookName(bookId)
            },
            borrowRequest = uiState.currentBorrowRequest,
            modifier = Modifier.padding(innerPadding)
        )
        if(isShowDialog) {
            ConfirmDelete(
                title = "Xóa đơn mượn",
                content = stringResource(R.string.delete_borrow_warning, uiState.currentBorrowRequest.id),
                onDelete = {
                    coroutineScope.launch {
                        viewModel.deleteBorrowRequest()
                        isShowDialog = false
                        navigateDone()
                    }
                },
                onCancel = { isShowDialog = false }
            )
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun BorrowRequestDetail(
    listBookIds: List<Int>,
    getMemberName: suspend (Int) -> String,
    getBookName: suspend (Int) -> String,
    borrowRequest: BorrowRequest,
    modifier: Modifier = Modifier
){
    var memberName by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(borrowRequest.memberId) {
        memberName = getMemberName(borrowRequest.memberId)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 30.dp, end = 30.dp)
    ) {
        Text(
            text="Thông tin đơn",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 20.dp)
        )

        InfoAbout(
            label = "Họ và tên người mượn",
            value = memberName,
            modifier= Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        )
       Row(modifier = Modifier.padding(bottom=10.dp)){
           InfoAbout(
               label = "Mã người mượn",
               value = String.format("%06d", borrowRequest.id),
               modifier=Modifier.weight(1f)
           )

           Spacer(modifier= Modifier.width(40.dp))
           InfoAbout(
               label = "Số lượng sách mượn",
               value = borrowRequest.bookCount.toString(),
               modifier = Modifier.weight(1f)
           )
       }
        Row(modifier = Modifier.padding(bottom=10.dp)){
            InfoAbout(
                label = "Ngày mượn",
                value = borrowRequest.borrowDate,
                modifier=Modifier.weight(1f)
            )

            Spacer(modifier= Modifier.width(40.dp))
            InfoAbout(
                label = "Ngày trả dự kiến",
                value = borrowRequest.exceptDate,
                modifier=Modifier.weight(1f)
            )
        }

        Row(modifier = Modifier.padding(bottom=30.dp)){
            borrowRequest.returnDate?.let {
                InfoAbout(
                    label = "Ngày trả",
                    value = it,
                    modifier=Modifier.weight(1f)
                )
            }

            Spacer(modifier= Modifier.width(40.dp))
            InfoAbout(
                label = "Trạng thái",
                value = if(borrowRequest.state) "Đã trả" else "Chưa trả",
                color = if(borrowRequest.state) MainColor else Delete,
                modifier = Modifier.weight(1f)
            )
        }

        Text(
            text= "Thông tin sách mượn",
            style= MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Start)
        )

//        val borrowRequests = listOf(
////            BorrowRequest(100000,"Tai","Dac nhan tam"," 25/01/2004",5," 25/06/2004"," 25/07/2004","Đã trả"),
////            BorrowRequest(100001,"Tai","Boku no pico"," 25/01/2004",5," 25/06/2004"," 25/07/2004","Đã trả"),
////            BorrowRequest(100002,"Tai","Yamete kudasai"," 25/01/2004",5," 25/06/2004"," 25/07/2004","Đã trả")
//        )

        BorrowRequestList(
            getBookName = { bookId ->
                getBookName(bookId)
            },
            bookIds = listBookIds
        )
    }

}
@SuppressLint("DefaultLocale")
@Composable
fun BorrowRequestList(
    getBookName: suspend (Int) -> String,
    bookIds: List<Int>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Dòng tiêu đề của bảng
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .border(1.dp, color = Color.Black),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoAboutTable(
                value = "ID",
                modifier= Modifier
                    .weight(1f)
                    .border(width = 0.2.dp, color = Black)
            )

            InfoAboutTable(
                value = "Tên sách",
                modifier= Modifier
                    .weight(2.5f)
                    .border(width = 0.2.dp, color = Black)
            )



        }

        // Danh sách dữ liệu
        LazyColumn {
            items(bookIds) { id ->
                var bookName by rememberSaveable { mutableStateOf("") }
                LaunchedEffect(id) {
                    bookName = getBookName(id)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, color = Color.Black),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    InfoAboutTable(
                        value = String.format("%06d", id),
                        modifier= Modifier
                            .weight(1f)
                            .border(width = 0.2.dp, color = Black)
                    )

                    InfoAboutTable(
                        value = bookName,
                        modifier= Modifier
                            .weight(2.5f)
                            .border(width = 0.2.dp, color = Black)
                    )
                }
            }
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun BookRow(book: Book) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, color = Color.Black),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        InfoAboutTable(
            value = String.format("%6d", book.id),
            modifier= Modifier
                .weight(1f)
                .border(width = 0.2.dp, color = Black)
        )

        InfoAboutTable(
            value = book.name,
            modifier= Modifier
                .weight(2f)
                .border(width = 0.2.dp, color = Black)
        )
    }
}


//@Preview(showBackground = true)
//@Composable
//fun BorrowRequestDetailPreview(){
//    BorrowRequestDetailScreen(
//        navigateBack = {},
//        navigateToEditBorrowRequest = {},
//        borrowRequest = BorrowRequestDetail(2,"Tai","Dac nhan tam"," 25/01/2004",5," 25/06/2004"," 25/07/2004","Đã trả")
//    )
//}

