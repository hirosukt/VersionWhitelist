package love.chihuyu.versionwhitelist

import net.md_5.bungee.api.plugin.Plugin

class VersionWhitelistPlugin: Plugin() {
    companion object {
        lateinit var VersionWhitelistPlugin: Plugin
    }

    init {
        VersionWhitelistPlugin = this
    }
}