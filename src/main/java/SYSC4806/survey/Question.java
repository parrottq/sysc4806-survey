package SYSC4806.survey;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Question implements Serializable {

    public enum Type {
        MultipleChoice
    }

    private Long id;
    private String title;
    private Type questionType;
    private ArrayList<Answer> possibleChoices;
    private ArrayList<Answer> answers;

    public Question() {
        this("", Type.MultipleChoice, new ArrayList<>(List.of()), new ArrayList<>());
    }

    public Question(String title, Type questionType, ArrayList<Answer> possibleChoices, ArrayList<Answer> answers) {
        this.title = title;
        this.questionType = questionType;
        this.possibleChoices = possibleChoices;
        this.answers = answers;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
