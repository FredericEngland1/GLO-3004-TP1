
public enum TypeBocal {
	A,
	B,
	C;

	/*
	* <brief> Methode qui retourne tous les type de bocaux possible.
	 */
	public TypeBocal nextType() {
        return values()[(this.ordinal()+1) % values().length];
    }
}