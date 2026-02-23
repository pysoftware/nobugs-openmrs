package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Search, Create, Update
public class EncounterResponse extends BaseModel {
    private String uuid;
    private String display;
    private OffsetDateTime encounterDatetime;
    private Patient patient;
    private EncounterLocation location;
    private EncounterForm form;
    private EncounterType encounterType;
    private List<EncounterObs> obs;
    private List<Object> orders;
    private Boolean voided;
    private EncounterVisit visit;
    private List<String> encounterProviders;
    private List<Object> diagnoses;
    private List<EncounterLink> links;
    private String resourceVersion;
}
