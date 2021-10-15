package seedu.smartnus.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.smartnus.testutil.Assert.assertThrows;
import static seedu.smartnus.testutil.TypicalQuestions.ALICE;
import static seedu.smartnus.testutil.TypicalQuestions.HOON;
import static seedu.smartnus.testutil.TypicalQuestions.IDA;
import static seedu.smartnus.testutil.TypicalQuestions.getTypicalSmartNus;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.smartnus.commons.exceptions.DataConversionException;
import seedu.smartnus.model.SmartNus;
import seedu.smartnus.model.ReadOnlySmartNus;

public class JsonSmartNusStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSmartNusStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    private java.util.Optional<ReadOnlySmartNus> readAddressBook(String filePath) throws Exception {
        return new JsonSmartNusStorage(Paths.get(filePath)).readSmartNus(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readAddressBook("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidQuestionAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidQuestionAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidQuestionAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook(
                "invalidAndValidQuestionAddressBook.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        SmartNus original = getTypicalSmartNus();
        JsonSmartNusStorage jsonAddressBookStorage = new JsonSmartNusStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveSmartNus(original, filePath);
        ReadOnlySmartNus readBack = jsonAddressBookStorage.readSmartNus(filePath).get();
        assertEquals(original, new SmartNus(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addQuestion(HOON);
        original.removeQuestion(ALICE);
        jsonAddressBookStorage.saveSmartNus(original, filePath);
        readBack = jsonAddressBookStorage.readSmartNus(filePath).get();
        assertEquals(original, new SmartNus(readBack));

        // Save and read without specifying file path
        original.addQuestion(IDA);
        jsonAddressBookStorage.saveSmartNus(original); // file path not specified
        readBack = jsonAddressBookStorage.readSmartNus().get(); // file path not specified
        assertEquals(original, new SmartNus(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlySmartNus addressBook, String filePath) {
        try {
            new JsonSmartNusStorage(Paths.get(filePath))
                    .saveSmartNus(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(new SmartNus(), null));
    }
}
