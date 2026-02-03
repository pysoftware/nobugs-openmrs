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
public class EncounterCreateForNewVisitRequest extends BaseModel {
    private LocalDateTime encounterDatetime;
    private String patient;
    private String encounterType;
    private String location;
    private List<EncounterProvider> encounterProviders;
    private Visit visit;
}
