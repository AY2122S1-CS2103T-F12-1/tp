package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlySmartNus;
import seedu.address.model.SmartNus;

public class JsonSmartNusStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readSmartNus(null));
    }

    private java.util.Optional<ReadOnlySmartNus> readSmartNus(String filePath) throws Exception {
        return new JsonSmartNusStorage(Paths.get(filePath)).readSmartNus(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readSmartNus("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readSmartNus("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readSmartNus("invalidPersonAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readSmartNus("invalidAndValidPersonAddressBook.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        SmartNus original = getTypicalAddressBook();
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
    private void saveAddressBook(ReadOnlySmartNus smartNus, String filePath) {
        try {
            new JsonSmartNusStorage(Paths.get(filePath))
                    .saveSmartNus(smartNus, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(new SmartNus(), null));
    }
}
