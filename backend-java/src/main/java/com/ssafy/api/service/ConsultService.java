package com.ssafy.api.service;

import com.ssafy.api.request.ConsultRegisterPostReq;
import com.ssafy.db.entity.Consult;
import com.ssafy.db.entity.Script;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface ConsultService {
    Consult create(Script script, String room);

    Map<Long, Consult> waitingConsultMap();

    boolean exist(Long consultId);

    void delete(Long consultId);
    Optional<Consult> getConsultByConsultId(Long consultId);
}