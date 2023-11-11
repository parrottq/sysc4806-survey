package SYSC4806.survey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Ibrahim Said
 * @version 1.0
 */
@Controller
public class PollDisplayController {
    private final PollRepository repo;

    @Autowired
    public PollDisplayController(PollRepository repo){this.repo = repo;}

    @GetMapping(value = "/display-polls")
    public String displayPolls(Model model){
        List<Poll> polls = StreamSupport.stream(repo.findAll().spliterator(), false)
                .collect(Collectors.toList());
        model.addAttribute("polls", polls);
        return "display-polls";
    }

    @GetMapping(path = "/current-poll/{id}")
    public String viewPoll(Model model, @PathVariable UUID id){
//        if (repo.findById(id).isPresent()){
//            model.addAttribute("poll", repo.findById(id).get());
//        }
        List<Poll> polls = StreamSupport.stream(repo.findAll().spliterator(), false)
                .collect(Collectors.toList());
        model.addAttribute("polls", polls);
        return "current-poll";
    }

}

