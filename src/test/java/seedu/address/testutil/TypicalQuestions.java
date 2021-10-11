package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_IMPORTANCE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_IMPORTANCE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.SmartNus;
import seedu.address.model.question.Question;

/**
 * A utility class containing a list of {@code Question} objects to be used in tests.
 */
public class TypicalQuestions {

    public static final Question ALICE = new QuestionBuilder().withName("Alice Pauline")
            .withImportance("1")
            .withTags("friends").build();
    public static final Question BENSON = new QuestionBuilder().withName("Benson Meier")
            .withImportance("2")
            .withTags("owesMoney", "friends").build();
    public static final Question CARL = new QuestionBuilder().withName("Carl Kurz").withImportance("3")
            .build();
    public static final Question DANIEL = new QuestionBuilder().withName("Daniel Meier").withImportance("1")
            .withTags("friends").build();
    public static final Question ELLE = new QuestionBuilder().withName("Elle Meyer").withImportance("2")
            .build();
    public static final Question FIONA = new QuestionBuilder().withName("Fiona Kunz").withImportance("3")
            .build();
    public static final Question GEORGE = new QuestionBuilder().withName("George Best").withImportance("1")
            .build();

    // Manually added
    public static final Question HOON = new QuestionBuilder().withName("Hoon Meier").withImportance("2")
            .build();
    public static final Question IDA = new QuestionBuilder().withName("Ida Mueller").withImportance("3")
            .build();

    // Manually added - Question's details found in {@code CommandTestUtil}
    public static final Question AMY = new QuestionBuilder().withName(VALID_NAME_AMY)
            .withImportance(VALID_IMPORTANCE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Question BOB = new QuestionBuilder().withName(VALID_NAME_BOB)
            .withImportance(VALID_IMPORTANCE_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalQuestions() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical questions.
     */

    public static SmartNus getTypicalAddressBook() {
        SmartNus ab = new SmartNus();
        for (Question question : getTypicalQuestions()) {
            ab.addQuestion(question);
        }
        return ab;
    }

    public static List<Question> getTypicalQuestions() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
