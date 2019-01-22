package com.abctreinamentos;
// Generated 02/01/2019 15:52:06 by Hibernate Tools 4.3.5.Final

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Cliente generated by hbm2java
 */
@Entity
@Table(name = "CLIENTE")
public class Cliente implements Serializable {

	private static final long serialVersionUID = 5725617525371386031L;
	private long cpf;
	private String nome;
	private String email;
	private Set<Pagamento> pagamentos = new HashSet<Pagamento>(0);

	public Cliente() {
	}

	public Cliente(long cpf, String nome, String email) {
		this.cpf = cpf;
		this.nome = nome;
		this.email = email;
	}

	public Cliente(long cpf, String nome, String email, Set<Pagamento> pagamentos) {
		this.cpf = cpf;
		this.nome = nome;
		this.email = email;
		this.pagamentos = pagamentos;
	}

	@Id

	@Column(name = "CPF", unique = true, nullable = false, precision = 22, scale = 0)
	public long getCpf() {
		return this.cpf;
	}

	public void setCpf(long cpf) {
		this.cpf = cpf;
	}

	@Column(name = "NOME", nullable = false, length = 80)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "EMAIL", nullable = false, length = 40)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente")
	public Set<Pagamento> getPagamentos() {
		return this.pagamentos;
	}

	public void setPagamentos(Set<Pagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}

	@Override
	public String toString() {
		return "Cliente - cpf= " + cpf + ", nome= " + nome + ", email= " + email;
	}
	
	

}