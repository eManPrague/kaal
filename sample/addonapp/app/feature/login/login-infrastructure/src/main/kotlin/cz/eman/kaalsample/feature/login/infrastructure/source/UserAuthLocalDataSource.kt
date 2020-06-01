package cz.eman.kaalsample.feature.login.infrastructure.source

import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.feature.login.domain.error.UserAuthErrorCode
import cz.eman.kaal.feature.login.domain.error.UserAuthErrorResult
import cz.eman.kaal.feature.login.domain.model.User
import cz.eman.kaalsample.feature.login.infrastructure.db.dao.UserDao
import cz.eman.kaalsample.feature.login.infrastructure.db.entity.UserEntity
import cz.eman.kaalsample.feature.login.infrastructure.mapper.UserMapper
import cz.kaal.sample.feature.login.data.source.UserAuthDataSource

/**
 *
 * @author eMan a.s.
 * @see[UserAuthDataSource]
 */
class UserAuthLocalDataSource(private val userDao: UserDao) : UserAuthDataSource {

    override suspend fun authorizeUser(user: User): Result<User> {
        val entity = userDao.findUser(username = user.username, password = user.password)
        return if (entity != null) {
            Result.success(data = UserMapper.mapUserEntityToUser(entity))
        } else {
            Result.error(
                error = UserAuthErrorResult(
                    code = UserAuthErrorCode.INVALID_USER_CREDENTIALS,
                    message = "Invalid username or password"
                )
            )
        }
    }

    override suspend fun registerUser(user: User): Result<User> {
        val entity = userDao.selectUserByUsername(user.username)
        return if (entity != null) {
            Result.error(
                error = UserAuthErrorResult(
                    code = UserAuthErrorCode.USER_ALREADY_EXIST,
                    message = "User already exist"
                )
            )
        } else {
            val userEntity = UserEntity(
                user.username,
                user.password
            )
            userDao.insert(userEntity)
            Result.Success(data = UserMapper.mapUserEntityToUser(userEntity))
        }
    }
}
