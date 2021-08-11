package net.whg.havens_ar;

import org.bukkit.plugin.java.JavaPlugin;

public class HavensAR extends JavaPlugin {
  @Override
  public void onEnable() {
    getCommand("ar").setExecutor(new ARCommand());
  }
}