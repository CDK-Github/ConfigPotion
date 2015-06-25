package fr.cedrik.ConfigPotion;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import fr.cedrik.config.Proprietes;
import fr.cedrik.config.ProprietesHelper;
import fr.cedrik.config.ProprietesLoader;
 
public class ConfigPotion extends JavaPlugin implements Listener {
	private final ProprietesHelper proprietesHelper;
    private final File proprietesFile;
    public Proprietes proprietes;
    private static final String mainDirectory = "plugins/ConfigPotion";
    public static final Logger log = Logger.getLogger("Minecraft");
	
	public ConfigPotion() {
		proprietes = new Proprietes();
        new File(mainDirectory).mkdirs();
        proprietesFile = new File(mainDirectory + File.separator + "proprietes.prop");
        proprietesHelper = new ProprietesHelper(proprietesFile, "ConfigPotion");
	}
	
	@Override
    public void onDisable() {
        log.info("[ConfigPotion] Desactive");
    }
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		proprietes = new ProprietesLoader().loadSettings(proprietesHelper);
	}
	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent event) {
		if (proprietes.isNerfForce() != false) {
			if ((event.getDamager() instanceof Player)) {
				Player player = (Player)event.getDamager();
				if (player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
					for (PotionEffect effect : player.getActivePotionEffects()) {
						if (effect.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {
							int niveau = effect.getAmplifier() + 1;
							double newDegats = event.getDamage(EntityDamageEvent.DamageModifier.BASE) / (niveau * 1.3D + 1.0D) + proprietes.getForceLevel() * niveau;
							double pourcentageDegats = newDegats / event.getDamage(EntityDamageEvent.DamageModifier.BASE);
							try {
								event.setDamage(EntityDamageEvent.DamageModifier.ARMOR, event.getDamage(EntityDamageEvent.DamageModifier.ARMOR) * pourcentageDegats);
							}
							catch (Exception localException) {}
							try {
								event.setDamage(EntityDamageEvent.DamageModifier.MAGIC, event.getDamage(EntityDamageEvent.DamageModifier.MAGIC) * pourcentageDegats);
							}
							catch (Exception localException1) {}
							try {
								event.setDamage(EntityDamageEvent.DamageModifier.RESISTANCE, event.getDamage(EntityDamageEvent.DamageModifier.RESISTANCE) * pourcentageDegats);
							}
							catch (Exception localException2) {}
							try {
								event.setDamage(EntityDamageEvent.DamageModifier.BLOCKING, event.getDamage(EntityDamageEvent.DamageModifier.BLOCKING) * pourcentageDegats);
							}
							catch (Exception localException3) {}
							event.setDamage(EntityDamageEvent.DamageModifier.BASE, newDegats);
							break;
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void onRegen(EntityRegainHealthEvent event) {
		if ((proprietes.isNerfRegen() != false) || (proprietes.isNerfVie() != false)) {
			final LivingEntity entity = (LivingEntity)event.getEntity();
			int lvl = 0;
			java.util.Collection<PotionEffect> Effects = entity.getActivePotionEffects();
			for (PotionEffect effect : Effects) {
				if ((effect.getType().getName() == "REGENERATION") || (effect.getType().getName() == "HEAL")) {
					lvl = effect.getAmplifier() + 1;
					break;
				}
			}
			if ((event.getRegainReason() == EntityRegainHealthEvent.RegainReason.MAGIC_REGEN) && (event.getAmount() == 1.0D) && (lvl > 0)) {
				if (proprietes.isNerfVie() != false) {
					getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
						public void run() {
							if (entity.getMaxHealth() >= entity.getHealth() + 1.0D) {
								entity.setHealth(entity.getHealth() + 1.0D);
							}
						}
					}, 50L / (lvl * 2));
				}
			}
			else if ((event.getRegainReason() == EntityRegainHealthEvent.RegainReason.MAGIC) && (event.getAmount() > 1.0D) && (lvl > 0)) {
				if (proprietes.isNerfRegen() != false) {
					event.setAmount(event.getAmount() * (proprietes.getHpLevel() * 0.25D));
				}
			}
		}
	}
}