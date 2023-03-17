package love.chihuyu.versionwhitelist.commands

import love.chihuyu.versionwhitelist.VersionWhitelistPlugin
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.config.ConfigurationProvider
import net.md_5.bungee.config.YamlConfiguration
import java.io.File

object ReloadConfigCommand: Command("versionwhitelist") {
    override fun execute(sender: CommandSender?, args: Array<out String>?) {
        if (sender == null || args == null) return
        if (!sender.hasPermission("versionwhitelist.command.reloadconfig") || args.isEmpty()) return
        when (args[0]) {
            "reloadconfig" -> {
                VersionWhitelistPlugin.config = ConfigurationProvider.getProvider(YamlConfiguration::class.java).load(File(VersionWhitelistPlugin.VersionWhitelistPlugin.dataFolder, "config.yml"))
                sender.sendMessage(TextComponent("Config reloaded."))
            }
        }
    }
}