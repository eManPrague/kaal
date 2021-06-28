package cz.eman.kaal.domain.mapper

/**
 * An abstract class which should be implemented by a simple mapper with one specific object
 * @author [eMan a.s.](mailto:info@eman.cz)
 * @since 1.0.0
 */
abstract class Mapper<in T, out R> {

    abstract fun mapFrom(from: T): R

}
