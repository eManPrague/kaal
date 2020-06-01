package cz.eman.kaalsample.feature.login.infrastructure.db.dao

import androidx.room.Dao
import androidx.room.Query
import cz.eman.kaalsample.feature.login.infrastructure.db.entity.UserEntity
import cz.eman.kaalsample.codebase.infrastructure.room.BaseDao

/**
 * @author eMan a.s.
 * @see[BaseDao]
 */
@Dao
interface UserDao : BaseDao<UserEntity> {

    @Query("SELECT * FROM user WHERE username = :username and password = :password")
    suspend fun findUser(username: String, password: String): UserEntity?

    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun selectUserByUsername(username: String): UserEntity?

}
