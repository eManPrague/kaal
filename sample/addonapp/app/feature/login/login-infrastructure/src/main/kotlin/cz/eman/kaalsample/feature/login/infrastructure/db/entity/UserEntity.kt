package cz.eman.kaalsample.feature.login.infrastructure.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author @author eMan a.s.
 */
@Entity(tableName = "user")
data class UserEntity(

    @PrimaryKey
    val username: String,
    val password: String
)
