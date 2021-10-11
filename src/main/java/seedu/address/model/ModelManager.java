package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.question.Question;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final SmartNus smartNus;
    private final UserPrefs userPrefs;
    private final FilteredList<Question> filteredQuestions;

    /**
     * Initializes a ModelManager with the given smartNus and userPrefs.
     */
    public ModelManager(ReadOnlySmartNus smartNus, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(smartNus, userPrefs);

        logger.fine("Initializing with address book: " + smartNus + " and user prefs " + userPrefs);

        this.smartNus = new SmartNus(smartNus);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredQuestions = new FilteredList<>(this.smartNus.getQuestionList());
    }

    public ModelManager() {
        this(new SmartNus(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getSmartNusFilePath() {
        return userPrefs.getSmartNusFilePath();
    }

    @Override
    public void setSmartNusFilePath(Path smartNusFilePath) {
        requireNonNull(smartNusFilePath);
        userPrefs.setSmartNusFilePath(smartNusFilePath);
    }

    //=========== SmartNus ================================================================================

    @Override
    public void setSmartNus(ReadOnlySmartNus smartNus) {
        this.smartNus.resetData(smartNus);
    }

    @Override
    public ReadOnlySmartNus getSmartNus() {
        return smartNus;
    }

    @Override
    public boolean hasQuestion(Question question) {
        requireNonNull(question);
        return smartNus.hasQuestion(question);
    }

    @Override
    public void deleteQuestion(Question target) {
        smartNus.removeQuestion(target);
    }

    @Override
    public void addQuestion(Question question) {
        smartNus.addQuestion(question);
        updateFilteredQuestionList(PREDICATE_SHOW_ALL_QUESTIONS);
    }

    @Override
    public void setQuestion(Question target, Question editedQuestion) {
        requireAllNonNull(target, editedQuestion);

        smartNus.setQuestion(target, editedQuestion);
    }

    //=========== Filtered Question List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Question} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Question> getFilteredQuestionList() {
        return filteredQuestions;
    }

    @Override
    public void updateFilteredQuestionList(Predicate<Question> predicate) {
        requireNonNull(predicate);
        filteredQuestions.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return smartNus.equals(other.smartNus)
                && userPrefs.equals(other.userPrefs)
                && filteredQuestions.equals(other.filteredQuestions);
    }

}
