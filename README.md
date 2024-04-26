## A demo project containing `quarkus-kafka-streams` & `quarkus-rest` for reproducing https://github.com/quarkusio/quarkus/issues/40315

It contains one class-producer:
```kotlin
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
```
application.properties:
```properties
app.config.topics=
```
gradle-dependencies:
```groovy
dependencies {
    implementation enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}")
    implementation 'io.quarkus:quarkus-kotlin'
    implementation 'io.quarkus:quarkus-kafka-streams'
    implementation 'org.jetbrains.kotlin:kotlin-stdlib-jdk8'
    implementation 'io.quarkus:quarkus-arc'
    implementation 'commons-codec:commons-codec'
    implementation 'io.quarkus:quarkus-rest'
}
```

Starting app causes exception:
```
2024-04-26 17:34:58,629 ERROR [io.qua.dep.dev.IsolatedDevModeMain] (main) Failed to start quarkus: java.lang.RuntimeException: io.quarkus.builder.BuildException: Build failure: Build failed due to errors
	[error]: Build step io.quarkus.arc.deployment.ArcProcessor#validate threw an exception: jakarta.enterprise.inject.spi.DeploymentException: jakarta.enterprise.inject.UnsatisfiedResolutionException: Unsatisfied dependency for type org.apache.kafka.streams.Topology and qualifiers [@Default]
	- injection target: io.quarkus.kafka.streams.runtime.devui.KafkaStreamsJsonRPCService#topology
	- declared on CLASS bean [types=[java.lang.Object, io.quarkus.kafka.streams.runtime.devui.KafkaStreamsJsonRPCService], qualifiers=[@Default, @Any], target=io.quarkus.kafka.streams.runtime.devui.KafkaStreamsJsonRPCService]
	at io.quarkus.arc.processor.BeanDeployment.processErrors(BeanDeployment.java:1508)
	at io.quarkus.arc.processor.BeanDeployment.init(BeanDeployment.java:320)
	at io.quarkus.arc.processor.BeanProcessor.initialize(BeanProcessor.java:160)
	at io.quarkus.arc.deployment.ArcProcessor.validate(ArcProcessor.java:488)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:568)
	at io.quarkus.deployment.ExtensionLoader$3.execute(ExtensionLoader.java:849)
	at io.quarkus.builder.BuildContext.run(BuildContext.java:256)
	at org.jboss.threads.ContextHandler$1.runWith(ContextHandler.java:18)
	at org.jboss.threads.EnhancedQueueExecutor$Task.doRunWith(EnhancedQueueExecutor.java:2516)
	at org.jboss.threads.EnhancedQueueExecutor$Task.run(EnhancedQueueExecutor.java:2495)
	at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.run(EnhancedQueueExecutor.java:1521)
	at java.base/java.lang.Thread.run(Thread.java:833)
	at org.jboss.threads.JBossThread.run(JBossThread.java:483)
```