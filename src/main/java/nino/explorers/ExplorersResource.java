package nino.explorers;

import model.Explorer;
import nino.explorers.dao.InjectableExplorerDao;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
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
    @Operation(summary = "Get an explorer")
    @APIResponse(description = "JSON containing the explorer",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Explorer.class)))
    public Explorer getExplorer(@NotNull UUID uuid) {
        return explorerDao.getExplorer(uuid);
    }

    @NotNull
    @Override
    public List<Explorer> listExplorers() {
        return explorerDao.listExplorers();
    }
}
