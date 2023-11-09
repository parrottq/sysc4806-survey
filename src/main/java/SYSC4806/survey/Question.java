package SYSC4806.survey;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.List;


@Entity
public class Question implements Serializable {

    public enum Type {
        MultipleChoice,
        Text
    }

    private UUID id;
    private String title;
    private Type questionType;
    private ArrayList<Answer> possibleChoices;  //Only for MC
    private ArrayList<Answer> answers;  //All answers(responses from user)

    public Question() {
        this("", Type.Text, new ArrayList<>(List.of()), new ArrayList<>());
    }

    public Question(String title, Type questionType, ArrayList<Answer> possibleChoices, ArrayList<Answer> answers) {
        this.title = title;
        this.questionType = questionType;
        this.possibleChoices = possibleChoices;
        this.answers = answers;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String text) {
        this.title = text;
    }

    public Type getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Type questionType) {
        this.questionType = questionType;
    }

    @OneToMany(cascade = { CascadeType.PERSIST })
    public Collection<Answer> getPossibleChoices() {
        return possibleChoices;
    }

    public void setPossibleChoices(Collection<Answer> possibleChoices) {
        this.possibleChoices = new ArrayList<>(possibleChoices);
    }

    @OneToMany(cascade = { CascadeType.PERSIST })
    public Collection<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Collection<Answer> answers) {
        this.answers = new ArrayList<>(answers);
    }

    public String toString() {
        String s = "";
        s += "Title:" + title;
        s += "|id:" + id;
        s += "|choices:" + possibleChoices;

        return s;
    }
}
