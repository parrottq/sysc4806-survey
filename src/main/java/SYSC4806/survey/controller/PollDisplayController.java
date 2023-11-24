package SYSC4806.survey.controller;

import SYSC4806.survey.model.Answer;
import SYSC4806.survey.model.Poll;
import SYSC4806.survey.model.Question;
import SYSC4806.survey.repository.AnswerRepository;
import SYSC4806.survey.repository.PollRepository;
import SYSC4806.survey.repository.QuestionRepository;
import SYSC4806.survey.websocket.PollResultsHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
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
    private final PollResultsHandler pollResultsHandler;

    @Autowired
    public PollDisplayController(PollRepository repo, AnswerRepository answerRepo, QuestionRepository questionRepo, PollResultsHandler pollResultsHandler){
        this.repo = repo;
        this.answerRepo = answerRepo;
        this.questionRepo = questionRepo;
        this.pollResultsHandler = pollResultsHandler;
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
    public String saveAnswers(@RequestBody Poll referencePoll){
        Poll actualPoll;
        if (repo.findById(referencePoll.getId()).isPresent()){
            actualPoll = repo.findById(referencePoll.getId()).get();
            for(Question q: referencePoll.getQuestions()) {
                String referenceId = String.valueOf(q.getId());
                ArrayList<Answer> referenceAnswers = (ArrayList<Answer>) q.getAnswers();
                for (Question actualQuestion: actualPoll.getQuestions()){
                    if (referenceId.equals(String.valueOf(actualQuestion.getId()))){
                        List<Answer> newList = Stream.concat(referenceAnswers.stream(), actualQuestion.getAnswers().stream()).toList();
                        answerRepo.saveAll(newList);
                        actualQuestion.setAnswers(newList);
                    }
                    questionRepo.save(actualQuestion);
                }
            }
            actualPoll.setClosed(true);
            repo.save(actualPoll);
            try {
                pollResultsHandler.pushPollUpdate(actualPoll.getId());
            } catch (JsonProcessingException ignored) {}
        }
        return "view-polls";
    }
}