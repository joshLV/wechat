package com.bingosoft.models.dto;

import lombok.Data;

@Data
public class GameShareRecordInputDto {
   private long shareId;
   private int partId;
   private int ruleId;
   private long orderId;
   private int shareModuleId;
   private String sharePage;
   private String sharePhone;
   private int shareStatus;
   private String shareNote;
}
