package com.DoAnTotNghiep.core.tour.service;

import com.DoAnTotNghiep.config.exception.BadRequestException;
import com.DoAnTotNghiep.core.tour.entity.MaxSenderBatchId;
import com.DoAnTotNghiep.core.tour.repository.MaxSenderBatchIdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaxSenderBatchIdService {
    
    @Autowired
    MaxSenderBatchIdRepository maxSenderBatchIdRepository;

    public List<MaxSenderBatchId> getAll() {
        return maxSenderBatchIdRepository.findAll();
    }

    public MaxSenderBatchId findById(Long id) {
        return maxSenderBatchIdRepository.findById(id).orElseThrow(BadRequestException::new);
    }

    public void createMaxSenderBatchId(MaxSenderBatchId maxSenderBatchId) {
        maxSenderBatchIdRepository.save(maxSenderBatchId);
    }

    public void updateMaxSenderBatchId(MaxSenderBatchId maxSenderBatchId) {
        maxSenderBatchIdRepository.save(maxSenderBatchId);
    }

    public void deleteMaxSenderBatchId(MaxSenderBatchId maxSenderBatchId) {
        maxSenderBatchIdRepository.deleteById(maxSenderBatchId.getId());
    }
}
