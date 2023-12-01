package SYSC4806.survey.controller;

import SYSC4806.survey.model.Answer;
import SYSC4806.survey.model.Poll;
import SYSC4806.survey.model.Question;
import SYSC4806.survey.repository.AnswerRepository;
import SYSC4806.survey.repository.PollRepository;
import SYSC4806.survey.repository.QuestionRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity savePoll(@RequestBody Poll poll, ModelMap modelMap, HttpServletResponse response) {
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

        //Create creation cookie
        ResponseCookie responseCookie = ResponseCookie.from("poll-created",poll.getId().toString())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(7 * 24 * 60 * 60) //One week
                .domain("localhost")
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).build();
    }
}
