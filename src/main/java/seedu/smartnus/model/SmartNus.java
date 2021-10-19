package seedu.smartnus.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.smartnus.model.note.Note;
import seedu.smartnus.model.note.NoteList;
import seedu.smartnus.model.question.Question;
import seedu.smartnus.model.question.UniqueQuestionList;

/**
 * Wraps all data at the SmartNus level
 * Duplicates are not allowed (by .isSameQuestion comparison)
 */
public class SmartNus implements ReadOnlySmartNus {

    private final UniqueQuestionList questions;
    private final NoteList notes;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        questions = new UniqueQuestionList();
        notes = new NoteList();
    }

    public SmartNus() {}

    /**
     * Creates a SmartNus using the Questions in the {@code toBeCopied}
     */
    public SmartNus(ReadOnlySmartNus toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the question list with {@code questions}.
     * {@code questions} must not contain duplicate questions.
     */
    public void setQuestions(List<Question> questions) {
        this.questions.setQuestions(questions);
    }

    public void setNotes(List<Note> notes) {
        this.notes.setNotes(notes);
    }
    /**
     * Resets the existing data of this {@code SmartNus} with {@code newData}.
     */
    public void resetData(ReadOnlySmartNus newData) {
        requireNonNull(newData);

        setQuestions(newData.getQuestionList());
        setNotes(newData.getNoteList());
    }

    //// question-level operations

    /**
     * Returns true if a question with the same identity as {@code question} exists in the SmarNus.
     */
    public boolean hasQuestion(Question question) {
        requireNonNull(question);
        return questions.contains(question);
    }

    /**
     * Adds a question to {@code SmartNus}.
     * The question must not already exist in {@code SmartNus}.
     */
    public void addQuestion(Question p) {
        questions.add(p);
    }

    /**
     * Replaces the given question {@code target} in the list with {@code editedQuestion}.
     * {@code target} must exist in the {@code SmartNus}.
     * The question identity of {@code editedQuestion} must not be the same as another existing question
     * in the {@code SmartNus}.
     */
    public void setQuestion(Question target, Question editedQuestion) {
        requireNonNull(editedQuestion);

        questions.setQuestion(target, editedQuestion);
    }

    /**
     * Removes {@code key} from this {@code SmartNus}.
     * {@code key} must exist in the {@code SmartNus}.
     */
    public void removeQuestion(Question key) {
        questions.remove(key);
    }

    ////// note level operations
    /**
     * Adds a note to {@code SmartNus}.
     */
    public void addNote(Note p) {
        notes.add(p);
    }

    /**
     * Replaces the given note {@code target} in the list with {@code editedNote}.
     */
    public void setNote(Note target, Note editedNote) {
        requireNonNull(editedNote);
        notes.setNote(target, editedNote);
    }

    /**
     * Removes {@code key} from this {@code SmartNus}.
     * {@code key} must exist in the {@code SmartNus}.
     */
    public void removeNote(Note key) {
        notes.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return questions.asUnmodifiableObservableList().size() + " questions";
        // TODO: refine later
    }

    @Override
    public ObservableList<Question> getQuestionList() {
        return questions.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Note> getNoteList() {
        return notes.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SmartNus // instanceof handles nulls
                && questions.equals(((SmartNus) other).questions)
                    && notes.equals(((SmartNus) other).notes));
    }

    @Override
    public int hashCode() {
        return questions.hashCode();
    }

    /// getter methods

    /**
     * Returns all the questions as a List.
     * @return A List of all the Questions.
     */
    public List<Question> getQuestionsAsList() {
        List<Question> allQuestions = new ArrayList<>();
        for (Question q : questions) {
            allQuestions.add(q);
        }
        return allQuestions;
    }

    /**
     * Returns all the notes as a List.
     * @return A List of all the Notes.
     */
    public List<Note> getNotesAsList() {
        List<Note> allNotes = new ArrayList<>();
        for (Note n : notes) {
            allNotes.add(n);
        }
        return allNotes;
    }
}
