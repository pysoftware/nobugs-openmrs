package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Visit extends BaseModel {
    private String patient;
    private String visitType;
    private LocalDateTime startDatetime;
    private LocalDateTime stopDatetime;
}
