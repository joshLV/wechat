package com.bingosoft.models.dto;

import lombok.Data;

@Data
public class TemplateMessageInputDto {
  private String touser;
  private String template_id;
  private String url;
  private TemplateData data;
}
