package cdx.opencdx.tinkar.service.impl;

import cdx.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import cdx.opencdx.tinkar.service.TinkarPrimitive;
import dev.ikm.tinkar.common.id.PublicId;
import dev.ikm.tinkar.common.service.*;
import dev.ikm.tinkar.provider.search.Searcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@Slf4j
@ExcludeFromJacocoGeneratedReport
public class TinkarPrimitiveImpl implements TinkarPrimitive {

    private static final String ARRAY_STORE_TO_OPEN = "Open SpinedArrayStore";

    @Override
    public PrimitiveDataSearchResult[] search(String str, Integer i) throws Exception {
        return PrimitiveData.get().search(str, i);
    }

    @Override
    public List<PublicId> descendantsOf(PublicId parentConceptId) {
        return Searcher.descendantsOf(parentConceptId);
    }

    @Override
    public List<PublicId> childrenOf(PublicId parentConceptId) {
        return Searcher.childrenOf(parentConceptId);
    }

    @Override
    public List<PublicId> getLidrRecordSemanticsFromTestKit(PublicId testKitConceptId) {
        return Searcher.getLidrRecordSemanticsFromTestKit(testKitConceptId);
    }

    @Override
    public List<PublicId> getResultConformancesFromLidrRecord(PublicId lidrRecordConceptId) {
        return Searcher.getResultConformancesFromLidrRecord(lidrRecordConceptId);
    }

    @Override
    public List<PublicId> getAllowedResultsFromResultConformance(PublicId resultConformanceConceptId) {
        return Searcher.getAllowedResultsFromResultConformance(resultConformanceConceptId);
    }

    @Override
    public void initializePrimitiveData(String pathParent, String pathChild) {
        if (!PrimitiveData.running()) {
            CachingService.clearAll();
            ServiceProperties.set(ServiceKeys.DATA_STORE_ROOT, new File(pathParent, pathChild));
            PrimitiveData.selectControllerByName(ARRAY_STORE_TO_OPEN);
            PrimitiveData.start();
        }
    }

    @Override
    public List<String> descriptionsOf(List<PublicId> conceptIds) {
        return Searcher.descriptionsOf(conceptIds);
    }
}
