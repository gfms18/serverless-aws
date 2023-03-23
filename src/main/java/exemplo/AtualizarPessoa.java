package exemplo;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.PessoaRequest;
import model.PessoaResponse;

public class AtualizarPessoa implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
	private DynamoDB dynamoDb = new DynamoDB(client);

	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		
		String parametro = request.getPathParameters().get("id");
		Integer id = Integer.parseInt(parametro);
		
		APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
		
		DynamoDBMapper mapper = new DynamoDBMapper(client);
		PessoaRequest pessoa = mapper.load(PessoaRequest.class, id);
		if(pessoa != null) {
			String body = request.getBody();
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			try {
				
				JsonNode rootNode = objectMapper.readTree(body);
				pessoa.setNome(rootNode.get("nome").asText());
				pessoa.setIdade(rootNode.get("idade").asInt());
				pessoa.setTrabalha(rootNode.get("trabalha").asBoolean());
				mapper.save(pessoa);
				
				response.setStatusCode(200);
				String mensagem = "Pessoa alterada com sucesso!";
				response.setBody(mensagem);
				
			} catch (JsonMappingException e) {
				
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				
				e.printStackTrace();
			}
			
			
			
			return response;

		} else {
			response.setStatusCode(400);
			String mensagem = "Pessoa inválida!";
			response.setBody(mensagem);
			
			return response;
		}
		

	}}
