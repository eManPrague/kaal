package cz.eman.kaal.domain.addon.manifest.entrypoint

/**
 * @author eMan s.r.o.
 */
enum class CustomEntryPointParamKey(override var keyName: String) : EntryPointParamKey {

    CUSTOM_PARAM_1("param1"),
    CUSTOM_PARAM_2("param2"),
    CUSTOM_PARAM_3("param3"),
    CUSTOM_PARAM_4("param4"),
    CUSTOM_PARAM_5("param5")
}
