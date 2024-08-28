package cdx.opencdx.tinkar.service;

import dev.ikm.tinkar.common.id.PublicId;
import dev.ikm.tinkar.common.service.PrimitiveDataSearchResult;

import java.util.List;

public interface TinkarPrimitive {

    PrimitiveDataSearchResult[] search(String str, Integer i) throws Exception;

    List<PublicId> descendantsOf(PublicId parentConceptId);

    List<PublicId> childrenOf(PublicId parentConceptId);

    List<PublicId> getLidrRecordSemanticsFromTestKit(PublicId testKitConceptId);

    List<PublicId> getResultConformancesFromLidrRecord(PublicId lidrRecordConceptId);

    List<PublicId> getAllowedResultsFromResultConformance(PublicId resultConformanceConceptId);

    void initializePrimitiveData(String pathParent, String pathChild);

    List<String> descriptionsOf(List<PublicId> conceptIds);
}
