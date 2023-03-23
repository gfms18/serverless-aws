package exemplo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import model.PessoaRequest;
import model.PessoaResponse;



public class AdicionarPessoa implements RequestHandler<PessoaRequest, PessoaResponse> {

	private AmazonDynamoDB amazonDynamoDB;

    private String DYNAMODB_TABLE_NAME = "pessoa";

	public PessoaResponse handleRequest(PessoaRequest pessoaRequest, Context context) {
		this.amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
         .withRegion(Regions.US_EAST_1)
         .build();
		
		persistData(pessoaRequest); 
		
		PessoaResponse pessoaResponse = new PessoaResponse();
		pessoaResponse.setMessage("Salvado com sucesso!");
        return pessoaResponse;
	}


	private void persistData(PessoaRequest pessoaRequest) throws ConditionalCheckFailedException {

		DynamoDB dynamodb = new DynamoDB(amazonDynamoDB);
			
	     Item item = new Item()
	    		.withPrimaryKey("id",pessoaRequest.getId())
	     		.withString("nome",pessoaRequest.getNome())
	     		.withInt("idade",pessoaRequest.getIdade())
	     		.withBoolean("trabalha",pessoaRequest.isTrabalha());
	     	
     
	   
	     Table table = dynamodb.getTable(DYNAMODB_TABLE_NAME);
	     table.putItem(item);
 }
	

}
