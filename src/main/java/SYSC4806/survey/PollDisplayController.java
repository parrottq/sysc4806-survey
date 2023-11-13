package SYSC4806.survey;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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


    @PostMapping(value = "/save-form")
    public String saveAnswers(Model model, @RequestBody Poll referencePoll){
        Poll actualPoll;
        if (repo.findById(referencePoll.getId()).isPresent()){
             actualPoll = repo.findById(referencePoll.getId()).get();
            for(Question q: referencePoll.getQuestions()) {
                String referenceId = String.valueOf(q.getId());
                ArrayList<Answer> referenceAnswers = (ArrayList<Answer>) q.getAnswers();
                for (Question actualQuestion: actualPoll.getQuestions()){
                    if (referenceId.equals(String.valueOf(actualQuestion.getId()))){
                        System.out.println("test4");
                        List<Answer> newList = Stream.concat(actualQuestion.getAnswers().stream(), referenceAnswers.stream()).toList();
                        System.out.println(newList);
                        actualQuestion.setAnswers(newList);
                    }
                }
            }
        }
        else {
            System.out.println("No ID matches repo");
        }

        ArrayList<Question> qu = (ArrayList<Question>) repo.findById(referencePoll.getId()).get().getQuestions();
        System.out.println(qu);

        return "view-polls";
    }

}

