package com.cedrik.config;


public class Proprietes {

    private boolean nerfForce;
    private boolean nerfVie;
    private boolean nerfRegen;
    private int forceLevel;
    private int hpLevel;
    private String version;

    public Proprietes() {
    	nerfForce = false;
    	forceLevel = 6;
    	nerfVie = false;
    	nerfRegen = false;
    	hpLevel = 6;
    	version = "1.1.0";
    }

	public boolean isNerfForce() {
		return nerfForce;
	}

	public void setNerfForce(boolean nerfForce) {
		this.nerfForce = nerfForce;
	}

	public boolean isNerfVie() {
		return nerfVie;
	}

	public void setNerfVie(boolean nerfVie) {
		this.nerfVie = nerfVie;
	}

	public boolean isNerfRegen() {
		return nerfRegen;
	}

	public void setNerfRegen(boolean nerfRegen) {
		this.nerfRegen = nerfRegen;
	}

	public int getForceLevel() {
		return forceLevel;
	}

	public void setForceLevel(int forceLevel) {
		this.forceLevel = forceLevel;
	}

	public int getHpLevel() {
		return hpLevel;
	}

	public void setHpLevel(int hpLevel) {
		this.hpLevel = hpLevel;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
    
}
