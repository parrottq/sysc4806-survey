package SYSC4806.survey;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.util.UUID;

@Entity
public class Answer implements Serializable {
    private UUID id;
    private String answerChoice;

    public Answer() {
        this("");
    }

    public Answer(String answerChoice) {
        this.answerChoice = answerChoice;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAnswerChoice() {
        return answerChoice;
    }

    public void setAnswerChoice(String answerChoice) {
        this.answerChoice = answerChoice;
    }

    public String toString() {
        String s = "";
        s += "AnswerChoice:" + answerChoice;
        s += "|id:" + id;
        return s;
    }
}
