package com.DoAnTotNghiep.core.tour.service;

import com.DoAnTotNghiep.config.exception.BadRequestException;
import com.DoAnTotNghiep.core.tour.entity.Province;
import com.DoAnTotNghiep.core.tour.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceService {
    @Autowired
    ProvinceRepository provinceRepository;

    public List<Province> getAll() {
        return provinceRepository.findAll();
    }

    public Province findById(Long id) {
        return provinceRepository.findById(id).orElseThrow(BadRequestException::new);
    }

    public void createProvince(Province province) {
        provinceRepository.save(province);
    }

    public void updateProvince(Province province) {
        provinceRepository.save(province);
    }

    public void deleteProvince(Province province) {
        provinceRepository.deleteById(province.getId());
    }
}
