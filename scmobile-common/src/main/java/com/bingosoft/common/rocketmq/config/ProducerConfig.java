package com.bingosoft.common.rocketmq.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProducerConfig {
    private String instanceName;
	private String tranInstanceName;
    private String topic;
}
