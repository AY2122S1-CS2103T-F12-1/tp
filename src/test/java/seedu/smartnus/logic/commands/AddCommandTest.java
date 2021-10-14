package seedu.smartnus.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.smartnus.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.smartnus.commons.core.GuiSettings;
import seedu.smartnus.logic.commands.exceptions.CommandException;
import seedu.smartnus.model.AddressBook;
import seedu.smartnus.model.Model;
import seedu.smartnus.model.ReadOnlyAddressBook;
import seedu.smartnus.model.ReadOnlyUserPrefs;
import seedu.smartnus.model.question.Question;
import seedu.smartnus.testutil.QuestionBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_questionAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingQuestionAdded modelStub = new ModelStubAcceptingQuestionAdded();
        Question validQuestion = new QuestionBuilder().build();

        CommandResult commandResult = new AddCommand(validQuestion).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validQuestion), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validQuestion), modelStub.questionsAdded);
    }

    @Test
    public void execute_duplicateQuestion_throwsCommandException() {
        Question validQuestion = new QuestionBuilder().build();
        AddCommand addCommand = new AddCommand(validQuestion);
        ModelStub modelStub = new ModelStubWithQuestion(validQuestion);

        assertThrows(CommandException.class,
                AddCommand.MESSAGE_DUPLICATE_QUESTION, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Question alice = new QuestionBuilder().withName("Alice").build();
        Question bob = new QuestionBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different question -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addQuestion(Question question) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasQuestion(Question question) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteQuestion(Question target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setQuestion(Question target, Question editedQuestion) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Question> getFilteredQuestionList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredQuestionList(Predicate<Question> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single question.
     */
    private class ModelStubWithQuestion extends ModelStub {
        private final Question question;

        ModelStubWithQuestion(Question question) {
            requireNonNull(question);
            this.question = question;
        }

        @Override
        public boolean hasQuestion(Question question) {
            requireNonNull(question);
            return this.question.isSameQuestion(question);
        }
    }

    /**
     * A Model stub that always accept the question being added.
     */
    private class ModelStubAcceptingQuestionAdded extends ModelStub {
        final ArrayList<Question> questionsAdded = new ArrayList<>();

        @Override
        public boolean hasQuestion(Question question) {
            requireNonNull(question);
            return questionsAdded.stream().anyMatch(question::isSameQuestion);
        }

        @Override
        public void addQuestion(Question question) {
            requireNonNull(question);
            questionsAdded.add(question);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
