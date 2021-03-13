package com.rpuch.testcontainers.gremlinserver;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

/**
 * @author Roman Puchkovskiy
 */
public class GremlinServerContainer extends GenericContainer<GremlinServerContainer> {
    private static final DockerImageName IMAGE_NAME = DockerImageName.parse("tinkerpop/gremlin-server")
            .withTag("3.4.10");
    private static final int PORT = 8182;

    public GremlinServerContainer() {
        this(IMAGE_NAME);
    }

    public GremlinServerContainer(DockerImageName dockerImageName) {
        super(dockerImageName);

        withExposedPorts(PORT);
        waitingFor(Wait.forLogMessage("(.*)Channel started at port " + PORT + "\\.\\s*$", 1));
    }

    public int getMappedServerPort() {
        return getMappedPort(PORT);
    }
}
