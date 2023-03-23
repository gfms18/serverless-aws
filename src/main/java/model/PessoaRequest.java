package model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.google.gson.Gson;

@DynamoDBTable(tableName = "pessoa")
public class PessoaRequest {

	
	private Integer id;
	private String nome;
	private int idade;
	private boolean trabalha;
	
	public PessoaRequest(Integer id, String nome, int idade, boolean trabalha) {
		super();
		this.id = id;
		this.nome = nome;
		this.idade = idade;
		this.trabalha = trabalha;
	}
	@DynamoDBHashKey(attributeName = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@DynamoDBAttribute(attributeName = "nome")
	public String getNome() {
		return nome;
	}
	
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@DynamoDBAttribute(attributeName = "idade")
	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	@DynamoDBAttribute(attributeName = "trabalha")
	public boolean isTrabalha() {
		return trabalha;
	}

	
	public void setTrabalha(boolean trabalha) {
		this.trabalha = trabalha;
	}

	public PessoaRequest() {
		super();
	}
	
	
	
//	public PersonRequest(String json) {
//		
//		Gson gson = new Gson();
//		
//		PersonRequest pessoa = new 
//	}
	
}
