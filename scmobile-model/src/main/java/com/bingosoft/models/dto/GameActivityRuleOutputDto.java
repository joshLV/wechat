package com.bingosoft.models.dto;

import java.util.Date;

import lombok.Data;

@Data
public class GameActivityRuleOutputDto {
   private long ruleId;
   private Date gameStart;
   private Date gameEnd;
   private String gameUrl;
}
