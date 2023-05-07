package openaitester;

import java.time.Duration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;

public class OpenAiTester {
    public static void main(String[] args) {
	String preface = "For the below message, please give me your feedback according to some conditions:\n"
		+ "If it is the description of the training log, please give me the advice for future training, the message type should be \"trainingLog\".\n"
		+ "If it is about the food, please specify the ingredient and nutrient content of the food being mentioned in the message, the message type should be \"diet\".\n"
		+ "If it is a question, just answer it in your opinion the message type should be \"question\".\n"
		+ "Otherwise, just return the original message, the message type should be \"other\".\n"
		+ "Please reply only to the matched condition.\n"
		+ "Please reply in the JSON format as below, and write the answer to the key: \"openAIfeedback\", write the message type to the key: \"messageType\".\n"
		+ "\n"
		+ "If there has any word in chinese, Please reply in traditional chinese, otherwise, just reply in the language message usage. \n"
		+ "\n" + "{\n" + "    \"messageType\": “message “type,\n"
		+ "    \"openAIfeedback\": \"your feedback\"\n" + "}\n" + "";
	String instruction = args[0];
	String prompt = preface + "\n" + instruction;
	OpenAiService service = new OpenAiService("",
		Duration.ofSeconds(50));
	CompletionRequest completionRequest = CompletionRequest.builder().prompt(prompt).model("text-davinci-003")
		.echo(false).maxTokens(500).build();

	service.createCompletion(completionRequest).getChoices().forEach((t) ->
	    {
		try {
		    Answer answer = new ObjectMapper().readValue(t.getText(), Answer.class);
		    System.out.println(answer.toString());
		} catch (JsonProcessingException e) {
		    e.printStackTrace();
		}
		System.out.println(t);
	    });

    }
}

class Answer {
    Object Training;
    Object Food;

    public Object getTraining() {
	return Training;
    }

    public void setTraining(Object training) {
	Training = training;
    }

    public Object getFood() {
	return Food;
    }

    public void setFood(Object food) {
	Food = food;
    }

}
