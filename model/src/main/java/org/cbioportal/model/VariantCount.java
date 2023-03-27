package org.cbioportal.model;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

public class VariantCount implements Serializable {
    
    @NotNull
    private String molecularProfileId;
    
    @NotNull
    private int entrezGeneId;
    
    private String keyword;
    
    @NotNull
    private int numberOfSamples;
    
    @NotNull
    private int numberOfSamplesWithMutationInGene;
    
    @NotNull
    private int numberOfSamplesWithKeyword;

    public VariantCount(String molecularProfileId, int entrezGeneId, String keyword, int numberOfSamples, int numberOfSamplesWithMutationInGene, int numberOfSamplesWithKeyword) {
        this.molecularProfileId = molecularProfileId;
        this.entrezGeneId = entrezGeneId;
        this.keyword = keyword;
        this.numberOfSamples = numberOfSamples;
        this.numberOfSamplesWithMutationInGene = numberOfSamplesWithMutationInGene;
        this.numberOfSamplesWithKeyword = numberOfSamplesWithKeyword;
    }

    public String getMolecularProfileId() {
        return molecularProfileId;
    }

    public int getEntrezGeneId() {
        return entrezGeneId;
    }

    public String getKeyword() {
        return keyword;
    }

    public int getNumberOfSamples() {
        return numberOfSamples;
    }

    public int getNumberOfSamplesWithMutationInGene() {
        return numberOfSamplesWithMutationInGene;
    }

    public int getNumberOfSamplesWithKeyword() {
        return numberOfSamplesWithKeyword;
    }
}
