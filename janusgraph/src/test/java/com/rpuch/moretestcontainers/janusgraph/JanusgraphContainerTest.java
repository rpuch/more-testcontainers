package com.rpuch.moretestcontainers.janusgraph;

import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.process.remote.RemoteConnection;
import org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Roman Puchkovskiy
 */
@Testcontainers
class JanusgraphContainerTest {
    @Container
    private static final JanusgraphContainer container = new JanusgraphContainer();

    @Test
    void containerStartsAndMakesGremlinServerAccessible() throws Exception {
        try (RemoteConnection connection = container.openConnection()) {
            assertThatRemoteConnectionIsAlive(connection);
        }
    }

    private void assertThatRemoteConnectionIsAlive(RemoteConnection connection) throws Exception {
        try (GraphTraversalSource g = openRemoteTraversalSource(connection)) {
            assertThatTraversalSourceIsAlive(g);
        }
    }

    private GraphTraversalSource openRemoteTraversalSource(RemoteConnection connection) {
        return AnonymousTraversalSource.traversal().withRemote(connection);
    }

    private void assertThatTraversalSourceIsAlive(GraphTraversalSource g) {
        long totalVertices = g.V().count().next();

        assertThat(totalVertices).isEqualTo(0);
    }

    @Test
    void connectsToCluster() {
        Cluster cluster = container.connectToCluster();

        assertThatClusterIsAlive(cluster);
    }

    private void assertThatClusterIsAlive(Cluster cluster) {
        Client client = cluster.connect(randomSessionId());
        long count = client.submit("g.V().count().next()").one().getLong();
        assertThat(count).isEqualTo(0);
    }

    private String randomSessionId() {
        return UUID.randomUUID().toString();
    }
}