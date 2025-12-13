package analysis.web.dtos;

public record StatisticResponse(
        Integer fileId,

        Integer paragraphs,

        Integer words,

        Integer symbols
) {}