package com.example.librarymanagement.ui.borrow

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.librarymanagement.R
import com.example.librarymanagement.data.borrow.BorrowRequestDetail
import com.example.librarymanagement.ui.InfoAbout
import com.example.librarymanagement.ui.InfoAboutTable
import com.example.librarymanagement.ui.InfoAppBar
import com.example.librarymanagement.ui.navigation.NavigationDestination

object BorrowRequestDetailDestination : NavigationDestination {
    override val route = "borrow_request_detail"
}

@Composable
fun BorrowRequestDetailScreen(
    navigateToEditBorrowRequest: () -> Unit,
    navigateBack: () -> Unit,
    borrowRequest : BorrowRequestDetail = BorrowRequestDetail(
        borrowId = 1,
        listBookIds = "",
        listBooks = "Quyen nay de test",
        memberName = "LNL",
        memberId = 1,
        borrowDate = "01/01/2023",
        exceptDate = "01/01/2023",
        returnDate = "01/01/2024",
        state = true
    )
) {
    Scaffold(
       topBar = {
           InfoAppBar(
               navigateToEdit = navigateToEditBorrowRequest,
               navigateBack = navigateBack,
               onDelete = {},
               title = stringResource( R.string.don_muon, borrowRequest.borrowId)
           )
       }
    ) { innerPadding ->
        BorrowRequestDetail(borrowRequest = borrowRequest, modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun BorrowRequestDetail(borrowRequest: BorrowRequestDetail, modifier: Modifier = Modifier ){
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
            value = borrowRequest.memberName,
            modifier= Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        )
       Row(modifier = Modifier.padding(bottom=10.dp)){
           InfoAbout(
               label = "Mã người mượn",
               value = borrowRequest.borrowId.toString(),
               modifier=Modifier.weight(1f)
           )

           Spacer(modifier= Modifier.width(40.dp))
           InfoAbout(
               label = "Số lượng sách mượn",
               value = "borrowRequest.bookCount.toString()",
               modifier=Modifier.weight(1f)
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
                value = borrowRequest.state.toString(),
                modifier=Modifier.weight(1f)
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

        BorrowRequestList(listOf())
    }

}
@Composable
fun BorrowRequestList(borrowRequests: List<BorrowRequestDetail>) {
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
                modifier=Modifier.weight(1f).border(width = 0.2.dp, color = Black)
            )

            InfoAboutTable(
                value = "Ten sach",
                modifier=Modifier.weight(2f).border(width = 0.2.dp, color = Black)
            )



        }

        // Danh sách dữ liệu
        LazyColumn {
            items(borrowRequests) { borrowRequest ->
                BookRow(borrowRequest)
            }
        }
    }
}

@Composable
fun BookRow(borrowRequest: BorrowRequestDetail) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, color = Color.Black),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        InfoAboutTable(
            value = borrowRequest.borrowId.toString(),
            modifier=Modifier.weight(1f).border(width = 0.2.dp, color = Black)
        )

        InfoAboutTable(
            value = borrowRequest.listBooks,
            modifier=Modifier.weight(2f).border(width = 0.2.dp, color = Black)
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

