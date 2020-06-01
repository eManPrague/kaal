package cz.eman.kaalsample.codebase.infrastructure.room

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

/**
 * This class provides auxiliary functions used by DAO classes.
 *
 * **Be Aware**:
 * This DAO can handle only one concrete database entity [T]
 *
 * @author eMan s.r.o.
 */
interface BaseDao<in T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg entities: T)

    @Update
    fun update(entity: T)

    @Update
    fun update(vararg entities: T)

    @Delete
    fun delete(entity: T)
}
