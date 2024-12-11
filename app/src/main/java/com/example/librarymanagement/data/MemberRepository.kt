package com.example.librarymanagement.data

import kotlinx.coroutines.flow.Flow

class MemberRepository(private val memberDao: MemberDao) {
    suspend fun insertMember(member: Member) = memberDao.insert(member)

    suspend fun updateMember(member: Member) = memberDao.update(member)

    suspend fun deleteMember(member: Member) = memberDao.delete(member)

    fun getAllMembersStream(): Flow<List<Member>> = memberDao.getAllMember()

    fun getMemberStream(id: Int): Flow<Member> = memberDao.getMember(id)

    fun searchMembersStream(searchText: String): Flow<List<Member>> = memberDao.searchMembers(searchText)
}