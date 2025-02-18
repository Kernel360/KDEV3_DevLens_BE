package com.seveneleven.notification.controller;

import com.seveneleven.notification.service.NotificationService;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import com.seveneleven.util.security.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    public static Map<Long, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    @GetMapping("/subscribe")
    public ResponseEntity<APIResponse<SseEmitter>> subscribe(@AuthenticationPrincipal CustomUserDetails user) {
        Long userId = user.getId();
        SseEmitter sseEmitter = notificationService.subscribe(userId);

        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, sseEmitter));
    }
}

