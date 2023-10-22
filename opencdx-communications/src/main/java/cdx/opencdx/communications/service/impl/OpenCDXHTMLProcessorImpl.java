/*
 * Copyright 2023 Safe Health Systems, Inc.
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
package cdx.opencdx.communications.service.impl;

import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.communications.service.OpenCDXHTMLProcessor;
import java.util.Map;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.StringTemplateResolver;

/**
 * Service for processing HTML templates.
 */
public class OpenCDXHTMLProcessorImpl implements OpenCDXHTMLProcessor {

    private TemplateEngine templateEngine;
    private StringTemplateResolver templateResolver;

    /**
     * Constructor to initialize HTML Processor with Thymeleaf template engine.
     *
     */
    public OpenCDXHTMLProcessorImpl() {
        this.templateEngine = new SpringTemplateEngine();
        this.templateResolver = new StringTemplateResolver();
        templateEngine.setTemplateResolver(templateResolver);
    }

    /**
     * Method to perform the HTML processing
     *
     * @return Fully process template with all substitutions
     * @throws OpenCDXNotAcceptable Thrown if variable substitution fails for missing
     *                              key/value.
     */
    @Override
    public String processHTML(String template, Map<String, Object> variables) throws OpenCDXNotAcceptable {
        Context context = new Context();
        context.setVariables(variables);
        return templateEngine.process(template, context);
    }
}
