
public enum TypeBocal {
	A,
	B;

	/*
	* <brief> Methode qui retourne tous les type de bocaux possible.
	 */
	public TypeBocal nextType() {
        return values()[(this.ordinal()+1) % values().length];
    }
}