package SYSC4806.survey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/** Controller for displaying polls and picking specific polls
 * @author Ibrahim Said
 * @version 1.0
 */
@Controller
public class PollDisplayController {
    private final PollRepository repo;
    private final AnswerRepository answerRepo;

    private final QuestionRepository questionRepo;

    @Autowired
    public PollDisplayController(PollRepository repo, AnswerRepository answerRepo, QuestionRepository questionRepo){
        this.repo = repo;
        this.answerRepo = answerRepo;
        this.questionRepo = questionRepo;
    }

    @GetMapping(value = "/display-polls")
    public String displayPolls(Model model){
        List<Poll> polls = StreamSupport.stream(repo.findAll().spliterator(), false)
                .collect(Collectors.toList());
        model.addAttribute("polls", polls);
        return "display-polls";
    }

    @GetMapping(value = "/current-poll/{id}")
    public String viewPoll(Model model, @PathVariable UUID id){
        if (repo.findById(id).isPresent()){
            model.addAttribute("poll", repo.findById(id).get());
        }
        return "current-poll";
    }

    @PostMapping(value = "/current-poll/{id}")
    public String saveAnswers(@PathVariable UUID id, Poll poll){
        if (repo.findById(id).isPresent()){
            poll = repo.findById(id).get();
        }
        for (Question q: poll.getQuestions()){
            for (Answer a: q.getAnswers()){
                answerRepo.save(a);
            }

        }
        repo.save(poll);
        return "current-poll";
    }
}

