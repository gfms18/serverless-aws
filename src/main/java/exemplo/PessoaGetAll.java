package exemplo;

import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import model.PessoaRequest;

public class PessoaGetAll implements RequestHandler<Object,List <PessoaRequest>>{

	final AmazonDynamoDB dynamoDb = AmazonDynamoDBClientBuilder.defaultClient();
    final DynamoDBMapper mapper = new DynamoDBMapper(dynamoDb);
	
	public List<PessoaRequest> handleRequest(Object input, Context context) {
		
		 DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
	      List<PessoaRequest> listaPessoas = mapper.scan(PessoaRequest.class, scanExpression);
		
		return listaPessoas;
	}

}
