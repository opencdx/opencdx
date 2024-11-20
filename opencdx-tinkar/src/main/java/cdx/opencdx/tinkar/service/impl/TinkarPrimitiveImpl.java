/*
 * Copyright 2024 Safe Health Systems, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cdx.opencdx.tinkar.service.impl;

import cdx.opencdx.commons.exceptions.OpenCDXBadRequest;
import cdx.opencdx.tinkar.service.TinkarPrimitive;
import dev.ikm.tinkar.common.id.PublicId;
import dev.ikm.tinkar.common.service.*;
import dev.ikm.tinkar.coordinate.Calculators;
import dev.ikm.tinkar.coordinate.navigation.calculator.NavigationCalculator;
import dev.ikm.tinkar.coordinate.stamp.calculator.Latest;
import dev.ikm.tinkar.coordinate.stamp.calculator.LatestVersionSearchResult;
import dev.ikm.tinkar.coordinate.stamp.calculator.StampCalculatorWithCache;
import dev.ikm.tinkar.coordinate.view.calculator.ViewCalculator;
import dev.ikm.tinkar.entity.EntityService;
import dev.ikm.tinkar.entity.PatternEntity;
import dev.ikm.tinkar.entity.PatternEntityVersion;
import dev.ikm.tinkar.provider.search.Searcher;
import dev.ikm.tinkar.terms.EntityProxy;
import dev.ikm.tinkar.terms.TinkarTerm;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.LongConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * The TinkarPrimitiveImpl class provides methods for performing various operations
 * on Tinkar primitive data.
 */
@Service
@Slf4j
public class TinkarPrimitiveImpl implements TinkarPrimitive, AutoCloseable {

    /**
     * The name of the array store that should be opened.
     * This variable is a constant of type String, and it is private and static.
     */
    private static final String ARRAY_STORE_TO_OPEN = "Open SpinedArrayStore";

    /**
     * Default Constructor
     * @param pathParent Parent path
     * @param pathChild Child path
     */
    public TinkarPrimitiveImpl(
            @Value("${data.path.parent}") String pathParent, @Value("${data.path.child}") String pathChild) {
        log.info("Creating IKM Interface: pathParent={}, pathChild={}", pathParent, pathChild);
        if (!PrimitiveData.running()) {
            log.debug("Initializing Primitive Data");
            CachingService.clearAll();
            log.debug("Cleared all caches");
            ServiceProperties.set(ServiceKeys.DATA_STORE_ROOT, new File(pathParent, pathChild));
            log.debug("Set data store root");
            PrimitiveData.selectControllerByName(ARRAY_STORE_TO_OPEN);
            log.debug("Selected controller by name");
            PrimitiveData.start();
            log.debug("Primitive data started");
        }
    }

    @Override
    public void close() {
        log.info("Closing IKM Interface");
        if (PrimitiveData.running()) {
            log.debug("Stopping Primitive Data");
            PrimitiveData.stop();
            log.debug("Primitive data stopped");
        }
    }

    /**
     * Returns a list of descendant concept IDs of the given parent concept ID.
     *
     * @param parentConceptId The parent concept ID.
     * @return A list of descendant concept IDs.
     */
    @Override
    public List<PublicId> descendantsOf(PublicId parentConceptId) {
        List<PublicId> descendants = new ArrayList<>();

        EntityProxy.Concept concept = EntityProxy.Concept.make(parentConceptId);
        NavigationCalculator navigationCalculator = Calculators.View.Default().navigationCalculator();

        navigationCalculator
                .descendentsOf(concept)
                .forEach(descendant -> descendants.add(PrimitiveData.publicId(descendant)));

        if (log.isInfoEnabled()) {
            log.debug(
                    "Descendants of ID: {}, Description: {}",
                    parentConceptId.asUuidArray()[0],
                    this.descriptionsOf(Collections.singletonList(parentConceptId))
                            .getFirst());
            descendants.forEach(descendant -> {
                List<String> strings = this.descriptionsOf(Collections.singletonList(descendant));
                log.debug("Descendant ID: {}, Description: {}", descendant.asUuidArray()[0], strings.getFirst());
            });
        }

        return descendants;
    }

