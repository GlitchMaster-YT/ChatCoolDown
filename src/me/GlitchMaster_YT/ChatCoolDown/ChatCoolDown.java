package me.GlitchMaster_YT.ChatCoolDown;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.*;

public class ChatCoolDown extends JavaPlugin implements Listener {

	public final Logger logger = getLogger();
	public static ChatCoolDown plugin;
	private final HashMap<Player, Long> cooldown = new HashMap<>();

	@Override
	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion() + " Has Been Enabled!");
		getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Has Been Disabled!");
		return;
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (cooldown.containsKey(p)) {
			if((System.currentTimeMillis() - cooldown.get(p)) > 5000){
				cooldown.remove(p);
				return;
			}
			if(!p.isOp() || !p.hasPermission("ChatCoolDown.bypass")){
			p.sendMessage(ChatColor.RED + "You must wait 5 seconds before being able to speak again!");
			e.setCancelled(true);
			}
		} else {
			if(!p.isOp() || !p.hasPermission("ChatCoolDown.bypass")){
				cooldown.put(p, System.currentTimeMillis());
			}
		}
	}
}