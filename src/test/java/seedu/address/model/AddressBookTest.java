package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.question.Question;
import seedu.address.model.question.exceptions.DuplicatePersonException;
import seedu.address.testutil.QuestionBuilder;

public class AddressBookTest {

    private final SmartNus addressBook = new SmartNus();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getQuestionList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        SmartNus newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two questions with the same identity fields
        Question editedAlice = new QuestionBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Question> newQuestions = Arrays.asList(ALICE, editedAlice);
        SmartNusStub newData = new SmartNusStub(newQuestions);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasQuestion(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasQuestion(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addQuestion(ALICE);
        assertTrue(addressBook.hasQuestion(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addQuestion(ALICE);
        Question editedAlice = new QuestionBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasQuestion(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getQuestionList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose questions list can violate interface constraints.
     */
    private static class SmartNusStub implements ReadOnlySmartNus {
        private final ObservableList<Question> questions = FXCollections.observableArrayList();

        SmartNusStub(Collection<Question> questions) {
            this.questions.setAll(questions);
        }

        @Override
        public ObservableList<Question> getQuestionList() {
            return questions;
        }
    }

}
