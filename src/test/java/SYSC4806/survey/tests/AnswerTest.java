package SYSC4806.survey.tests;

import SYSC4806.survey.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AnswerTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    void testSaveAnswer() {
        Answer answer = new Answer("Yes");
        answerRepository.save(answer);

        assertThat(answer.getId()).isNotNull();

        Answer savedAnswer = answerRepository.findById(answer.getId()).orElse(null);
        assertThat(savedAnswer).isNotNull();
        assertThat(savedAnswer.getAnswerChoice()).isEqualTo("Yes");
    }

    @Test
    void testFindAllAnswers() {
        Answer answer1 = new Answer("Yes");
        Answer answer2 = new Answer("No");
        answerRepository.saveAll(List.of(answer1, answer2));

        List<Answer> answers = (List<Answer>) answerRepository.findAll();
        assertThat(answers).hasSize(2);
        assertThat(answers.get(0).getAnswerChoice()).isEqualTo("Yes");
        assertThat(answers.get(1).getAnswerChoice()).isEqualTo("No");
    }

}