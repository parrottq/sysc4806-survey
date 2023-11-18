package SYSC4806.survey.websocket.model;

import java.util.UUID;

public class PollResultsRequest {
    public PollResultsRequestType type;
    public UUID pollId;

    public PollResultsRequest() {
        this(PollResultsRequestType.Subscribe, UUID.randomUUID());
    }

    public PollResultsRequest(PollResultsRequestType type, UUID pollId ) {
        this.type = type;
        this.pollId = pollId;
    }
}
