package pure.fsm.hazelcast.serialization;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.hazelcast.core.HazelcastInstance;
import pure.fsm.hazelcast.resource.DistributedLockResource;
import pure.fsm.hazelcast.resource.DistributedLockResourceDeserializer;
import pure.fsm.hazelcast.resource.DistributedLockResourceSerializer;

public class DistributedLockModule extends SimpleModule {

    public DistributedLockModule(HazelcastInstance hazelcastInstance) {
        addSerializer(DistributedLockResource.class, new DistributedLockResourceSerializer());
        addDeserializer(DistributedLockResource.class, new DistributedLockResourceDeserializer(hazelcastInstance));
    }
}