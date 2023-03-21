package love.chihuyu.versionwhitelist.listener

import love.chihuyu.versionwhitelist.VersionWhitelistAPI
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.event.ServerConnectEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler


object LoginListener: Listener {

    @EventHandler
    fun onLogin(e: ServerConnectEvent) {
        val player = e.player
        val server = e.target

        if (VersionWhitelistAPI.checkVersion(player.pendingConnection.version, server)) {
            e.target = (player.server ?: return).info
            player.sendMessage(TextComponent("${ChatColor.RED}You are using unsupported version!"))
            e.isCancelled = true
        }
    }
}