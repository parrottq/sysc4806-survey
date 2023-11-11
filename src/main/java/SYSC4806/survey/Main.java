package SYSC4806.survey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Array;
import java.util.ArrayList;

@SpringBootApplication
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public boolean checkIfTrue(boolean bool) {
        return bool;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner demo(PollRepository pollRepository) {
        return (args) -> {
            log.info("Boot");

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

            Poll p = new Poll("Hey", aq);

            pollRepository.save(p);

            // fetch all Polls
            log.info("AddressBooks found with findAll():");
            log.info("-------------------------------");
            for (Poll poll : pollRepository.findAll()) {
                log.info("Poll id=" + poll.getId());
                log.info("--------------------------------");
                for (Question question : poll.getQuestions()) {
                    log.info(question.toString());
                    for (Answer answer : question.getAnswers()) {
                        log.info(answer.toString());
                    }
                }
                log.info("--------------------------------");
            }
            log.info("");
        };
    }
}
