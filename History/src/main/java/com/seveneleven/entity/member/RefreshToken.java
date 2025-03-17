package com.seveneleven.entity.member;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24 * 14)
public class RefreshToken {

    @Id
    private String refreshToken;

    private String userId;

    @TimeToLive
    private Long ttl;

    @Builder
    public RefreshToken(String refreshToken, String userId, Long ttl) {
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.ttl = ttl;
    }

}
