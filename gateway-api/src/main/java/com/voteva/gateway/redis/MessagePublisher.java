package com.voteva.gateway.redis;

import com.voteva.gateway.redis.model.Task;

public interface MessagePublisher {

    void publish(String topic, Task task);
}
