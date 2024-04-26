package com.github.sszuev

import io.quarkus.arc.properties.UnlessBuildProperty
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Produces
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.Topology

@ApplicationScoped
@UnlessBuildProperty(name = "app.config.topics", stringValue = "", enableIfMissing = false)
class AppTopologyFactory {
    @Produces
    fun buildTopology(): Topology = StreamsBuilder().build()
}