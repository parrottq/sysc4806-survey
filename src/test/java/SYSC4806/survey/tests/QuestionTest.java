package SYSC4806.survey.tests;

import static org.assertj.core.api.Assertions.assertThat;

import SYSC4806.survey.Answer;
import SYSC4806.survey.Question;
import SYSC4806.survey.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class QuestionTest {
    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void testQuestion() {
        for (var _elem: questionRepository.findAll()) {
            assertThat(false).isTrue(); // Should be empty
        }

        Long id;

        {
            var question = new Question("What?", Question.Type.MultipleChoice,
                new ArrayList<>(List.of(new Answer("1"), new Answer("3")))
            );
            id = questionRepository.save(question).getId();
        }

        {
            var question = questionRepository.findById(id).get();
            assertThat(question.getTitle()).isEqualTo("What?");
            assertThat(question.getQuestionType()).isEqualTo(Question.Type.MultipleChoice);

            var answers = new ArrayList<>(question.getAnswers());
            assertThat(answers.size()).isEqualTo(2);
            assertThat(answers.get(0).getAnswerChoice()).isEqualTo("1");
            assertThat(answers.get(1).getAnswerChoice()).isEqualTo("3");
        }
    }
}
