package SYSC4806.survey.tests;

import SYSC4806.survey.model.Answer;
import SYSC4806.survey.model.Poll;
import SYSC4806.survey.model.Question;
import SYSC4806.survey.repository.PollRepository;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PollRepositoryTest {

    @Resource
    private PollRepository pollRepository;

    @Test
    public void contextLoads() {
        assertThat(pollRepository).isNotNull();
    }

    @Test
    public void testSavePolls() {
        ArrayList<Answer> al0 = new ArrayList<>();

        Answer a1 = new Answer("Good");
        Answer a2 = new Answer("Bad");
        Answer a3 = new Answer("Good");
        Answer a4 = new Answer("Bad");
        Answer a5 = new Answer("Good");
        Answer a6 = new Answer("Good");
        Answer a7 = new Answer("Good");
        Answer a8 = new Answer("Bad");
        ArrayList<Answer> al1 = new ArrayList<>();
        al1.add(a1);
        al1.add(a2);

        ArrayList<Answer> al2 = new ArrayList<>();
        al2.add(a3);
        al2.add(a4);

        ArrayList<Answer> al3 = new ArrayList<>();
        al3.add(a5);
        al3.add(a6);
        al3.add(a7);
        al3.add(a8);

        Question q1 = new Question("How are you?", Question.Type.Text, al0, al1);
        Question q2 = new Question("What's up?", Question.Type.MultipleChoice, al2, al3);

        ArrayList<Question> aq = new ArrayList<>();

        aq.add(q1);
        aq.add(q2);

        Poll p1 = new Poll("Hey", aq);

        pollRepository.save(p1);

        al0 = new ArrayList<>();

        a1 = new Answer("Good");
        a2 = new Answer("Bad");
        a3 = new Answer("Good");
        a4 = new Answer("Bad");
        a5 = new Answer("Good");
        a6 = new Answer("Good");
        a7 = new Answer("Good");
        a8 = new Answer("Bad");
        al1 = new ArrayList<>();
        al1.add(a1);
        al1.add(a2);

        al2 = new ArrayList<>();
        al2.add(a3);
        al2.add(a4);

        al3 = new ArrayList<>();
        al3.add(a5);
        al3.add(a6);
        al3.add(a7);
        al3.add(a8);

        q1 = new Question("How are you?", Question.Type.Text, al0, al1);
        q2 = new Question("What's up?", Question.Type.MultipleChoice, al2, al3);

        aq = new ArrayList<>();

        aq.add(q1);
        aq.add(q2);

        Poll p2 = new Poll("Yo", aq);

        pollRepository.save(p2);

        ArrayList<Poll> testA = new ArrayList<>();
        testA.add(p1);
        testA.add(p2);
        Iterator<Poll> expected = testA.iterator();

        Iterator<Poll> actual = pollRepository.findAll().iterator();

        for (;expected.hasNext() && actual.hasNext();) {
            assertThat(actual.next()).isEqualTo(expected.next());
        }
        assertThat(expected.hasNext()).isEqualTo(false);
        assertThat(actual.hasNext()).isEqualTo(false);
    }
}