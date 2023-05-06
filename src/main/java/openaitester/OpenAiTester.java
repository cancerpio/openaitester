package openaitester;

import java.time.Duration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;

public class OpenAiTester {
    public static void main(String[] args) {
	String preface = "如果以下段落在描述訓練,預測一週後, 同樣次數可完成的重量,如果在描述飲食,列出食物名稱,還有這些食物的營養成分。中間要空行並整理成JSON格式";
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
