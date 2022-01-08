package dev.moru3.elytradaisuki

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.plugin.java.JavaPlugin

class ElytraDaisuki : JavaPlugin(), Listener {
    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
    }

    @EventHandler
    fun onEvent(event: PlayerMoveEvent) {
        if(enabled.contains(event.player)) {
            event.player.isGliding = true
        }
    }

    val cache = mutableListOf<Player>()
    val enabled = mutableListOf<Player>()

    @EventHandler
    fun onChat(event: AsyncPlayerChatEvent) {
        if(!event.player.isOp) { return }
        when(event.message) {
            "エリトラ！！！" -> {
                Bukkit.getScheduler().runTaskLater(this, Runnable {
                    Bukkit.broadcastMessage("${ChatColor.RED}@${event.player.name}${ChatColor.WHITE} エリトラ、大好きー？")
                    cache.add(event.player)
                    Bukkit.getScheduler().runTaskLater(this, Runnable {
                        cache.add(event.player)
                    }, 100)
                }, 20)
            }
            "大好きー！" -> {
                if(cache.contains(event.player)) {
                    Bukkit.getScheduler().runTaskLater(this, Runnable {
                        Bukkit.broadcastMessage("${ChatColor.RED}@${event.player.name}${ChatColor.WHITE} 有効化しました。エリトラなしでも使用できます。")
                        cache.remove(event.player)
                        enabled.add(event.player)
                    }, 20)
                }
            }
            "エリトラ、終了" -> {
                Bukkit.getScheduler().runTaskLater(this, Runnable {
                    enabled.remove(event.player)
                    Bukkit.broadcastMessage("${ChatColor.RED}@${event.player.name}${ChatColor.WHITE} 了解しました。")
                }, 20)
            }
        }
    }
}