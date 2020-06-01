package cz.eman.kaalsample.feature.login.infrastructure.mapper

import cz.eman.kaal.feature.login.domain.model.User
import cz.eman.kaalsample.feature.login.infrastructure.db.entity.UserEntity

/**
 * @author eMan a.s.
 */
object UserMapper {

    fun mapUserEntityToUser(entity: UserEntity) =
        User(username = entity.username, password = entity.password)
}
