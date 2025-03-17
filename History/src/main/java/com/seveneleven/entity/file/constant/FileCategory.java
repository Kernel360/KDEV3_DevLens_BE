package com.seveneleven.entity.file.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum FileCategory {
    COMPANY_LOGO_IMAGE(
            Set.of(".png", ".jpg", ".jpeg", ".gif", ".webp"),
            List.of("image/png", "image/apng", "image/jpeg", "image/gif", "image/webp")
    ),
    USER_PROFILE_IMAGE(
            Set.of(".png", ".jpg", ".jpeg", ".gif", ".webp"),
            List.of("image/png", "image/apng", "image/jpeg", "image/gif", "image/webp")
    ),
    PROJECT_IMAGE(
            Set.of(".png", ".jpg", ".jpeg", ".gif", ".webp"),
            List.of("image/png", "image/apng", "image/jpeg", "image/gif", "image/webp")
    ),
    POST_ATTACHMENT(
            Set.of(".png", ".jpg", ".jpeg", ".gif", ".webp",
                    ".pdf", ".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".txt",
                    ".mp4", ".avi", ".mov", ".wmv", ".flv", ".webm", ".mkv", ".mpeg", ".3gp",
                    ".mp3", ".wav", ".aac", ".flac", ".ogg", ".wma", ".m4a", ".opus",
                    ".zip", ".7z", ".rar", ".tar"),
            List.of(
                    "image/png", "image/jpeg", "image/gif", "image/webp",
                    "application/pdf", "application/msword",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.template",
                    "application/vnd.ms-word.document.macroEnabled.12",
                    "application/vnd.ms-excel",
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    "application/vnd.ms-excel.sheet.macroEnabled.12",
                    "application/vnd.ms-powerpoint",
                    "application/vnd.openxmlformats-officedocument.presentationml.presentation",
                    "application/vnd.ms-powerpoint.presentation.macroEnabled.12",
                    "text/plain",
                    "video/mp4", "video/x-msvideo", "video/quicktime", "video/x-ms-wmv",
                    "video/x-flv", "video/webm", "video/x-matroska", "video/mpeg", "video/3gpp",
                    "audio/mpeg", "audio/mp3", "audio/wav", "audio/x-wav", "audio/aac",
                    "audio/flac", "audio/ogg", "audio/x-ms-wma", "audio/mp4", "audio/opus",
                    "application/zip", "application/x-7z-compressed",
                    "application/x-rar-compressed", "application/vnd.rar",
                    "application/x-tar", "application/tar", "application/gzip"
            )
    ),
    CHECK_REQUEST_ATTACHMENT(
            Set.of(".png", ".jpg", ".jpeg", ".gif", ".webp",
                    ".pdf", ".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".txt",
                    ".mp4", ".avi", ".mov", ".wmv", ".flv", ".webm", ".mkv", ".mpeg", ".3gp",
                    ".mp3", ".wav", ".aac", ".flac", ".ogg", ".wma", ".m4a", ".opus",
                    ".zip", ".7z", ".rar", ".tar"),
            List.of(
                    "image/png", "image/jpeg", "image/gif", "image/webp",
                    "application/pdf", "application/msword",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.template",
                    "application/vnd.ms-word.document.macroEnabled.12",
                    "application/vnd.ms-excel",
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    "application/vnd.ms-excel.sheet.macroEnabled.12",
                    "application/vnd.ms-powerpoint",
                    "application/vnd.openxmlformats-officedocument.presentationml.presentation",
                    "application/vnd.ms-powerpoint.presentation.macroEnabled.12",
                    "text/plain",
                    "video/mp4", "video/x-msvideo", "video/quicktime", "video/x-ms-wmv",
                    "video/x-flv", "video/webm", "video/x-matroska", "video/mpeg", "video/3gpp",
                    "audio/mpeg", "audio/mp3", "audio/wav", "audio/x-wav", "audio/aac",
                    "audio/flac", "audio/ogg", "audio/x-ms-wma", "audio/mp4", "audio/opus",
                    "application/zip", "application/x-7z-compressed",
                    "application/x-rar-compressed", "application/vnd.rar",
                    "application/x-tar", "application/tar", "application/gzip"
            )
    ),
    CHECK_REJECTION_ATTACHMENT(
            Set.of(".png", ".jpg", ".jpeg", ".gif", ".webp",
                    ".pdf", ".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".txt",
                    ".mp4", ".avi", ".mov", ".wmv", ".flv", ".webm", ".mkv", ".mpeg", ".3gp",
                    ".mp3", ".wav", ".aac", ".flac", ".ogg", ".wma", ".m4a", ".opus",
                    ".zip", ".7z", ".rar", ".tar"),
            List.of(
                    "image/png", "image/jpeg", "image/gif", "image/webp",
                    "application/pdf", "application/msword",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.template",
                    "application/vnd.ms-word.document.macroEnabled.12",
                    "application/vnd.ms-excel",
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    "application/vnd.ms-excel.sheet.macroEnabled.12",
                    "application/vnd.ms-powerpoint",
                    "application/vnd.openxmlformats-officedocument.presentationml.presentation",
                    "application/vnd.ms-powerpoint.presentation.macroEnabled.12",
                    "text/plain",
                    "video/mp4", "video/x-msvideo", "video/quicktime", "video/x-ms-wmv",
                    "video/x-flv", "video/webm", "video/x-matroska", "video/mpeg", "video/3gpp",
                    "audio/mpeg", "audio/mp3", "audio/wav", "audio/x-wav", "audio/aac",
                    "audio/flac", "audio/ogg", "audio/x-ms-wma", "audio/mp4", "audio/opus",
                    "application/zip", "application/x-7z-compressed",
                    "application/x-rar-compressed", "application/vnd.rar",
                    "application/x-tar", "application/tar", "application/gzip"
            )
    );

    private final Set<String> allowedExtensions;
    private final List<String> allowedMimeTypes;

    public Set<String> getAllowedExtensions() {
        return allowedExtensions;
    }

    public List<String> getAllowedMimeTypes() {
        return allowedMimeTypes;
    }
}