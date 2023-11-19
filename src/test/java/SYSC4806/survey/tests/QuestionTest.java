package SYSC4806.survey.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import SYSC4806.survey.model.Answer;
import SYSC4806.survey.model.Question;
import SYSC4806.survey.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class QuestionTest {
    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void testQuestion() {
        for (var _elem: questionRepository.findAll()) {
            assertThat(false).isTrue(); // Should be empty
        }

        UUID id;

        {
            var question = new Question("What?", Question.Type.MultipleChoice,
                    new ArrayList<>(List.of(new Answer("1"), new Answer("2"), new Answer("3"))),
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

    @Test
    void testSettersAndGetters() {
        Question question = new Question();
        String title = "My Questions";
        Question.Type type = Question.Type.MultipleChoice;
        List<Answer> possibleChoices = new ArrayList<>();
        possibleChoices.add(new Answer("Choice 1"));
        possibleChoices.add(new Answer("Choice 2"));
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer("Choice 1"));

        question.setTitle(title);
        question.setQuestionType(type);
        question.setPossibleChoices(possibleChoices);
        question.setAnswers(answers);

        assertEquals(title, question.getTitle());
        assertEquals(type, question.getQuestionType());
        assertEquals(possibleChoices, question.getPossibleChoices());
        assertEquals(answers, question.getAnswers());
    }

}
