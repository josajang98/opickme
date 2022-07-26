package com.ssafy.api.controller;

import com.ssafy.api.request.FeedbackRegisterPostReq;
import com.ssafy.api.service.FeedbackService;
import com.ssafy.api.service.ScriptService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Feedback;
import com.ssafy.db.entity.Script;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api/v1/feedback")
@RequiredArgsConstructor
public class FeedbackController {
    private final FeedbackService feedbackService;
    private final ScriptService scriptService;

    @PostMapping("/")
    public ResponseEntity<BaseResponseBody> create(@RequestBody FeedbackRegisterPostReq feedbackRegisterPostReq){
        Long scriptId = feedbackRegisterPostReq.getScriptId();
        String content = feedbackRegisterPostReq.getContent();

        Optional<Script> script = scriptService.getScriptByScriptId(scriptId);
        boolean isOverlap = feedbackService.overlapCheck(scriptId);
        if (isOverlap)
            return ResponseEntity.status(404).body(BaseResponseBody.of(500,"이미 피드백이 진행된 스크립트입니다."));
        if (script.isPresent()){
            feedbackService.create(content,script.get());
            return ResponseEntity.status(201).body(BaseResponseBody.of(201,"피드백 추가 성공"));
        }
        return ResponseEntity.status(404).body(BaseResponseBody.of(404,"존재하지 않는 스크립트 입니다."));
    }

    @GetMapping("/")
    public List<Feedback> getList(){
        return feedbackService.getList();
    }

    @GetMapping("/{feedbackId}")
    public Feedback getDetail(@PathVariable Long feedbackId){
        return feedbackService.getDetail(feedbackId).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 피드백이 없습니다. id : " + feedbackId);
            }
        });
    }

    @DeleteMapping("/{feedbackId}")
    public ResponseEntity<BaseResponseBody> delete(@PathVariable Long feedbackId){
        BaseResponseBody responseBody = feedbackService.delete(feedbackId);
        Integer statusCode = responseBody.getStatusCode();
        return ResponseEntity.status(statusCode).body(responseBody);
    }
}