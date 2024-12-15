package com.example.librarymanagement.data.member

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import java.io.File

class MemberRepository(private val memberDao: MemberDao) {
    suspend fun insertMember(member: Member) = memberDao.insert(member)

    suspend fun updateMember(member: Member) = memberDao.update(member)

    suspend fun deleteMember(member: Member) {
        member.imageUri?.let { uriString ->
            val uri = Uri.parse(uriString)
            if (uri.scheme == "file") { // Kiểm tra xem URI có trỏ tới file không
                val file = File(uri.path ?: "")
                if (file.exists()) {
                    file.delete() // Xóa file
                }
            }
        }
        memberDao.delete(member)
    }

    fun getAllMembersStream(): Flow<List<Member>> = memberDao.getAllMember()

    fun getMemberStream(id: Int): Flow<Member> = memberDao.getMember(id)

    fun searchMembersStream(searchText: String): Flow<List<Member>> = memberDao.searchMembers(searchText)
}