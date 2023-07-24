package org.jetlinks.community.notify.manager.subscriber.providers;

import lombok.extern.slf4j.Slf4j;
import org.hswebframework.web.authorization.Authentication;
import org.jetlinks.core.event.EventBus;
import org.jetlinks.core.metadata.PropertyMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.StringType;
import org.jetlinks.community.notify.manager.subscriber.Subscriber;
import org.jetlinks.community.topic.Topics;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@Slf4j
public class AlarmOrgProvider extends AlarmProvider {

    public AlarmOrgProvider(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    public String getId() {
        return "alarm-org";
    }

    @Override
    public String getName() {
        return "部门告警";
    }

    @Override
    public Mono<Subscriber> createSubscriber(String id, Authentication authentication, Map<String, Object> config) {
        String topic = Topics.alarm(TargetType.org.name(), "*", getAlarmId(config));
        return doCreateSubscriber(id, authentication, topic);
    }

    @Override
    public Flux<PropertyMetadata> getDetailProperties(Map<String, Object> config) {
        return super.getDetailProperties(config)
            .concatWith(Flux.just(
                SimplePropertyMetadata.of("targetId", "组织ID", StringType.GLOBAL),
                SimplePropertyMetadata.of("targetName", "组织名称", StringType.GLOBAL)
            ));
    }

}
