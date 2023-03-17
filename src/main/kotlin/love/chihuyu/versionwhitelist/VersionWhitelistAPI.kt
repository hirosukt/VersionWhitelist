package love.chihuyu.versionwhitelist

import love.chihuyu.versionwhitelist.VersionWhitelistPlugin.Companion.config
import net.md_5.bungee.api.config.ServerInfo

object VersionWhitelistAPI {

    fun checkVersion(protocol: Int, server: ServerInfo): Boolean {
        val whitelist = config.getSection("whitelist")
        return server.name in whitelist.keys && protocol !in whitelist.getIntList(server.name)
    }
}