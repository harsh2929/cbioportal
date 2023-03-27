package org.cbioportal.model;

import java.util.HashMap;
import java.util.Map;

public enum CNA {
    AMP((short)2, "Amplified"),
    GAIN((short)1, "Gained"),
    DIPLOID((short)0, "Diploid"),
    HETLOSS((short)-1, "Heterozygously deleted"),
    HOMDEL((short)-2, "Homozygously deleted");

    private short code;
    private String desc;

    private CNA(short code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static final Map<Short, CNA> cache = new HashMap<>();

    static {
        for (CNA cna : CNA.values()) {
            cache.put(cna.code, cna);
        }
    }

    public static CNA getByCode(short code) {
        CNA cna = cache.get(code);
        if (cna == null) {
            throw new IllegalArgumentException("Invalid CNA code: " + code);
            // or you could return a default value instead of throwing an exception, e.g.
            // return DIPLOID;
        }
        return cna;
    }

    public short getCode() {
        return code;
    }

    public String getDescription() {
        return desc;
    }
}
