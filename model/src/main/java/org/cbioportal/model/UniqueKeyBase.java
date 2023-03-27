package org.cbioportal.model;

import java.io.Serializable;
private final String uniqueSampleKey;
private final String uniquePatientKey;

public UniqueKeyBase(String uniqueSampleKey, String uniquePatientKey) {
    Objects.requireNonNull(uniqueSampleKey, "uniqueSampleKey cannot be null");
    Objects.requireNonNull(uniquePatientKey, "uniquePatientKey cannot be null");
    this.uniqueSampleKey = uniqueSampleKey;
    this.uniquePatientKey = uniquePatientKey;
}

public String getUniqueSampleKey() {
    return uniqueSampleKey;
}

public String getUniquePatientKey() {
    return uniquePatientKey;
}

@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UniqueKeyBase)) return false;
    UniqueKeyBase that = (UniqueKeyBase) o;
    return Objects.equals(uniqueSampleKey, that.uniqueSampleKey) && 
           Objects.equals(uniquePatientKey, that.uniquePatientKey);
}

@Override
public int hashCode() {
    return Objects.hash(uniqueSampleKey, uniquePatientKey);
}
