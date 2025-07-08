package guru.springframework.springaipromptengineering;

import org.junit.jupiter.api.Test;
import org.springframework.ai.autoconfigure.openai.OpenAiChatProperties;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by jt, Spring Framework Guru.
 */
public class ChainOfThoughtTests extends BaseTestClass {

    @Autowired
    OpenAiChatProperties openAiChatProperties;

    /*
      Chain of thought - adding a series of intermediate reasoning steps to the prompt.
      See - https://arxiv.org/abs/2201.11903
     */
    /**
     * 전통적인 프롬프트 테스트: 모델이 이전에 27로 잘못 답변했던 문제
     */
    @Test
    void testTraditionalPrompt() {
        var prompt = """
                Q: Roger has 5 tennis balls. He buys 2 more cans of tennis balls, each containing 3 balls. \s
                How many tennis balls does Roger have now?
                """;

        var promptTemplate = new PromptTemplate(prompt);

        var response = chatModel.call(promptTemplate.create());

        //models previously would answer 27
        System.out.println(response.getResult().getOutput().getContent());
    }

    /**
     * CoT(Chain-of-Thought) 프롬프트 테스트: 중간 추론 단계를 추가하여 모델의 정확도를 향상
     */
    @Test
    void testChainOfThroughPrompt() {
        var chainOfThoughtPrompt = """
                Q: Roger has 5 tennis balls. He buys 2 more cans of tennis balls, each containing 3 balls. \s
                How many tennis balls does Roger have now?
                
                A: Roger started with 5 balls. 2 cans of 3 balls each is 6 balls. 5 + 6 = 11. So Roger has 11 tennis balls.
                
                Q: The cafeteria had 23 apples originally. They used 20 apples to make lunch and bought 6 more. How many \s
                apples does the cafeteria have now?
                """;

        var promptTemplate = new PromptTemplate(chainOfThoughtPrompt);

        var response = chatModel.call(promptTemplate.create());

        System.out.println(response.getResult().getOutput().getContent());
    }

    /**
     * 전통적인 프롬프트 테스트 2: 1단어로 답변하도록 제약 조건을 추가
     */
    @Test
    void testTraditionalPrompt2() {
        var prompt = """
                Q: Roger has 5 tennis balls. He buys 2 more cans of tennis balls, each containing 3 balls. \s
                How many tennis balls does Roger have now? Answer in 1 word.
                """;

        var promptTemplate = new PromptTemplate(prompt);

        var response = chatModel.call(promptTemplate.create());

        //models previously would answer 27
        System.out.println(response.getResult().getOutput().getContent());
    }
}
