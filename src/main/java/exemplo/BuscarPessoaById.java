package exemplo;

import java.util.Collections;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import model.PessoaRequest;
import model.PessoaResponse;

public class BuscarPessoaById implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent > {

	static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
	private DynamoDB dynamoDb = new DynamoDB(client);
	
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context)  {
		
		String parametro = request.getPathParameters().get("id");
		Integer id = Integer.parseInt(parametro);
		
		DynamoDBMapper mapper = new DynamoDBMapper(client);
		PessoaRequest pessoa = mapper.load(PessoaRequest.class, id);

		if(pessoa != null) {
						
			APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
			ObjectMapper objectMapper = new ObjectMapper();			
			
			try {
				String json = objectMapper.writeValueAsString(pessoa);
				response.setStatusCode(200);
				response.setHeaders(Collections.singletonMap("Content-Type", "application/json"));
				response.setBody(json);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return response;
			
		} else {
			APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
			response.setStatusCode(404);
			response.setBody("Esta pessoa não foi encontrada.");
			return response;
		}
		
	}

}
