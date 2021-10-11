package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.question.Question;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlySmartNus {

    /**
     * Returns an unmodifiable view of the questions list.
     * This list will not contain any duplicate questions.
     */
    ObservableList<Question> getQuestionList();

}
