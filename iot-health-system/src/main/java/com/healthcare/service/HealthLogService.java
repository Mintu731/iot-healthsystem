package com.healthcare.service;

import com.healthcare.dto.HealthLogResponseDto;
import com.healthcare.dto.ThingSpeakFeedDto;
import com.healthcare.dto.ThingSpeakResponseDto;
import com.healthcare.entity.HealthLog;
import com.healthcare.entity.User;
import com.healthcare.repository.HealthLogRepository;
import com.healthcare.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class HealthLogService {

    private final UserRepository userRepository;
    private final HealthLogRepository healthLogRepository;

    // ✅ SINGLE CONSTRUCTOR (NO LOMBOK)
    public HealthLogService(UserRepository userRepository,
                            HealthLogRepository healthLogRepository) {
        this.userRepository = userRepository;
        this.healthLogRepository = healthLogRepository;
    }

    /* =====================================================
       SYNC DATA FROM THINGSPEAK (SAFE)
       ===================================================== */
    public int syncFromThingSpeak(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // ✅ Validate ThingSpeak credentials
        if (user.getTsChannelId() == null || user.getTsReadKey() == null) {
            return 0;
        }

        if (user.getTsReadKey().length() < 16) {
            return 0;
        }

        String url = "https://api.thingspeak.com/channels/"
                + user.getTsChannelId()
                + "/feeds.json?api_key="
                + user.getTsReadKey()
                + "&results=100";

        RestTemplate restTemplate = new RestTemplate();
        ThingSpeakResponseDto response;

        // ✅ NEVER break registration or API
        try {
            response = restTemplate.getForObject(url, ThingSpeakResponseDto.class);
        } catch (Exception e) {
            return 0;
        }

        if (response == null || response.getFeeds() == null) {
            return 0;
        }

        List<HealthLog> newLogs = new ArrayList<>();
        Long maxEntryId = user.getLastEntryId();

        for (ThingSpeakFeedDto feed : response.getFeeds()) {

            // Skip already synced entries
            if (feed.getEntry_id() <= user.getLastEntryId()) {
                continue;
            }

            // Validate sensor values
            if (!isValidFeed(feed)) {
                continue;
            }

            HealthLog log = new HealthLog();
            log.setEntryId(feed.getEntry_id());
            log.setBpm(parseDouble(feed.getField1()));
            log.setAvgBpm(parseDouble(feed.getField2()));
            log.setTemperature(parseDouble(feed.getField6()));
            log.setHumidity(parseDouble(feed.getField7()));
            log.setUser(user);

            newLogs.add(log);
            maxEntryId = feed.getEntry_id();
        }

        if (!newLogs.isEmpty()) {
            healthLogRepository.saveAll(newLogs);
            user.setLastEntryId(maxEntryId);
            userRepository.save(user);
        }

        return newLogs.size();
    }


    /* =====================================================
       PATIENT VIEW – DTO (NO USER OBJECT)
       ===================================================== */
    public List<HealthLogResponseDto> getMyLogs(Long userId) {

        return healthLogRepository.findByUserId(userId)
                .stream()
                .map(log -> {
                    HealthLogResponseDto dto = new HealthLogResponseDto();
                    dto.setId(log.getId());
                    dto.setEntryId(log.getEntryId());
                    dto.setBpm(log.getBpm());
                    dto.setAvgBpm(log.getAvgBpm());
                    dto.setTemperature(log.getTemperature());
                    dto.setHumidity(log.getHumidity());
                    return dto;
                })
                .toList();
    }

    /* =====================================================
       ADMIN VIEW
       ===================================================== */
    public List<HealthLog> getPatientLogs(Long adminId, Long userId) {

        if (adminId != 1) {
            throw new IllegalStateException("Access denied");
        }

        return healthLogRepository.findByUserId(userId);
    }

    /* =====================================================
       HELPERS
       ===================================================== */
    private Double parseDouble(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return Double.parseDouble(value);
    }

    private boolean isValidFeed(ThingSpeakFeedDto feed) {

        Double bpm = parseDouble(feed.getField1());
        Double temperature = parseDouble(feed.getField6());
        Double humidity = parseDouble(feed.getField7());

        if (bpm == null || bpm <= 0) return false;
        if (temperature == null || humidity == null) return false;

        if (bpm < 30 || bpm > 220) return false;
        if (temperature < 20 || temperature > 45) return false;
        if (humidity < 0 || humidity > 100) return false;

        return true;
    }
}
