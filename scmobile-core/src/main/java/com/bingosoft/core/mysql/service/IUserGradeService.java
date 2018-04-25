package com.bingosoft.core.mysql.service;

import java.util.List;

import com.bingosoft.models.dto.UserGradeOutputDto;

public interface IUserGradeService {
    public List<UserGradeOutputDto> getUserGradeList();
}
