package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.question.Question;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Question> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a question with the same identity as {@code question} exists in the address book.
     */
    boolean hasPerson(Question question);

    /**
     * Deletes the given question.
     * The question must exist in the address book.
     */
    void deletePerson(Question target);

    /**
     * Adds the given question.
     * {@code question} must not already exist in the address book.
     */
    void addPerson(Question question);

    /**
     * Replaces the given question {@code target} with {@code editedQuestion}.
     * {@code target} must exist in the address book.
     * The question identity of {@code editedQuestion} must not be the same as another existing question in the address book.
     */
    void setPerson(Question target, Question editedQuestion);

    /** Returns an unmodifiable view of the filtered question list */
    ObservableList<Question> getFilteredPersonList();

    /**
     * Updates the filter of the filtered question list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Question> predicate);
}
