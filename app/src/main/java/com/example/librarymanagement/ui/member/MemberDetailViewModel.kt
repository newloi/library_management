package com.example.librarymanagement.ui.member

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.librarymanagement.data.member.Member
import com.example.librarymanagement.data.member.MemberRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MemberDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val memberRepository: MemberRepository
) : ViewModel() {
    private val memberId: Int = checkNotNull(savedStateHandle[MemberDetailDestination.memberIdArg])
    val uiState: StateFlow<MemberDetailUiState> =
        memberRepository.getMemberStream(memberId)
            .filterNotNull()
            .map {
                MemberDetailUiState(currentMember = it)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = MemberDetailUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    suspend fun deleteMember() {
        memberRepository.deleteMember(uiState.value.currentMember)
    }
}

data class MemberDetailUiState(
    val currentMember: Member = Member(
        id = 0,
        name = "",
        gender = "",
        dateOfBirth = "",
        address = "",
        registrationDate = "",
    )
)