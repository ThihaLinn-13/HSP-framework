package com.hover.project.kycData.service;

import com.hover.project.kycData.dto.KycDataCreateDto;
import com.hover.project.kycData.dto.KycDataDto;
import com.hover.project.util.type.ApiResponse;
import com.hover.project.util.type.PageResult;

import java.util.List;
import java.util.UUID;

public interface KycDataService {

    public ApiResponse<PageResult<List<KycDataDto>>> getKycData(int page, int size);

    public ApiResponse<UUID> addKycData(KycDataCreateDto kycDataCreateDto);

}
