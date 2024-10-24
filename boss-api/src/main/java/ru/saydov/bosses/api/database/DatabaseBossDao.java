package ru.saydov.bosses.api.database;

import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author saydov
 */
public interface DatabaseBossDao {

    void saveRecord(
            final @NonNull String id, final @NonNull String time, final @NonNull String data
    );

    @Nullable List<String> getRecordsById(final @NonNull String id);

}
