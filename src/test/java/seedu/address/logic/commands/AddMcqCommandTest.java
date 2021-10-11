package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlySmartNus;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.SmartNus;
import seedu.address.model.question.Question;
import seedu.address.testutil.QuestionBuilder;

class AddMcqCommandTest {
    @Test
    public void constructor_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddMcqCommand(null));
    }

    @Test
    public void execute_questionAcceptedByModel_addSuccessful() throws Exception {
        AddMcqCommandTest.ModelStubAcceptingQuestionAdded modelStub =
                new AddMcqCommandTest.ModelStubAcceptingQuestionAdded();
        Question validQuestion = new QuestionBuilder().build();

        CommandResult commandResult = new AddMcqCommand(validQuestion).execute(modelStub);

        assertEquals(String.format(AddMcqCommand.MESSAGE_SUCCESS, validQuestion), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validQuestion), modelStub.questionsAdded);
    }

    @Test
    public void execute_duplicateQuestion_throwsCommandException() {
        Question validQuestion = new QuestionBuilder().build();
        AddMcqCommand addCommand = new AddMcqCommand(validQuestion);
        AddMcqCommandTest.ModelStub modelStub = new AddMcqCommandTest.ModelStubWithQuestion(validQuestion);

        assertThrows(CommandException.class,
                AddCommand.MESSAGE_DUPLICATE_QUESTION, () -> addCommand.execute(modelStub));
    }

    /**
     * A Model stub that contains a single question.
     */
    private class ModelStubWithQuestion extends AddMcqCommandTest.ModelStub {
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

    @Test
    public void equals() {
        Question alice = new QuestionBuilder().withName("Alice").build();
        Question bob = new QuestionBuilder().withName("Bob").build();
        AddMcqCommand addAliceCommand = new AddMcqCommand(alice);
        AddMcqCommand addBobCommand = new AddMcqCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddMcqCommand addAliceCommandCopy = new AddMcqCommand(alice);
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
        public Path getSmartNusFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSmartNusFilePath(Path smartNusFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addQuestion(Question question) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSmartNus(ReadOnlySmartNus newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlySmartNus getSmartNus() {
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
     * A Model stub that always accept the question being added.
     */
    private class ModelStubAcceptingQuestionAdded extends AddMcqCommandTest.ModelStub {
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
        public ReadOnlySmartNus getSmartNus() {
            return new SmartNus();
        }
    }
}
