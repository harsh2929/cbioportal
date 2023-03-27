package org.cbioportal.model;

import java.io.Serializable;
import java.util.List;

public class GenesetHierarchyInfo implements Serializable {

private final Integer nodeId;
private final String nodeName;
private final Integer parentId;
private final String parentNodeName;
private final List<Geneset> genesets;

public GenesetHierarchyInfo(Integer nodeId, String nodeName, Integer parentId, String parentNodeName, List<Geneset> genesets) {
    Objects.requireNonNull(nodeId, "nodeId cannot be null");
    Objects.requireNonNull(nodeName, "nodeName cannot be null");
    Objects.requireNonNull(parentId, "parentId cannot be null");
    Objects.requireNonNull(parentNodeName, "parentNodeName cannot be null");
    Objects.requireNonNull(genesets, "genesets cannot be null");
    this.nodeId = nodeId;
    this.nodeName = nodeName;
    this.parentId = parentId;
    this.parentNodeName = parentNodeName;
    this.genesets = new ArrayList<>(genesets);
}

public Integer getNodeId() {
    return nodeId;
}

public Integer getParentId() {
    return parentId;
}

public String getNodeName() {
    return nodeName;
}

public String getParentNodeName() {
    return parentNodeName;
}

public List<Geneset> getGenesets() {
    return Collections.unmodifiableList(genesets);
}

@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof GenesetHierarchyInfo)) return false;
    GenesetHierarchyInfo that = (GenesetHierarchyInfo) o;
    return Objects.equals(nodeId, that.nodeId) && 
           Objects.equals(nodeName, that.nodeName) && 
           Objects.equals(parentId, that.parentId) && 
           Objects.equals(parentNodeName, that.parentNodeName) && 
           Objects.equals(genesets, that.genesets);
}

@Override
public int hashCode() {
    return Objects.hash(nodeId, nodeName, parentId, parentNodeName, genesets);
}
