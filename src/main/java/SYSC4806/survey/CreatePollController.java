package SYSC4806.survey;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A controller class for the creation of a poll and the included questions
 */
@Controller
public class CreatePollController {

    @Autowired
    private PollRepository pollRepository;
    private QuestionRepository questionRepository;

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

    /**
     * Looks up the question ids and adds them to a poll object. Then saves the poll.
     * @param poll
     * @param questionIds
     * @param model
     * @return
     */
    @PostMapping("/save-poll")
    public String savePoll(@RequestParam Poll poll, @RequestParam UUID[] questionIds, @ModelAttribute Model model) {
        ArrayList<Question> questions = new ArrayList<>();
        for(UUID id: questionIds) {
            questions.add(questionRepository.findById(id).orElseThrow());
        } poll.setQuestions(questions);

        pollRepository.save(poll);

        model.addAttribute("poll", poll);

        return "view-poll";
    }

    /**
     * Saves an individual question to the database
     * @param question
     * @param model
     */
    @PostMapping("/save-question")
    public void saveQuestion(@RequestParam Question question, @ModelAttribute Model model) {
        questionRepository.save(question);
    }

}
