package SYSC4806.survey;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * A controller class for the creation of a poll and the included questions
 */
@Controller
public class CreatePollController {

    @Autowired
    private PollRepository pollRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    /**
     * Creates a new poll for the homepage
     * @param model
     * @return
     */
    @GetMapping(value={"/","/create"})
    public String createPoll(Model model) {
        model.addAttribute("poll", new Poll());

        return "create-poll";
    }

    //Only for testing purposes
    @GetMapping(value="/view-polls")
    public String viewPolls(Model model) {
        List<Poll> polls = StreamSupport.stream(pollRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        model.addAttribute("polls", polls);
        return "view-polls";
    }

    /**
     * Saves a poll passed to it to the repository
     * @param poll
     * @param modelMap
     * @return
     */
    @PostMapping("/save-poll")
    public String savePoll(@RequestBody Poll poll, ModelMap modelMap) {
        System.out.println(poll);
        for(Question q: poll.getQuestions()) {
            for(Answer a: q.getPossibleChoices()) {
                answerRepository.save(a);
            }
            questionRepository.save(q);
        }

        pollRepository.save(poll);

        List<Poll> polls = StreamSupport.stream(pollRepository.findAll().spliterator(), false)
                        .collect(Collectors.toList());
        modelMap.addAttribute("polls", polls);
        return "view-polls";
    }
}