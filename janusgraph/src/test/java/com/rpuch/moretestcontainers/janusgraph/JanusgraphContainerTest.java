/*
 * Copyright 2021 more-testcontainers contributors
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