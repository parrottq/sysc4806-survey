package SYSC4806.survey.tests;

import SYSC4806.survey.Poll;
import SYSC4806.survey.PollDisplayController;
import SYSC4806.survey.PollRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class DisplayControllerTest {
    @Autowired
    private PollRepository pollRepository;
    @Autowired
    private PollDisplayController controller;

    private Model model;

    @Test
    public void testDisplayOfPolls(){
//        pollRepository.save(new Poll());
//        controller = new PollDisplayController(pollRepository, answerRepo);
//        assertNotNull(controller.displayPolls(model));
    }
    @Test
    public void testViewingOfPoll(){
        //TODO figure out how to mock model
    }
}
