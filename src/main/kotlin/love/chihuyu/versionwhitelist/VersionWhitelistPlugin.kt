package love.chihuyu.versionwhitelist

import love.chihuyu.versionwhitelist.commands.ReloadConfigCommand
import love.chihuyu.versionwhitelist.listener.LoginListener
import net.md_5.bungee.api.plugin.Plugin
import net.md_5.bungee.config.Configuration
import net.md_5.bungee.config.ConfigurationProvider
import net.md_5.bungee.config.YamlConfiguration
import java.io.File

class VersionWhitelistPlugin: Plugin() {
    companion object {
        lateinit var VersionWhitelistPlugin: Plugin
        lateinit var config: Configuration
    }

    init {
        VersionWhitelistPlugin = this
    }

    override fun onEnable() {
        super.onEnable()

        config = ConfigurationProvider.getProvider(YamlConfiguration::class.java).load(File(dataFolder, "config.yml"))

        val configDir = File(dataFolder.absolutePath)
        if (!configDir.exists()) {
            configDir.mkdir()
            val configFile = configDir.resolve("config.yml")
            configFile.createNewFile()
            configFile.writeText(getResourceAsStream("config.yml").bufferedReader().readText())
        }

        proxy.pluginManager.registerListener(this, LoginListener)

        proxy.pluginManager.registerCommand(this, ReloadConfigCommand)
    }
}