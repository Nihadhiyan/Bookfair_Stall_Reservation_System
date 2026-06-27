package com.bookfair.backend.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public final class RequestUtils {

    public static String getClientIpAddress(HttpServletRequest request) {
        Objects.requireNonNull(request, "HttpServletRequest cannot be null when extracting client IP");

        // Step 1: Inspect X-Forwarded-For header injected by upstream load
        // balancers/proxies
        String header = request.getHeader("X-Forwarded-For");
        if (header != null && !header.isBlank() && !"unknown".equalsIgnoreCase(header)) {
            // X-Forwarded-For can contain a comma-separated list of proxies: "clientIp,
            // proxy1, proxy2"
            // The leftmost IP is always the original originating client address.
            String[] ips = header.split(",");
            String clientIp = ips[0].trim();
            return sanitizeIp(clientIp);
        }

        // Step 2: Check X-Real-IP fallback commonly set by NGINX ingress controllers
        header = request.getHeader("X-Real-IP");
        if (header != null && !header.isBlank() && !"unknown".equalsIgnoreCase(header)) {
            return sanitizeIp(header.trim());
        }

        // Step 3: Fall back to raw socket remote address if no reverse proxy headers
        // exist
        String remoteAddr = request.getRemoteAddr();
        return sanitizeIp(remoteAddr != null ? remoteAddr : "UNKNOWN");
    }

    /**
     * Extracts client device signature from the HTTP User-Agent header.
     * <p>
     * Capturing device signature allows users to identify session origins during
     * security
     * audits (e.g., viewing active sessions showing "Mozilla/5.0 (Macintosh; Intel
     * Mac OS X...)").
     * </p>
     *
     * @param request The servlet request containing client headers; must not be
     *                null.
     * @return Truncated device info string, or "UNKNOWN_DEVICE" if missing.
     */
    public static String getDeviceInfo(HttpServletRequest request) {
        Objects.requireNonNull(request, "HttpServletRequest cannot be null when extracting device info");

        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null || userAgent.isBlank()) {
            return "UNKNOWN_DEVICE";
        }

        // Truncate length to 512 characters to prevent database column overflow
        // exceptions
        return userAgent.length() > 512 ? userAgent.substring(0, 512) : userAgent;
    }

    /**
     * Ensures extracted IP addresses fit within the database IPv6 column boundary
     * (45 characters).
     */
    private static String sanitizeIp(String ip) {
        if (ip == null) {
            return "UNKNOWN";
        }
        return ip.length() > 45 ? ip.substring(0, 45) : ip;
    }
}
