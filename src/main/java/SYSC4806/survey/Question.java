package SYSC4806.survey;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
public class Question implements Serializable {

    public enum Type {
        MultipleChoice
    }

    private UUID id;
    private String title;
    private Type questionType;
    private ArrayList<Answer> answers;

    public Question() {
        this("", Type.MultipleChoice, new ArrayList<>());
    }

    public Question(String title, Type questionType, ArrayList<Answer> answers) {
        this.title = title;
        this.questionType = questionType;
        this.answers = answers;
    }

    @Id
    @UuidGenerator
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
    public Collection<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Collection<Answer> answers) {
        this.answers = new ArrayList<>(answers);
    }
}
