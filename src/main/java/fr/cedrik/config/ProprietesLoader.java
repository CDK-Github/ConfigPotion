package fr.cedrik.config;

import fr.cedrik.config.Proprietes;
import fr.cedrik.config.ProprietesHelper;

public class ProprietesLoader {

    Proprietes settings;
    private static final String nerfForce = "Nerf-Force";
    private static final String nerfVie = "Nerf-Vie";
    private static final String nerfRegen = "Nerf-Regen";
    private static final String forceLevel = "Niveau-Force";
    private static final String hpLevel = "Niveau-HP";
    private static final String version = "Version";

    public Proprietes loadSettings(ProprietesHelper helper) {
        settings = new Proprietes();
        helper.loadConfig();
        if (!proprietesValides(helper)) {
            proprietesManquantes(helper);
            helper.saveConfig();
        }
        proprietesChargement(helper);
        return settings;
    }
    
    private void proprietesManquantes(ProprietesHelper helper) {
    	Proprietes temp = new Proprietes();
        if (helper.getProperty(nerfForce) == null) {
            helper.setProperty(nerfForce, Boolean.toString(temp.isNerfForce()));
        }
        if (helper.getProperty(nerfVie) == null) {
            helper.setProperty(nerfVie, String.valueOf(temp.isNerfVie()));
        }
        if (helper.getProperty(nerfRegen) == null) {
            helper.setProperty(nerfRegen, Boolean.toString(temp.isNerfRegen()));
        }
        if (helper.getProperty(forceLevel) == null) {
            helper.setProperty(forceLevel, Integer.toString(temp.getForceLevel()));
        }
        if (helper.getProperty(hpLevel) == null) {
            helper.setProperty(hpLevel, Integer.toString(temp.getHpLevel()));
        }
        if (helper.getProperty(version) == null) {
        	helper.setProperty(version, String.valueOf(temp.getVersion()));
        }
    }
    
    private boolean proprietesValides(ProprietesHelper helper) {
        return (helper.getProperty(version) != null)
                && (helper.getProperty(nerfForce) != null)
                && (helper.getProperty(nerfVie) != null)
                && (helper.getProperty(nerfRegen) != null)
                && (helper.getProperty(forceLevel) != null)
                && (helper.getProperty(hpLevel) != null);
    }

    private void proprietesChargement(ProprietesHelper helper) {
        settings.setNerfForce(Boolean.valueOf(helper.getProperty(nerfForce)));
        settings.setNerfVie(Boolean.valueOf(helper.getProperty(nerfVie)));
        settings.setNerfRegen(Boolean.valueOf(helper.getProperty(nerfRegen)));
        settings.setForceLevel(Integer.valueOf(helper.getProperty(forceLevel)));
        settings.setHpLevel(Integer.valueOf(helper.getProperty(hpLevel)));
        settings.setVersion(String.valueOf(helper.getProperty(version)));
    }
}
