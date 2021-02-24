package cz.eman.kaal.domain.action.model

/**
 *
 *  @author Roman Holomek <roman.holomek@gmail.com>
 */
sealed class Action(open val name: ActionName) {

    data class SimpleAction(
        override val name: ActionName
    ) : Action(name = name)

    data class DataAction(
        override val name: ActionName,
        val data: ActionData
    ) : Action(name = name)
}
