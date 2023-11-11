package SYSC4806.survey;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class GetResultsController {

    /**
     * View results of a poll
     */
    @GetMapping(value={"/view-polls/results"})
    public String viewResults(@RequestParam Poll poll, Model model) {
        model.addAttribute("poll", poll);

        return "view-results";
    }
}
