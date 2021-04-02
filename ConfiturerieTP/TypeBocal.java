
public enum TypeBocal {
	A,
	B;

	/*
	* <brief> Methode qui retourne tous les type de bocaux possible.
	 */
	public static TypeBocal[] typesBocaux() {
		return new TypeBocal[]{TypeBocal.A, TypeBocal.B};
	}
}