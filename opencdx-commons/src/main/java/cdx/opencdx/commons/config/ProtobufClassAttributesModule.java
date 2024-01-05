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
package cdx.opencdx.commons.config;

import cdx.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedClassResolver;
import com.fasterxml.jackson.databind.introspect.BasicBeanDescription;
import com.fasterxml.jackson.databind.introspect.BasicClassIntrospector;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Message;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;

/**
 * Class used to register for Jackson ObjectMapper for reading protobuf generated java classes
 */
@Slf4j
@ExcludeFromJacocoGeneratedReport
public class ProtobufClassAttributesModule extends Module {

    private final Map<Class<?>, Map<String, FieldDescriptor>> cache = new ConcurrentHashMap<>();

    /**
     * Default Constructor
     */
    public ProtobufClassAttributesModule() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    @Override
    public String getModuleName() {
        return "ProtobufClassAttributesModule";
    }

    @Override
    public Version version() {
        return VersionUtil.versionFor(getClass());
    }

    @Override
    public void setupModule(SetupContext context) {

        context.setClassIntrospector(new ProtobufClassIntrospector());

        context.insertAnnotationIntrospector(annotationIntrospector);
    }

    final NopAnnotationIntrospector annotationIntrospector = new NopAnnotationIntrospector() {

        @Override
        @ExcludeFromJacocoGeneratedReport
        public VisibilityChecker<?> findAutoDetectVisibility(AnnotatedClass ac, VisibilityChecker<?> checker) {
            if (Message.class.isAssignableFrom(ac.getRawType())) {
                return checker.withGetterVisibility(Visibility.PUBLIC_ONLY).withFieldVisibility(Visibility.ANY);
            }
            return super.findAutoDetectVisibility(ac, checker);
        }

        @Override
        @ExcludeFromJacocoGeneratedReport
        public Object findNamingStrategy(AnnotatedClass ac) {
            if (!Message.class.isAssignableFrom(ac.getRawType())) {
                return super.findNamingStrategy(ac);
            }

            return new PropertyNamingStrategies.NamingBase() {
                @Override
                public String translate(String propertyName) {
                    if (propertyName.endsWith("_")) {
                        return propertyName.substring(0, propertyName.length() - 1);
                    }
                    return propertyName;
                }
            };
        }
    };

    @ExcludeFromJacocoGeneratedReport
    class ProtobufClassIntrospector extends BasicClassIntrospector {

        @Override
        public BasicBeanDescription forDeserialization(DeserializationConfig cfg, JavaType type, MixInResolver r) {
            BasicBeanDescription desc = super.forDeserialization(cfg, type, r);
            if (Message.class.isAssignableFrom(type.getRawClass())) {
                return protobufBeanDescription(cfg, type, r, desc);
            }
            return desc;
        }

        @Override
        public BasicBeanDescription forSerialization(SerializationConfig cfg, JavaType type, MixInResolver r) {
            BasicBeanDescription desc = super.forSerialization(cfg, type, r);
            if (Message.class.isAssignableFrom(type.getRawClass())) {
                return protobufBeanDescription(cfg, type, r, desc);
            }
            return desc;
        }

        private BasicBeanDescription protobufBeanDescription(
                MapperConfig<?> cfg, JavaType type, MixInResolver r, BasicBeanDescription baseDesc) {
            Map<String, FieldDescriptor> types = cache.computeIfAbsent(type.getRawClass(), this::getTypeDescriptor);

            AnnotatedClass ac = AnnotatedClassResolver.resolve(cfg, type, r);

            List<BeanPropertyDefinition> props = new ArrayList<>();

            baseDesc.findProperties().forEach(property -> {
                String name = property.getName();
                if (types.containsKey(name)) {
                    if (property.hasField()
                            && property.getField().getType().isJavaLangObject()
                            && types.get(name)
                                    .getType()
                                    .equals(com.google.protobuf.Descriptors.FieldDescriptor.Type.STRING)) {
                        addStringFormatAnnotation(property);
                    }
                    props.add(property.withSimpleName(name));
                }
            });

            return new BasicBeanDescription(cfg, type, ac, new ArrayList<>(props)) {};
        }

        @JsonFormat(shape = Shape.STRING)
        @ExcludeFromJacocoGeneratedReport
        static class AnnotationHelper {}

        private void addStringFormatAnnotation(BeanPropertyDefinition p) {
            JsonFormat jsonFormatAnnotation = AnnotationHelper.class.getAnnotation(JsonFormat.class);
            p.getField().getAllAnnotations().addIfNotPresent(jsonFormatAnnotation);
        }

        private Map<String, FieldDescriptor> getTypeDescriptor(Class<?> type) {
            try {
                Descriptor descriptor =
                        (Descriptor) type.getMethod("getDescriptor").invoke(null);
                Map<String, FieldDescriptor> typeDescriptors = new HashMap<>();
                descriptor.getFields().forEach(fieldDescriptor -> {
                    typeDescriptors.put(fieldDescriptor.getName(), fieldDescriptor);
                    typeDescriptors.put(fieldDescriptor.getJsonName(), fieldDescriptor);
                });
                return typeDescriptors;
            } catch (Exception e) {
                log.error("Error getting proto descriptor for swagger UI.", e);
                return new HashMap<>();
            }
        }
    }
}
