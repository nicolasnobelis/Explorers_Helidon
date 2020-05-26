package nino.explorers;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

@ApplicationScoped
@ApplicationPath("/rest")
public class ExplorersApplication extends Application {
    /**
     * we need to register the filter because of
     * https://github.com/oracle/helidon/issues/1432
     */
    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(ExplorersResource.class, ResponseFilter.class);
    }
}
