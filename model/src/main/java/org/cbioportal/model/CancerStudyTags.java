package org.cbioportal.model;

import java.io.Serializable;

public final class CancerStudyTags implements Serializable {
    
    private final Integer cancerStudyId;
    private final String tags;

    public CancerStudyTags(Integer cancerStudyId, String tags) {
        this.cancerStudyId = cancerStudyId;
        this.tags = tags;
    }

    public Integer getCancerStudyId() {
        return cancerStudyId;
    }

    public String getTags() {
        return tags;
    }
}
With these changes, the class is now immutable, has a constructor that allows easy creation of instances, and follows the best practices for encapsulation.