    @Override
    public List<PublicId> parentsOf(PublicId conceptId) {
        List<PublicId> parents = new ArrayList<>();

        EntityProxy.Concept concept = EntityProxy.Concept.make(conceptId);
        NavigationCalculator navigationCalculator = Calculators.View.Default().navigationCalculator();

        navigationCalculator.parentsOf(concept).forEach(parent -> parents.add(PrimitiveData.publicId(parent)));

        if (log.isDebugEnabled()) {
            log.debug(
                    "Parents of ID: {}, Description: {}",
                    conceptId.asUuidArray()[0],
                    this.descriptionsOf(Collections.singletonList(conceptId)).getFirst());
            parents.forEach(parent -> {
                List<String> strings = this.descriptionsOf(Collections.singletonList(parent));
                log.debug("Parent ID: {}, Description: {}", parent.asUuidArray()[0], strings.getFirst());
            });
        }

        return parents;
    }

    @Override
    public List<PublicId> ancestorOf(PublicId conceptId) {
        List<PublicId> ancestors = new ArrayList<>();

        EntityProxy.Concept concept = EntityProxy.Concept.make(conceptId);
        NavigationCalculator navigationCalculator = Calculators.View.Default().navigationCalculator();

        navigationCalculator.ancestorsOf(concept).forEach(parent -> ancestors.add(PrimitiveData.publicId(parent)));

        if (log.isDebugEnabled()) {
            log.debug(
                    "Parents of ID: {}, Description: {}",
                    conceptId.asUuidArray()[0],
                    this.descriptionsOf(Collections.singletonList(conceptId)).getFirst());
            ancestors.forEach(ancestor -> {
                List<String> strings = this.descriptionsOf(Collections.singletonList(ancestor));
                log.debug("Parent ID: {}, Description: {}", ancestor.asUuidArray()[0], strings.getFirst());
            });
        }

        return ancestors;
    }

    /**
     * Returns a list of child PublicIds of the given parent PublicId.
     *
     * @param parentConceptId the parent PublicId
     * @return a list of child PublicIds
     */
    @Override
    public List<PublicId> childrenOf(PublicId parentConceptId) {
        List<PublicId> children = new ArrayList<>();
        EntityProxy.Concept concept = EntityProxy.Concept.make(parentConceptId);
        NavigationCalculator navigationCalculator = Calculators.View.Default().navigationCalculator();

        navigationCalculator.childrenOf(concept).forEach(parent -> children.add(PrimitiveData.publicId(parent)));

        if (log.isInfoEnabled()) {
            log.debug(
                    "Children of ID: {}, Description: {}",
                    parentConceptId.asUuidArray()[0],
                    this.descriptionsOf(Collections.singletonList(parentConceptId))
                            .getFirst());
            children.forEach(child -> {
                List<String> strings = this.descriptionsOf(Collections.singletonList(child));
                log.debug("Child ID: {}, Description: {}", child.asUuidArray()[0], strings.getFirst());
            });
        }

        return children;
    }

    /**
     * Determines the list of PublicId objects to which the given member belongs.
     *
     * @param member the PublicId object whose group memberships are to be found
     * @return a list of PublicId objects representing the groups to which the specified member belongs
     */
    @Override
    public List<PublicId> memberOf(PublicId member) {
        ArrayList<PublicId> memberOfList = new ArrayList<>();

        StampCalculatorWithCache stampCalc = Calculators.Stamp.DevelopmentLatest();
        if (PrimitiveData.get().hasPublicId(member)) {
            EntityService.get().getEntity(member.asUuidArray()).ifPresent(entity -> {
                if (entity instanceof PatternEntity<?> patternEntity) {
                    EntityService.get().forEachSemanticOfPattern(patternEntity.nid(), semanticEntityOfPattern -> {
                        if (stampCalc.latest(semanticEntityOfPattern).get().active()) {
                            memberOfList.add(semanticEntityOfPattern
                                    .referencedComponent()
                                    .publicId());
                        }
                    });
                }
            });
        }

        if (log.isDebugEnabled()) {
            log.debug(
                    "Members for Member ID: {}, Description: {}",
                    member.asUuidArray()[0],
                    this.descriptionsOf(Collections.singletonList(member)).getFirst());
            memberOfList.forEach(memberOf -> {
                List<String> strings = this.descriptionsOf(Collections.singletonList(memberOf));
                log.debug("Member ID: {}, Description: {}", memberOf.asUuidArray()[0], strings.getFirst());
            });
        }

        return memberOfList;
    }

