package com.example.librarymanagement.ui.member

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.librarymanagement.data.Member
import com.example.librarymanagement.data.MemberRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MembersScreenViewModel(private val memberRepository: MemberRepository) : ViewModel() {
    // StateFlow chứa trạng thái của UI
    private val _membersScreenUiState = MutableStateFlow(MembersScreenUiState())
    val membersScreenUiState: StateFlow<MembersScreenUiState> = _membersScreenUiState

    private var currentSearchJob: Job? = null // Để quản lý tìm kiếm
//    private var isSortIncreasing: Boolean = true


//    companion object {
//        private const val TIMEOUT_MILLIS = 5_000L
//    }

    init {
        // Mặc định hiển thị toàn bộ sách
        loadAllMembers()
    }

    private fun loadAllMembers() {
        viewModelScope.launch {
            memberRepository.getAllMembersStream()
                .map { members ->
                    val currentMembers = members
                    MembersScreenUiState(
                        members = if(_membersScreenUiState.value.isSortIncreasing) {
                            currentMembers.sortedBy { it.name }
                        } else {
                            currentMembers.sortedByDescending { it.name }
                        },
                        isSortIncreasing = _membersScreenUiState.value.isSortIncreasing,
                        sortBy = _membersScreenUiState.value.sortBy
                    )
                }
                .collect {
                    _membersScreenUiState.value = it
                }
        }
    }

    fun searchMembers(searchText: String) {
        currentSearchJob?.cancel() // Hủy job tìm kiếm trước đó (nếu có)
        currentSearchJob = viewModelScope.launch {
            if (searchText.isBlank()) {
                // Nếu không nhập từ khóa, hiển thị toàn bộ sách
                loadAllMembers()
            } else {
                memberRepository.searchMembersStream(searchText)
                    .map {members ->
                        val currentMembers = members
                        MembersScreenUiState(
                            members = if(_membersScreenUiState.value.isSortIncreasing) {
                                currentMembers.sortedBy { it.name }
                            } else {
                                currentMembers.sortedByDescending { it.name }
                            },
                            isSortIncreasing = _membersScreenUiState.value.isSortIncreasing,
                            sortBy = _membersScreenUiState.value.sortBy
                        )
                    }
                    .collect {
                        _membersScreenUiState.value = it
                    }
            }
        }
    }

    fun toggleSortOrder() {
//        isSortIncreasing = !isSortIncreasing
//        loadAllmembers()
        _membersScreenUiState.update { currentState ->
            currentState.copy(
                isSortIncreasing = !currentState.isSortIncreasing
            )
        }
    }

    fun setSortBy(sortBy: Int) {
        _membersScreenUiState.update { currentState ->
            currentState.copy(
                sortBy = sortBy
            )
        }
    }

    suspend fun deleteMember(member: Member) {
        memberRepository.deleteMember(member)
        // Làm mới danh sách sau khi xóa
        searchMembers("") // Hiển thị lại toàn bộ sách
    }
}

data class MembersScreenUiState(
    val members: List<Member> = listOf(),
    val isSortIncreasing: Boolean = true,
    val sortBy: Int = 0
)