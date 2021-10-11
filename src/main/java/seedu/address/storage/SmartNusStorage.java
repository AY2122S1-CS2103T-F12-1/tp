package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlySmartNus;
import seedu.address.model.SmartNus;

/**
 * Represents a storage for {@link SmartNus}.
 */
public interface SmartNusStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getSmartNusFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlySmartNus}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlySmartNus> readSmartNus() throws DataConversionException, IOException;

    /**
     * @see #getSmartNusFilePath()
     */
    Optional<ReadOnlySmartNus> readSmartNus(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlySmartNus} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveSmartNus(ReadOnlySmartNus addressBook) throws IOException;

    /**
     * @see #saveSmartNus(ReadOnlySmartNus)
     */
    void saveSmartNus(ReadOnlySmartNus addressBook, Path filePath) throws IOException;

}
