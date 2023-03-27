package org.cbioportal.model;

import javax.validation.constraints.NotNull;

public class ClinicalData extends UniqueKeyBase implements Binnable {
    private final int internalId;
    private final String sampleId;
    private final String patientId;
    private final String studyId;
    private final String attrId;
    private final String attrValue;
    private final ClinicalAttribute clinicalAttribute;

    public ClinicalData(int internalId, String sampleId, String patientId, String studyId, String attrId,
            String attrValue, ClinicalAttribute clinicalAttribute) {
        this.internalId = internalId;
        this.sampleId = sampleId;
        this.patientId = patientId;
        this.studyId = studyId;
        this.attrId = attrId;
        this.attrValue = attrValue;
        this.clinicalAttribute = clinicalAttribute;
    }

    public int getInternalId() {
        return internalId;
    }

    public String getSampleId() {
        return sampleId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getStudyId() {
        return studyId;
    }

    public String getAttrId() {
        return attrId;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public ClinicalAttribute getClinicalAttribute() {
        return clinicalAttribute;
    }
}
