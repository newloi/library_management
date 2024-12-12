package com.example.librarymanagement.ui.member

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.librarymanagement.data.member.MemberRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MemberEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val memberRepository: MemberRepository
) : ViewModel() {
    var memberUiState by mutableStateOf(MemberUiState())
        private set

    private val memberId: Int = checkNotNull(savedStateHandle[MemberDetailDestination.memberIdArg])

    private fun validateInput(uiState: MemberDetail = memberUiState.memberDetail): Boolean {
        return with(uiState) {
            name.isNotBlank() && gender.isNotBlank() &&
                    dateOfBirth.isNotBlank() && address.isNotBlank() &&
                    registrationDate.isNotBlank()
        }
    }

    init {
        viewModelScope.launch {
            memberUiState = memberRepository.getMemberStream(memberId)
                .filterNotNull()
                .first()
                .toMemberUiState(true)
        }
    }

    fun updateUiState(memberDetail: MemberDetail) {
        memberUiState = MemberUiState(memberDetail = memberDetail, isMemberValid = validateInput(memberDetail))
    }

    suspend fun updateMember() {
        if(validateInput(memberUiState.memberDetail)) {
            memberRepository.updateMember(memberUiState.memberDetail.toMember())
        }
    }

    fun showDialog() {
        memberUiState = memberUiState.copy(isShowDialog = !memberUiState.isShowDialog)
    }
}