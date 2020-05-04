package cz.eman.kaal.domain.mapper

/**
 * An abstract class which should be implemented by a simple mapper with one specific object
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 * @since 0.2.0
 */
abstract class Mapper<in T, out R> {

    abstract fun mapFrom(from: T): R

}
