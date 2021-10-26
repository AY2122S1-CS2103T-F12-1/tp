package seedu.smartnus.model.note;

import static java.util.Objects.requireNonNull;
import static seedu.smartnus.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.smartnus.model.question.exceptions.QuestionNotFoundException;

public class NoteList implements Iterable<Note> {
    private final ObservableList<Note> internalList = FXCollections.observableArrayList();
    private final ObservableList<Note> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds a note to the list.
     */
    public void add(Note toAdd) {
        requireNonNull(toAdd);
        // todo: check if note is duplicated
        internalList.add(toAdd);
    }

    /**
     * Replaces the note {@code target} in the list with {@code editedNote}.
     * {@code target} must exist in the list.
     * The note identity of {@code editedNote} must not be the same as another existing question in the list.
     */
    public void setNote(Note target, Note editedNote) {
        requireAllNonNull(target, editedNote);

        int index = internalList.indexOf(target);
        if (index == -1) {
            // todo: create an exception class for notes
            throw new RuntimeException();
        }

        internalList.set(index, editedNote);
    }

    /*
    public void setNotes(NoteList replacement) {
        requireNonNull(replacement);
        setNotes(replacement.internalList);
    }
    */

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Note> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Note> iterator() {
        return internalList.iterator();
    }

    /**
     * Replaces the contents of this list with {@code notes}.
     * {@code notes} must not contain duplicate notes.
     */
    public void setNotes(List<Note> notes) {
        requireAllNonNull(notes);
        internalList.setAll(notes);
    }

    /**
     * Removes the equivalent question from the list.
     * The question must exist in the list.
     */
    public void remove(Note toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            // todo: create an exception for this.
            throw new QuestionNotFoundException();
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NoteList // instanceof handles nulls
                && internalList.equals(((NoteList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}