package guru.springframework.springaipromptengineering;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

/**
 * Created by jt, Spring Framework Guru.
 */
@SpringBootTest
// MakingTheModelThinkTests 클래스는 모델의 사고 과정을 테스트합니다.
// 이 클래스는 Spring Boot 테스트 환경에서 실행됩니다.
// BaseTestClass를 상속받아 공통 설정 및 유틸리티를 활용합니다.
public class MakingTheModelThinkTests extends BaseTestClass {

    String story = """
        In a charming village, siblings Jack and Jill set out on
        a quest to fetch water from a hilltop well.
        As they climbed, singing joyfully, misfortune
        struck—Jack tripped on a stone and tumbled
        down the hill, with Jill following suit. 
        Though slightly battered, the pair returned home to
        comforting embraces. Despite the mishap, 
        their adventurous spirits remained undimmed, and they 
        continued exploring with delight.
        """;

    // prompt는 주어진 텍스트를 요약하고, 폴란드어로 번역하며, 이름 목록을 추출한 후 JSON 객체로 출력하는 지시사항을 포함합니다.
    String prompt = """
            Perform the following actions:
            1 - Summarize the following text delimited by triple
            backticks with 1 sentence.
            2 - Translate the summary into Polish.
            3 - List each name in the Polish summary.
            4 - Output a json object that contains the following
            keys: polish_summary, num_names.
            Separate your answers with line breaks.
            Text:
            ```{text}```
            """;

    // testSteps 메서드는 prompt를 사용하여 모델의 다단계 지시 수행 능력을 테스트합니다.
    @Test
    void testSteps() {
        var promptTemplate = new PromptTemplate(prompt ,
                Map.of("text", story));

        System.out.println(chatModel.call(promptTemplate.create()).getResult().getOutput().getContent());
    }

    // prompt2Incorrect는 학생의 재무 계산 솔루션이 올바른지 여부를 판단하는 질문을 포함합니다.
    // 이 프롬프트는 모델이 직접 계산하지 않고 바로 판단하게 하여 잘못된 결과를 초래할 수 있습니다.
    String prompt2Incorrect = """
            Determine if the student's solution is correct or not.
                        
            Question:
            I'm building a solar power installation and I need
             help working out the financials.
            - Land costs $100 / square foot
            - I can buy solar panels for $250 / square foot
            - I negotiated a contract for maintenance that will cost 
            me a flat $100k per year, and an additional $10 / square foot
            
            What is the total cost for the first year of operations
            as a function of the number of square feet.
                        
            Student's Solution:
            Let x be the size of the installation in square feet.
            Costs:
            1. Land cost: 100x
            2. Solar panel cost: 250x
            3. Maintenance cost: 100,000 + 100x
            Total cost: 100x + 250x + 100,000 + 100x = 450x + 100,000
            """;

    // testIncorrectPrompt 메서드는 모델이 직접 계산 없이 학생의 솔루션을 평가할 때 발생할 수 있는 문제를 보여줍니다.
    @Test
    void testIncorrectPrompt() {
        var promptTemplate = new PromptTemplate(prompt2Incorrect);

        System.out.println(chatModel.call(promptTemplate.create()).getResult().getOutput().getContent());
    }

    // prompt3Correct는 모델이 학생의 솔루션을 평가하기 전에 스스로 문제를 해결하도록 지시하여,
    // 더 정확한 판단을 내릴 수 있도록 유도하는 프롬프트입니다.
    String prompt3Correct = """
            Your task is to determine if the student's solution is correct or not.
            To solve the problem do the following:
            - First, work out your own solution to the problem including the final total.
            - Then compare your solution to the student's solution and evaluate if the student's solution is correct or not.
            
            Don't decide if the student's solution is correct until you have done the problem yourself.
                        
            Use the following format:
            Question:
            ```question here```
            
            Student's solution:
            ```student's solution here```
            
            Actual solution:
            ```steps to work out the solution and your solution here```
            
            Is the student's solution the same as actual solution just calculated:
            ```yes or no```
            
            Student grade:
            ```correct or incorrect```
                        
            Question:
            ```
            I'm building a solar power installation and I need help working out the financials.
            - Land costs $100 / square foot
            - I can buy solar panels for $250 / square foot
            - I negotiated a contract for maintenance that will cost me a flat $100k per year, and an additional $10 / square foot
            
            What is the total cost for the first year of operations as a function of the number of square feet.
            ```
            
            Student's solution:
            ```
            Let x be the size of the installation in square feet.
            Costs:
            1. Land cost: 100x
            2. Solar panel cost: 250x
            3. Maintenance cost: 100,000 + 100x
            Total cost: 100x + 250x + 100,000 + 100x = 450x + 100,000
            ```
            
            Actual solution:
            ```actual solution here```
            
            """;

    // testCorrectPrompt 메서드는 모델이 먼저 스스로 문제를 해결한 후 학생의 솔루션을 평가하는 방식을 테스트합니다.
    @Test
    void testCorrectPrompt() {
        var promptTemplate = new PromptTemplate(prompt3Correct);

        System.out.println(chatModel.call(promptTemplate.create()).getResult().getOutput().getContent());
    }

    // prompt4는 물리적 추론 문제를 해결하는 전문가 역할을 부여하고,
    // 컵과 구슬의 위치 변화를 단계별로 설명하도록 요구하는 프롬프트입니다.
    String prompt4 = """
       You are an expert at solving reasoning problems. A cup is an object with an open top and close on the sides and bottom. The open top does not prevent objects from passing through it.
       
       Assume the laws of physics on Earth. A small marble is put into a normal cup and the cup is placed upside down on a table,
        causing the open side of the cup to be in contact with the table. Gravity will cause the ball to fall to the table.
       Someone then picks the cup up without changing its orientation and puts it inside the microwave. Where is the ball now. Determine the position of the ball in each step. Explain 
       why the ball is postioned where it is.
       """;

    // testTheBallPrompt 메서드는 모델의 물리적 추론 및 단계별 설명 능력을 테스트합니다.
    @Test
    void testTheBallPrompt() {
        var promptTemplate = new PromptTemplate(prompt4);
        System.out.println(chatModel.call(promptTemplate.create()).getResult().getOutput().getContent());
    }
}
