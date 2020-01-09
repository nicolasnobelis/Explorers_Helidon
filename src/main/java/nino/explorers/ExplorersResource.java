package nino.explorers;

import model.Explorer;
import nino.explorers.dao.InjectableExplorerDao;
import org.jetbrains.annotations.NotNull;
import rest.ExplorerService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import java.util.List;
import java.util.UUID;


@Path("/")
@RequestScoped
public class ExplorersResource implements ExplorerService {
    @Inject
    private InjectableExplorerDao explorerDao;

    @NotNull
    @Override
    public UUID createOrUpdateExplorer(@NotNull Explorer explorer) {
        return explorerDao.createOrUpdateExplorer(explorer);
    }

    @Override
    public boolean deleteExplorer(@NotNull UUID uuid) {
        return false; // TODO
    }

    @Override
    public Explorer getExplorer(@NotNull UUID uuid) {
        return explorerDao.getExplorer(uuid);
    }

    @NotNull
    @Override
    public List<Explorer> listExplorers() {
        return List.of(); // TODO
    }
}
