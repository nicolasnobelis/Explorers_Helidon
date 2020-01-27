/*
 * Copyright (c) 2018, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nino.explorers;

import dao.ExplorerDao;
import dao.impl.MapExpeditionDao;
import dao.impl.MapShipDao;
import io.helidon.microprofile.server.Server;
import nino.explorers.dao.InjectableExplorerDao;
import utils.SampleDataGenerator;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

/**
 * The application main class.
 */
public final class Main {

    /**
     * Cannot be instantiated.
     */
    private Main() { }

    /**
     * Application main entry point.
     * @param args command line arguments
     * @throws IOException if there are problems reading logging properties
     */
    public static void main(final String[] args) throws IOException {
        // load logging configuration
        setupLogging();

        // start the server
        Server server = startServer();

        System.out.println("http://localhost:" + server.port() + "/greet");
    }

    /**
     * Start the server.
     * @return the created {@link Server} instance
     */
    static Server startServer() {
        // Server will automatically pick up configuration from
        // microprofile-config.properties
        // and Application classes annotated as @ApplicationScoped

        Server server = Server.create();
        server.start();

        // init the server with some data
        // todo beautify ?
        BeanManager beanManager = server.cdiContainer().getBeanManager();
        Bean<InjectableExplorerDao> bean = (Bean<InjectableExplorerDao>) beanManager.getBeans(InjectableExplorerDao.class).iterator().next();
        CreationalContext<InjectableExplorerDao> context = beanManager.createCreationalContext(bean);
        InjectableExplorerDao explorerDao = (InjectableExplorerDao) beanManager.getReference(bean, ExplorerDao.class, context);

        // todo fix other daos
        SampleDataGenerator.INSTANCE.generateData(new MapShipDao(), explorerDao, new MapExpeditionDao());

        return server;
    }

    /**
     * Configure logging from logging.properties file.
     */
    private static void setupLogging() throws IOException {
        try (InputStream is = Main.class.getResourceAsStream("/logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        }
    }
}
