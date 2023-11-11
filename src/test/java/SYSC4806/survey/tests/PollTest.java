package SYSC4806.survey.tests;

import SYSC4806.survey.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PollTest {
    @Autowired
    private PollRepository pollRepository;

    @Test
    void testPoll() {


        var question = new Question("What?", Question.Type.MultipleChoice,
                new ArrayList<>(List.of(new Answer("1"), new Answer("2"), new Answer("3"))),
                new ArrayList<>(List.of(new Answer("1"), new Answer("3")))
        );

        UUID id;
        {
            var poll = new Poll("First", new ArrayList<>(List.of(question)));
            id = pollRepository.save(poll).getId();
        }

        {
            var poll = pollRepository.findById(id).get();
            assertThat(poll.getTitle()).isEqualTo("First");
            assertThat(poll.isClosed()).isFalse();

            var questions = new ArrayList<>(poll.getQuestions());
            assertThat(questions.size()).isEqualTo(1);
            var question1 = questions.get(0);
            assertThat(question1.getTitle()).isEqualTo("What?");
        }
    }

    @Test
    void testSettersAndGetters() {
        Poll poll = new Poll();
        String title = "My sample Poll";
        List<Question> questions = new ArrayList<>();

        questions.add(new Question("Question 1", Question.Type.Text, new ArrayList<>(1), new ArrayList<>(2)));
        questions.add(new Question("Question 2", Question.Type.MultipleChoice, new ArrayList<>(3), new ArrayList<>(4)));

        poll.setTitle(title);
        poll.setQuestions(questions);
        assertFalse(poll.isClosed());
        poll.setClosed(true);

        assertEquals(title, poll.getTitle());
        assertEquals(questions, poll.getQuestions());
        assertTrue(poll.isClosed());
    }
}
