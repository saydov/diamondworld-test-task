package ru.saydov.bosses.api.database.sqlite;

import lombok.NonNull;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jetbrains.annotations.Nullable;
import ru.saydov.bosses.api.database.DatabaseBossDao;

import java.util.List;

/**
 * @author saydov
 */
public interface DatabaseBossSQLiteDao extends DatabaseBossDao {

    @SqlQuery("INSERT OR IGNORE INTO records (boss_id, data) VALUES (:ID, :DATA)")
    void saveRecord(final @NonNull @Bind("ID") String id,
                    final @NonNull @Bind("DATA") String data);

    @SqlQuery("SELECT data FROM records WHERE boss_id = :ID")
    @Nullable List<String> getRecordsById(final @NonNull @Bind("ID") String id);

}
