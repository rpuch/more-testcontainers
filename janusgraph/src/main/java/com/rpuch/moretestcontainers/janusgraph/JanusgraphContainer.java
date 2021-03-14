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

import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.process.remote.RemoteConnection;
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

    public RemoteConnection openConnection() {
        return DriverRemoteConnection.using(getContainerIpAddress(), getMappedServerPort());
    }

    public Cluster connectToCluster() {
        return Cluster.build(getContainerIpAddress())
                .port(getMappedServerPort())
                .create();
    }
}
