[![Maven Central](https://img.shields.io/maven-central/v/com.rpuch.more-testcontainers/more-testcontainers-parent.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.rpuch.more-testcontainers%22%20AND%20a:%22more-testcontainers-parent%22)
[![Build Status](https://github.com/rpuch/more-testcontainers/actions/workflows/maven.yml/badge.svg)](https://github.com/rpuch/more-testcontainers/actions/workflows/maven.yml)
[![codecov](https://codecov.io/gh/rpuch/more-testcontainers/branch/master/graph/badge.svg?token=W2Y9TCBXNE)](https://codecov.io/gh/rpuch/more-testcontainers)

# More Testcontainers #

This is collection of additional [Test containers](https://www.testcontainers.org/)

## gremlin-server ##

### Maven ###

```xml
<dependency>
  <groupId>com.rpuch.more-testcontainers</groupId>
  <artifactId>gremlin-server-testcontainer</artifactId>
  <version>1.0.0</version>
  <scope>test</scope>
</dependency>
```

### Gradle ###

```
testRuntime 'com.rpuch.more-testcontainers:gremlin-server-testcontainer:1.0.0'
```

### Junit 5 test ###

```java
@Testcontainers
class MyTest {
    @Container
    private GremlinServerContainer gremlinServer = new GremlinServerContainer();

    @Test
    void simpleTest() throws Exception {
        try (RemoteConnection connection = gremlinServer.openConnection()) {
            GraphTraversalSource g = AnonymousTraversalSource.traversal().withRemote(connection);

            long totalVertices = g.V().count().next();
            assertThat(totalVertices).isEqualTo(0);
        }
    }
}
```

## JanusGraph ##

### Maven ###

```xml
<dependency>
  <groupId>com.rpuch.more-testcontainers</groupId>
  <artifactId>janusgraph-testcontainer</artifactId>
  <version>1.0.0</version>
  <scope>test</scope>
</dependency>
```

### Gradle ###

```
testRuntime 'com.rpuch.more-testcontainers:janusgraph-testcontainer:1.0.0'
```

### Junit 5 test ###

```java
@Testcontainers
class MyTest {
    @Container
    private JanusgraphContainer janusgraph = new JanusgraphContainer();

    @Test
    void simpleTest() throws Exception {
        try (RemoteConnection connection = janusgraph.openConnection()) {
            GraphTraversalSource g = AnonymousTraversalSource.traversal().withRemote(connection);

            long totalVertices = g.V().count().next();
            assertThat(totalVertices).isEqualTo(0);
        }
    }
}
```
