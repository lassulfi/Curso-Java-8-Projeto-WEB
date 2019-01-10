package enums;

public enum FormIDEnum {

	Cliente, Curso, Pagamento;

	public static FormIDEnum fromInteger(int id) {
		switch (id) {
		case 1:
			return Cliente;
		case 2:
			return Curso;
		case 3:
			return Pagamento;
		}
		return null;
	}
}
