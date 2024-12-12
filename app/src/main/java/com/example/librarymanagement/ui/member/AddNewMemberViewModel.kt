package com.example.librarymanagement.ui.member

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.librarymanagement.data.member.Member
import com.example.librarymanagement.data.member.MemberRepository

class AddNewMemberViewModel(private val memberRepository: MemberRepository) : ViewModel() {
    var memberUiState by mutableStateOf(MemberUiState())
        private set

    fun updateUiState(memberDetail: MemberDetail) {
        memberUiState = MemberUiState(memberDetail = memberDetail, isMemberValid = validateInput(memberDetail))
    }

    private fun validateInput(uiState: MemberDetail = memberUiState.memberDetail): Boolean {
        return with(uiState) {
            name.isNotBlank() && gender.isNotBlank() &&
                    dateOfBirth.isNotBlank() && address.isNotBlank() &&
                    registrationDate.isNotBlank()
        }
    }

    suspend fun saveMember(){
        if(validateInput()) {
            memberRepository.insertMember(memberUiState.memberDetail.toMember())
        }
    }

    fun showDialog() {
        memberUiState = memberUiState.copy(isShowDialog = !memberUiState.isShowDialog)
    }
}

data class MemberDetail(
    val id: Int = 0,
    val name: String = "",
    val gender: String = "",
    val dateOfBirth: String = "",
    val address: String = "",
    val registrationDate: String = ""
)

data class MemberUiState(
    val isMemberValid: Boolean = false,
    val memberDetail: MemberDetail = MemberDetail(),
    val isShowDialog: Boolean = false
)

fun MemberDetail.toMember(): Member = Member(
    id = id,
    name = name,
    gender = gender,
    dateOfBirth = dateOfBirth,
    address = address,
    registrationDate = registrationDate
)

fun Member.toMemberDetail(): MemberDetail = MemberDetail(
    id = id,
    name = name,
    gender = gender,
    dateOfBirth = dateOfBirth,
    address = address,
    registrationDate = registrationDate
)

fun Member.toMemberUiState(isMemberValid: Boolean = false): MemberUiState = MemberUiState(
    memberDetail = this.toMemberDetail(),
    isMemberValid = isMemberValid
)