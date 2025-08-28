package com.hover.project.kycData.impl;

import com.hover.project.kycData.dao.KycDataDao;
import com.hover.project.kycData.dto.KycDataCreateDto;
import com.hover.project.kycData.dto.KycDataDto;
import com.hover.project.kycData.entity.KycData;
import com.hover.project.kycData.service.KycDataService;
import com.hover.project.util.type.ApiResponse;
import com.hover.project.util.type.HttpStatusCode;
import com.hover.project.util.type.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class KycDataServiceImpl implements KycDataService {

    @Autowired
    private KycDataDao kycDataDao;

    @Override
    public ApiResponse<PageResult<List<KycDataDto>>> getKycData(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<KycData> kycPage = kycDataDao.findAll(pageable);
        System.out.println(kycPage.getContent());

        Map<String, List<KycDataDto>> data = Map.of(
                "kycRequestList",
                kycPage.getContent().stream()
                        .map(KycDataDto::mapToDto)
                        .toList());

        PageResult<List<KycDataDto>> pageResult = new PageResult<>(
                kycPage.getTotalElements(),
                kycPage.getTotalPages(),
                kycPage.getNumber(),
                data);

        return new ApiResponse<>(HttpStatusCode.OK, pageResult);
    }

    @Override
    public ApiResponse<UUID> addKycData(KycDataCreateDto kycDataCreateDto) {

        var kycData = KycDataCreateDto.mapToEntity(kycDataCreateDto);
        System.out.println(kycData);

        var addedKycData = kycDataDao.save(kycData);

        return new ApiResponse<>(HttpStatusCode.CREATED, addedKycData.getId());
    }
}
