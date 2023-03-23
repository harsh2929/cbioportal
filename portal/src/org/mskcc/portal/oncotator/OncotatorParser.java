package org.mskcc.portal.oncotator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Parses JSON Retrieved from Oncotator.
 */
public class OncotatorParser {

    /**
     * Parses the JSON returned by the oncotator web service, and returns
     * the information as a new OncotateRecord instance.
     *
     * @param key  chr#_start_end_allele1_allele2
     * @param json JSON object returned by the web service
     * @return new OncotatorRecord, or null if JSON has an error
     * @throws JsonProcessingException if there is an error parsing the JSON
     */
    public static OncotatorRecord parseJSON(String key, String json) throws JsonProcessingException {
        try (ObjectMapper mapper = new ObjectMapper()) {
            JsonNode rootNode = mapper.readTree(json);

            OncotatorRecord oncotator = new OncotatorRecord(key);

            // check if JSON has an ERROR

            JsonNode errorNode = rootNode.path("ERROR");
            if (!errorNode.isNull()) {
                System.out.println("JSON parse error for " + key + ": " + errorNode.textValue());
                return null;
            }

            // proceed in case of no JSON error

            JsonNode genomeChange = rootNode.path("genome_change");
            if (!genomeChange.isNull()) {
                oncotator.setGenomeChange(genomeChange.textValue());
            }

            JsonNode cosmic = rootNode.path("Cosmic_overlapping_mutations");
            if (!cosmic.isNull()) {
                oncotator.setCosmicOverlappingMutations(cosmic.textValue());
            }

            JsonNode dbSnpRs = rootNode.path("dbSNP_RS");
            if (!dbSnpRs.isNull()) {
                oncotator.setDbSnpRs(dbSnpRs.textValue());
            }

            JsonNode bestTranscriptIndexNode = rootNode.path("best_canonical_transcript");


        if (!bestTranscriptIndexNode.isMissingNode()) {
            int transcriptIndex = bestTranscriptIndexNode.getIntValue();
            JsonNode transcriptsNode = rootNode.path("transcripts");
            JsonNode bestTranscriptNode = transcriptsNode.get(transcriptIndex);

            String variantClassification = bestTranscriptNode.path("variant_classification").getTextValue();
            String proteinChange = bestTranscriptNode.path("protein_change").getTextValue();
            String geneSymbol = bestTranscriptNode.path("gene").getTextValue();
            int exonAffected = bestTranscriptNode.path("exon_affected").getIntValue();
            oncotator.setVariantClassification(variantClassification);
            oncotator.setProteinChange(proteinChange);
            oncotator.setGene(geneSymbol);
            oncotator.setExonAffected(exonAffected);
        }

        return oncotator;
    }
}
