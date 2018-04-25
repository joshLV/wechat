package com.bingosoft.models.csf;

import java.util.List;

import lombok.Data;

@Data
public class PackageParamsInputDto {
  private String userMobile;
  private List<sCampaignsOrderPackageInputDto> packageList;
  private CrmpfPubInfoInputDto crmpfPubInfo;
}