    /**
     * Retrieves a list of Lidr record semantics from a test kit with the given testKitConceptId.
     *
     * @param testKitConceptId The concept ID of the test kit.
     * @return A list of PublicIds representing the Lidr record semantics.
     */
    @Override
    public List<PublicId> getLidrRecordSemanticsFromTestKit(PublicId testKitConceptId) {
        return Searcher.getLidrRecordSemanticsFromTestKit(testKitConceptId);
    }

    /**
     * Retrieves a list of PublicIds representing the result conformances from a given LidrRecordConceptId.
     *
     * @param lidrRecordConceptId The PublicId of the LidrRecordConcept.
     * @return A list of PublicIds representing the result conformances.
     */
    @Override
    public List<PublicId> getResultConformancesFromLidrRecord(PublicId lidrRecordConceptId) {
        return Searcher.getResultConformancesFromLidrRecord(lidrRecordConceptId);
    }

    /**
     * Retrieves a list of allowed results from a result conformance concept ID.
     *
     * @param resultConformanceConceptId The concept ID of the result conformance.
     * @return A list of public IDs representing the allowed results.
     */
    @Override
    public List<PublicId> getAllowedResultsFromResultConformance(PublicId resultConformanceConceptId) {
        return Searcher.getAllowedResultsFromResultConformance(resultConformanceConceptId);
    }

    /**
     * Retrieves the descriptions of the given concept IDs.
     *
     * @param conceptIds the list of concept IDs
     * @return the list of descriptions for the given concept IDs
     */
    @Override
    public List<String> descriptionsOf(List<PublicId> conceptIds) {
        return Searcher.descriptionsOf(conceptIds);
    }

    /**
     * Retrieves the PublicId for a given concept.
     *
     * @param concept The concept for which to retrieve the PublicId.
     * @return The PublicId of the given concept.
     */
    @Override
    public PublicId getPublicId(String concept) {
        return new PublicId() {
            @Override
            public UUID[] asUuidArray() {
                return new UUID[] {UUID.fromString(concept)};
            }

            @Override
            public int uuidCount() {
                return 1;
            }

            @Override
            public void forEach(LongConsumer longConsumer) {
                // TODO to be implemented later
            }
        };
    }

    /**
     * Retrieves the concept for a given PublicId.
     *
     * @param device The device for which to retrieve the PublicId.
     * @return The PublicId of the given device.
     */
    @Override
    public PublicId getPublicIdForDevice(String device) {

        ViewCalculator viewCalc = Calculators.View.Default();
        Latest<PatternEntityVersion> latestIdPattern =
                viewCalc.latestPatternEntityVersion(TinkarTerm.IDENTIFIER_PATTERN);
        AtomicReference<PublicId> result = new AtomicReference<>();

        try {
            EntityService.get().forEachSemanticOfPattern(TinkarTerm.IDENTIFIER_PATTERN.nid(),
                    semanticEntity -> viewCalc.latest(semanticEntity).ifPresent(latestSemanticVersion -> {
                String idValue = latestIdPattern
                        .get()
                        .getFieldWithMeaning(TinkarTerm.IDENTIFIER_VALUE, latestSemanticVersion);
                if (idValue.equals(device)) {
                    result.set(latestSemanticVersion.referencedComponent().publicId());
                }
            }));
        } catch (Exception e) {
            log.error("Encountered exception {}", e.getMessage());
        }

        if (result.get() != null) {
            return result.get();
        }

        return null;
    }

    @Override
    public List<PublicId> search(String search, int limit) throws OpenCDXBadRequest {
        try {
            return Calculators.View.Default().search(search, limit).stream()
                    .filter(item -> item.latestVersion().isPresent())
                    .map(LatestVersionSearchResult::latestVersion)
                    .map(latestVersion -> latestVersion.get().referencedComponent().publicId())
                    .toList();
        } catch (Exception e) {
            throw new OpenCDXBadRequest("TinkarPrimitiveImpl", 1, "Search Failed", e);
        }
    }
}
