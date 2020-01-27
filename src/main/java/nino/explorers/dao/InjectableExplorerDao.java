package nino.explorers.dao;

import dao.ExplorerDao;
import dao.impl.MapExplorerDao;
import model.Explorer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class InjectableExplorerDao implements ExplorerDao {
    private ExplorerDao dao = new MapExplorerDao();

    @NotNull
    public UUID createOrUpdateExplorer(@NotNull Explorer explorer) {
        return dao.createOrUpdateExplorer(explorer);
    }

    public boolean deleteExplorer(@NotNull UUID uuid) {
        return dao.deleteExplorer(uuid);
    }

    @Nullable
    public Explorer getExplorer(@NotNull UUID uuid) {
        return dao.getExplorer(uuid);
    }

    @NotNull
    public List<Explorer> listExplorers() {
        return dao.listExplorers();
    }
}
