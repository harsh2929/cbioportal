package org.cbioportal.model;

/**
 * Represents data that can be binned, such as clinical or custom data.
 */
public interface BinningData {
    
 
    String getAttributeId();
 
    String getAttributeValue();

    SampleId getSampleId();

    PatientId getPatientId();
    

    StudyId getStudyId();
    
    boolean isPatientAttribute();
}
