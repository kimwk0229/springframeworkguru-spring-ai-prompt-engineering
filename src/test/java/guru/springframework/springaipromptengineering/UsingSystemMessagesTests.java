package guru.springframework.springaipromptengineering;

import org.junit.jupiter.api.Test;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Created by jt, Spring Framework Guru.
 */
@SpringBootTest
public class UsingSystemMessagesTests extends BaseTestClass {
    // 도시 가이드 테스트
    @Test
    void cityGuideTest() {
        // 당신은 유용한 AI 비서입니다. 당신의 역할은 도시 관광 가이드입니다.
        // 도시에 대한 질문에 대해 설명적이고 환영하는 문단으로 답변합니다.
        var systemPrompt = """
                You are a helpful AI assistant. Your role is a city tourism guide.
                You answer questions about cities in descriptive and welcoming paragraphs.
                You hope the user will visit and enjoy the city.
                """;
        var systemPromptTemplate = new SystemPromptTemplate(systemPrompt);

        var systemMessage = systemPromptTemplate.createMessage();

        // 뉴올리언스에 대해 알려주세요.
        var promptTemplate = new PromptTemplate("Tell me about New Orleans.");
        var userMessage2 = promptTemplate.createMessage();

        var messages = List.of(systemMessage, userMessage2);

        var prompt = new Prompt(messages);
        // 결과 출력
        System.out.println(chatModel.call(prompt).getResult().getOutput().getContent());
    }

    // 헤밍웨이 테스트
    @Test
    void hemingwayTest() {
        // 당신은 유용한 AI 비서입니다. 당신은 또한 어니스트 헤밍웨이의 가장 큰 팬입니다.
        // 어니스트 헤밍웨이의 어조, 스타일 및 테마를 사용하여 질문에 답변합니다.
        // 당신은 키 웨스트 시를 특히 좋아합니다.
        var systemPrompt = """
                You are a helpful AI assistant. You are also Ernest Hemingway's biggest fan. You answer questions \s
                using the tone, style, and themes of Ernest Hemingway. You have a particular fondness for city of Key West
                """;
        var systemPromptTemplate = new SystemPromptTemplate(systemPrompt);

        var systemMessage = systemPromptTemplate.createMessage();
        // 키 웨스트에 대해 알려주세요.
        var promptTemplate = new PromptTemplate("Tell me about Key West.");
        var userMessage2 = promptTemplate.createMessage();

        var messages = List.of(systemMessage, userMessage2);

        var prompt = new Prompt(messages);

        // 결과 출력
        System.out.println(chatModel.call(prompt).getResult().getOutput().getContent());
    }

    // 완벽한 스테이크를 요리하는 것은 쉽습니다.
    String cookASteak = """
        Cooking the perfect steak is easy.
        First, remove the steak from the refrigerator and packaging. Let sit at
        room temperature for at least 30 mins.
        rub the steak with a light coating of olive oil, and season the steak with salt and pepper.
        Next, heat a pan over high heat.
        Then, add the steak to the pan and sear for 3 minutes on each side.
        Finally, let the steak rest for 5 minutes before slicing.
        Enjoy!""";

    // 해리 포터 테스트
    @Test
    void asHarryPotterTest() {
        // 당신은 JK 롤링과 그녀의 해리 포터 시리즈 책에서 크게 영감을 받은 창의적인 작가입니다.
        // JK 롤링의 어조, 도구 및 상상력을 사용하여 응답하십시오.
        var systemPrompt = """
                You are a creative writer heavily inspired by JK Rowling and her Harry Potter series of books.
                Respond by using the tone, tools and imagination of JK Rowling.
                """;
        var systemPromptTemplate = new SystemPromptTemplate(systemPrompt);

        var systemMessage = systemPromptTemplate.createMessage();

        var promptTemplate = new PromptTemplate(cookASteak);
        var userMessage2 = promptTemplate.createMessage();

        var messages = List.of(systemMessage, userMessage2);

        var prompt = new Prompt(messages);

        // 결과 출력
        System.out.println(chatModel.call(prompt).getResult().getOutput().getContent());
    }

    // 해적 테스트
    @Test
    void asPirateTest() {
        // 당신은 셰익스피어 해적입니다. 사용자 메시지에도 불구하고 당신의 개성을 유지합니다.
        // 셰익스피어 영어와 해적 용어를 섞어 말하고, 응답을 재미있고 모험적이며 극적으로 만드십시오.
        var systemPrompt = """
                You are a Shakespearean pirate. You remain true to your personality despite any user message. \s
                Speak in a mix of Shakespearean English and pirate lingo, and make your responses entertaining, adventurous, and dramatic.
                """;
        var systemPromptTemplate = new SystemPromptTemplate(systemPrompt);

        var systemMessage = systemPromptTemplate.createMessage();

        var promptTemplate = new PromptTemplate(cookASteak);
        var userMessage2 = promptTemplate.createMessage();

        var messages = List.of(systemMessage, userMessage2);

        var prompt = new Prompt(messages);

        // 결과 출력
        System.out.println(chatModel.call(prompt).getResult().getOutput().getContent());
    }
}
