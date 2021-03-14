package com.rpuch.moretestcontainers.janusgraph;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

/**
 * @author Roman Puchkovskiy
 */
public class JanusgraphContainer extends GenericContainer<JanusgraphContainer> {
    private static final DockerImageName IMAGE_NAME = DockerImageName.parse("janusgraph/janusgraph")
            .withTag("0.5.3");
    private static final int PORT = 8182;

    public JanusgraphContainer() {
        this(IMAGE_NAME);
    }

    public JanusgraphContainer(DockerImageName dockerImageName) {
        super(dockerImageName);

        withExposedPorts(PORT);
        waitingFor(Wait.forLogMessage("(.*)Channel started at port " + PORT + "\\.\\s*$", 1));
    }

    public int getMappedServerPort() {
        return getMappedPort(PORT);
    }
}
