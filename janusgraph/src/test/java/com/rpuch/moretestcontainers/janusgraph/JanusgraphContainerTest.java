package com.rpuch.moretestcontainers.janusgraph;

import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.process.remote.RemoteConnection;
import org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Roman Puchkovskiy
 */
@Testcontainers
class JanusgraphContainerTest {
    @Container
    private final JanusgraphContainer container = new JanusgraphContainer();

    @Test
    void containerStartsAndMakesGremlinServerAccessible() throws Exception {
        try (RemoteConnection connection = openConnection();
                GraphTraversalSource g = openRemoteTraversalSoruce(connection)) {

            long totalVertices = g.V().count().next();

            assertThat(totalVertices).isEqualTo(0);
        }
    }

    private DriverRemoteConnection openConnection() {
        return DriverRemoteConnection.using(container.getContainerIpAddress(), container.getMappedServerPort());
    }

    private GraphTraversalSource openRemoteTraversalSoruce(RemoteConnection connection) {
        return AnonymousTraversalSource.traversal().withRemote(connection);
    }
}