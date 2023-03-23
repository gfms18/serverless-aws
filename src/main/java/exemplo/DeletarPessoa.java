package exemplo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import model.PessoaRequest;
import model.PessoaResponse;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;

public class DeletarPessoa implements RequestHandler<PessoaRequest,  PessoaResponse> {

	static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
	private DynamoDB dynamoDb = new DynamoDB(client);


  	public PessoaResponse handleRequest(PessoaRequest input, Context context) {
	
  		PessoaRequest pessoa = new PessoaRequest();
  		DynamoDBMapper mapper = new DynamoDBMapper(client);
  		pessoa = mapper.load(PessoaRequest.class, input.getId());
  		mapper.delete(pessoa);
  		
  		PessoaResponse pr = new PessoaResponse();
  		pr.setMessage("Pessoa apagada com sucesso");
  		
	return pr;
}
}
