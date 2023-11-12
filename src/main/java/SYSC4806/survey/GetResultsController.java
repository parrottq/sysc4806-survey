package SYSC4806.survey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.UUID;

@Controller
public class GetResultsController {

    @Autowired
    private PollRepository pollRepository;

    /**
     * View results of a poll
     */
    @GetMapping("/view-polls/results")
    public String getResults(@RequestParam String id, Model model) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return "view-polls";
        }

        Optional<Poll> poll = pollRepository.findById(uuid);

        if (poll.isPresent()) {
            model.addAttribute("poll", poll.get());
            return "view-results";
        }
        return "view-polls";
    }
}
