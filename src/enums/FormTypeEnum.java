package enums;

public enum FormTypeEnum {

	ConsultarTodos, ConsultarUm, Cadastrar, Alterar, Excluir;

	public static FormTypeEnum fromInteger(int id) {
		switch (id) {
		case 1:
			return ConsultarTodos;
		case 2:
			return ConsultarUm;
		case 3:
			return Cadastrar;
		case 4:
			return Alterar;
		case 5:
			return Excluir;
		default:
			return null;
		}
	}
}
