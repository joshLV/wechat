package com.bingosoft.common.rocketmq.config;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerConfig {
	private String instanceName;
    private List<String> subscribe;
}
