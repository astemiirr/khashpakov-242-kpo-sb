package storage.web.dtos;

import lombok.Builder;

@Builder
public record FileResponse(
        Integer id,

        String name,

        String path,

        String hash
) {}