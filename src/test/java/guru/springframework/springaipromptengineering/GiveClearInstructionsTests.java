package guru.springframework.springaipromptengineering;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

/**
 * Created by jt, Spring Framework Guru.
 */
@SpringBootTest
public class GiveClearInstructionsTests extends BaseTestClass {

    /**
     * LLM에게 JSON 형식으로 데이터를 생성하도록 지시하는 테스트
     */
    @Test
    void testGetJSON() {
        var prompt = """
                Generate a list of 4 made up cars. Provide them in a JSON format
                with the following attributes: make, model, year, and color. Return the JSON string.
                """;

        System.out.println(chat(prompt));
    }
    /**
     * LLM에게 XML 형식으로 데이터를 생성하도록 지시하는 테스트
     */
    @Test
    void testGetXML() {
        var prompt = """
                Generate a list of 4 made up cars. Provide them in a XML format
                with the following attributes: make, model, year, and color. Return the XML string.
                """;

        System.out.println(chat(prompt));
    }
    /**
     * LLM에게 YAML 형식으로 데이터를 생성하도록 지시하는 테스트
     */
    @Test
    void testGetYAML() {
        var prompt = """
                Generate a list of 4 made up cars. Provide them in a YAML format
                with the following attributes: make, model, year, and color. Return the YAML string.
                """;

        System.out.println(chat(prompt));
    }

    /**
     * LLM에게 주어진 텍스트에서 지시사항을 추출하여 단계별로 재작성하도록 지시하는 프롬프트
     */
    String directionsPrompt = """
            You will be provided with text delimited by triple quotes.
            If it contains a sequence of instructions,
            re-write those instructions in the following format:
                        
            Step 1 - ...
            Step 2 - ...
            Step N - ...
                        
            If the text does not contain a sequence of instructions, then simply write \\"No steps provided.\\"
                        
            \\"\\"\\"{text_1}\\"\\"\\"
            """;

    /**
     * 스테이크 요리 지시사항을 담은 텍스트
     */
    String cookASteak = """
        Cooking the perfect steak is easy.
        First, remove the steak from the refrigerator and packaging. Let sit at
        room temperature for at least 30 mins.
        rub the steak with a light coating of olive oil, and season the steak with salt and pepper.
        Next, heat a pan over high heat.
        Then, add the steak to the pan and sear for 3 minutes on each side.
        Finally, let the steak rest for 5 minutes before slicing.
        Enjoy!""";

    /**
     * 책 설명을 담은 텍스트 (지시사항 없음)
     */
    String bookDescription = """
            Book Elon Musk
            When Elon Musk was a kid in South Africa, he was regularly beaten by bullies. One day a group pushed him down some concrete steps and kicked him until his face was a swollen ball of flesh. He was in the hospital for a week. But the physical scars were minor compared to the emotional ones inflicted by his father, an engineer, rogue, and charismatic fantasist.
                        
            His father’s impact on his psyche would linger. He developed into a tough yet vulnerable man-child, prone to abrupt Jekyll-and-Hyde mood swings, with an exceedingly high tolerance for risk, a craving for drama, an epic sense of mission, and a maniacal intensity that was callous and at times destructive.
                        
            At the beginning of 2022—after a year marked by SpaceX launching thirty-one rockets into orbit, Tesla selling a million cars, and him becoming the richest man on earth—Musk spoke ruefully about his compulsion to stir up dramas. “I need to shift my mindset away from being in crisis mode, which it has been for about fourteen years now, or arguably most of my life,” he said.""";

    @Test
    /**
     * 스테이크 요리 지시사항을 LLM에게 단계별로 재작성하도록 요청하는 테스트
     */
    void testCookSteak() {
        var promptTemplate = new PromptTemplate(directionsPrompt, Map.of("text_1", cookASteak));

        System.out.println(chatModel.call(promptTemplate.create()).getResult().getOutput().getContent());
    }

    /**
     * 책 설명을 LLM에게 전달하여 지시사항이 없는 경우 "No steps provided."를 반환하는지 확인하는 테스트
     */
    @Test
    void testBookDescription() {
        var promptTemplate = new PromptTemplate(directionsPrompt, Map.of("text_1", bookDescription));

        System.out.println(chatModel.call(promptTemplate.create()).getResult().getOutput().getContent());
    }

    /**
     * 스테이크 요리 지시사항을 LLM에게 Snoop Dog의 어조로 재작성하도록 요청하는 테스트
     */
    @Test
    void testCookSteakAsSnoopDog() {
        var promptTemplate = new PromptTemplate(directionsPrompt + "Give the directions using the tone of Snoop Dog",
                Map.of("text_1", cookASteak));

        System.out.println(chatModel.call(promptTemplate.create()).getResult().getOutput().getContent());
    }

    /**
     * 스테이크 요리 지시사항을 LLM에게 JK 롤링의 해리포터 책 스타일로 재작성하도록 요청하는 테스트
     */
    @Test
    void testCookSteakAsHarryPotter() {
        var promptTemplate = new PromptTemplate(directionsPrompt + "Give the directions using the tone, tools and imagination of JK Rowling in a Harry Potter book",
                Map.of("text_1", cookASteak));

        System.out.println(chatModel.call(promptTemplate.create()).getResult().getOutput().getContent());
    }
}
