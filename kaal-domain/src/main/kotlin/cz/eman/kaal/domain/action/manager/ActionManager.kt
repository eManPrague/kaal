package cz.eman.kaal.domain.action.manager

import cz.eman.kaal.domain.action.resolver.ActionResolver

/**
 *
 *  @author Roman Holomek <roman.holomek@gmail.com>
 */
interface ActionManager : ActionProcessManager {

    /**
     * Will register [ActionResolver]
     */
    fun registerActionResolver(resolver: ActionResolver)
}
