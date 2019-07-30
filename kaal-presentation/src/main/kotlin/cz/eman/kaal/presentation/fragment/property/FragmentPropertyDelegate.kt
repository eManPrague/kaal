package cz.eman.kaal.presentation.fragment.property

import android.os.Binder
import android.os.Bundle
import android.os.Parcelable
import androidx.core.app.BundleCompat
import androidx.fragment.app.Fragment
import java.io.Serializable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Easier way how to handle fragment's arguments [Bundle] by using this delegate
 *
 * Inspired by Adam Powell and his talk on Google I/O 2017.
 *
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 * @see [kotlin.properties.ReadWriteProperty]
 * @see [Fragment]
 * @since 0.2.0
 */
class FragmentPropertyDelegate<T : Any> : ReadWriteProperty<Fragment, T> {

    override operator fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val args = thisRef.arguments
            ?: throw IllegalStateException("Cannot read property ${property.name} because there are no arguments!!!")
        @Suppress("UNCHECKED_CAST")
        val value = args.get(property.name) as T?
        return value ?: throw IllegalStateException("Property [${property.name}] could not be read")
    }

    override operator fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        if (thisRef.arguments == null) {
            thisRef.arguments = Bundle()
        }

        val arguments = thisRef.arguments
        arguments?.let { arg ->
            val key = property.name
            when (value) {
                is String -> arg.putString(key, value)
                is Int -> arg.putInt(key, value)
                is Short -> arg.putShort(key, value)
                is Long -> arg.putLong(key, value)
                is Float -> arg.putFloat(key, value)
                is Char -> arg.putChar(key, value)
                is CharArray -> arg.putCharArray(key, value)
                is CharSequence -> arg.putCharSequence(key, value)
                is Byte -> arg.putByte(key, value)
                is ByteArray -> arg.putByteArray(key, value)
                is Bundle -> arg.putBundle(key, value)
                is Binder -> BundleCompat.putBinder(arg, key, value)
                is Parcelable -> arg.putParcelable(key, value)
                is Serializable -> arg.putSerializable(key, value)
                else -> throw IllegalStateException("Unsupported type [${value.javaClass.canonicalName}] of property [${property.name}]!")
            }
        }
    }
}
